# Épica 6 — Manejo de errores

## Objetivo

Estandarizar los errores producidos por la API y desacoplar al consumidor de los mensajes internos retornados por Alfresco.

---

## HU-21 — Respuestas de error estandarizadas

**Como** consumidor de la API  
**quiero** recibir errores consistentes  
**para** poder identificar fácilmente qué salió mal.

### Respuesta de ejemplo

```json
{
  "status": 404,
  "code": "DOCUMENT_NOT_FOUND",
  "message": "The requested document does not exist",
  "timestamp": "2026-08-18T19:20:00"
}
```

### Excepciones de dominio propuestas

```text
DocumentNotFoundException
FolderNotFoundException
InvalidDocumentTypeException
RepositoryUnavailableException
DuplicateNodeException
InvalidFolderTreeException
VersionNotFoundException
InvalidMetadataException
```

### Criterios de aceptación

- Todas las excepciones controladas deben producir una estructura de error homogénea.
- Debe utilizarse `@RestControllerAdvice`.
- Deben utilizarse `@ExceptionHandler` específicos.
- No deben exponerse stack traces al consumidor.
- Los errores internos deben registrarse en logs.
- Los errores de Alfresco deben traducirse a errores propios de la API cuando corresponda.

### Códigos HTTP sugeridos

| Situación | Código |
|---|---:|
| Recurso no encontrado | 404 |
| Request inválido | 400 |
| Conflicto de nombre o estado | 409 |
| Tipo de archivo no permitido | 415 |
| Alfresco no disponible | 503 |
| Error interno inesperado | 500 |
