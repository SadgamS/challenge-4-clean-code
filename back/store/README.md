# Store Backend

Este módulo implementa el backend del proyecto `store` usando Spring Boot y una arquitectura tipo Clean/Hexagonal.

**Resumen:**
- **Lenguaje / Framework:** Java 17, Spring Boot
- **Persistencia:** PostgreSQL (configurado via `spring.datasource`)
- **Migraciones:** Flyway (scripts en `src/main/resources/db/migration`)
- **Autenticación:** JWT (cabecera `Authorization: Bearer <token>`)

**Estructura principal**
- `src/main/java/com/bo/store/adapter/in/web` : Controladores HTTP (API REST)
- `src/main/java/com/bo/store/dto` : DTOs y mapeadores
- `src/main/java/com/bo/store/domain/model` : Entidades / objetos de dominio
- `src/main/java/com/bo/store/port` : Puertos (interfaces) de la capa de dominio
- `src/main/java/com/bo/store/usecase` : Implementaciones de casos de uso
- `src/main/resources/application.yaml` : Configuración de la aplicación

**Cómo ejecutar (local)**
Prerequisitos: Java 17+, Docker (opcional), Maven wrapper incluido.

1. Ejecutar con Maven (modo desarrollo):

```
./mvnw spring-boot:run
```

2. Empaquetar y ejecutar el JAR:

```
./mvnw clean package -DskipTests
java -jar back/store/target/*.jar
```

3. Usar Docker (construir imagen):

```
docker build -t store-back ./back/store
docker run -p 8080:8080 --env-file .env store-back
```

Si usas `docker-compose` en la raíz del repo, también puedes levantar todo junto:

```
docker-compose up --build
```

**Configuración importante** (`src/main/resources/application.yaml`)
- `spring.datasource.url`: `jdbc:postgresql://localhost:5432/storedb`
- `spring.datasource.username` / `password`: credenciales de la base de datos
- `spring.jpa.hibernate.ddl-auto`: `none` (las migraciones se gestionan con Flyway)
- `security.jwt.secret`: clave secreta para firmar JWT (debe ser segura y >=32 chars)
- `security.jwt.expiration-ms`: tiempo de expiración en ms (por defecto `3600000` = 1h)

**Migraciones**
- Los scripts de Flyway están en `src/main/resources/db/migration` (por ejemplo `V1__init.sql`).
- Asegúrate de que la base de datos PostgreSQL esté accesible para que Flyway aplique las migraciones al iniciar.

**API / Endpoints**
Nota: Todas las rutas excepto las bajo `/auth/**` requieren un `Authorization: Bearer <token>` válido.

- **Autenticación**
	- `POST /auth/register`
		- Request: `AuthRequest` { `username`, `password` }
		- Response: `201 Created` (sin body)
		- Uso: registra un nuevo usuario.

	- `POST /auth/token`
		- Request: `AuthRequest` { `username`, `password` }
		- Response: `200 OK` con `AuthResponse` { `accessToken`, `tokenType`, `expiresIn` }
		- Uso: obtiene un token JWT para autenticación.

- **Productos** (`/products`)
	- `GET /products`
		- Response: `200 OK` lista de `ProductDTO`
	- `GET /products/{id}`
		- Response: `200 OK` `ProductDTO`
	- `POST /products`
		- Request: `ProductDTO` (sin `id` para creación)
		- Response: `201 Created` con `Location: /products/{id}` y body `ProductDTO`
	- `PUT /products/{id}`
		- Request: `ProductDTO`
		- Response: `200 OK` `ProductDTO` actualizado
	- `DELETE /products/{id}`
		- Response: `204 No Content`

**DTOs relevantes**
- `AuthRequest` : `{ username: String, password: String }`
- `AuthResponse` : `{ accessToken: String, tokenType: "Bearer", expiresIn: Long }`
- `ProductDTO` : `{ id: Long, name: String, description: String, price: BigDecimal, stock: Integer }`

**Seguridad (JWT)**
- Implementación: `com.bo.store.security.JwtUtil` genera y valida tokens.
- Header: enviar `Authorization: Bearer <token>` en peticiones protegidas.
- El token contiene el `subject` (username) y un claim `roles` con roles separados por comas.
- Configuración: la clave se lee de `security.jwt.secret` en `application.yaml`.

**Ejemplos rápidos (curl)**

- Registrar usuario:

```
curl -X POST http://localhost:8080/auth/register \
	-H "Content-Type: application/json" \
	-d '{"username":"user1","password":"secret"}'
```

- Obtener token:

```
curl -X POST http://localhost:8080/auth/token \
	-H "Content-Type: application/json" \
	-d '{"username":"user1","password":"secret"}'
```

- Listar productos (con token):

```
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8080/products
```

**Base de datos de desarrollo (ejemplo con Docker)**

```
docker run -d --name store-postgres -e POSTGRES_DB=storedb -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 postgres:15
```


