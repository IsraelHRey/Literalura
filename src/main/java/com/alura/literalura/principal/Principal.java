package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvertirDatos;

import java.util.*;

public class Principal {
    private Scanner lectura = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvertirDatos conversor = new ConvertirDatos();
    private final String URL_BASE = "https://gutendex.com/books/";


    private LibroRepository repositorio;
    private AutorRepository autorRepositorio;

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorio = repository;
        this.autorRepositorio = autorRepository;
    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = lectura.nextInt();
            lectura.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var nombreLibro = lectura.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        var datos = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibro> libroBuscado = datos.resultado().stream()
                .findFirst();

        if (libroBuscado.isPresent()) {
            DatosLibro datosLibro = libroBuscado.get();


            DatosAutor datosAutor = datosLibro.autores().get(0);
            Autor autor = autorRepositorio.findByNombreContainsIgnoreCase(datosAutor.nombre())
                    .orElseGet(() -> {

                        Autor nuevoAutor = new Autor(datosAutor);
                        return autorRepositorio.save(nuevoAutor);
                    });


            Libro libro = new Libro(datosLibro);
            libro.setAutor(autor);


            repositorio.save(libro);

            System.out.println("----- LIBRO REGISTRADO CON ÉXITO -----");
            System.out.println(libro);
        }
    }

    private void listarLibrosRegistrados() {

        List<Libro> libros = repositorio.findAll();


        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros registrados.");
        } else {

            libros.stream()
                    .sorted(Comparator.comparing(Libro::getTitulo))
                    .forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepositorio.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autores.stream()
                    .sorted(Comparator.comparing(Autor::getNombre))
                    .forEach(System.out::println);
        }
    }

    private void listarAutoresVivosEnAnio() {
        System.out.println("Ingresa el año que deseas buscar:");
        try {
            var anio = lectura.nextInt();
            lectura.nextLine();

            List<Autor> autores = autorRepositorio.autoresVivosEnDeterminadoAnio(anio);

            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + anio);
            } else {
                autores.forEach(System.out::println);
            }
        } catch (InputMismatchException e) {

            System.out.println("Error: Debes ingresar un número válido para el año.");
            lectura.nextLine();
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("""
            Ingrese el idioma para buscar los libros:
            es - español
            en - inglés
            fr - francés
            pt - portugués
            """);
        var idioma = lectura.nextLine();

        List<Libro> libros = repositorio.findByIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma en la base de datos.");
        } else {

            libros.forEach(System.out::println);


            long cantidad = libros.stream().count();
            System.out.println("-------------------------------------------");
            System.out.println("Cantidad de libros encontrados: " + cantidad);
            System.out.println("-------------------------------------------\n");
        }
    }
}




