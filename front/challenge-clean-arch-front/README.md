# Frontend - Challenge Clean Architecture

Este proyecto es el frontend de la aplicación `store`, escrito en Angular (v21). Proporciona la interfaz para autenticación y gestión de productos siguiendo una estructura simple y modular.

**Resumen:**
- **Framework:** Angular 21
- **Estado:** aplicación de ejemplo para demostración de Clean Architecture
- **API base:** configurada en `src/environments/environment.ts` como `apiUrl: '/api'` (por defecto la app espera que el backend esté accesible bajo `/api`)

**Estructura principal**
- `src/app/app.routes.ts` : Rutas de la aplicación
- `src/app/auth` : Componentes de autenticación (`login`, `register`)
- `src/app/core` : Servicios y utilidades (`AuthService`, `TokenInterceptor`, `AuthGuard`)
- `src/app/products` : Componentes para listar y editar/crear productos
- `src/app/services` : Servicios para consumir la API (`ProductService`)
- `src/environments` : Configuración por entorno

**Rutas / Navegación** (definidas en `app.routes.ts`)
- `GET /` → `ProductListComponent` (protegida por `AuthGuard`)
- `GET /products/new` → `ProductFormComponent` (creación)
- `GET /products/:id/edit` → `ProductFormComponent` (edición)
- `GET /login` → `LoginComponent`
- `GET /register` → `RegisterComponent`

Las rutas protegidas requieren que el usuario esté autenticado; el `AuthGuard` utiliza `AuthService.isAuthenticated()`.

**Servicios y contratos con la API**
- `AuthService` (en `src/app/core/auth.service.ts`)
	- `POST ${environment.apiUrl}/auth/register` : registra usuario ({ username, password })
	- `POST ${environment.apiUrl}/auth/token` : obtiene token JWT (envía { username, password })
	- El token se almacena en `localStorage` bajo la clave `store_token`.

- `ProductService` (en `src/app/services/product.service.ts`)
	- `GET ${environment.apiUrl}/products` : lista productos
	- `GET ${environment.apiUrl}/products/{id}` : obtiene producto
	- `POST ${environment.apiUrl}/products` : crea producto
	- `PUT ${environment.apiUrl}/products/{id}` : actualiza producto
	- `DELETE ${environment.apiUrl}/products/{id}` : elimina producto

**Intercepción de token**
- `TokenInterceptor` añade la cabecera `Authorization: Bearer <token>` a las peticiones si `AuthService.getToken()` tiene un token válido.
- En caso de respuesta `401`, el interceptor hace `logout()` y redirige a `/login`.

**Modelos (cliente)**
- `src/app/models/product.model.ts` : interfaz `Product` usada en los formularios y servicios con campos: `id?, name, description, price, stock`.

**Cómo ejecutar (desarrollo)**

Prerequisitos: Node.js (recomendado v18+), npm, Angular CLI opcional.

1. Instalar dependencias:

```bash
npm install
```

2. Ejecutar en modo desarrollo:

```bash
npm start
# o
ng serve
```

Por defecto `ng serve` levantará la app en `http://localhost:4200`. Recuerda que `environment.apiUrl` apunta a `/api`, por lo que para desarrollo local puedes usar un proxy o configurar el backend para servir bajo `/api`.

Ejemplo de proxy `proxy.conf.json` para desarrollo (opcional):

```json
{
	"/api": {
		"target": "http://localhost:8080",
		"secure": false,
		"changeOrigin": true
	}
}
```

Y arrancar con:

```bash
ng serve --proxy-config proxy.conf.json
```

**Construir para producción**

```bash
npm run build
```

La carpeta `dist/` contendrá la build optimizada.

**Docker / Nginx**
- Este repositorio incluye un `Dockerfile` y `nginx.conf` (en la raíz del frontend) que permiten construir una imagen estática y servir la app con Nginx. Asegúrate de ajustar `environment.prod.ts` para apuntar al backend correcto.

**Ejemplos rápidos (interacción con la API)**

- Registrar usuario:

```bash
curl -X POST http://localhost:8080/api/auth/register \
	-H "Content-Type: application/json" \
	-d '{"username":"user1","password":"secret"}'
```

- Obtener token:

```bash
curl -X POST http://localhost:8080/api/auth/token \
	-H "Content-Type: application/json" \
	-d '{"username":"user1","password":"secret"}'
```

- Listar productos (desde frontend la petición se hace a `/api/products`, Token automático si está en `localStorage`):

```
GET /api/products
Headers: Authorization: Bearer <TOKEN>
```

