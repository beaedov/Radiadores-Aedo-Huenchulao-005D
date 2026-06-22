# Radiadores del Puerto – Sistema de Microservicios

Este proyecto nace de la necesidad de digitalizar la gestión del taller **Radiadores del Puerto**. El plan es dejar el papel y las planillas sueltas para tener un sistema ordenado donde se pueda administrar a los clientes, sus vehículos, las cotizaciones, las órdenes de reparación, los pagos, las facturas, las citas y el catálogo de productos y servicios, todo en un mismo lugar.

Para realizarlo decidimos trabajar con una **arquitectura de microservicios**, donde cada parte del negocio funciona de manera independiente pero conectada al resto a través de un API Gateway. Esto nos permitió dividir el trabajo, mantener el código más ordenado y dejar la puerta abierta a que el sistema pueda crecer en el futuro sin tener que reescribirlo todo.

## Sobre el proyecto

La aplicación está pensada para que el taller pueda llevar el control completo del flujo de trabajo: desde que un cliente llega con su vehículo, se le hace una cotización, se aprueba una orden de reparación, se asigna un mecánico, se gestionan los repuestos, se registra el pago y se emite la factura. Cada uno de estos procesos vive en su propio microservicio, lo que hace que si algo falla en una parte, el resto del sistema siga funcionando.

---

## Arquitectura

El sistema está formado por **10 microservicios**, un **microservicio de autenticación** y un **API Gateway** que se encarga de enrutar todas las peticiones y validar los tokens de seguridad.

| Microservicio | Para qué sirve |
|---|---|
| `service-cliente` | Maneja todo lo relacionado con los clientes del taller y sus reseñas. |
| `service-vehiculo` | Registra y administra los vehículos y sus tipos. |
| `service-catalogo` | Mantiene el catálogo de radiadores, categorías y compatibilidades. |
| `service-cotizacion` | Genera y gestiona las cotizaciones y solicitudes. |
| `service-orden-reparacion` | Controla las órdenes de reparación, estados y garantías. |
| `service-mecanico` | Administra los mecánicos y sus especialidades. |
| `service-pago` | Gestiona los pagos y los métodos de pago. |
| `service-repuesto` | Administra los repuestos y sus categorías. |
| `service-factura` | Genera las facturas y sus detalles. |
| `service-agenda` | Maneja las citas y los tipos de cita. |
| `service-auth` | Microservicio de autenticación: registro, login y generación de tokens JWT. |
| `api-gateway` | Punto de entrada único que enruta las peticiones y valida los tokens. |

---

## Funcionalidades destacadas

- **Documentación con Swagger / OpenAPI**: cada microservicio expone su documentación interactiva, centralizada en el API Gateway mediante un menú desplegable.
- **Pruebas unitarias** con JUnit 5 y Mockito (tests puros y tests con WebClient simulado).
- **Seguridad con JWT**: el `service-auth` genera tokens firmados, y el API Gateway actúa como guardia validando cada petición a las rutas protegidas.
- **Comunicación entre microservicios** mediante WebClient.

---

## Tecnologías

- **Java 21**
- **Spring Boot 3.x**
- **Spring Cloud Gateway** (para el API Gateway)
- **Spring Data JPA** (para conectar con la base de datos)
- **Spring WebFlux** (para la comunicación reactiva entre microservicios)
- **Spring Security** (para la autenticación en `service-auth`)
- **JWT (jjwt 0.11.5)** (para los tokens de seguridad)
- **springdoc-openapi 2.7.0** (para Swagger)
- **Maven** (gestor de dependencias)
- **MySQL** administrado con **Laragon** (phpMyAdmin para gestión visual)
- **Lombok** (para reducir código repetitivo)
- **JUnit 5 + Mockito** (para las pruebas unitarias)
- **Postman** (para probar los endpoints)
- **SLF4J** (para logs estructurados mediante `@Slf4j`)

---

## Requisitos previos

Antes de levantar el proyecto necesitas tener instalado:

- Java JDK 21
- Laragon (incluye MySQL y phpMyAdmin) o simplemente phpMyAdmin
- Git

---

## Instalación

1. **Clona el repositorio:**

```bash
git clone https://github.com/beaedov/Radiadores-Aedo-Huenchulao-005D.git
cd Radiadores-Aedo-Huenchulao-005D
```

2. **Inicia Laragon** y enciende MySQL.

3. **Entra a phpMyAdmin** (`http://localhost/phpmyadmin`) y crea las bases de datos necesarias, una para cada microservicio:

```sql
CREATE DATABASE db_cliente;
CREATE DATABASE db_vehiculo;
CREATE DATABASE db_catalogo;
CREATE DATABASE db_cotizacion;
CREATE DATABASE db_orden_reparacion;
CREATE DATABASE db_mecanico;
CREATE DATABASE db_pago;
CREATE DATABASE db_repuesto;
CREATE DATABASE db_factura;
CREATE DATABASE db_agenda;
CREATE DATABASE db_seguridad;
```

4. **Crea las tablas de seguridad** en `db_seguridad`:

```sql
USE db_seguridad;

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE usuario_roles (
    usuario_id BIGINT NOT NULL,
    rol_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

INSERT INTO roles (nombre_rol) VALUES ('CLIENTE'), ('MECANICO'), ('ADMINISTRADOR');
```

5. **Compila los microservicios** con Maven:

```bash
mvn clean install
```

---

## Configuración

Cada microservicio tiene su propio `application.properties` donde se define la conexión a la base de datos y el puerto. Ejemplo típico:

```properties
# Puerto del microservicio
server.port=8087

# Conexión a la base de datos (MySQL desde Laragon)
spring.datasource.url=jdbc:mysql://localhost:3306/db_mecanico
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> Por defecto, MySQL en Laragon usa el usuario `root` **sin contraseña**. 

### Puertos

| Servicio | Puerto |
|---|---|
| api-gateway | 9090 |
| service-auth | 8081 |
| service-catalogo | 8082 |
| service-cliente | 8083 |
| service-cotizacion | 8084 |
| service-orden-reparacion | 8085 |
| service-vehiculo | 8086 |
| service-mecanico | 8087 |
| service-pago | 8088 |
| service-repuesto | 8089 |
| service-factura | 8090 |
| service-agenda | 8091 |

---

## Uso de la autenticación

Las rutas `/api/v1/**` están protegidas. Para acceder a ellas necesitas un token JWT.

1. **Registrar un usuario:** `POST http://localhost:9090/auth/registrar`

```json
{
    "nombreUsuario": "pepe",
    "contrasena": "123",
    "correo": "pepe@radiadores.com"
}
```

2. **Iniciar sesión:** `POST http://localhost:9090/auth/login`

```json
{
    "nombreUsuario": "pepe",
    "contrasena": "123"
}
```

Devuelve un token.

3. **Usar el token:** en cada petición a una ruta protegida, agrega la cabecera de autorización en Postman → pestaña **Authorization** → tipo **Bearer Token** → pega el token.

---

## Documentación Swagger

Con el API Gateway levantado, accede a la documentación centralizada en:

```
http://localhost:9090/swagger-ui.html
```

Usa el menú desplegable **"Select a definition"** para cambiar entre los servicios documentados.

---

## Pruebas unitarias

El proyecto incluye pruebas unitarias con JUnit 5 y Mockito:

- **Test puro** (`MecanicoServiceTest`): prueba un servicio que solo usa el repositorio, simulando la base de datos con Mockito.
- **Test con WebClient** (`PagoServiceTest`): prueba un servicio que llama a otro microservicio, simulando la cadena del WebClient.

Para ejecutarlas, usa el botón ▶ junto a cada clase de test en IntelliJ, o:

```bash
mvn test
```

---

## Estructura del proyecto

```
Radiadores-Aedo-Huenchulao-005D/
│
├── api-gateway/
├── service-auth/
├── service-cliente/
├── service-vehiculo/
├── service-catalogo/
├── service-cotizacion/
├── service-orden-reparacion/
├── service-mecanico/
├── service-pago/
├── service-repuesto/
├── service-factura/
├── service-agenda/
│
└── README.md
```

Cada microservicio sigue una estructura estándar de Spring Boot:

```
service-xxxxx/
├── src/
│   ├── main/
│   │   ├── java/com/radiadores/service_xxxxx/
│   │   │   ├── controller/    → Controladores REST
│   │   │   ├── service/       → Lógica de negocio
│   │   │   ├── repository/    → Repositorios JPA
│   │   │   ├── model/         → Entidades JPA
│   │   │   ├── config/        → Configuración (Swagger, etc.)
│   │   │   └── Application.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

---

## Autores

- **Benjamin Aedo** – [@beaedov]
- **Thomas Huenchulao** – [@thuenchulao]

Proyecto desarrollado para **FullStack1 – Sección 005D**.
