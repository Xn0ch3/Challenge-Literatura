package com.aluracursos.desafioSpringboot.Principal;

import com.aluracursos.desafioSpringboot.Models.Autor;
import com.aluracursos.desafioSpringboot.Models.Libro;
import com.aluracursos.desafioSpringboot.Service.GutendexService;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {
    private final GutendexService gutendexService;
    private final Scanner scanner;

    public Principal(GutendexService gutendexService) {
        this.gutendexService = gutendexService;
        this.scanner = new Scanner(System.in);
    }

    public void muestraMenu() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar libros registrados");
            System.out.println("3. Listar autores registrados");
            System.out.println("4. Listar autores vivos en un año determinado");
            System.out.println("5. Listar libros por idioma");
            System.out.println("6. Cargar datos iniciales desde API");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

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
                case 6:
                    cargarDatosIniciales();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private void cargarDatosIniciales() {
        System.out.println("Cargando 10 libros de cada idioma desde la API...");
        gutendexService.cargar10LibrosPorIdioma();
    }


    private void buscarLibroPorTitulo() {
        System.out.print("\nIngrese el título o parte del título a buscar: ");
        String titulo = scanner.nextLine();

        List<Libro> libros = gutendexService.buscarLibrosPorTitulo(titulo);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros con ese título.");
        } else {
            System.out.println("\nLibros encontrados:");
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autores: " + libro.getAutores().stream()
                        .map(Autor::getNombre)
                        .collect(Collectors.joining(", ")));
                System.out.println("Idiomas: " + String.join(", ", libro.getIdiomas()));
                System.out.println("Descargas: " + libro.getDescargas());
                System.out.println("----------------------");
            });
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = gutendexService.obtenerTodosLosLibros();

        System.out.println("\nLibros registrados:");
        libros.forEach(libro -> {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autores: " + libro.getAutores().stream()
                    .map(Autor::getNombre)
                    .collect(Collectors.joining(", ")));
            System.out.println("Idiomas: " + String.join(", ", libro.getIdiomas()));
            System.out.println("Descargas: " + libro.getDescargas());
            System.out.println("----------------------");
        });
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = gutendexService.obtenerTodosLosAutores();

        System.out.println("\nAutores registrados:");
        autores.forEach(autor -> {
            System.out.println("Nombre: " + autor.getNombre());
            System.out.println("Año de nacimiento: " + autor.getFechaNacimiento());
            System.out.println("Año de fallecimiento: " + autor.getFechaMuerte());
            System.out.println("----------------------");
        });
    }

    private void listarAutoresVivosEnAnio() {
        System.out.print("\nIngrese el año para ver autores vivos: ");
        int anio = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        List<Autor> autores = gutendexService.obtenerAutoresVivosEnAnio(anio);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio);
        } else {
            System.out.println("\nAutores vivos en " + anio + ":");
            autores.forEach(autor -> {
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Años de vida: " + autor.getFechaNacimiento() + " - " + autor.getFechaMuerte());
                System.out.println("----------------------");
            });
        }
    }

    private void listarLibrosPorIdioma() {
        // Map de códigos a nombres completos
        Map<String, String> codigosANombres = Map.of(
                "es", "Español",
                "en", "Inglés",
                "fr", "Francés",
                "pt", "Portugués"
        );

        // Obtener los idiomas disponibles en la base de datos
        List<String> idiomasDisponibles = gutendexService.obtenerIdiomasDisponibles();

        if (idiomasDisponibles.isEmpty()) {
            System.out.println("No hay idiomas disponibles en la base de datos.");
            return;
        }

        // Filtrar solo los que tenemos en el mapa (para mostrar nombre completo)
        List<String> listaIdiomas = idiomasDisponibles.stream()
                .filter(codigosANombres::containsKey)
                .toList();

        if (listaIdiomas.isEmpty()) {
            System.out.println("No hay idiomas válidos disponibles en la base de datos.");
            return;
        }

        // Mostrar los idiomas disponibles con su nombre completo
        System.out.println("\nSeleccione un idioma disponible:");
        for (int i = 0; i < listaIdiomas.size(); i++) {
            String codigo = listaIdiomas.get(i);
            String nombre = codigosANombres.get(codigo);
            System.out.printf("%d. %s (%s)%n", i + 1, nombre, codigo);
        }

        // Leer la opción elegida
        int opcionIdioma;
        try {
            opcionIdioma = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida.");
            return;
        }

        if (opcionIdioma < 1 || opcionIdioma > listaIdiomas.size()) {
            System.out.println("Opción inválida.");
            return;
        }

        String idiomaSeleccionado = listaIdiomas.get(opcionIdioma - 1);

        // Buscar libros en el idioma seleccionado
        List<Libro> libros = gutendexService.buscarLibrosPorIdioma(idiomaSeleccionado);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma " + codigosANombres.get(idiomaSeleccionado));
        } else {
            System.out.println("\nLibros en idioma " + codigosANombres.get(idiomaSeleccionado) + ":");
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autores: " + libro.getAutores().stream()
                        .map(Autor::getNombre)
                        .collect(Collectors.joining(", ")));
                System.out.println("Descargas: " + libro.getDescargas());
                System.out.println("----------------------");
            });
        }
    }


}