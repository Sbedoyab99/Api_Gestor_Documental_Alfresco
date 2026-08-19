# Épica 4 — Control de versiones

## Objetivo

Mantener el historial de cambios de los documentos, permitiendo subir nuevas versiones, consultar versiones históricas, descargar su contenido y restaurarlas.

---

## HU-14 — Subir nueva versión

**Como** usuario  
**quiero** subir una nueva versión de un documento existente  
**para** mantener su historial de modificaciones.

### Endpoint propuesto

```http
POST /api/documents/{id}/versions
Content-Type: multipart/form-data
```

### Parámetros propuestos

```text
file
versionType = MAJOR | MINOR
comment
```

### Ejemplo conceptual

```json
{
  "versionType": "MINOR",
  "comment": "Corrección de datos del cliente"
}
```

### Ejemplo de historial

```text
1.0
1.1
1.2
2.0
```

### Criterios de aceptación

- El documento debe existir.
- El nuevo archivo debe respetar los tipos permitidos.
- Debe poder indicarse si la versión es `MAJOR` o `MINOR`.
- Debe permitirse agregar un comentario de versión.
- La nueva versión debe conservar el mismo documento lógico.
- El historial anterior no debe perderse.

---

## HU-15 — Consultar historial de versiones

**Como** usuario  
**quiero** consultar todas las versiones de un documento  
**para** revisar su historial de cambios.

### Endpoint propuesto

```http
GET /api/documents/{id}/versions
```

### Respuesta de ejemplo

```json
[
  {
    "version": "2.0",
    "major": true,
    "comment": "Contrato firmado",
    "createdAt": "2026-08-18T18:00:00"
  },
  {
    "version": "1.1",
    "major": false,
    "comment": "Corrección de dirección",
    "createdAt": "2026-08-18T17:00:00"
  }
]
```

### Criterios de aceptación

- Debe devolverse todo el historial disponible.
- Cada versión debe incluir número de versión, fecha y comentario.
- Debe indicarse si es una versión mayor o menor.
- Si el documento no existe, debe devolverse `404 Not Found`.

---

## HU-16 — Descargar una versión específica

**Como** usuario  
**quiero** descargar una versión histórica de un documento  
**para** consultar el contenido que tenía anteriormente.

### Endpoint propuesto

```http
GET /api/documents/{id}/versions/{versionId}/content
```

### Criterios de aceptación

- El documento debe existir.
- La versión solicitada debe existir.
- La descarga debe devolver exactamente el contenido de esa versión.
- Debe conservarse el MIME type correspondiente.
- Si la versión no existe, debe devolverse `404 Not Found`.

---

## HU-17 — Restaurar una versión anterior

**Como** usuario  
**quiero** restaurar una versión anterior de un documento  
**para** recuperar contenido que fue reemplazado incorrectamente.

### Endpoint propuesto

```http
POST /api/documents/{id}/versions/{versionId}/restore
```

### Comportamiento esperado

La restauración no debe destruir el historial anterior. Debe generar una nueva versión basada en el contenido histórico.

```text
1.0 Original
1.1 Modificación
2.0 Modificación incorrecta
3.0 Restauración de 1.1
```

### Criterios de aceptación

- El documento y la versión deben existir.
- La restauración debe generar una nueva versión.
- Las versiones anteriores deben conservarse.
- Debe registrarse un comentario indicando que se realizó una restauración.
