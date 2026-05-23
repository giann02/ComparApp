# ComparApp

Aplicación Android que compara precios de servicios de transporte (Uber, DiDi, Cabify) para una ruta determinada, mostrando cuál es la opción más económica.

## Funcionalidades

- Registro e inicio de sesión de usuarios
- Ingreso de origen y destino con autocompletado de direcciones
- Cálculo de distancia real por calles usando la API de Mapbox
- Comparación de precios entre Uber, DiDi Express y Cabify
- Ahorro estimado respecto a la opción más cara
- Guardado de rutas favoritas por usuario
- Cierre de sesión

## Cálculo de precios

Los precios mostrados son aproximados y se calculan en base a tres factores:

1. **Tarifa base** — costo fijo de arranque de cada servicio
2. **Precio por km** — costo variable según la distancia real de la ruta
3. **Factor de demanda** — multiplicador aleatorio que simula la demanda en tiempo real (similar al "surge pricing" real)

La fórmula es: `precio = (tarifa_base + precio_por_km × distancia) × demanda`

Esto permite que los resultados varíen levemente en cada consulta, reflejando el comportamiento real de estas aplicaciones.

## APIs utilizadas

| API | Endpoint | Para qué se usa |
|-----|----------|-----------------|
| **Mapbox Geocoding** | `geocoding/v5/mapbox.places` | Se usa para dos cosas: autocompletado de direcciones mientras el usuario escribe (devuelve hasta 5 sugerencias) y conversión de la dirección elegida a coordenadas (lat, lon) |
| **Mapbox Directions** | `directions/v5/mapbox/driving` | Calcula la distancia real en km por calles entre dos coordenadas |

Ambas APIs están restringidas a Argentina (`country=AR`) y con proximidad centrada en Buenos Aires.

## Tecnologías utilizadas

- **Kotlin** con **Jetpack Compose**
- **Room** — base de datos local para usuarios y rutas favoritas
- **Retrofit** — llamadas a la API de Mapbox
- **ViewModel** — manejo de estado
- **Navigation Compose** — navegación entre pantallas
- **Arquitectura MVVM** con separación en capas (data, domain, ui)

## Estructura del proyecto

```
app/src/main/java/com/example/comparapp/
├── data/
│   ├── datasource/     # Fuentes de datos (Room, Mapbox)
│   ├── local/          # Entidades, DAOs y base de datos Room
│   ├── remote/         # Interfaz Retrofit para Mapbox
│   └── repository/     # Implementaciones de repositorios
├── domain/
│   ├── model/          # Modelos de dominio
│   └── repository/     # Interfaces de repositorios
└── ui/
    ├── components/     # Componentes reutilizables
    ├── navigation/     # Navegación de la app
    ├── screens/        # Pantallas (login, register, main, resultados, favoritas)
    └── theme/          # Colores, tipografía y tema
```

## Integrantes

- Gianluca Panigatti
- Danilo Lewicki
- Victoria Bogetti
- Tomas Catellani
