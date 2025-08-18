package com.aluracursos.desafioSpringboot.Service;

public interface IConvierteDatos {

    <T> T obtenerDatos(String json, Class<T> clase);


}
