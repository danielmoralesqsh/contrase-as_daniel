package com.daniel.vaulcontraseas.Modelo;

public class Nota {

    String id, titulo, contenido, t_registro, t_actualizacion;

    // Constructor
    public Nota(String id, String titulo, String contenido, String t_registro, String t_actualizacion) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.t_registro = t_registro;
        this.t_actualizacion = t_actualizacion;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getT_registro() {
        return t_registro;
    }

    public void setT_registro(String t_registro) {
        this.t_registro = t_registro;
    }

    public String getT_actualizacion() {
        return t_actualizacion;
    }

    public void setT_actualizacion(String t_actualizacion) {
        this.t_actualizacion = t_actualizacion;
    }
}