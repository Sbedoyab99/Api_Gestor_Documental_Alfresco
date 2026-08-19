# Alfresco Gestor

API Spring Boot para gestionar contenido almacenado en Alfresco Community.

## Entorno local de Alfresco

El archivo `compose.yaml` levanta Alfresco Content Repository Community 26.2,
PostgreSQL 17 y ActiveMQ 6.2. Requiere Docker Desktop con al menos 4 GB de
memoria disponibles para estos contenedores.

Alfresco se publica en `http://localhost:8081/alfresco` para no ocupar el puerto
`8080` de la API Spring Boot.

### Iniciar y comprobar Alfresco

```powershell
docker compose up -d
docker compose ps
docker compose logs -f alfresco
```

El primer inicio puede tardar varios minutos. Está listo cuando `docker compose
ps` muestra el servicio `alfresco` como `healthy`. La comprobación manual es:

```powershell
Invoke-RestMethod `
  http://localhost:8081/alfresco/api/-default-/public/alfresco/versions/1/probes/-ready-
```

Las credenciales locales iniciales son `admin` / `admin`.

### Ejecutar la API

```powershell
$env:ALFRESCO_USERNAME = "admin"
$env:ALFRESCO_PASSWORD = "admin"
./mvnw.cmd spring-boot:run
```

Comprobar la integración:

```powershell
Invoke-RestMethod http://localhost:8080/api/repository/health
```

### Documentación de la API

Con la aplicación en ejecución:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- OpenAPI YAML: `http://localhost:8080/v3/api-docs.yaml`

Estas rutas y el endpoint de salud son públicos. Los demás endpoints permanecen
protegidos por Spring Security.

### Detener el entorno

```powershell
# Conserva contenedores y datos
docker compose stop

# Elimina contenedores, pero conserva los volúmenes con los datos
docker compose down
```

`docker compose down --volumes` elimina también todos los documentos y la base
de datos, por lo que es una operación destructiva.

## Alcance del entorno mínimo

La búsqueda de texto completo y las transformaciones están desactivadas. Las
historias de carpetas, carga, descarga, versiones y metadatos pueden
desarrollarse contra este entorno. Cuando implementemos búsqueda o
previsualización añadiremos Elasticsearch, el indexador y Transform Core.
