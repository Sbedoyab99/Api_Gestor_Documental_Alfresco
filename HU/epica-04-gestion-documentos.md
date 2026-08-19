# Épica 4 — Gestión de documentos

## Objetivo

Administrar documentos PDF e imágenes dentro de las carpetas de un proyecto.

## HU-09 — Subir documento

```http
POST /api/projects/{projectId}/folders/{folderId}/documents
Content-Type: multipart/form-data
```

**Como** usuario **quiero** subir un documento **para** almacenarlo en el proyecto.

### Criterios de aceptación

- Acepta `application/pdf`, `image/jpeg`, `image/png` e `image/webp`.
- Valida extensión, MIME real, tamaño máximo configurable y pertenencia de la carpeta al proyecto.
- Rechaza nombres duplicados según la política definida.
- Devuelve `201 Created`, identificador Alfresco y metadatos básicos.

## HU-10 — Consultar documento

```http
GET /api/projects/{projectId}/documents/{documentId}
```

- Devuelve nombre, MIME, tamaño, fechas, versión y metadatos básicos.
- Devuelve `404` si no existe o no pertenece al proyecto.

## HU-11 — Descargar documento

```http
GET /api/projects/{projectId}/documents/{documentId}/content
```

- Transmite el contenido sin cargarlo completamente en memoria.
- Envía `Content-Type`, longitud y nombre de archivo adecuados.

## HU-12 — Renombrar o mover documento

```http
PATCH /api/projects/{projectId}/documents/{documentId}
PUT /api/projects/{projectId}/documents/{documentId}/move
```

- Permite renombrar y mover mediante operaciones independientes.
- El destino debe ser una carpeta del mismo proyecto.
- Gestiona colisiones de nombre con `409 Conflict`.

## HU-13 — Eliminar documento

```http
DELETE /api/projects/{projectId}/documents/{documentId}
```

- Verifica autorización y pertenencia al proyecto.
- Devuelve `204 No Content` y respeta el comportamiento de papelera de Alfresco.
