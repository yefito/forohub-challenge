# Foro Hub API REST

API REST desarrollada con Spring Boot que simula la funcionalidad de un foro en línea.

## Objetivo del Proyecto

Este proyecto fue desarrollado como parte de un challenge técnico con el fin de fortalecer habilidades en el desarrollo backend utilizando Java y Spring Boot. Se implementó una API REST segura, escalable y bien estructurada.

## Descripción General

Foro Hub permite la autenticación de usuarios y la gestión de tópicos en un entorno protegido mediante autenticación JWT. La arquitectura sigue buenas prácticas y patrones comunes en el desarrollo de APIs RESTful.

## Funcionalidades Principales

### Autenticación con JWT

- Endpoint: `/login`
- Autenticación mediante correo y contraseña.
- Se genera un token JWT para proteger endpoints privados.

### Gestión de Tópicos

- Crear, listar, actualizar y eliminar tópicos.
- Solo usuarios autenticados pueden gestionar sus propios tópicos.

### Manejo Global de Errores

Respuestas estandarizadas para errores comunes:

- `404`: Recurso no encontrado
- `400`: Validaciones fallidas
- `401`: Credenciales inválidas
- `403`: Acceso denegado



## Estructura del Proyecto

```
src/main/java/com/forohub/
├── controller/              → Controladores de la API (Tópicos)
├── domain/
│   └── topico/              → Lógica relacionada con los tópicos
│   └── usuario/             → Para autentificacion
├── infra/
│   ├── security/            → Configuración de seguridad y JWT
│   └── exceptions/          → Manejo global de errores
└── ForoHubApplication.java  → Clase principal del proyecto
```

```
src/main/resources/
├── application.properties   → Configuración del entorno
└── db/migration/            → Scripts de migración con Flyway
```

## Instalación y Ejecución

1. Clona el repositorio:

```bash
git clone https://github.com/tu-usuario/foro-hub.git
```

2. Configura la base de datos:

Edita el archivo `application.properties` con tus credenciales:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/foro_hub
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=validate
flyway.enabled=true
```

3. Ejecuta la aplicación:

Abre el proyecto y corre la clase `ForoHubApplication.java`.


