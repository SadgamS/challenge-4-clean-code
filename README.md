# Reto 4. Construir una API para la gestión de productos y autenticación
### Brayan Dennis Aguilar Aparicio

## Tecnologías Utilizadas
- Backend: Spring Boot (Java, Maven, Java 17+)
    Por qué: maduro, buen soporte para arquitectura por capas, Spring Security para JWT, Spring Data JPA para repositorios, integración con Flyway/Liquibase para migraciones y gran ecosistema. Facilita aplicar Clean Architecture separando capas y dependencias.
- Base de datos: PostgreSQL
    Por qué: fiable, open-source, buena compatibilidad con contenedores y producción. Adecuada para las entidades relacionales de productos y usuarios.
- Frontend: Angular (TypeScript)
    Por qué: buena separación por módulos, CLI rápido para scaffolding, sistema de servicios e inyección de dependencias que facilita implementar guards/interceptors para auth. Encaja bien con la separación de responsabilidades requerida por la arquitectura limpia del frontend.


