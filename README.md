# 📚 Literalura - Catálogo de Libros

**Literalura** es una aplicación de consola desarrollada en **Java** con **Spring Boot** que permite gestionar un catálogo de libros consumiendo datos de la API externa **Gutendex**. 
El proyecto aplica conceptos de Programación Orientada a Objetos, persistencia en bases de datos relacionales y manipulación de datos JSON.

---

## 🚀 Funcionalidades
El sistema ofrece un menú interactivo con las siguientes opciones obligatorias:

1.  **Búsqueda de libro por título**: Consulta la API, recupera la información y la persiste en la base de datos.
2.  **Listar libros registrados**: Muestra todos los libros almacenados en PostgreSQL.
3.  **Listar autores registrados**: Exhibe la lista de autores con sus años de vida.
4.  **Listar autores vivos en un año**: Filtra autores vivos en un periodo específico.
5.  **Estadísticas por idioma**: Lista y contabiliza libros por código de idioma (es, en, fr, pt).

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje**: Java 17
* **Framework**: Spring Boot 3
* **Persistencia**: Spring Data JPA
* **Base de Datos**: PostgreSQL
* **Manejo de JSON**: Jackson
* **Protocolo HTTP**: HttpClient

---

## 📋 Requerimientos Técnicos Implementados

### 1. Análisis de Datos (Deserialización)
Uso de la biblioteca **Jackson** para el mapeo eficiente de JSON a objetos Java mediante **Records**:
- Uso de `@JsonAlias` para mapear campos específicos.
- Uso de `@JsonIgnoreProperties` para evitar errores por campos desconocidos.

### 2. Persistencia y Relaciones
Implementación de una base de datos relacional manteniendo la integridad de los datos:
- Relación **Many-to-One** entre `Libro` y `Autor`.
- Persistencia automática al realizar búsquedas exitosas en la API.

### 3. Consultas Avanzadas
Uso de **Derived Queries** y la anotación `@Query` para filtrados específicos en el repositorio:

@Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :anio AND (a.fechaDeFallecimiento IS NULL OR a.fechaDeFallecimiento >= :anio)")
List<Autor> autoresVivosEnDeterminadoAnio(Integer anio); 

###Configuración Local
Para que el proyecto funcione, configura tu application.properties:

Properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update

Desarrollado por: Israel Hernandez 🚀🏆
