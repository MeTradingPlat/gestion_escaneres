# Gestion Escaneres - Microservicio de Trading

Microservicio especializado en la gestión de escaners de mercado para la plataforma **MeTradingPlat**. Permite crear, configurar y gestionar escaners personalizados con múltiples filtros técnicos y fundamentales para análisis de activos financieros.

## Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Arquitectura](#arquitectura)
- [Tecnologías](#tecnologías)
- [Patrones de Diseño](#patrones-de-diseño)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [API Endpoints](#api-endpoints)
- [Filtros Disponibles](#filtros-disponibles)
- [Desarrollo](#desarrollo)
- [Testing](#testing)

---

## Descripción General

Este microservicio forma parte de la arquitectura de microservicios de **MeTradingPlat** y se encarga de:

- **Gestión de Escaners**: Crear, actualizar, eliminar y consultar escaners de mercado
- **Gestión de Filtros**: 41 tipos de filtros técnicos y fundamentales configurables
- **Gestión de Mercados**: Administración de mercados (Acciones, Opciones, Futuros, etc.)
- **Validación Dinámica**: Validación de parámetros según el tipo de filtro
- **State Pattern**: Gestión de estados de escaners (Activo, Inactivo, Archivado)

### Características Principales

- **41 Filtros Especializados** organizados en 7 categorías
- **Validación Dinámica** de parámetros según tipo de filtro
- **Arquitectura Hexagonal** (Ports & Adapters)
- **Inyección de Dependencias** con Spring Boot
- **Internacionalización** con i18n
- **Virtual Threads** (Java 21) para alta concurrencia
- **Integración con Eureka** para service discovery

---

## Arquitectura

Este microservicio implementa **Arquitectura Hexagonal** (Ports & Adapters):

```
┌─────────────────────────────────────────────────────────────┐
│                    INFRASTRUCTURE LAYER                      │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐           ┌─────────────────────────┐  │
│  │  REST Controllers│           │  JPA Repositories       │  │
│  │  - Escaner       │           │  - EscanerRepository    │  │
│  │  - Filtro        │           │  - MercadoRepository    │  │
│  │  - Mercado       │           └─────────────────────────┘  │
│  │  - EstadoEscaner │                                        │
│  └─────────────────┘                                        │
│           │                              ▲                   │
│           │ HTTP                         │ JPA               │
│           ▼                              │                   │
├─────────────────────────────────────────────────────────────┤
│                     APPLICATION LAYER                        │
├─────────────────────────────────────────────────────────────┤
│  ┌──────────────────────────────────────────────────────┐   │
│  │               Use Cases (Ports)                      │   │
│  │  - GestionarEscanerIntPort                           │   │
│  │  - GestionarFiltroIntPort                            │   │
│  │  - GestionarMercadoIntPort                           │   │
│  └──────────────────────────────────────────────────────┘   │
│           │                              ▲                   │
│           │                              │                   │
│           ▼                              │                   │
├─────────────────────────────────────────────────────────────┤
│                       DOMAIN LAYER                           │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌──────────────┐  ┌──────────────────┐   │
│  │   Models    │  │    Enums     │  │  State Pattern   │   │
│  │  - Escaner  │  │ - EnumFiltro │  │  - EstadoEscaner │   │
│  │  - Filtro   │  │ - EnumParam  │  │  - Activo        │   │
│  │  - Mercado  │  │ - EnumValor  │  │  - Inactivo      │   │
│  └─────────────┘  └──────────────┘  │  - Archivado     │   │
│                                      └──────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

### Capa de Negocio - Estrategias de Filtros

```
infrastructure/business/strategies/
│
├── IFiltroFactory (Abstract Factory)
│   └── Implementado por 41 factorías concretas
│
├── GestorFiltroFactory (Registry + Facade)
│   ├── Registro de todas las factorías
│   └── Punto de acceso unificado
│
└── filtros/
    ├── FiltroFactoryVolume.java
    ├── FiltroFactoryRSI.java
    ├── FiltroFactoryATR.java
    └── ... (38 más)
```

---

## Tecnologías

### Backend

| Tecnología | Versión | Descripción |
|-----------|---------|-------------|
| **Java** | 21 | Lenguaje principal (Virtual Threads) |
| **Spring Boot** | 3.4.11 | Framework base |
| **Spring Cloud** | 2024.0.0 | Microservicios |
| **Spring Data JPA** | 3.4.11 | Persistencia |
| **Hibernate** | 6.x | ORM |
| **MySQL** | 8.x | Base de datos |
| **Lombok** | Latest | Reducción de boilerplate |
| **Maven** | 3.9+ | Gestión de dependencias |

### Infraestructura

- **Netflix Eureka**: Service Discovery
- **AWS RDS**: Base de datos MySQL en la nube
- **Virtual Threads**: Alta concurrencia (Java 21)

---

## Patrones de Diseño

### 1. **Strategy Pattern** (Validaciones)

Implementado en `ValidadorParametroFiltro` para validar diferentes tipos de parámetros de forma intercambiable.

```java
public interface IValidacionFiltro {
    Optional<ResultadoValidacion> validar(EnumParametro parametro, Valor valor);
}

// Implementaciones:
// - ValidacionFloat
// - ValidacionInteger
// - ValidacionString
// - ValidacionCondicional
```

**Uso:**
```java
@Service
@RequiredArgsConstructor
public class ValidadorParametroFiltro {

    public Optional<ResultadoValidacion> validarFloat(...) {
        IValidacionFiltro estrategiaValidacion = crearEstrategiaFloat(min, max);
        return estrategiaValidacion.validar(enumParametro, valor);
    }

    private IValidacionFiltro crearEstrategiaFloat(Float min, Float max) {
        return new ValidacionFloat(min, max);
    }
}
```

### 2. **Abstract Factory Pattern** (Creación de Filtros)

Implementado en `IFiltroFactory` con 41 implementaciones concretas.

```java
public interface IFiltroFactory {
    EnumFiltro obtenerEnumFiltro();
    EnumCategoriaFiltro obtenerEnumCategoria();
    Filtro obtenerFiltro();
    Filtro obtenerFiltro(Map<EnumParametro, Valor> valoresSeleccionados);
    List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valores);
}

// 41 implementaciones:
@Component
@RequiredArgsConstructor
public class FiltroFactoryVolume implements IFiltroFactory {
    private final ValidadorParametroFiltro objValidador;
    // ...
}
```

### 3. **Registry Pattern** (Gestión de Factorías)

Implementado en `GestorFiltroFactory` para registrar y acceder a todas las factorías.

```java
@Service
@RequiredArgsConstructor
public class GestorFiltroFactory implements GestorEstrategiaFiltroIntPort {
    private final Map<EnumFiltro, IFiltroFactory> mapEnumFiltroIFiltroFactory;
    private final Map<EnumCategoriaFiltro, List<EnumFiltro>> mapCategoriaFiltros;

    public GestorFiltroFactory(Set<IFiltroFactory> filtros) {
        // Auto-descubrimiento via Spring DI
        this.mapEnumFiltroIFiltroFactory = new HashMap<>();
        filtros.forEach(f -> mapEnumFiltroIFiltroFactory.put(f.obtenerEnumFiltro(), f));
    }
}
```

### 4. **Facade Pattern** (Acceso Simplificado)

`GestorFiltroFactory` también actúa como Facade, proporcionando una interfaz simplificada.

### 5. **State Pattern** (Estados de Escaner)

Implementado para gestionar el ciclo de vida de los escaners.

```java
public interface EstadoEscaner {
    EstadoEscaner activar();
    EstadoEscaner desactivar();
    EstadoEscaner archivar();
    EnumEstadoEscaner obtenerEnumEstadoEscaner();
}

// Estados concretos:
// - EscanerActivo
// - EscanerInactivo
// - EscanerArchivado
```

### 6. **Dependency Injection** (Lombok + Spring)

Todas las factorías usan `@RequiredArgsConstructor` para inyección automática:

```java
@Component
@RequiredArgsConstructor
public class FiltroFactoryRSI implements IFiltroFactory {
    private final ValidadorParametroFiltro objValidador; // Auto-inyectado
}
```

---

## Estructura del Proyecto

```
gestion_escaneres/
│
├── src/main/java/com/metradingplat/gestion_escaneres/
│   │
│   ├── application/                    # Capa de Aplicación (Ports)
│   │   ├── dto/                        # Data Transfer Objects
│   │   ├── input/                      # Puertos de entrada (Use Cases)
│   │   └── output/                     # Puertos de salida (Interfaces)
│   │
│   ├── domain/                         # Capa de Dominio
│   │   ├── enums/                      # Enumeraciones de negocio
│   │   │   ├── valores/                # Valores de enums (EnumTimeframe, etc.)
│   │   │   ├── EnumFiltro.java         # 41 tipos de filtros
│   │   │   ├── EnumParametro.java      # 77 parámetros de filtros
│   │   │   └── EnumCategoriaFiltro.java # 7 categorías
│   │   ├── models/                     # Entidades de dominio
│   │   │   ├── Escaner.java
│   │   │   ├── Filtro.java
│   │   │   ├── Parametro.java
│   │   │   ├── Valor.java
│   │   │   └── Mercado.java
│   │   ├── states/                     # State Pattern
│   │   │   ├── EstadoEscaner.java      # Interfaz
│   │   │   └── escaner/
│   │   │       ├── EscanerActivo.java
│   │   │       ├── EscanerInactivo.java
│   │   │       └── EscanerArchivado.java
│   │   └── usecases/                   # Casos de uso de dominio
│   │
│   └── infrastructure/                 # Capa de Infraestructura
│       ├── business/                   # Lógica de negocio
│       │   ├── strategies/             # Estrategias de filtros
│       │   │   ├── IFiltroFactory.java
│       │   │   ├── GestorFiltroFactory.java
│       │   │   └── filtros/            # 41 Implementaciones
│       │   │       ├── FiltroFactoryVolume.java
│       │   │       ├── FiltroFactoryRSI.java
│       │   │       ├── FiltroFactoryATR.java
│       │   │       └── ... (38 más)
│       │   └── validation/             # Validaciones
│       │       ├── IValidacionFiltro.java
│       │       ├── ValidadorParametroFiltro.java
│       │       └── ValidacionFloat.java
│       │
│       ├── configuration/              # Configuración Spring
│       │
│       ├── input/                      # Adaptadores de entrada
│       │   ├── controllerGestionarEscaner/
│       │   │   └── controller/
│       │   │       ├── EscanerRestController.java
│       │   │       └── EstadoEscanerRestController.java
│       │   ├── controllerGestionarFiltro/
│       │   │   └── controller/
│       │   │       └── FiltroRestController.java
│       │   └── controllerGestionarMercado/
│       │       └── controller/
│       │           └── MercadoRestController.java
│       │
│       └── output/                     # Adaptadores de salida
│           ├── persistence/            # JPA Repositories
│           ├── exceptionsController/   # Manejo de excepciones
│           ├── formatter/              # Formateadores de respuesta
│           └── messageSource/          # Internacionalización
│
├── src/main/resources/
│   ├── application.properties          # Configuración principal
│   ├── import.sql                      # Datos iniciales
│   └── messages_es.properties          # Mensajes en español
│
└── pom.xml                             # Maven dependencies
```

---

## Instalación

### Prerrequisitos

- **Java 21+** (con soporte para Virtual Threads)
- **Maven 3.9+**
- **MySQL 8.x**
- **Eureka Server** (para service discovery)

### Clonar el Repositorio

```bash
git clone https://github.com/tu-org/metradingplat.git
cd metradingplat/gestion_escaneres
```

### Compilar el Proyecto

```bash
./mvnw clean install
```

---

## Configuración

### Variables de Entorno Requeridas

```bash
export MYSQL_USER=tu_usuario
export MYSQL_PASSWORD=tu_password
```

### Configuración de Base de Datos

Editar `src/main/resources/application.properties`:

```properties
# URL de conexión MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/bd_escaneres?useSSL=false&serverTimezone=America/Bogota

# Credenciales (desde variables de entorno)
spring.datasource.username=${MYSQL_USER}
spring.datasource.password=${MYSQL_PASSWORD}

# Estrategia Hibernate
spring.jpa.hibernate.ddl-auto=update
```

### Configuración de Eureka

```properties
# Service Discovery
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.instance.prefer-ip-address=true
```

### Puerto del Servicio

```properties
server.port=8081
spring.application.name=gestion-escaneres
```

### Ejecutar el Servicio

```bash
./mvnw spring-boot:run
```

El servicio estará disponible en: `http://localhost:8081`

---

## API Endpoints

### Escaners

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/escaneres` | Listar todos los escaners |
| `GET` | `/api/escaneres/{id}` | Obtener escaner por ID |
| `POST` | `/api/escaneres` | Crear nuevo escaner |
| `PUT` | `/api/escaneres/{id}` | Actualizar escaner |
| `DELETE` | `/api/escaneres/{id}` | Eliminar escaner |

### Estados de Escaner

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `PUT` | `/api/escaneres/{id}/activar` | Activar escaner |
| `PUT` | `/api/escaneres/{id}/desactivar` | Desactivar escaner |
| `PUT` | `/api/escaneres/{id}/archivar` | Archivar escaner |

### Filtros

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/filtros` | Listar todos los filtros disponibles |
| `GET` | `/api/filtros/{enumFiltro}` | Obtener filtro específico |
| `GET` | `/api/filtros/categoria/{categoria}` | Filtros por categoría |
| `POST` | `/api/filtros/validar` | Validar configuración de filtro |

### Mercados

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/mercados` | Listar mercados disponibles |
| `GET` | `/api/mercados/{id}` | Obtener mercado por ID |

---

## Filtros Disponibles

### 7 Categorías con 41 Filtros Especializados

#### 1. Volumen (6 filtros)
- `VOLUME` - Volumen de trading
- `AVERAGE_VOLUME` - Volumen promedio
- `VOLUMEN_POST_PRE` - Volumen pre/post mercado
- `RELATIVE_VOLUME` - Volumen relativo
- `RELATIVE_VOLUME_SAME_TIME` - Volumen relativo mismo horario
- `VOLUME_SPIKE` - Picos de volumen

#### 2. Precio y Movimiento (13 filtros)
- `CHANGE` - Cambio de precio
- `PERCENTAGE_CHANGE` - Cambio porcentual
- `PRECIO` - Precio actual
- `GAP_FROM_CLOSE` - Gap desde cierre
- `POSITION_IN_RANGE` - Posición en rango
- `PERCENTAGE_RANGE` - Rango porcentual
- `RELATIVE_RANGE` - Rango relativo
- `RANGE_DOLLAR` - Rango en dólares
- `CROSSING_ABOVE_BELOW` - Cruces de niveles
- `HALT` - Detenciones de trading
- Y más...

#### 3. Volatilidad (2 filtros)
- `ATR` - Average True Range
- `ATRP` - Average True Range Percentage

#### 4. Momentum e Indicadores Técnicos (5 filtros)
- `RSI` - Relative Strength Index
- `DISTANCE_FROM_VWAP_EMA_MA` - Distancia desde VWAP/EMA/MA
- `BACK_TO_EMA_ALERT` - Alerta de retorno a EMA
- `THROUGH_EMA_VWAP_ALERT` - Alerta de cruce EMA/VWAP
- `EMA_VWAP_SUPPORT_RESISTANCE` - Soporte/Resistencia EMA/VWAP

#### 5. Patrones de Precio (10 filtros)
- `BEARISH_BULLISH_ENGULFING_CANDLE` - Velas envolventes
- `FIRST_CANDLE` - Primera vela
- `CONSECUTIVE_CANDLES` - Velas consecutivas
- `HIGH_LOW_OF_DAY` - Máximos/Mínimos del día
- `NEW_CANDLE_HIGH_LOW` - Nuevos máximos/mínimos
- `PERCENTAGE_PULLBACK_HIGHS_LOWS` - Retroceso porcentual
- `BREAK_OVER_RECENT_HIGHS_LOWS` - Ruptura de niveles
- `OPENING_RANGE_BREAKDOWN` - Ruptura rango apertura (bajista)
- `OPENING_RANGE_BREAKOUT` - Ruptura rango apertura (alcista)
- `PIVOTS` - Puntos pivote

#### 6. Tiempo (1 filtro)
- `MINUTOS_IN_MARKET` - Minutos en el mercado

#### 7. Fundamentales (5 filtros)
- `FLOAT` - Acciones en circulación
- `MARKET_CAP` - Capitalización de mercado
- `SHARES_OUTSTANDING` - Acciones totales
- `SHORT_INTEREST` - Interés corto
- `SHORT_RATIO` - Ratio de cortos

#### 8. Noticias (2 filtros)
- `NOTICIAS` - Presencia de noticias
- `DAYS_UNTIL_EARNINGS` - Días hasta reporte de ganancias

### Ejemplo de Uso de Filtro

```json
{
  "enumFiltro": "RSI",
  "parametros": {
    "PERIODO_RSI": {
      "tipo": "INTEGER",
      "valor": 14
    },
    "TIMEFRAME_RSI": {
      "tipo": "STRING",
      "valor": "1D"
    },
    "CONDICION": {
      "tipo": "CONDICIONAL",
      "operador": "GREATER_THAN",
      "valor": 70
    }
  }
}
```

---

## Desarrollo

### Agregar un Nuevo Filtro

1. **Agregar enum en `EnumFiltro.java`:**

```java
public enum EnumFiltro {
    // ...
    MI_NUEVO_FILTRO("filter.miNuevoFiltro.name", "filter.miNuevoFiltro.description"),
}
```

2. **Agregar parámetros en `EnumParametro.java`:**

```java
public enum EnumParametro {
    // ...
    PARAMETRO_1_MI_FILTRO("parameter.miNuevoFiltro.parametro1"),
    PARAMETRO_2_MI_FILTRO("parameter.miNuevoFiltro.parametro2"),
}
```

3. **Crear factoría del filtro:**

```java
@Component
@RequiredArgsConstructor
public class FiltroFactoryMiNuevoFiltro implements IFiltroFactory {

    private final EnumFiltro enumFiltro = EnumFiltro.MI_NUEVO_FILTRO;
    private final EnumCategoriaFiltro enumCategoria = EnumCategoriaFiltro.TU_CATEGORIA;
    private final ValidadorParametroFiltro objValidador;

    @Override
    public Filtro obtenerFiltro(Map<EnumParametro, Valor> valoresSeleccionados) {
        // Implementación
    }

    @Override
    public List<ResultadoValidacion> validarValoresSeleccionados(Map<EnumParametro, Valor> valores) {
        // Validaciones
    }
}
```

4. **Agregar mensajes en `messages_es.properties`:**

```properties
filter.miNuevoFiltro.name=Mi Nuevo Filtro
filter.miNuevoFiltro.description=Descripción del filtro
parameter.miNuevoFiltro.parametro1=Parámetro 1
```

5. **Compilar y probar:**

```bash
./mvnw clean compile
./mvnw test
```

### Convenciones de Código

- **Nombres de clases:** PascalCase (`FiltroFactoryVolume`)
- **Nombres de métodos:** camelCase (`obtenerFiltro`)
- **Constantes:** UPPER_SNAKE_CASE (`VOLUME_SPIKE`)
- **Indentación:** 4 espacios
- **Imports:** Organizados alfabéticamente por grupos
- **Lombok:** Usar `@RequiredArgsConstructor` para DI

---

## Testing

### Ejecutar Tests

```bash
# Todos los tests
./mvnw test

# Test específico
./mvnw test -Dtest=FiltroFactoryVolumeTest

# Con cobertura
./mvnw test jacoco:report
```

### Estructura de Tests

```
src/test/java/
├── application/
├── domain/
└── infrastructure/
    └── business/
        └── strategies/
            └── filtros/
                ├── FiltroFactoryVolumeTest.java
                └── FiltroFactoryRSITest.java
```

---

## Documentación Técnica Adicional

### Patrones de Diseño Implementados

Consulta la documentación detallada de patrones en:
- [IValidacionFiltro.java](src/main/java/com/metradingplat/gestion_escaneres/infrastructure/business/validation/IValidacionFiltro.java) - Strategy Pattern
- [IFiltroFactory.java](src/main/java/com/metradingplat/gestion_escaneres/infrastructure/business/strategies/IFiltroFactory.java) - Abstract Factory Pattern
- [GestorFiltroFactory.java](src/main/java/com/metradingplat/gestion_escaneres/infrastructure/business/strategies/GestorFiltroFactory.java) - Registry + Facade Pattern
- [EstadoEscaner.java](src/main/java/com/metradingplat/gestion_escaneres/domain/states/EstadoEscaner.java) - State Pattern

### Arquitectura Hexagonal

El proyecto sigue los principios de Arquitectura Hexagonal:
- **Dominio**: Independiente de frameworks
- **Aplicación**: Define casos de uso (Ports)
- **Infraestructura**: Implementa adaptadores (Adapters)

---

## Contribuir

1. Fork el repositorio
2. Crear una rama: `git checkout -b feature/nueva-funcionalidad`
3. Commit cambios: `git commit -am 'Agregar nueva funcionalidad'`
4. Push a la rama: `git push origin feature/nueva-funcionalidad`
5. Crear Pull Request

---

## Licencia

Copyright (c) 2025 MeTradingPlat. Todos los derechos reservados.

---

## Contacto

Para soporte o consultas: [contacto@metradingplat.com](mailto:contacto@metradingplat.com)

---

## Changelog

### v0.0.1-SNAPSHOT (Actual)
- Implementación inicial con 41 filtros
- Arquitectura Hexagonal completa
- Integración con Eureka
- Virtual Threads (Java 21)
- Refactorización completa de estrategias con Lombok
