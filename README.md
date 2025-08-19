# 📚 Challenge Literatura - App de Gestión de Libros y Autores

[![Banner](https://cdn.pixabay.com/photo/2017/02/10/10/33/book-2053756_1280.jpg)](https://raw.githubusercontent.com/Xn0ch3/Challenge-Literatura/main/resources/ChallengeLiteratura.png)

Bienvenido al **Challenge Literatura**, una aplicación desarrollada en **Java con Spring Boot** que permite consultar, almacenar y filtrar libros y autores usando datos de la API de [Gutendex](https://gutendex.com/).

https://raw.githubusercontent.com/Xn0ch3/Challenge-Literatura/main/resources/20250819-0124-29.9083765.mp4
---

## 🔹 Tabla de Contenidos

* [Acerca del Proyecto](#-acerca-del-proyecto)
* [Características](#-características)
* [Tecnologías Utilizadas](#-tecnologías-utilizadas)
* [Instalación](#-instalación)
* [Uso](#-uso)
* [Estructura del Proyecto](#-estructura-del-proyecto)
* [Mejoras Futuras](#-mejoras-futuras)
* [Licencia](#-licencia)

---

## 📖 Acerca del Proyecto

Esta aplicación permite:

* Consultar libros por título y ver sus autores, idiomas y descargas.
* Listar todos los libros y autores registrados en la base de datos.
* Filtrar autores vivos en un año específico.
* Filtrar libros por idioma.
* Cargar libros automáticamente desde la API de Gutendex, guardando **10 libros por idioma** (inglés, español, francés y portugués).

El objetivo del challenge fue demostrar habilidades en **Spring Boot, JPA/Hibernate, consumo de APIs REST y manejo de bases de datos**.

---

## ✅ Características

* Búsqueda de libros por título con resultados parciales.
* Listado completo de libros y autores en la base de datos.
* Filtrado de autores vivos en un año determinado.
* Filtrado de libros por idioma.
* Carga automática de datos desde la API de Gutendex.
* Almacenamiento relacional con PostgreSQL y relaciones entre libros y autores.

---

## 🛠 Tecnologías Utilizadas

* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA / Hibernate**
* **PostgreSQL**
* **API REST**: Gutendex
* **Maven** para gestión de dependencias
* **Jackson** para manejo de JSON

---

## ⚙️ Instalación

1. Clonar el repositorio:

```bash
git clone https://github.com/Xn0ch3/Challenge-Literatura.git
cd Challenge-Literatura
```

2. Configurar la base de datos PostgreSQL en `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literatura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
```

3. Construir y ejecutar la aplicación:

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🖥 Uso

Al iniciar la aplicación, verás un menú interactivo:

```
--- MENÚ PRINCIPAL ---
1. Buscar libro por título
2. Listar libros registrados
3. Listar autores registrados
4. Listar autores vivos en un año determinado
5. Listar libros por idioma
6. Cargar datos iniciales desde API
0. Salir
```

### Ejemplo de uso:

1. Buscar libros con la palabra "pride":

```
Ingrese el título o parte del título a buscar: pride
Libros encontrados:
Título: Pride and Prejudice
Autores: Austen, Jane
Idiomas: en
Descargas: 62904
```

2. Listar autores vivos en un año:

```
Ingrese el año: 1800
Autores vivos:
- Jane Austen (1775 - 1817)
```

3. Cargar libros desde la API:

```
Cargando libros en idioma: en
Se cargaron 10 libros en idioma en
```

---

## 📁 Estructura del Proyecto

```
src/main/java/com/aluracursos/desafioSpringboot
├── Models          # Entidades Libro, Autor y DTOs de la API
├── Repository      # Repositorios JPA
├── Service         # Servicios para consumo de API y lógica de negocio
├── Principal       # Clase principal con menú
```

---

## 🚀 Mejoras Futuras

* Implementar **interfaz gráfica** con **Thymeleaf o React**.
* Añadir **paginación y búsqueda avanzada** de libros y autores.
* Mejorar **manejo de errores y validaciones**.
* Agregar **pruebas unitarias y de integración**.
* Posible **despliegue en la nube** (Heroku, AWS, etc.).

---

## 📝 Licencia

Este proyecto está bajo la licencia MIT.
¡Comparte, aprende y contribuye!


