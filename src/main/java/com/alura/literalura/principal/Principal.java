package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvertirDatos;

import java.util.Optional;
import java.util.Scanner;

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
                        // Si el autor no existe, lo creamos y guardamos
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
}




