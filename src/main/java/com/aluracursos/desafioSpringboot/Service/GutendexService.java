package com.aluracursos.desafioSpringboot.Service;

import com.aluracursos.desafioSpringboot.Models.Autor;
import com.aluracursos.desafioSpringboot.Models.Datos;
import com.aluracursos.desafioSpringboot.Models.Libro;
import com.aluracursos.desafioSpringboot.Repository.AutorRepository;
import com.aluracursos.desafioSpringboot.Repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class GutendexService {
    private static final String GUTENDEX_API_URL = "https://gutendex.com/books/";

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final ConsumoAPI consumoAPI;
    private final ConvierteDatos conversor;

    @Autowired
    public GutendexService(LibroRepository libroRepository, AutorRepository autorRepository,
                           ConsumoAPI consumoAPI, ConvierteDatos conversor) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.consumoAPI = consumoAPI;
        this.conversor = conversor;
    }

    @Transactional
    public void cargar10LibrosPorIdioma() {
        List<String> idiomas = List.of("en", "es", "fr", "pt"); // Idiomas a cargar

        for (String idioma : idiomas) {
            System.out.println("\nCargando libros en idioma: " + idioma);
            String url = GUTENDEX_API_URL + "?languages=" + idioma;
            int librosCargados = 0;

            while (url != null && librosCargados < 10) { // Solo 10 libros por idioma
                String json = consumoAPI.obtenerDatos(url);
                Datos datos = conversor.obtenerDatos(json, Datos.class);

                for (var datosLibro : datos.resultados()) {
                    if (librosCargados >= 10) break; // Rompe si ya cargamos 10

                    if (libroRepository.findByTitulo(datosLibro.titulo()).isEmpty()) {
                        Libro libro = new Libro();

                        // Ajuste para títulos largos
                        String titulo = datosLibro.titulo();
                        if (titulo.length() > 1000) {
                            titulo = titulo.substring(0, 1000);
                        }
                        libro.setTitulo(titulo);

                        libro.setDescargas(datosLibro.numeroDeDescargas() != null
                                ? datosLibro.numeroDeDescargas().intValue()
                                : 0);
                        libro.setIdiomas(new HashSet<>(datosLibro.idiomas()));
                        libroRepository.save(libro);

                        // Guardar autores
                        datosLibro.autor().forEach(datosAutor -> {
                            Autor autor = autorRepository.findByNombre(datosAutor.nombre())
                                    .orElseGet(() -> {
                                        Autor nuevoAutor = new Autor();
                                        nuevoAutor.setNombre(datosAutor.nombre());
                                        nuevoAutor.setFechaNacimiento(datosAutor.fechaDeNacimiento());
                                        nuevoAutor.setFechaMuerte(datosAutor.fechaDeMuerte());
                                        return autorRepository.save(nuevoAutor);
                                    });
                            libro.agregarAutor(autor);
                        });

                        libroRepository.save(libro);
                        librosCargados++;
                    }
                }

                url = datos.siguientePagina(); // URL siguiente página
            }

            System.out.println("Se cargaron " + librosCargados + " libros en idioma " + idioma);
        }

        System.out.println("\n¡Carga de libros finalizada!");
    }


    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }

    public List<Autor> obtenerTodosLosAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> obtenerAutoresVivosEnAnio(int anio) {
        return autorRepository.findAutoresVivosEnAnio(anio);
    }
    public List<Libro> buscarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma);
    }


    public List<String> obtenerIdiomasDisponibles() {
        return libroRepository.findAllIdiomas();
    }


    @Transactional
    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    @Transactional
    public Autor guardarAutor(Autor autor) {
        return autorRepository.save(autor);
    }
}
