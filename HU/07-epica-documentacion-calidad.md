# Épica 7 — Documentación y calidad

## Objetivo

Documentar la API y garantizar su calidad mediante pruebas unitarias y pruebas de integración desacopladas de una instancia real de Alfresco.

---

## HU-22 — Documentar API con OpenAPI

**Como** desarrollador  
**quiero** consultar la documentación Swagger/OpenAPI  
**para** conocer y probar los endpoints disponibles.

### Criterios de aceptación

- La aplicación debe exponer una especificación OpenAPI.
- Debe incluir una interfaz Swagger UI.
- Los endpoints deben mostrar sus parámetros y modelos de request/response.
- Deben documentarse los códigos HTTP esperados.
- Los endpoints multipart deben poder probarse desde Swagger UI.
- Deben documentarse los tipos MIME permitidos.

### Herramienta propuesta

```text
springdoc-openapi
```

---

## HU-23 — Pruebas unitarias

**Como** desarrollador  
**quiero** disponer de pruebas unitarias  
**para** validar las reglas de negocio sin depender de Alfresco.

### Servicios mínimos a probar

```text
FolderService
DocumentService
DocumentVersionService
MetadataService
```

### Herramientas propuestas

```text
JUnit 5
Mockito
```

### Criterios de aceptación

- Los servicios deben poder probarse de forma aislada.
- El acceso a Alfresco debe abstraerse mediante interfaces.
- Deben existir pruebas para escenarios exitosos y fallidos.
- Deben probarse las validaciones principales.
- No debe requerirse una instancia real de Alfresco para ejecutar estas pruebas.

---

## HU-24 — Pruebas de integración con Alfresco simulado

**Como** desarrollador  
**quiero** probar la integración HTTP con Alfresco sin depender siempre de una instalación real  
**para** ejecutar las pruebas de forma rápida y reproducible.

### Arquitectura propuesta

```text
Spring Boot
      │
      ▼
 AlfrescoClient
      │
      ├──── dev ───────► Alfresco real
      │
      └──── test ──────► WireMock
```

### Escenarios mínimos

```text
200 OK
201 Created
400 Bad Request
401 Unauthorized
404 Not Found
409 Conflict
500 Internal Server Error
Timeout
```

### Herramientas propuestas

```text
WireMock
Spring Boot Test
JUnit 5
```

### Criterios de aceptación

- Las pruebas deben simular las respuestas principales de Alfresco.
- Debe comprobarse la serialización y deserialización de requests y responses.
- Debe probarse el manejo de errores HTTP.
- Deben probarse timeouts o indisponibilidad del repositorio.
- Las pruebas deben poder ejecutarse sin levantar Alfresco.
