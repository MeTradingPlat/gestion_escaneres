# 🚀 Suite de Pruebas Postman - Gestión de Escáneres

Esta carpeta contiene una colección profesional de pruebas para el módulo de **Gestión de Escáneres** del sistema MeTradingPlat.

## 📁 Archivos Incluidos

```
postman/
├── Gestion_Escaneres_Collection.json        # Colección principal (Parte 1)
├── Gestion_Escaneres_Collection_Part2.json  # Colección extendida (Parte 2)
├── Gestion_Escaneres_Environment.json       # Variables de entorno
└── README.md                                 # Esta documentación
```

## 🎯 Características de la Suite

✅ **Tests Automáticos**: Cada request incluye validaciones automáticas
✅ **Variables Dinámicas**: IDs y nombres se generan y guardan automáticamente
✅ **Casos de Éxito y Error**: Cobertura completa de happy path y error handling
✅ **Nueva Estructura de Errores**: Tests específicos para validar la mejora RFC 7807
✅ **Scripts Pre-request**: Preparación automática de datos
✅ **Documentación Inline**: Cada request está documentado

## 📦 Importación en Postman

### Opción 1: Importación Manual

1. Abrir Postman
2. Click en **Import** (esquina superior izquierda)
3. Arrastrar los 2 archivos JSON de colección al área de importación
4. Click en **Import**
5. Importar también el archivo de **Environment**

### Opción 2: Desde Línea de Comandos (Newman)

```bash
# Instalar Newman (CLI de Postman)
npm install -g newman

# Ejecutar la colección
newman run Gestion_Escaneres_Collection.json \
  -e Gestion_Escaneres_Environment.json \
  --reporters cli,html \
  --reporter-html-export test-results.html
```

## 🏗️ Estructura de la Colección

### Parte 1: Fundamentos

#### 01 - Health & Setup
- ✅ Health Check
- ✅ Listar Mercados

#### 02 - CRUD Escáneres (Happy Path)
- ✅ Crear Escáner
- ✅ Obtener Escáner por ID
- ✅ Listar Escáneres Activos
- ✅ Actualizar Escáner

#### 03 - Validaciones y Errores
- ❌ Nombre duplicado (GC-0002)
- ❌ Campo requerido faltante (400)
- ❌ Horario inválido (GC-0005)
- ❌ Sin mercados (GC-0005)
- ❌ Entidad inexistente (GC-0003)

### Parte 2: Avanzado

#### 04 - Gestión de Estados
- ✅ Iniciar Escáner
- ✅ Detener Escáner
- ✅ Archivar Escáner
- ❌ Iniciar escáner archivado (GC-0005)
- ✅ Desarchivar Escáner
- ✅ Listar Escáneres Archivados

#### 05 - Gestión de Filtros
- ✅ Obtener Categorías
- ✅ Obtener Filtros por Categoría
- ✅ Obtener Filtro por Defecto (RSI)
- ✅ Guardar Filtros (Happy Path)
- ❌ **Guardar Filtros con Parámetros Inválidos** ⭐ (Nueva estructura de errores)
- ✅ Obtener Filtros de Escáner

#### 06 - Edge Cases y Cleanup
- ❌ Actualizar a nombre existente
- ✅ Eliminar Escáner
- ❌ Verificar eliminación

## 🎨 Códigos de Error del Sistema

| Código   | Descripción                    | HTTP Status |
|----------|--------------------------------|-------------|
| GC-0001  | Error Genérico                 | 500         |
| GC-0002  | Entidad Ya Existe              | 406         |
| GC-0003  | Entidad No Encontrada          | 406         |
| GC-0004  | Cambio de Estado No Permitido  | 400         |
| GC-0005  | Violación de Regla de Negocio  | 400         |
| GC-0009  | Tipo de Argumento Inválido     | 400         |

## ⭐ Test Clave: Nueva Estructura de Errores

El test más importante de la colección es:

**05 - Gestión de Filtros > ERROR - Guardar filtros con parámetros inválidos**

Este test valida la nueva estructura de errores implementada:

```json
{
  "codigoError": "GC-0005",
  "mensaje": "Error de validación en los filtros configurados",
  "codigoHttp": 400,
  "url": "/api/escaner/filtro/escaner/123",
  "metodo": "POST",
  "erroresValidacion": [
    {
      "filtro": "RSI",
      "parametro": "PERIODO_RSI",
      "mensaje": "El período debe ser mayor que 0",
      "filtroIndex": null
    }
  ]
}
```

**Validaciones que realiza:**
- ✅ Status code 400
- ✅ Estructura ValidationErrorResponse
- ✅ Código de error GC-0005
- ✅ Array erroresValidacion no vacío
- ✅ Cada error tiene filtro, parámetro y mensaje
- ✅ Identifica correctamente el filtro (RSI) y parámetro (PERIODO_RSI)

## 🔧 Variables de Entorno

El archivo `Gestion_Escaneres_Environment.json` define:

| Variable          | Descripción                              | Valor Inicial      |
|-------------------|------------------------------------------|--------------------|
| `baseUrl`         | URL base del servicio                    | http://localhost:8081 |
| `scannerId`       | ID del escáner (auto-generado)           | (vacío)            |
| `scannerName`     | Nombre del escáner (auto-generado)       | (vacío)            |
| `otherScannerName`| Nombre auxiliar para tests               | (vacío)            |

**Nota:** Las variables `scannerId`, `scannerName` y `otherScannerName` se auto-generan durante la ejecución de los tests.

## 📊 Orden de Ejecución Recomendado

Para ejecutar la suite completa en orden:

1. **01 - Health & Setup**: Verificar que el servicio está activo
2. **02 - CRUD Escáneres**: Crear datos de prueba
3. **03 - Validaciones y Errores**: Validar manejo de errores
4. **04 - Gestión de Estados**: Probar transiciones de estado
5. **05 - Gestión de Filtros**: Validar filtros y la nueva estructura de errores ⭐
6. **06 - Edge Cases**: Tests de casos límite y cleanup

## 🚦 Interpretación de Resultados

### ✅ Tests Exitosos
Todos los tests tienen validaciones automáticas. Si un test pasa, significa que:
- El status code es el esperado
- La estructura de respuesta es correcta
- Los datos retornados son válidos
- Las reglas de negocio se cumplen

### ❌ Tests de Error Esperado
Algunos tests están diseñados para fallar (validar errores):
- Deben retornar códigos 4xx
- Deben incluir estructura de error estándar
- Deben tener el código de error correcto (GC-XXXX)

## 🔍 Debugging

### Ver logs en Postman Console
1. Click en **Console** (parte inferior de Postman)
2. Los tests imprimen información útil:
   ```
   ✅ Escáner creado exitosamente con ID: 123
   ✅ Mercados disponibles: NASDAQ, NYSE, AMEX
   📋 Errores de validación: [...]
   ```

### Verificar variables
1. Click en el icono del ojo (👁️) en la esquina superior derecha
2. Verificar que `scannerId` y `scannerName` tienen valores
3. Si están vacíos, ejecutar primero "02 - Crear Escáner"

## 🎯 Casos de Uso Profesionales

### Desarrollo Local
```bash
# Ejecutar suite completa con reporte HTML
newman run Gestion_Escaneres_Collection.json \
  -e Gestion_Escaneres_Environment.json \
  --reporters cli,html \
  --reporter-html-export results/test-report.html
```

### CI/CD Pipeline
```yaml
# Ejemplo para GitHub Actions
- name: Run API Tests
  run: |
    newman run postman/Gestion_Escaneres_Collection.json \
      -e postman/Gestion_Escaneres_Environment.json \
      --reporters junit \
      --reporter-junit-export test-results.xml
```

### Testing de Regresión
```bash
# Ejecutar solo tests de errores
newman run Gestion_Escaneres_Collection.json \
  --folder "03 - Validaciones y Errores" \
  -e Gestion_Escaneres_Environment.json
```

### Performance Testing
```bash
# Ejecutar con múltiples iteraciones
newman run Gestion_Escaneres_Collection.json \
  -e Gestion_Escaneres_Environment.json \
  -n 10 \
  --delay-request 500
```

## 📝 Personalización

### Cambiar Puerto del Servicio
Editar `Gestion_Escaneres_Environment.json`:
```json
{
  "key": "baseUrl",
  "value": "http://localhost:9090",  // Cambiar puerto
  "enabled": true
}
```

### Agregar Nuevos Tests
1. Abrir la colección en Postman
2. Crear nuevo request en la carpeta apropiada
3. Añadir script de test:
```javascript
pm.test("Mi test personalizado", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData).to.have.property('campo');
});
```

## 🆘 Troubleshooting

### Error: "Could not get response"
- ✅ Verificar que el servicio esté corriendo (`http://localhost:8081/actuator/health`)
- ✅ Verificar que el puerto sea el correcto en las variables de entorno
- ✅ Verificar firewall y antivirus

### Error: "scannerId is undefined"
- ✅ Ejecutar primero el test "02 - Crear Escáner - Caso Exitoso"
- ✅ Verificar que el test de creación pasó exitosamente
- ✅ Verificar en Postman Console que el ID se guardó

### Tests fallan con 406 (GC-0002)
- ✅ Nombre de escáner ya existe en BD
- ✅ Ejecutar "06 - Eliminar Escáner" para limpiar
- ✅ O cambiar el nombre en pre-request script

### Error: "Request timeout"
- ✅ Aumentar timeout en Postman: Settings > General > Request timeout
- ✅ Verificar que la BD responde correctamente
- ✅ Verificar logs del backend

## 📚 Referencias

- [Documentación de Postman](https://learning.postman.com/docs/)
- [Newman CLI](https://github.com/postmanlabs/newman)
- [RFC 7807 - Problem Details](https://tools.ietf.org/html/rfc7807)
- [Spring Boot Testing Best Practices](https://spring.io/guides/gs/testing-web/)

## 🤝 Contribuciones

Para añadir nuevos tests:
1. Seguir la estructura de carpetas existente
2. Incluir tests automáticos en cada request
3. Documentar el propósito del test en la descripción
4. Actualizar este README con los nuevos tests

---

**Última actualización:** Enero 2025
**Versión de la API:** v1
**Mantenedor:** Equipo de Desarrollo MeTradingPlat
