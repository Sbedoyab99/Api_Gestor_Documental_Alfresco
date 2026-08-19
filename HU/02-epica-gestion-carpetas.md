# Épica 2 — Gestión de carpetas

## Objetivo

Permitir la creación, consulta, modificación, movimiento y eliminación de carpetas y subcarpetas dentro del repositorio documental.

---

## HU-02 — Crear carpeta

**Como** usuario  
**quiero** crear una carpeta dentro del repositorio  
**para** organizar documentos.

### Endpoint propuesto

```http
POST /api/folders
```

### Request de ejemplo

```json
{
  "name": "Contratos",
  "parentId": "abc123"
}
```

### Criterios de aceptación

- El nombre de la carpeta es obligatorio.
- `parentId` debe corresponder a una carpeta existente.
- No debe permitirse crear una carpeta dentro de un documento.
- La respuesta debe incluir el identificador generado por Alfresco.
- Deben manejarse apropiadamente conflictos por nombres duplicados.
- Deben validarse nombres vacíos o inválidos.

---

## HU-03 — Crear un árbol de carpetas

**Como** usuario  
**quiero** crear una estructura completa de carpetas mediante un árbol  
**para** generar estructuras documentales complejas en una sola operación.

### Endpoint propuesto

```http
POST /api/folders/tree
```

### Request de ejemplo

```json
{
  "name": "Empresa",
  "children": [
    {
      "name": "Recursos Humanos",
      "children": [
        {
          "name": "Contratos"
        },
        {
          "name": "Hojas de Vida"
        }
      ]
    },
    {
      "name": "Contabilidad",
      "children": [
        {
          "name": "Facturas"
        }
      ]
    }
  ]
}
```

### Resultado esperado

```text
Empresa
├── Recursos Humanos
│   ├── Contratos
│   └── Hojas de Vida
└── Contabilidad
    └── Facturas
```

### Criterios de aceptación

- La estructura debe soportar múltiples niveles de profundidad.
- Cada carpeta creada debe quedar asociada correctamente con su padre.
- La creación debe procesarse de forma recursiva.
- La respuesta debe permitir conocer los identificadores asignados a las carpetas creadas.
- Si ocurre un error durante la creación, la API debe informar qué elemento produjo el fallo.

---

## HU-04 — Consultar contenido de una carpeta

**Como** usuario  
**quiero** consultar las subcarpetas y documentos de una carpeta  
**para** navegar por el repositorio.

### Endpoint propuesto

```http
GET /api/folders/{folderId}/children
```

### Criterios de aceptación

- La respuesta debe incluir carpetas y documentos.
- Cada elemento debe indicar su tipo.
- Deben devolverse como mínimo el identificador, nombre, tipo y fechas relevantes.
- Si la carpeta no existe, debe responderse con `404 Not Found`.

---

## HU-05 — Consultar árbol de carpetas

**Como** usuario  
**quiero** obtener recursivamente la estructura de carpetas  
**para** representar gráficamente el repositorio.

### Endpoint propuesto

```http
GET /api/folders/{folderId}/tree
```

### Respuesta de ejemplo

```json
{
  "id": "1",
  "name": "Empresa",
  "children": [
    {
      "id": "2",
      "name": "Contabilidad",
      "children": []
    }
  ]
}
```

### Criterios de aceptación

- La respuesta debe conservar la jerarquía del repositorio.
- Solo deben incluirse carpetas en el árbol.
- Deben soportarse varios niveles de profundidad.
- Si la carpeta raíz solicitada no existe, debe devolverse `404 Not Found`.

---

## HU-06 — Renombrar carpeta

**Como** usuario  
**quiero** modificar el nombre de una carpeta  
**para** mantener actualizada la organización documental.

### Endpoint propuesto

```http
PATCH /api/folders/{id}
```

### Request de ejemplo

```json
{
  "name": "Contratos 2026"
}
```

### Criterios de aceptación

- El nuevo nombre es obligatorio.
- La carpeta debe existir.
- Deben manejarse conflictos por nombres duplicados.
- La respuesta debe devolver la carpeta actualizada.

---

## HU-07 — Mover carpeta

**Como** usuario  
**quiero** mover una carpeta a otra ubicación  
**para** reorganizar la biblioteca documental.

### Endpoint propuesto

```http
PUT /api/folders/{id}/move
```

### Request de ejemplo

```json
{
  "targetFolderId": "xyz123"
}
```

### Criterios de aceptación

- La carpeta origen debe existir.
- La carpeta destino debe existir.
- No debe permitirse mover una carpeta dentro de sí misma.
- No debe permitirse crear ciclos en la jerarquía.
- La respuesta debe reflejar la nueva ubicación.

---

## HU-08 — Eliminar carpeta

**Como** usuario  
**quiero** eliminar una carpeta  
**para** retirar estructuras documentales que ya no necesito.

### Endpoint propuesto

```http
DELETE /api/folders/{id}?recursive=false
```

### Criterios de aceptación

- Si la carpeta está vacía, debe poder eliminarse.
- Si contiene elementos y `recursive=false`, debe devolverse `409 Conflict`.
- Si `recursive=true`, deben eliminarse la carpeta y todos sus descendientes.
- Si la carpeta no existe, debe devolverse `404 Not Found`.
