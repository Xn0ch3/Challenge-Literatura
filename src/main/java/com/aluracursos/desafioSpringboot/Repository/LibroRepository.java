package com.aluracursos.desafioSpringboot.Repository;

import com.aluracursos.desafioSpringboot.Models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    List<Libro> findByOrderByDescargasDesc();

    Optional<Libro> findByTitulo(String titulo);

    // 📚 Listar todos los libros
    List<Libro> findAll();

    // 🌍 Buscar libros por idioma (soporta 'es', 'es-ES', 'en-US', etc.)
    @Query("""
                SELECT DISTINCT l 
                FROM Libro l
                JOIN l.idiomas i
                WHERE lower(i) LIKE lower(concat(:codigo, '%'))
            """)
    List<Libro> findByIdioma(@Param("codigo") String codigo);


    // 🌍📋 Listar TODOS los idiomas existentes en los libros cargados
    @Query("SELECT DISTINCT i FROM Libro l JOIN l.idiomas i")
    List<String> findAllIdiomas();
}
