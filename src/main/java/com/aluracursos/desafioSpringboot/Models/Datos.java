package com.aluracursos.desafioSpringboot.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        @JsonAlias("count") int total,
        @JsonAlias("next") String siguientePagina,
        @JsonAlias("previous") String paginaAnterior,
        @JsonAlias("results") List<DatosLibros> resultados
) {
}