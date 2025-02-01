package com.daniel.vaulcontraseas.Modelo;

public class Password {

    String id, titulo, cuenta, nombre_usuario, password, sitio_web, nota, t_registro, t_actualizacion;

    public Password(String id, String t_actualizacion, String t_registro, String nota, String sitio_web,
                    String password, String nombre_usuario, String cuenta, String titulo) {
        this.id = id;
        this.t_actualizacion = t_actualizacion;
        this.t_registro = t_registro;
        this.nota = nota;
        this.sitio_web = sitio_web;
        this.password = password;
        this.nombre_usuario = nombre_usuario;
        this.cuenta = cuenta;
        this.titulo = titulo;
    }

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

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSitio_web() {
        return sitio_web;
    }

    public void setSitio_web(String sitio_web) {
        this.sitio_web = sitio_web;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getT_actualizacion() {
        return t_actualizacion;
    }

    public void setT_actualizacion(String t_actualizacion) {
        this.t_actualizacion = t_actualizacion;
    }

    public String getT_registro() {
        return t_registro;
    }

    public void setT_registro(String t_registro) {
        this.t_registro = t_registro;
    }
}
