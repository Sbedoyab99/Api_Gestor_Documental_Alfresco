# Épica 5 — Control de versiones

## Objetivo

Mantener y consultar el historial de contenido de los documentos del proyecto.

## HU-14 — Subir nueva versión

```http
POST /api/projects/{projectId}/documents/{documentId}/versions
Content-Type: multipart/form-data
```

Campos: `file`, `versionType` (`MAJOR` o `MINOR`) y `comment`.

### Criterios de aceptación

- Aplica las mismas validaciones de formato y tamaño que la carga inicial.
- Incrementa la versión conforme al tipo solicitado y conserva el historial.
- El comentario es obligatorio y tiene longitud máxima.
- Verifica que el documento pertenezca al proyecto.

## HU-15 — Consultar historial de versiones

```http
GET /api/projects/{projectId}/documents/{documentId}/versions
```

- Devuelve versión, tipo, comentario, autor y fecha, con paginación si aplica.
- Ordena desde la versión más reciente.

## HU-16 — Descargar una versión específica

```http
GET /api/projects/{projectId}/documents/{documentId}/versions/{versionId}/content
```

- Descarga exactamente la versión solicitada con cabeceras correctas.
- Devuelve `404` si el documento o la versión no existen.

## HU-17 — Restaurar una versión anterior

```http
POST /api/projects/{projectId}/documents/{documentId}/versions/{versionId}/restore
```

```json
{ "versionType": "MAJOR", "comment": "Restauración aprobada" }
```

- La restauración crea una nueva versión y no destruye el historial.
- Registra versión fuente, comentario y actor.
