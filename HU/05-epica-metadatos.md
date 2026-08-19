# Épica 5 — Gestión de metadatos

## Objetivo

Permitir consultar y modificar información descriptiva asociada a los documentos, utilizando las capacidades de metadatos y content models de Alfresco.

---

## HU-18 — Consultar metadatos

**Como** usuario  
**quiero** consultar los metadatos de un documento  
**para** conocer su información documental.

### Endpoint propuesto

```http
GET /api/documents/{id}/metadata
```

### Respuesta de ejemplo

```json
{
  "title": "Contrato prestación de servicios",
  "description": "Contrato cliente ACME",
  "documentType": "CONTRACT",
  "author": "Santiago Bedoya",
  "tags": [
    "contratos",
    "2026"
  ],
  "documentDate": "2026-08-18"
}
```

### Criterios de aceptación

- El documento debe existir.
- Deben devolverse metadatos estándar y personalizados.
- La ausencia de un metadato opcional no debe producir error.
- Los nombres internos utilizados por Alfresco no deberían filtrarse directamente al consumidor si existe una representación de dominio más adecuada.

---

## HU-19 — Definir metadatos personalizados

**Como** administrador del sistema  
**quiero** disponer de un modelo de metadatos personalizado  
**para** clasificar documentos según las necesidades del gestor documental.

### Aspect propuesto

```text
portfolio:documentMetadata
```

### Propiedades iniciales

```text
documentType
documentNumber
author
documentDate
expirationDate
description
```

### Criterios de aceptación

- Debe crearse un content model personalizado en Alfresco.
- El modelo debe poder asociarse a documentos.
- Las propiedades deben estar disponibles mediante la REST API.
- Los campos de fecha deben utilizar tipos apropiados.
- Debe documentarse qué propiedades son obligatorias y cuáles opcionales.

---

## HU-20 — Modificar metadatos

**Como** usuario  
**quiero** modificar los metadatos de un documento  
**para** mantener su información actualizada.

### Endpoint propuesto

```http
PATCH /api/documents/{id}/metadata
```

### Request de ejemplo

```json
{
  "documentType": "CONTRACT",
  "documentNumber": "CT-2026-00124",
  "documentDate": "2026-08-18",
  "expirationDate": "2027-08-18"
}
```

### Criterios de aceptación

- El documento debe existir.
- Spring Boot debe validar los metadatos antes de enviarlos a Alfresco.
- Deben rechazarse valores inválidos.
- La modificación de metadatos no debe reemplazar el contenido binario del documento.
- La respuesta debe devolver los metadatos actualizados.
