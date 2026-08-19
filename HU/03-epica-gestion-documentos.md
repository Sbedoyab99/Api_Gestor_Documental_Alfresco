# Épica 3 — Gestión de documentos

## Objetivo

Permitir subir, consultar, descargar, mover, renombrar y eliminar documentos dentro de las carpetas del repositorio.

---

## HU-09 — Subir documento

**Como** usuario  
**quiero** subir documentos a una carpeta  
**para** almacenarlos dentro del repositorio documental.

### Endpoint propuesto

```http
POST /api/folders/{folderId}/documents
Content-Type: multipart/form-data
```

### Tipos permitidos

```text
application/pdf
image/jpeg
image/png
image/webp
```

### Criterios de aceptación

- La carpeta destino debe existir.
- Solo deben aceptarse archivos PDF o imágenes permitidas.
- Debe validarse la extensión.
- Debe validarse el MIME type.
- Debe rechazarse cualquier formato no permitido.
- El tamaño máximo permitido debe ser configurable.
- La respuesta debe incluir el identificador asignado por Alfresco.
- Debe almacenarse el nombre original del archivo.

---

## HU-10 — Consultar documento

**Como** usuario  
**quiero** consultar la información de un documento  
**para** conocer sus características.

### Endpoint propuesto

```http
GET /api/documents/{id}
```

### Respuesta de ejemplo

```json
{
  "id": "a72...",
  "name": "contrato.pdf",
  "mimeType": "application/pdf",
  "size": 458212,
  "createdAt": "2026-08-18T19:00:00",
  "modifiedAt": "2026-08-18T19:15:00",
  "version": "1.0",
  "metadata": {}
}
```

### Criterios de aceptación

- El documento debe existir.
- Debe devolverse su información principal.
- La respuesta debe incluir su versión actual.
- Si no existe, debe devolverse `404 Not Found`.

---

## HU-11 — Descargar documento

**Como** usuario  
**quiero** descargar un documento  
**para** visualizar o utilizar su contenido.

### Endpoint propuesto

```http
GET /api/documents/{id}/content
```

### Criterios de aceptación

- Debe descargarse el contenido almacenado en Alfresco.
- Debe conservarse el MIME type correcto.
- La respuesta debe incluir el nombre original del archivo.
- Si el documento no existe, debe devolverse `404 Not Found`.

---

## HU-12 — Renombrar o mover documento

**Como** usuario  
**quiero** cambiar el nombre o ubicación de un documento  
**para** reorganizar la biblioteca documental.

### Endpoints propuestos

```http
PATCH /api/documents/{id}
```

```http
PUT /api/documents/{id}/move
```

### Criterios de aceptación

- El documento debe existir.
- Para moverlo, la carpeta destino debe existir.
- Deben manejarse conflictos de nombres.
- La operación no debe crear una nueva versión del contenido si solo cambia la ubicación o el nombre.
- La respuesta debe devolver la información actualizada.

---

## HU-13 — Eliminar documento

**Como** usuario  
**quiero** eliminar un documento  
**para** retirar contenido que ya no sea necesario.

### Endpoint propuesto

```http
DELETE /api/documents/{id}
```

### Criterios de aceptación

- El documento debe existir.
- Debe eliminarse utilizando las capacidades del repositorio documental.
- Si el documento no existe, debe devolverse `404 Not Found`.
- La API debe informar correctamente si Alfresco no puede completar la operación.
