# Épica 3 — Gestión de carpetas

## Objetivo

Administrar la estructura de carpetas dentro de la Document Library de cada proyecto. Todos los endpoints deben comprobar que los nodos pertenecen al `projectId` indicado.

## HU-02 — Crear carpeta

**Como** usuario **quiero** crear una carpeta en un proyecto **para** organizar documentos.

```http
POST /api/projects/{projectId}/folders
```

```json
{ "name": "Contratos", "parentId": "abc123" }
```

### Criterios de aceptación

- El nombre es obligatorio y `parentId` debe ser una carpeta del proyecto; si se omite, se usa la Document Library.
- No permite crear bajo un documento y trata nombres duplicados con `409 Conflict`.
- Devuelve `201 Created` y el identificador generado por Alfresco.

## HU-03 — Crear árbol de carpetas

**Como** usuario **quiero** crear una estructura completa en una operación **para** preparar el expediente del proyecto.

```http
POST /api/projects/{projectId}/folders/tree
```

```json
{
  "name": "Empresa",
  "children": [
    { "name": "Recursos Humanos", "children": [{ "name": "Contratos" }] },
    { "name": "Contabilidad", "children": [{ "name": "Facturas" }] }
  ]
}
```

### Criterios de aceptación

- Valida todo el árbol antes de crearlo, con límites configurables de profundidad y cantidad de nodos.
- Informa qué nodos se crearon si Alfresco falla durante el proceso y aplica la estrategia de compensación documentada.
- Devuelve la jerarquía con los identificadores creados.

## HU-04 — Consultar contenido de una carpeta

```http
GET /api/projects/{projectId}/folders/{folderId}/children?page=0&size=20
```

- Devuelve subcarpetas y documentos paginados.
- Verifica que la carpeta pertenezca al proyecto.

## HU-05 — Consultar árbol de carpetas

```http
GET /api/projects/{projectId}/folders/{folderId}/tree?maxDepth=5
```

- Devuelve recursivamente la estructura, respetando un límite de profundidad.
- Evita respuestas ilimitadas e identifica si el resultado fue truncado.

## HU-06 — Renombrar carpeta

```http
PATCH /api/projects/{projectId}/folders/{folderId}
```

```json
{ "name": "Contratos 2027" }
```

- Valida el nuevo nombre, pertenencia al proyecto y colisiones con hermanos.

## HU-07 — Mover carpeta

```http
PUT /api/projects/{projectId}/folders/{folderId}/move
```

```json
{ "targetFolderId": "xyz123" }
```

- Origen y destino pertenecen al mismo proyecto.
- Impide mover una carpeta dentro de sí misma o de un descendiente.

## HU-08 — Eliminar carpeta

```http
DELETE /api/projects/{projectId}/folders/{folderId}?recursive=false
```

- Una carpeta no vacía devuelve `409 Conflict` salvo que `recursive=true`.
- La eliminación recursiva exige autorización y elimina únicamente nodos del proyecto.
- La respuesta exitosa es `204 No Content`.
