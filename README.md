# ⚙️ SimaGrow – Backend API (Spring Boot)

SimaGrow es el motor del proyecto. Mientras que las apps se encargan de verse bonitas, el backend de Spring Boot gestiona incidencias, autentica usuarios y asegura que los datos no desaparezcan cada vez que el usuario cierra la aplicación.

---

## 🛠️ Arquitectura y Endpoints

Nos centramos en los objetos **JSON** y en las peticiones para evitar errores de servidor inesperados (500 Internal Server Error).

### 🔐 Seguridad y Acceso

* **Auth Controller:** Gestión de registro y login. Comprobamos la identidad del usuario.
* **Data Persistence:** Sincronización entre los clientes y la base de datos.

### 📋 Gestión de Recursos

* CRUD *completo* para reportar, listar y gestionar problemas.
* Entidades obligatorias para el flujo de *sign-in* y otros.

---

## 🚀 Funciones Principales

* 🛡️ **Autenticación:** Validación de credenciales para el acceso.
* 💾 **Persistencia:** Repositorio central de datos persistentes.
* 🏗️ **Arquitectura en Capas:** Estructura siguiendo el patrón `Controller` -> `Service` -> `Repository`.
* 🔄 **RESTful API:** Comunicación mediante JSON para conectarse con Android y QT.

---

## 🏗️ Tecnologías

* **Java**
* **Spring Boot**
* **Spring Data JPA**
* **Hibernate**
* **Maven**

---

## 👤 Autor

**Ruben Santacatalina Pellicer**
