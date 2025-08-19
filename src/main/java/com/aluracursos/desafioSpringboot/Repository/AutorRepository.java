package com.aluracursos.desafioSpringboot.Repository;

import com.aluracursos.desafioSpringboot.Models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anio AND (a.fechaMuerte IS NULL OR a.fechaMuerte >= :anio)")
    List<Autor> findAutoresVivosEnAnio(@Param("anio") Integer anio);

    List<Autor> findByNombreContainingIgnoreCase(String nombre);

    Optional<Autor> findByNombre(String nombre);
}
