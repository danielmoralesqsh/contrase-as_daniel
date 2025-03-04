package com.daniel.vaulcontraseas.Modelo;

public class Tarjeta {

    private String id;
    private String titulo;
    private String numero_tarjeta;
    private String nombre_tarjeta;
    private String fecha_expiracion;
    private String cvv;
    private String nota;
    private String t_registro;
    private String t_actualizacion;

    // Constructor
    public Tarjeta(String id, String titulo, String numero_tarjeta, String nombre_tarjeta, String fecha_expiracion,
                   String cvv, String nota, String t_registro, String t_actualizacion) {
        this.id = id;
        this.titulo = titulo;
        this.numero_tarjeta = numero_tarjeta;
        this.nombre_tarjeta = nombre_tarjeta;
        this.fecha_expiracion = fecha_expiracion;
        this.cvv = cvv;
        this.nota = nota;
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

    public String getNumero_tarjeta() {
        return numero_tarjeta;
    }

    public void setNumero_tarjeta(String numero_tarjeta) {
        this.numero_tarjeta = numero_tarjeta;
    }

    public String getNombre_tarjeta() {
        return nombre_tarjeta;
    }

    public void setNombre_tarjeta(String nombre_tarjeta) {
        this.nombre_tarjeta = nombre_tarjeta;
    }

    public String getFecha_expiracion() {
        return fecha_expiracion;
    }

    public void setFecha_expiracion(String fecha_expiracion) {
        this.fecha_expiracion = fecha_expiracion;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
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