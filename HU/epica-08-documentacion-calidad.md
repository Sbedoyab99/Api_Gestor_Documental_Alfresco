# Épica 8 — Documentación y calidad

## Objetivo

Hacer que la API sea comprensible, verificable y mantenible.

## HU-22 — Documentar API con OpenAPI

**Como** desarrollador **quiero** consultar Swagger **para** conocer y probar los endpoints.

### Criterios de aceptación

- Documenta todos los endpoints, incluidos multipart, paginación, roles y respuestas de error.
- Incluye ejemplos para proyectos/Sites, miembros, carpetas, documentos, versiones y metadatos.
- Declara autenticación, límites de archivo y códigos HTTP.
- La especificación OpenAPI se valida en el pipeline.

## HU-23 — Pruebas unitarias

### Criterios de aceptación

- Cubre servicios de Site, membresía, carpeta, documento, versión y metadatos.
- Prueba reglas críticas: ámbito del proyecto, último `SiteManager`, ciclos de carpetas, archivos permitidos y restauración de versiones.
- Usa JUnit 5 y Mockito sin depender de una instancia real de Alfresco.
- El pipeline publica resultados y aplica un umbral de cobertura acordado.

## HU-24 — Pruebas de integración con Alfresco

### Criterios de aceptación

- El adaptador de Alfresco se prueba contra WireMock con respuestas `200`, `201`, `400`, `401`, `403`, `404`, `409`, `500` y timeout.
- Incluye contratos para Sites, contenedor `documentLibrary`, miembros, nodos, contenido y versiones.
- Verifica mapeos de solicitudes/respuestas y traducción de errores.
- Existe un perfil separado para pruebas smoke contra Alfresco real, sin convertirlo en requisito para las pruebas locales.

## Orden sugerido de implementación

1. Integración con Alfresco.
2. Sites, Document Library y miembros.
3. Carpetas dentro del Site.
4. Documentos.
5. Versiones.
6. Metadatos.
7. Manejo transversal de errores, documentación y pruebas.
