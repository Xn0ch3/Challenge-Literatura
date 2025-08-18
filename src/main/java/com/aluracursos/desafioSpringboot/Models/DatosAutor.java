package com.aluracursos.desafioSpringboot.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(

        @JsonAlias("name") String nombre,

        @JsonAlias("birth_year") String fechaDeNacimiento

) {
}
