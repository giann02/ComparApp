# ComparApp

Aplicación Android que compara precios de servicios de transporte (Uber, DiDi, Cabify) para una ruta determinada, mostrando cuál es la opción más económica.

## Funcionalidades

- Registro e inicio de sesión de usuarios
- Recuperación de contraseña
- Verificación de identidad por detección de rostro (CameraX + ML Kit)
- Ingreso de origen y destino con autocompletado de direcciones
- Cálculo de distancia real por calles usando la API de Mapbox
- Comparación de precios entre Uber, DiDi Express y Cabify
- Ahorro estimado respecto a la opción más cara
- Apertura directa de la app del proveedor seleccionado con origen y destino
- Guardado y eliminación de rutas favoritas por usuario (toggle con ícono de estrella)
- Historial de ahorros acumulados por viaje
- Estadísticas de ahorro total y promedio por viaje
- Cierre de sesión

## Cálculo de precios

Los precios mostrados son aproximados y se calculan en base a tres factores:

1. **Tarifa base** — costo fijo de arranque de cada servicio
2. **Precio por km** — costo variable según la distancia real de la ruta
3. **Factor de demanda** — multiplicador aleatorio que simula la demanda en tiempo real (similar al "surge pricing" real)

La fórmula es: `precio = (tarifa_base + precio_por_km × distancia) × demanda`

Esto permite que los resultados varíen levemente en cada consulta, simulando el comportamiento real de estas aplicaciones.

## Integración con apps de transporte

Al seleccionar un proveedor, la app intenta abrirlo con las coordenadas de origen y destino prellenadas:

| Proveedor | Método |
|-----------|--------|
| **Uber** | Deeplink `uber://` con coordenadas de pickup y dropoff |
| **Cabify** | Deeplink `cabify://cabify.com/ride` con parámetros de paradas |
| **DiDi** | Apertura directa por nombre de paquete (`com.didiglobal.passenger`) |

Si la app del proveedor no está instalada, se abre el navegador con su sitio web.

## APIs utilizadas

| API | Endpoint | Para qué se usa |
|-----|----------|-----------------|
| **Mapbox Geocoding** | `geocoding/v5/mapbox.places` | Autocompletado de direcciones y conversión a coordenadas (lat, lon) |
| **Mapbox Directions** | `directions/v5/mapbox/driving` | Calcula la distancia real en km por calles entre dos coordenadas |

Ambas APIs están restringidas a Argentina (`country=AR`) y con proximidad centrada en Buenos Aires.

## Tecnologías utilizadas

- **Kotlin** con **Jetpack Compose**
- **Room** — base de datos local para usuarios, rutas favoritas e historial de ahorros
- **Retrofit** — llamadas a la API de Mapbox
- **CameraX + ML Kit Face Detection** — verificación de presencia del usuario
- **ViewModel + StateFlow** — manejo de estado reactivo
- **Navigation Compose** — navegación entre pantallas
- **Arquitectura MVVM** con separación en capas (data, domain, ui)

## Seguridad

- Las contraseñas se almacenan con hash SHA-256 antes de persistirse en Room

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
    ├── screens/        # Pantallas (login, register, main, resultados, favoritas, historial, facedetection)
    └── theme/          # Colores, tipografía y tema
```

## Integrantes

- Gianluca Panigatti
- Danilo Lewicki
- Victoria Bogetti
- Tomas Catellani
