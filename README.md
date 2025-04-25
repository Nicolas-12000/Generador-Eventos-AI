# Generador AI de Landing Pages para Eventos

## 1. Descripción

Este proyecto tiene como objetivo desarrollar una aplicación web innovadora utilizando **Spring Boot (Java 21)** que simplifica la creación de landing pages para eventos. La idea central es permitir a los usuarios interactuar con un **chatbot impulsado por Inteligencia Artificial (Azure OpenAI Service)** para describir los detalles de su evento (nombre, fecha, lugar, ponentes, descripción, etc.).

La aplicación procesará esta conversación, extraerá la información relevante y, utilizando la IA, generará el contenido textual necesario. Este contenido se integrará dinámicamente en **plantillas HTML/CSS predefinidas (gestionadas con Thymeleaf)**, resultando en una landing page funcional y estéticamente agradable para el evento, incluyendo un mapa interactivo (Google Maps) con la ubicación.

Se busca ofrecer una solución rápida, personalizada y eficiente para la promoción de eventos, reduciendo el esfuerzo técnico requerido por parte del usuario.

## 2. Arquitectura del Software

El proyecto se desarrollará siguiendo los principios de la **Arquitectura Limpia (Clean Architecture)** y la **Arquitectura Hexagonal (Ports and Adapters)**. Esta elección busca maximizar la **modularidad, testeabilidad, mantenibilidad y escalabilidad** de la aplicación, desacoplando el núcleo de la lógica de negocio de las dependencias externas (frameworks, bases de datos, APIs).

La estructura conceptual se dividirá en las siguientes capas:

* **Capa de Dominio (Domain Core):**
    * Contendrá las **entidades de negocio** (`Evento`, `Usuario`, `MensajeChat`, `Plantilla`, etc.) con sus atributos y lógica de negocio intrínseca (reglas de validación, invariantes).
    * Será la capa más interna y **no tendrá dependencias** de ninguna otra capa.
    * Definirá las **interfaces (puertos)** que necesita para operaciones externas (ej. `EventoRepositoryPort`, `ChatRepositoryPort`).

* **Capa de Aplicación (Application / Use Cases):**
    * Orquestará los **casos de uso** del sistema (ej. `CrearEventoDesdeChatUseCase`, `AutenticarUsuarioUseCase`, `ObtenerHistorialChatsUseCase`).
    * Implementará la lógica de aplicación, coordinando la interacción entre el dominio y la infraestructura.
    * **Dependerá únicamente de la capa de Dominio**.
    * Definirá interfaces (puertos) para las herramientas que necesita (ej. `ProveedorAIPort`, `ServicioTokensPort`, `MapaServicePort`).

* **Capa de Infraestructura (Infrastructure / Adapters):**
    * Contendrá las **implementaciones concretas** de los puertos definidos en las capas internas y gestionará la interacción con el mundo exterior.
    * Se dividirá en:
        * **Adaptadores de Entrada (Driving Adapters):** Puntos de entrada que invocan los casos de uso.
            * Controladores Spring Web (API REST para el chat, MVC para servir las páginas y plantillas Thymeleaf).
        * **Adaptadores de Salida (Driven Adapters):** Implementaciones que interactúan con servicios externos.
            * Implementación de Repositorios con Spring Data JPA (para MySQL).
            * Cliente HTTP/SDK para interactuar con Azure OpenAI Service.
            * Implementación del servicio de autenticación JWT con Spring Security.
            * Integración con la API de Google Maps (principalmente en el frontend, pero podría haber lógica de backend si es necesario).

Esta separación estricta permite cambiar o actualizar tecnologías en la capa de infraestructura (ej. cambiar de MySQL a PostgreSQL, o de Azure OpenAI a otro proveedor de IA) con un impacto mínimo en las capas de Aplicación y Dominio.

## 3. Características Planeadas

### Versión Inicial (MVP)

* **Autenticación:** Registro e inicio de sesión de usuarios (Spring Security + JWT).
* **Chatbot Básico:** Interfaz de chat (Thymeleaf + JS) para recoger información del evento.
* **Generación de Contenido IA:** Integración con Azure OpenAI para generar texto descriptivo.
* **Plantillas Fijas:** Uso de 1-2 plantillas Thymeleaf predefinidas para la estructura de la landing page.
* **Renderizado de Landing Page:** Generación y visualización de la página del evento con el contenido de la IA y mapa básico (Google Maps).
* **Historial Simple:** Listado de eventos/chats creados por el usuario con opción de borrado.
* **Persistencia:** Almacenamiento en base de datos MySQL (Usuarios, Eventos, Chats).

### Mejoras Futuras

* **Múltiples Plantillas:** Permitir al usuario elegir entre varias plantillas de diseño.
* **Personalización vía Chat:** Interpretar peticiones básicas de estilo (ej. "colores corporativos", "tono formal/informal") para ajustar la generación de IA y/o selección/modificación de CSS en la plantilla.
* **Gestión de Tokens IA:** Monitorizar y (opcionalmente) limitar el uso de la API de IA por usuario/evento.
* **Previsualización en Chat:** Mostrar un borrador o resumen de la landing page directamente en la interfaz del chat antes de la generación final.
* **Mejoras UI/UX:** Refinar la interfaz del chat y la gestión del historial.
* **Despliegue:** Contenerización (Docker) y scripts para despliegue en la nube (ej. Azure).
* **Pruebas:** Cobertura extensiva de pruebas unitarias, de integración y E2E.

## 4. Pila Tecnológica Principal

* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3.x
* **Base de Datos:** MySQL
* **Persistencia:** Spring Data JPA / Hibernate
* **Seguridad:** Spring Security (JWT)
* **Motor de Plantillas:** Thymeleaf
* **Build Tool:** Maven
* **IA:** Azure OpenAI Service
* **Mapas:** Google Maps JavaScript API
* **Desarrollo:** Lombok, Spring Boot DevTools

## 5. Cómo Empezar (Configuración Inicial)

### Prerrequisitos

* JDK 21+
* Maven 3.8+
* Servidor MySQL accesible
* Cuenta de Azure con acceso a OpenAI Service (Endpoint y API Key)
* API Key de Google Maps Platform (habilitada para Maps JavaScript API)

### Pasos

1.  **Clonar el Repositorio:** `git clone <URL>`
2.  **Configurar `application.properties` (o `.yml`):**
    * Detalles de conexión a MySQL (`spring.datasource.*`).
    * Credenciales de Azure OpenAI (`azure.openai.endpoint`, `azure.openai.key`, etc.).
    * Secreto para JWT (`jwt.secret`, `jwt.expiration.ms`).
3.  **Compilar:** `mvn clean install`
4.  **Ejecutar:** `mvn spring-boot:run`
5.  **Inyectar API Key de Google Maps:** Asegúrate de incluir tu API Key de Google Maps en el código HTML/JavaScript del frontend donde se carga el mapa.

---

*Este README es un documento vivo y se actualizará a medida que el proyecto avance.*
