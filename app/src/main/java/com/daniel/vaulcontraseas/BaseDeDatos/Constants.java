package com.daniel.vaulcontraseas.BaseDeDatos;

public class Constants {

    //nombre de la base
    public static final String BD_NAME = "PASSWORD_BD";

    //version de la base de datos
    public static final int BD_VERSION = 3; // Incrementa la versión de la base de datos

    //nombre de la tabla de contraseñas
    public static final String TABLE_NAME = "PASSWORD_TABLE";

    //nombre de la tabla de notas
    public static final String TABLE_NOTAS = "NOTAS_TABLE";

    //nombre de la tabla de tarjetas de crédito
    public static final String TABLE_TARJETAS = "TARJETAS_TABLE";

    //columnas de la tabla de contraseñas
    public static final String C_ID = "ID";
    public static final String C_TITULO = "TITULO";
    public static final String C_CUENTA = "CUENTA";
    public static final String C_NOMBRE_USUARIO = "NOMBRE_USUARIO";
    public static final String C_PASSWORD = "PASSWORD";
    public static final String C_SITIO_WEB = "SITIO_WEB";
    public static final String C_NOTA = "NOTA";
    public static final String C_TIEMPO_REGISTRO = "TIEMPO_REGISTRO";
    public static final String C_TIEMPO_ACTUALIZACION = "TIEMPO_ACTUALIZACION";

    //columnas de la tabla de notas
    public static final String N_ID = "ID";
    public static final String N_TITULO = "TITULO";
    public static final String N_CONTENIDO = "CONTENIDO";
    public static final String N_TIEMPO_REGISTRO = "TIEMPO_REGISTRO";
    public static final String N_TIEMPO_ACTUALIZACION = "TIEMPO_ACTUALIZACION";

    //columnas de la tabla de tarjetas de crédito
    public static final String T_ID = "ID";
    public static final String T_TITULO = "TITULO";
    public static final String T_NUMERO_TARJETA = "NUMERO_TARJETA";
    public static final String T_NOMBRE_TARJETA = "NOMBRE_TARJETA";
    public static final String T_FECHA_EXPIRACION = "FECHA_EXPIRACION";
    public static final String T_CVV = "CVV";
    public static final String T_NOTA = "NOTA";
    public static final String T_TIEMPO_REGISTRO = "TIEMPO_REGISTRO";
    public static final String T_TIEMPO_ACTUALIZACION = "TIEMPO_ACTUALIZACION";

    //crear tabla de contraseñas
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + "("
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_TITULO + " TEXT, "
            + C_CUENTA + " TEXT, "
            + C_NOMBRE_USUARIO + " TEXT, "
            + C_PASSWORD + " TEXT, "
            + C_SITIO_WEB + " TEXT, "
            + C_NOTA + " TEXT, "
            + C_TIEMPO_REGISTRO + " TEXT, "
            + C_TIEMPO_ACTUALIZACION + " TEXT"
            + ")";

    //crear tabla de notas
    public static final String CREATE_TABLE_NOTAS = "CREATE TABLE " + TABLE_NOTAS
            + "("
            + N_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + N_TITULO + " TEXT, "
            + N_CONTENIDO + " TEXT, "
            + N_TIEMPO_REGISTRO + " TEXT, "
            + N_TIEMPO_ACTUALIZACION + " TEXT"
            + ")";

    //crear tabla de tarjetas de crédito
    public static final String CREATE_TABLE_TARJETAS = "CREATE TABLE " + TABLE_TARJETAS
            + "("
            + T_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + T_TITULO + " TEXT, "
            + T_NUMERO_TARJETA + " TEXT, "
            + T_NOMBRE_TARJETA + " TEXT, "
            + T_FECHA_EXPIRACION + " TEXT, "
            + T_CVV + " TEXT, "
            + T_NOTA + " TEXT, "
            + T_TIEMPO_REGISTRO + " TEXT, "
            + T_TIEMPO_ACTUALIZACION + " TEXT"
            + ")";
}