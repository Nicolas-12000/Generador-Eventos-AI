# 📱 Generador AI de Landing Pages para Eventos

### 📝 Descripción

Aplicación web desarrollada con **Spring Boot (Java 21)** que permite a los usuarios crear landing pages para eventos conversando con un chatbot potenciado por **Inteligencia Artificial (Azure OpenAI Service)**.  
A partir de esa conversación se genera contenido personalizado que se integra en **plantillas Thymeleaf** y se renderiza como una página funcional con **Google Maps** embebido.

El enfoque es ofrecer una solución **simple, eficiente y escalable**, evitando sobre-ingeniería y dependencias innecesarias, pero aplicando patrones de diseño esenciales para mantener un código limpio y duradero.

---

## ⚙️ Arquitectura

Se sigue una arquitectura basada en principios de **Clean Architecture** y **Hexagonal Architecture (Ports and Adapters)**, separando de forma clara:

- **Dominio**: Entidades y lógica de negocio sin dependencias externas.
- **Casos de Uso (Application)**: Coordinan la lógica aplicativa.
- **Infraestructura**: Controladores, clientes externos y persistencia.

---

## 📐 Patrones de Diseño Utilizados (Por Qué y Dónde)

| Patrón                    | Uso                                                              |
|:--------------------------|:-----------------------------------------------------------------|
| **Singleton**              | Para clientes de **Azure OpenAI** y **Google Maps**.             |
| **Factory / Builder**      | Para construir entidades `Evento` a partir de mensajes de chat.  |
| **Chain of Responsibility**| Para modularizar el procesamiento de mensajes en el chatbot.     |
| **Decorator**              | Para añadir dinámicamente secciones al codigo base y a las landing. |
|**Adapter (Ports & Adapters)**  |	Como parte de la Arquitectura Hexagonal, conecta el dominio con controladores, repositorios y servicios externos|

👉 Solo estos **5 patrones**, por ser los que realmente aportan valor inmediato sin generar complejidad innecesaria.

---

## 🖥️ Características

✅ Registro e inicio de sesión con JWT  
✅ Interfaz de chat para describir eventos  
✅ Generación de contenido descriptivo con Azure OpenAI  
✅ Renderizado de landing page con Thymeleaf y Google Maps  
✅ Historial de eventos creados  
✅ Persistencia con MySQL  

---

## 📦 Pila Tecnológica

- **Java 21**
- **Spring Boot 3.x**
- **Thymeleaf**
- **MySQL** + Spring Data JPA
- **Spring Security (JWT)**
- **Azure OpenAI Service**
- **Google Maps JavaScript API**
- **Maven**
- **Lombok** (solo para reducir boilerplate, opcional según preferencia)

---

## 📑 Filosofía de Desarrollo

✅ **Simple pero funcional**  
✅ **Robusto sin sobre-ingeniería**  
✅ **Mínimas dependencias externas**  
✅ **Código limpio, mantenible y duradero**  
✅ **Patrones de diseño solo donde realmente aportan valor**

---

## 🛠️ Configuración Inicial

### Requisitos

- JDK 21+
- Maven 3.8+
- Servidor MySQL
- Cuenta de Azure con Azure OpenAI
- API Key de Google Maps Platform

### Pasos

git clone <URL>

### Configura application.properties:

spring.datasource.url=
spring.datasource.username=
spring.datasource.password=

azure.openai.endpoint=
azure.openai.key=

jwt.secret=
jwt.expiration.ms=

### Compila y ejecuta:


mvn clean install
mvn spring-boot:run
Incluye tu API Key de Google Maps en los scripts de los templates HTML.