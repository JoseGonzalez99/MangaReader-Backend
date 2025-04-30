# 📚 JMangaReader-Backend

Bienvenido al backend del sistema **JMangaReader**, una aplicación robusta diseñada para gestionar mangas, usuarios y su experiencia de lectura. Esta arquitectura utiliza **Spring Boot**, **MongoDB** para usuarios y trazabilidad, y **PostgreSQL** para contenido estructurado como mangas, volúmenes, capítulos, páginas y proveedores.

---

## ⚙️ Tecnologías Principales

- **Java 17**
- **Spring Boot 3.4+**
- **MongoDB** (usuarios, tokens, auditoría)
- **PostgreSQL** (contenido estructurado)
- **Spring Security con JWT + Refresh Tokens**
- **Swagger/OpenAPI**
- **Firebase Authentication (Android/iOS/Web)**
- **Lombok, MapStruct, Validation**

---

## 📁 Estructura de Carpetas

```
jaitymangareader
├── auth               # Módulo de autenticación (JWT, refresh, revoke)
├── audit              # Trazabilidad de sesiones, eventos de usuario
├── user               # Usuarios, perfiles, contexto, preferencias
├── content            # Mangas, volúmenes, capítulos, páginas, proveedores
├── interactioncontent # Comentarios, likes, historial
├── core               # Errores, DTOs comunes, utilidades
├── config             # Seguridad, CORS, OpenAPI, Inicialización
```

---

## 🔐 Seguridad

- Autenticación vía **JWT**
- Soporte de **Refresh Token**
- **Revoke Token** para logout seguro (jti)
- **Firebase Authentication** para apps móviles y frontend (token exchange)
- Roles: `STAFF`, `CLIENT`
- Endpoints protegidos con `@PreAuthorize`

---

## 🧾 MongoDB (Documentos)

| Colección         | Descripción                              |
|------------------|------------------------------------------|
| `users`          | Usuarios del sistema                     |
| `refresh_tokens` | Tokens persistentes                      |
| `revoked_tokens` | JWTs revocados                           |
| `user_events`    | Auditoría (LOGIN, LOGOUT, etc)           |
| `comments`       | Comentarios de usuarios                  |
| `user_contexts`  | Contexto: preferencias, historial, etc.  |

---

## 🗃 PostgreSQL (Entidades)

- `mangas`: título, autor, portada
- `volumes`: volumen de un manga
- `chapters`: capítulos por volumen
- `chapter_sources`: fuentes por idioma/proveedor
- `pages`: páginas del capítulo
- `providers`: origen de los mangas

Incluye triggers automáticos para `chapter_count` y `volume_count`.

---

## 📘 Endpoints Principales

### 🔐 Autenticación
- `POST /api/v1/auth/login`
- `POST /api/v1/auth/register`
- `POST /api/v1/auth/logout`
- `POST /api/v1/auth/refresh`
- `POST /api/v1/auth/firebase-login`

### 👤 Usuario
- `GET /api/v1/me`
- `PUT /api/v1/me`
- `PUT /api/v1/me/password`
- `GET /api/v1/me/context/preferences`
- `PUT /api/v1/me/context/preferences`
- `GET /api/v1/me/context/history`
- `GET /api/v1/me/context/last-read`

### 📚 Contenido
- `GET /api/v1/mangas`
- `GET /api/v1/mangas/{id}`
- `GET /api/v1/mangas/{mangaId}/volumes`
- `GET /api/v1/volumes/{id}`
- `GET /api/v1/volumes/{volumeId}/chapters`
- `GET /api/v1/chapters/{id}`
- `GET /api/v1/public/pages/by-chapter/{chapterId}?lang=es&providerId=...`
- `GET /api/v1/public/sources/{chapterId}/available-sources`

---

## 🚀 Ejecutar localmente

```bash
# MongoDB
docker run -d -p 27017:27017 --name mongo mongo

# PostgreSQL
docker run -d -p 5432:5432 --name postgres -e POSTGRES_USER=${USER} -e POSTGRES_PASSWORD=${SECRET} -e POSTGRES_DB=manga_db postgres

# Compilar
mvn clean install

# Ejecutar
mvn spring-boot:run
```

---

## 🧪 Testing

- Swagger disponible en `/swagger-ui.html`
- Librería `springdoc-openapi-starter-webmvc-ui`
- Archivos de prueba automatizada incluidos

---

## 🧠 Autor

Desarrollado por Jose Gonzalez.
