# CRUD Food

Una sencilla aplicación CRUD (Crear, Leer, Actualizar, Borrar) para gestionar alimentos y clientes.
La aplicación está construida en Java utilizando Maven.

La arquitectura es un enfoque por capas inspirado en MVC, con una separación entre la capa de presentación (vista), la lógica de negocio (servicios) y el acceso a datos (repositorios).

## Configuración

La configuración de la aplicación se gestiona en el fichero `src/main/resources/application.properties`.

Estas son las propiedades que puedes configurar:

### Proveedor de Base de Datos

`APP_DATABASE_PROVIDER`: Define la base de datos que se utilizará.
- `local`: Utiliza una base de datos H2 en memoria. No se necesita ninguna otra configuración. La base de datos se creará y destruirá con la aplicación.
- `postgres`: Utiliza una base de datos PostgreSQL. Deberás proporcionar los detalles de la conexión.

### Configuración para PostgreSQL

Si `APP_DATABASE_PROVIDER` está configurado como `postgres`, debes configurar las siguientes propiedades:

- `DATABASE_URL`: La URL de conexión a tu base de datos PostgreSQL.
  - Ejemplo: `jdbc:postgresql://localhost:5432/CRUDfood`
- `DATABASE_USER`: El nombre de usuario para la base de datos.
- `DATABASE_PASSWORD`: La contraseña para el usuario de la base de datos.

### Configuración para H2 (Base de datos local)

Si `APP_DATABASE_PROVIDER` está configurado como `local`, puedes opcionalmente cambiar estas propiedades, aunque los valores por defecto suelen ser suficientes:

- `LOCAL_DATABASE_URL`: La URL de conexión a la base de datos H2.
- `LOCAL_DATABASE_USER`: El nombre de usuario.
- `LOCAL_DATABASE_PASSWORD`: La contraseña.

### Implementación del Repositorio

- `APP_CUSTOMER_REPOSITORY_IMPL`: Define la implementación del repositorio a utilizar. Actualmente está configurado como `jpa`.
- `APP_FOOD_REPOSITORY_IMPL`: Define la implementación del repositorio a utilizar. Actualmente está configurado como `jpa`.

## Cómo ejecutar la aplicación

1.  Asegúrate de tener Java 21 y Maven instalados.
2.  Configura el fichero `application.properties` según tus necesidades.
3.  Construye el proyecto con Maven:
    ```bash
    mvn clean install
    ```
4.  Ejecuta la aplicación:
    ```bash
    java -jar target/CRUDfood-1.0-SNAPSHOT.jar
    ```
    O puedes ejecutar la clase `MainApp` desde tu IDE.

