# 📱 Generador AI de Landing Pages para Eventos

### 📝 Descripción

Aplicación web desarrollada con **Spring Boot (Java 21)** que permite a los usuarios crear landing pages para eventos conversando con un chatbot potenciado por **Azure OpenAI Service**. A partir de esa conversación se genera contenido personalizado que se integra en **plantillas Thymeleaf** y se renderiza como una página funcional con **Google Maps** embebido.

El enfoque es ofrecer una solución **simple, eficiente y escalable**, evitando sobre-ingeniería y aprovechando las capacidades que Spring provee de forma nativa para aplicar patrones de diseño cuando aportan valor.

---

## ⚙️ Arquitectura

Se adopta una versión *ligera* de Hexagonal + Clean Architecture, basada en capas mínimas:

```text
┌──────────────┐     ┌────────────┐     ┌────────────┐
│  Controller  │ →   │  Service   │ →   │ Repository │
└──────────────┘     └────────────┘     └────────────┘
        │                  │                  │
        └──→ DTOs / Mappers│                  │
                           └──→ Entidades     │
                                              └──→ JPA / MySQL
```

* **Controller**: Maneja peticiones HTTP y render Thymeleaf.
* **Service**: Orquesta lógica de negocio, llamadas a Azure OpenAI y transformaciones.
* **Repository**: Usa Spring Data JPA para persistencia.

Este enfoque reduce la complejidad de múltiples puertos/adapters, manteniendo separación de responsabilidades y un monolito modular.

---

## 📐 Patrones de Diseño y Funcionalidades Spring

| Patrón / Funcionalidad          | Implementación en Spring                                                                                                              |
| ------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| **Singleton**                   | Todos los `@Service` y `@Bean` son singletons por defecto.                                                                            |
| **Proxy / AOP**                 | `@Transactional`, `@Cacheable`, `@Retryable` (Spring Retry) para transacciones, cache y reintentos.                                   |
| **Factory / Builder**           | Lombok `@Builder` o fábricas estáticas para construir `Evento`.                                                                       |
| **Adapter**                     | MapStruct o mappers manuales para DTO ↔ Entidad.                                                                                      |
| **Facade**                      | Servicios que encapsulan llamadas a Azure OpenAI y Google Maps.                                                                       |
| ~~**Chain of Responsibility**~~ | Gestionado internamente por filtros de Spring MVC (`OncePerRequestFilter`, `HandlerInterceptor`) en lugar de implementar manualmente. |
| **Strategy**                    | Definir estrategias de renderizado o proveedores de mapas intercambiables.                                                            |
| **Circuit Breaker / Retry**     | Spring Cloud Circuit Breaker o Spring Retry para resiliencia al llamar APIs externas.                                                 |

> ⚠️ Se omite Chain of Responsibility explícito para no duplicar la infraestructura que Spring ya ofrece.

---

## 🖥️ Características Principales

* **Autenticación JWT** con `spring-security`.
* **Interfaz de chat** para describir eventos.
* **Generación de contenido** con Azure OpenAI.
* **Renderizado** de landing pages con Thymeleaf y Google Maps.
* **Historial** de eventos creados.
* **Persistencia** en MySQL con Spring Data JPA.
* **Resiliencia**: reintentos y circuit breaker al invocar servicios externos.

---

## 📦 Pila Tecnológica

* Java 21
* Spring Boot 3.x
* Thymeleaf
* MySQL + Spring Data JPA
* Spring Security (JWT)
* Azure OpenAI Service
* Google Maps JavaScript API
* Maven
* Lombok (opcional)

---

## ⚙️ Configuración Inicial

1. Clonar repositorio:

   ```bash
   git clone <URL>
   cd <directorio>
   ```

2. Ajustar `src/main/resources/application.properties` con lo siguiente (no usar Docker por ahora):

   ```properties
   spring.application.name=Generador Eventos AI

   # Puerto del servidor
   server.port=8084

   # Database
   spring.datasource.url=jdbc:mysql://localhost:3306/eventpages
   spring.datasource.username=root
   spring.datasource.password=
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true

   # DEBUG de mapeos de columnas y tipos
   logging.level.org.hibernate.mapping=DEBUG
   logging.level.org.hibernate.dialect.Dialect$SizeStrategyImpl=TRACE

   # JWT
   jwt.secret=yourSuperSecretKeyThatIsVeryLongAndSecureShhh
   jwt.expiration-in-ms=86400000
   ```

3. Compilar y ejecutar sin contenedores:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## 🧩 Microservicios: ¿Sí o No?: ¿Sí o No?

* **Monolito Modular** (recomendado para MVP): Rápido de implementar, más fácil de desplegar y mantener cuando el equipo es pequeño.
* **Microservicios** (considerar en fase avanzada): Útiles si prevés escalado independiente de módulos (chat, generación AI, gestión de eventos), pero conlleva mayor complejidad (deploy, monitoreo, comunicación entre servicios).

> Para el plazo y alcance actual, se sugiere un monolito modular. Se puede evolucionar a microservicios en iteraciones posteriores si la carga o complejidad crece.

---

¡Listo! Con este README tendrás una guía clara para implementar de forma ágil y mantenible, aprovechando las capacidades de Spring sin caer en sobre-ingeniería. Si quieres ajustar algo más, avísame.

```
```
