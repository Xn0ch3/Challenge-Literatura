package com.aluracursos.desafioSpringboot.Principal;

import com.aluracursos.desafioSpringboot.Models.Datos;
import com.aluracursos.desafioSpringboot.Models.DatosLibros;
import com.aluracursos.desafioSpringboot.Service.ConsumoAPI;
import com.aluracursos.desafioSpringboot.Service.ConvierteDatos;
import com.fasterxml.jackson.core.JsonToken;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";

    private ConsumoAPI consumoAPI = new ConsumoAPI();

    private ConvierteDatos conversor = new ConvierteDatos();

    private Scanner scanner = new Scanner(System.in);
    public void muestraMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);


        // Top 10 de Libros más descargados (con numeración)
        System.out.println("Top 10 de Libros más descargados");

// 1) Contador mutable para enumerar el top
        AtomicInteger indice = new AtomicInteger(1);

        datos.resultados().stream()
                // 2) Ordena por descargas de mayor a menor
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())

                // 3) Se queda solo con los primeros 10
                .limit(10)

                // 4) Formatea cada libro con su posición, título y descargas
                .map(libro -> String.format("%2d) %s — %.0f descargas",
                        indice.getAndIncrement(),          // posición en el top
                        libro.titulo().toUpperCase(),       // título en mayúsculas
                        libro.numeroDeDescargas()           // descargas (double -> sin decimales)
                ))

                // 5) Imprime cada línea formateada
                .forEach(System.out::println);

        //Busqueda de libros por Nombre.
        System.out.println("Ingrese el Nombre en Ingles del libro que quiere buscar: ");
        var tituloLibro = scanner.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter( l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()){
            System.out.println("Libro encontrado");
            System.out.println(libroBuscado.get());
        }else {
            System.out.println("No se encontro Libro");
        }

        //Trabajando con Estadisticas: Descargas
        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter( d -> d.numeroDeDescargas() > 0 )
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
        System.out.println("Cantidad de Descargas Promedio: " + est.getAverage());
        System.out.println("Cantidad Maximas de Descargas: " + est.getMax());
        System.out.println("Cantidad Minimas de Descargas: " + est.getMin());
        System.out.println("Cantidad de registros evaluados para calcular estadisticas: " + est.getCount());
    }

}
