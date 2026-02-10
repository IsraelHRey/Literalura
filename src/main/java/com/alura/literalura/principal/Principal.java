package com.alura.literalura.principal;

import com.alura.literalura.model.Datos;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvertirDatos;

import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner lectura = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvertirDatos conversor = new ConvertirDatos();
    private final String URL_BASE = "https://gutendex.com/books/";

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
            var libro = libroBuscado.get(); // Sacamos el libro del Optional
            System.out.println("\n---------- LIBRO ENCONTRADO ----------");
            System.out.println("Título: " + libro.titulo());
            System.out.println("Autor: " + libro.autores().stream()
                    .map(a -> a.nombre()).findFirst().orElse("Desconocido"));
            System.out.println("Idioma: " + (libro.idiomas() == null || libro.idiomas().isEmpty()
                    ? "Desconocido"
                    : libro.idiomas().get(0)));
            System.out.println("Número de descargas: " + libro.numeroDeDescargas());
            System.out.println("--------------------------------------\n");
        } else {
            System.out.println("Libro no encontrado");
        }
    }
}


