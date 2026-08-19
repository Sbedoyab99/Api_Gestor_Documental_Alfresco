# Épica 2 — Gestión de Sites/proyectos y miembros

## Objetivo

Representar cada proyecto como un Site de Alfresco, administrar su ciclo de vida y sus participantes, y resolver su Document Library antes de realizar operaciones de carpetas o documentos.

## Modelo de roles

La API acepta los roles de Alfresco `SiteManager`, `SiteCollaborator`, `SiteContributor` y `SiteConsumer`. El identificador del Site (`siteId`) es estable y distinto de su título visible.

---

## HU-25 — Crear Site/proyecto

**Como** administrador  
**quiero** crear un proyecto como Site de Alfresco  
**para** disponer de un espacio documental independiente.

### Endpoint

```http
POST /api/projects
```

```json
{
  "id": "migracion-erp",
  "title": "Migración ERP",
  "description": "Documentación del proyecto",
  "visibility": "PRIVATE"
}
```

### Criterios de aceptación

- `id` y `title` son obligatorios; `id` cumple el formato definido por la API.
- La visibilidad permitida es `PUBLIC`, `MODERATED` o `PRIVATE`.
- La API crea el Site en Alfresco y devuelve `201 Created`, su identificador y ubicación.
- Un identificador duplicado devuelve `409 Conflict`.
- La respuesta no depende de que la Document Library ya haya sido consultada.

---

## HU-26 — Listar Sites/proyectos

**Como** usuario  
**quiero** listar los proyectos disponibles  
**para** seleccionar el ámbito documental en el que trabajaré.

### Endpoint

```http
GET /api/projects?page=0&size=20
```

### Criterios de aceptación

- Devuelve proyectos paginados con `id`, título, descripción y visibilidad.
- Respeta los Sites que el usuario autenticado puede consultar.
- Permite ordenar de forma estable y valida los límites de paginación.
- Una colección vacía devuelve `200 OK`.

---

## HU-27 — Consultar detalle de un Site/proyecto

**Como** usuario  
**quiero** consultar un proyecto específico  
**para** conocer su configuración.

### Endpoint

```http
GET /api/projects/{projectId}
```

### Criterios de aceptación

- Devuelve los datos normalizados del Site solicitado.
- Un Site inexistente devuelve `404 Not Found`.
- Un usuario sin acceso recibe una respuesta coherente con la política de seguridad, sin filtrar información sensible.

---

## HU-28 — Actualizar Site/proyecto

**Como** administrador del proyecto  
**quiero** actualizar sus datos  
**para** mantener vigente su información.

### Endpoint

```http
PATCH /api/projects/{projectId}
```

```json
{
  "title": "Migración ERP 2027",
  "description": "Alcance actualizado",
  "visibility": "PRIVATE"
}
```

### Criterios de aceptación

- Permite modificar título, descripción y visibilidad; el `projectId` permanece inmutable.
- Solo se modifican los campos enviados.
- Valida valores vacíos y visibilidad.
- Devuelve `404` si el Site no existe y `403` si el actor no puede administrarlo.

---

## HU-29 — Eliminar Site/proyecto

**Como** administrador  
**quiero** eliminar un proyecto  
**para** retirar un espacio que dejó de utilizarse.

### Endpoint

```http
DELETE /api/projects/{projectId}
```

### Criterios de aceptación

- Solo un actor autorizado puede eliminar el Site.
- La API requiere confirmación explícita mediante `confirm=true` si el Site contiene documentos.
- La eliminación exitosa devuelve `204 No Content`.
- Un Site inexistente devuelve `404`; los conflictos de Alfresco se traducen al formato de error de la API.
- La documentación advierte que la operación afecta contenedores, carpetas y documentos del proyecto.

---

## HU-30 — Obtener Document Library

**Como** usuario  
**quiero** obtener la biblioteca documental del proyecto  
**para** usar su nodo raíz en las operaciones de contenido.

### Endpoint

```http
GET /api/projects/{projectId}/document-library
```

### Respuesta de ejemplo

```json
{
  "projectId": "migracion-erp",
  "containerId": "documentLibrary",
  "nodeId": "8f2c...",
  "name": "documentLibrary"
}
```

### Criterios de aceptación

- La API consulta el contenedor `documentLibrary` del Site y devuelve el `nodeId` requerido por Alfresco.
- Nunca acepta un nodo de otro Site como biblioteca del proyecto.
- Devuelve `404` si el Site o el contenedor no existen.
- Las épicas de carpetas y documentos usan esta resolución para delimitar el proyecto.

---

## HU-31 — Agregar miembro

**Como** administrador del proyecto  
**quiero** agregar un usuario con un rol  
**para** darle acceso al Site.

### Endpoint

```http
POST /api/projects/{projectId}/members
```

```json
{ "userId": "santiago", "role": "SiteCollaborator" }
```

### Criterios de aceptación

- `userId` y `role` son obligatorios y el rol debe pertenecer al catálogo permitido.
- El usuario y el Site deben existir.
- Devuelve `201 Created`; una membresía existente devuelve `409 Conflict`.
- Solo un usuario autorizado puede gestionar miembros.

---

## HU-32 — Listar miembros

**Como** usuario autorizado  
**quiero** listar los miembros del proyecto  
**para** conocer quién tiene acceso y con qué rol.

### Endpoint

```http
GET /api/projects/{projectId}/members?page=0&size=20
```

### Criterios de aceptación

- Devuelve una colección paginada con identificador de usuario y rol.
- Permite filtrar por rol.
- Una colección vacía devuelve `200 OK`.
- No expone información personal adicional que Alfresco no requiera para la membresía.

---

## HU-33 — Consultar un miembro

**Como** usuario autorizado  
**quiero** consultar la membresía de una persona  
**para** verificar su rol en el proyecto.

### Endpoint

```http
GET /api/projects/{projectId}/members/{userId}
```

### Criterios de aceptación

- Devuelve el usuario y su rol en el Site.
- Una membresía inexistente devuelve `404 Not Found`.

---

## HU-34 — Cambiar rol de un miembro

**Como** administrador del proyecto  
**quiero** cambiar el rol de un miembro  
**para** ajustar sus permisos.

### Endpoint

```http
PUT /api/projects/{projectId}/members/{userId}/role
```

```json
{ "role": "SiteManager" }
```

### Criterios de aceptación

- El nuevo rol debe ser válido y reemplaza al anterior.
- Site, usuario y membresía deben existir.
- Impide dejar el Site sin ningún `SiteManager`.
- Devuelve la membresía actualizada.

---

## HU-35 — Remover miembro

**Como** administrador del proyecto  
**quiero** remover un miembro  
**para** revocar su acceso al Site.

### Endpoint

```http
DELETE /api/projects/{projectId}/members/{userId}
```

### Criterios de aceptación

- La eliminación exitosa devuelve `204 No Content`.
- Una membresía inexistente devuelve `404`.
- Impide remover al último `SiteManager`.
- Remover la membresía no elimina documentos creados por el usuario.
