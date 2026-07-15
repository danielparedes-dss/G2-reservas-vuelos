# ✈️ Poli Airlines - Sistema de Reservas de Vuelos

Aplicación de escritorio desarrollada en **Java Swing** que simula un sistema de reservas de vuelos nacionales e internacionales, con selección visual de asientos, gestión de pasajeros y persistencia de datos en archivos CSV.

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Arquitectura del Proyecto](#-arquitectura-del-proyecto)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación y Ejecución](#-instalación-y-ejecución)
- [Estructura de Archivos](#-estructura-de-archivos)
- [Persistencia de Datos](#-persistencia-de-datos)
- [Manejo de Excepciones](#-manejo-de-excepciones)
- [Estudiantes](#-estudiantes)

## 🚀 Características

- Selección entre **vuelos nacionales** e **internacionales**, cada uno con su propio cálculo de tarifa.
- Cálculo dinámico de precios según la clase del asiento (Primera Clase, Ejecutiva, Económica).
- Mapa visual de asientos (verde = libre, rojo = ocupado) generado automáticamente por vuelo.
- Reserva de múltiples asientos en una misma operación (selección de cantidad de pasajeros).
- Registro de datos del pasajero (cédula, nombre, apellido, correo, teléfono).
- Persistencia de vuelos y reservas en archivos `.csv`, con recarga de disponibilidad al reiniciar la aplicación.
- Validación de reglas de negocio mediante una excepción personalizada (`ReservaInvalidaException`), por ejemplo para evitar doble asignación de un mismo asiento.

## 🏗 Arquitectura del Proyecto

El sistema sigue un diseño orientado a objetos con herencia y polimorfismo:

```
Vuelo (clase abstracta)
 ├── VueloNacional   (impuesto local + subsidio estatal)
 └── VueloInternacional (tasa internacional)

VentanaPrincipal (JFrame) -> interfaz gráfica principal y punto de entrada (main)
Asiento          -> representa cada silla del avión (número, clase, estado)
Pasajero         -> datos del titular de la reserva
Reserva          -> vincula pasajero + vuelo + asiento, controla su estado
ReservaInvalidaException -> excepción de negocio para reservas inválidas
```

> Se recomienda incluir un diagrama UML de clases en `docs/diagrama-clases.png` generado con alguna herramienta (draw.io, PlantUML, StarUML, etc.) para complementar esta descripción.

## 💻 Requisitos Previos

- **JDK 11 o superior** (el proyecto usa `java.time.LocalDateTime`).
- Un IDE compatible con Swing (NetBeans, IntelliJ IDEA o Eclipse) o la línea de comandos.
- Conocimientos básicos de Programación Orientada a Objetos (POO), incluyendo clases, objetos, herencia, encapsulamiento y polimorfismo.
- Conocimientos básicos de Java Swing, para comprender la creación de ventanas, botones, paneles, etiquetas y eventos.

## ▶️ Instalación y Ejecución

### Opción 1: Desde un IDE
1. Clona el repositorio: `git clone <URL_DEL_REPOSITORIO>`
2. Abre el proyecto en tu IDE.
3. Ejecuta la clase `poliairlines.VentanaPrincipal` (contiene el método `main`).

### Opción 2: Desde la línea de comandos
```bash
# Compilar
javac -d bin src/poliairlines/*.java

# Ejecutar
java -cp bin poliairlines.VentanaPrincipal
```

Al iniciar por primera vez, la aplicación generará automáticamente los archivos `vuelosNacionales.csv` y `vuelosInternacionales.csv` con datos de ejemplo si no existen.

## 📂 Estructura de Archivos

```
src/
└── poliairlines/
    ├── VentanaPrincipal.java       # Interfaz gráfica y lógica de la aplicación
    ├── Vuelo.java                  # Clase abstracta base de un vuelo
    ├── VueloNacional.java          # Vuelo nacional (extiende Vuelo)
    ├── VueloInternacional.java     # Vuelo internacional (extiende Vuelo)
    ├── Asiento.java                # Modelo de un asiento
    ├── Pasajero.java                # Modelo de un pasajero
    ├── Reserva.java                # Lógica de una reserva
    └── ReservaInvalidaException.java # Excepción personalizada de negocio
```

## 💾 Persistencia de Datos

La aplicación no usa base de datos; utiliza tres archivos CSV generados/leídos en el directorio de ejecución:

| Archivo | Contenido |
|---|---|
| `vuelosNacionales.csv` | Catálogo de vuelos nacionales (código, origen, destino, fecha, impuesto local, subsidio) |
| `vuelosInternacionales.csv` | Catálogo de vuelos internacionales (código, origen, destino, fecha, tasa internacional) |
| `reservas.csv` | Historial de reservas confirmadas (código de reserva, datos del pasajero, vuelo, asiento, estado) |

## ⚠️ Manejo de Excepciones

`ReservaInvalidaException` se lanza cuando se intenta reservar un asiento ya ocupado, evitando así la doble asignación. Incluye un código de error (`codigoError`) y un motivo (`motivoError`) para facilitar el diagnóstico.

## 👤 Estudiantes
- Lenin Antony Cachipuendo Cuascota 
- Matthew Ezequiel Llerena Montoya 
- Eythan David Mafla Benavides 
- Edwin Daniel Paredes Satian 
- Jordy Sebastián Tipantuña Negrete

