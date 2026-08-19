# Épica 7 — Manejo de errores

## Objetivo

Entregar respuestas previsibles y seguras ante errores de dominio, validación e integración con Alfresco.

## HU-21 — Respuestas de error estandarizadas

```json
{
  "status": 404,
  "code": "DOCUMENT_NOT_FOUND",
  "message": "El documento solicitado no existe",
  "timestamp": "2026-08-18T19:20:00Z",
  "traceId": "9d83...",
  "path": "/api/projects/demo/documents/123"
}
```

### Criterios de aceptación

- Un manejador global traduce excepciones a códigos HTTP y códigos de negocio consistentes.
- Incluye errores para Site, membresía, carpeta, documento, versión, formato, duplicado, autorización y repositorio no disponible.
- Los errores de validación identifican cada campo inválido.
- Las respuestas no exponen stack traces, credenciales ni cuerpos internos de Alfresco.
- Los timeouts y fallas transitorias se distinguen de errores definitivos.
- Cada respuesta incluye un identificador de trazabilidad correlacionable con logs.
