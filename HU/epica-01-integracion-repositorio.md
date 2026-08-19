# Épica 1 — Integración con el repositorio

## Objetivo

Establecer la comunicación entre la API desarrollada en Spring Boot y Alfresco Community Edition, validando que el repositorio documental esté disponible antes de implementar operaciones más complejas.

---

## HU-01 — Verificar conexión con Alfresco

**Como** administrador de la aplicación  
**quiero** comprobar la conexión con el repositorio documental  
**para** saber si el servicio se encuentra disponible.

### Criterios de aceptación

- La API debe poder conectarse a Alfresco.
- Debe existir un endpoint para consultar el estado de la integración.
- Si Alfresco responde correctamente, el endpoint debe indicar que el repositorio se encuentra disponible.
- Si Alfresco no está disponible, la API debe responder apropiadamente sin exponer detalles internos del sistema.
- Los errores de conexión deben ser registrados en logs.

### Endpoint propuesto

```http
GET /api/repository/health
```

### Respuesta esperada

```json
{
  "status": "UP"
}
```

### Consideraciones técnicas

- Utilizar `RestClient` de Spring para consumir la API REST de Alfresco.
- Configurar la URL base y credenciales mediante propiedades de configuración.
- Evitar hardcodear credenciales.
- Crear una abstracción para el cliente de Alfresco que pueda ser reemplazada en pruebas.
