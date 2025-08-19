package com.aluracursos.desafioSpringboot.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer fechaDeNacimiento,
        @JsonAlias("death_year") Integer fechaDeMuerte
) {
    public boolean estaVivoEnAnio(int anio) {
        if (fechaDeNacimiento == null) return false;
        if (fechaDeMuerte == null) return anio >= fechaDeNacimiento;
        return anio >= fechaDeNacimiento && anio <= fechaDeMuerte;
    }

}