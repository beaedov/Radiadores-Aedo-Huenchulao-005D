[README_FULLSTACK.md](https://github.com/user-attachments/files/27940253/README_FULLSTACK.md)
# Radiadores del Puerto – Sistema de Microservicios

Este proyecto nace de la necesidad de digitalizar la gestión del taller **Radiadores del Puerto**. El plan es dejar el papel y las planillas sueltas para tener un sistema ordenado donde se pudieran administrar los clientes, sus vehículos, las cotizaciones, las órdenes de reparación y el catálogo de productos y servicios, todo en un mismo lugar.

Para relizarlo decidimos trabajar con una **arquitectura de microservicios**, donde cada parte del negocio funciona de manera independiente pero conectada al resto a través de un API Gateway. Esto nos permitió dividir el trabajo, mantener el código más ordenado y dejar la puerta abierta a que el sistema pueda crecer en el futuro sin tener que reescribirlo todo.

## Sobre el proyecto

La aplicación está pensada para que el taller pueda llevar el control completo del flujo de trabajo: desde que un cliente llega con su vehículo, se le hace una cotización, se aprueba una orden de reparación y se consulta el catálogo de servicios disponibles. Cada uno de estos procesos vive en su propio microservicio, lo que hace que si algo falla en una parte, el resto del sistema siga funcionando.

---

## Arquitectura

El sistema está formado por **5 microservicios** y un **API Gateway** que se encarga de enrutar todas las peticiones:

| Microservicio | Para qué sirve |

| `service-cliente` | Maneja todo lo relacionado con los clientes del taller. |
| `service-vehiculo` | Registra y administra los vehículos asociados a cada cliente. |
| `service-catalogo` | Mantiene el catálogo de productos y servicios disponibles. |
| `service-cotizacion` | Genera y gestiona las cotizaciones. |
| `service-orden-reparacion` | Controla las órdenes de reparación activas y completadas. |
| `api-gateway` | Punto de entrada único que comunica al cliente con los microservicios. |

---

## Tecnologías

Para construir el sistema usamos las siguientes herramientas:

- **Java 21**
- **Spring Boot 3.x**
- **Spring Cloud Gateway** (para el API Gateway)
- **Spring Data JPA** (para conectar con la base de datos)
- **Spring WebFlux** (para manejo reactivo entre microservicios)
- **Maven** (gestor de dependencias)
- **MySQL** corriendo sobre **XAMPP** (con phpMyAdmin para administrar visualmente las bases de datos)
- **Lombok** (para reducir código repetitivo)
- **Postman** (para probar los endpoints)
- **SLF4J** (para logs estructurados mediante la anotación `@Slf4j` de Lombok)

---

## Dependencias principales

Cada microservicio incluye en su `pom.xml` las siguientes dependencias:

```xml
<!-- Spring Boot Starter Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Boot Starter WebFlux -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>

<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Validación -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- Conector MySQL -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- Spring Boot Test -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

### Dependencia adicional para el API Gateway

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

---

## Requisitos previos

Antes de levantar el proyecto necesitas tener instalado:

- [Java JDK 17 o superior](https://www.oracle.com/java/technologies/downloads/)
- [XAMPP](https://www.apachefriends.org/) (que ya incluye MySQL y phpMyAdmin)
- [Git](https://git-scm.com/)

---

## Instalación

1. **Clona el repositorio:**

```bash
git clone https://github.com/beaedov/Radiadores-Aedo-Huenchulao-005D.git
cd Radiadores-Aedo-Huenchulao-005D
```

2. **Inicia XAMPP** y enciende los módulos de **Apache** y **MySQL**.

3. **Entra a phpMyAdmin** desde tu navegador (`http://localhost/phpmyadmin`) y crea las bases de datos necesarias, una para cada microservicio:

```sql
CREATE DATABASE db_cliente;
CREATE DATABASE db_vehiculo;
CREATE DATABASE db_catalogo;
CREATE DATABASE db_cotizacion;
CREATE DATABASE db_orden_reparacion;
```

4. **Compila los microservicios** con Maven:

```bash
mvn clean install
```

---

## Configuración

Cada microservicio tiene su propio archivo `application.properties` donde se define la conexión a la base de datos y el puerto en el que correrá. Un ejemplo típico se ve así:

```properties
# Puerto del microservicio
server.port=8081

# Conexión a la base de datos (MySQL desde XAMPP)
spring.datasource.url=jdbc:mysql://localhost:3306/db_cliente
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

> Por defecto, MySQL en XAMPP usa el usuario `root` **sin contraseña**. Si tú le pusiste una, recuerda actualizarla en el archivo.

### Puertos sugeridos

| Servicio | Puerto |
|---|---|
| api-gateway | 8080 |
| service-cliente | 8083 |
| service-vehiculo | 8086 |
| service-catalogo | 8082 |
| service-cotizacion | 8084 |
| service-orden-reparacion | 8085 |

---

## Estructura del proyecto

```
Radiadores-Aedo-Huenchulao-005D/
│
├── api-gateway/
├── service-cliente/
├── service-vehiculo/
├── service-catalogo/
├── service-cotizacion/
├── service-orden-reparacion/
│
└── README.md
```

Cada microservicio sigue una estructura estándar de Spring Boot:

```
service-xxxxx/
├── src/
│   ├── main/
│   │   ├── java/com/radiadores/
│   │   │   ├── controller/    → Controladores REST
│   │   │   ├── service/       → Lógica de negocio
│   │   │   ├── repository/    → Repositorios JPA
│   │   │   ├── entity/        → Entidades JPA
│   │   │   └── Application.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```
## Datos de prueba

Los scripts SQL con datos de prueba se encuentran en la carpeta `/sql` del repositorio.

Para cargarlos, abre phpMyAdmin y ejecuta cada script en su base de datos correspondiente:

| Script | Base de datos |
|---|---|
| db_cliente.sql | db_cliente |
| db_catalogo.sql | db_catalogo |
| db_vehiculo.sql | db_vehiculo |
| db_cotizacion.sql | db_cotizacion |
| db_orden_reparacion.sql | db_orden_reparacion |

---

## Autores

- **Benjamin Aedo** – [@beaedov]
- **Thomas Huenchulao**[@thuenchulao]

Proyecto desarrollado para **FullStack1 – Sección 005D**.
