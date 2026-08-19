# Épica 6 — Gestión de metadatos

## Objetivo

Consultar y mantener información documental estructurada mediante propiedades y aspectos de Alfresco.

## HU-18 — Consultar metadatos

```http
GET /api/projects/{projectId}/documents/{documentId}/metadata
```

- Devuelve propiedades estándar y personalizadas normalizadas.
- Verifica que el documento pertenezca al proyecto.
- Representa fechas en ISO 8601 y omite datos internos innecesarios.

## HU-19 — Definir metadatos personalizados

**Como** responsable documental **quiero** disponer del aspecto `portfolio:documentMetadata` **para** clasificar documentos con un modelo común.

Propiedades iniciales: `documentType`, `documentNumber`, `author`, `documentDate`, `expirationDate` y `description`.

### Criterios de aceptación

- El modelo se versiona y despliega de forma reproducible en Alfresco.
- Define tipos, obligatoriedad, cardinalidad y restricciones.
- La documentación explica cómo activar el aspecto y mapear sus propiedades en la API.

## HU-20 — Modificar metadatos

```http
PATCH /api/projects/{projectId}/documents/{documentId}/metadata
```

```json
{
  "documentType": "CONTRACT",
  "documentNumber": "CT-2026-00124",
  "documentDate": "2026-08-18",
  "expirationDate": "2027-08-18"
}
```

- Solo modifica los campos enviados.
- Valida tipos, fechas, catálogos y reglas cruzadas antes de invocar Alfresco.
- Devuelve los metadatos actualizados y errores de campo comprensibles.
