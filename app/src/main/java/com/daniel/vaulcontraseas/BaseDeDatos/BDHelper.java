package com.daniel.vaulcontraseas.BaseDeDatos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.daniel.vaulcontraseas.Modelo.Password;
import com.daniel.vaulcontraseas.Modelo.Nota;

import java.util.ArrayList;

public class BDHelper extends SQLiteOpenHelper {

    public BDHelper(@Nullable Context context) {
        super(context, Constants.BD_NAME, null, Constants.BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de contraseñas
        db.execSQL(Constants.CREATE_TABLE);
        // Crear la tabla de notas
        db.execSQL(Constants.CREATE_TABLE_NOTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar las tablas si existen
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NOTAS);
        // Volver a crear las tablas
        onCreate(db);
    }

    // Métodos para la tabla de contraseñas

    public long insertarRegistro(String titulo, String cuenta, String nombre_usuario, String password,
                                 String sitio_web, String nota, String T_registro, String T_actualizacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.C_TITULO, titulo);
        values.put(Constants.C_CUENTA, cuenta);
        values.put(Constants.C_NOMBRE_USUARIO, nombre_usuario);
        values.put(Constants.C_PASSWORD, password);
        values.put(Constants.C_SITIO_WEB, sitio_web);
        values.put(Constants.C_NOTA, nota);
        values.put(Constants.C_TIEMPO_REGISTRO, T_registro);
        values.put(Constants.C_TIEMPO_ACTUALIZACION, T_actualizacion);
        long id = db.insert(Constants.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public void actualizarRegistro(String id, String titulo, String cuenta, String nombre_usuario, String password,
                                   String sitio_web, String nota, String T_registro, String T_actualizacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.C_TITULO, titulo);
        values.put(Constants.C_CUENTA, cuenta);
        values.put(Constants.C_NOMBRE_USUARIO, nombre_usuario);
        values.put(Constants.C_PASSWORD, password);
        values.put(Constants.C_SITIO_WEB, sitio_web);
        values.put(Constants.C_NOTA, nota);
        values.put(Constants.C_TIEMPO_REGISTRO, T_registro);
        values.put(Constants.C_TIEMPO_ACTUALIZACION, T_actualizacion);
        db.update(Constants.TABLE_NAME, values, Constants.C_ID + " = ?", new String[]{id});
        db.close();
    }

    public ArrayList<Password> ObtenerTodosLosRegistros(String orderby) {
        ArrayList<Password> passwordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderby;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Password modelo_password = new Password(
                        "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_TIEMPO_ACTUALIZACION)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_TIEMPO_REGISTRO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_NOTA)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_SITIO_WEB)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_PASSWORD)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_NOMBRE_USUARIO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_CUENTA)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_TITULO)));
                passwordList.add(modelo_password);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return passwordList;
    }

    public ArrayList<Password> BuscarRegistros(String consulta) {
        ArrayList<Password> passwordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE "
                + Constants.C_TITULO + " LIKE '%" + consulta + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Password modelo_password = new Password(
                        "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_TIEMPO_ACTUALIZACION)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_TIEMPO_REGISTRO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_NOTA)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_SITIO_WEB)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_PASSWORD)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_NOMBRE_USUARIO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_CUENTA)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_TITULO)));
                passwordList.add(modelo_password);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return passwordList;
    }

    public int ObtenerNumeroRegistros() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public void Eliminar_registro(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.C_ID + " = ?", new String[]{id});
        db.close();
    }

    public void Eliminar_todos_registros() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Constants.TABLE_NAME);
        db.close();
    }

    // Métodos para la tabla de notas

    public long insertarNota(String titulo, String contenido, String T_registro, String T_actualizacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.N_TITULO, titulo);
        values.put(Constants.N_CONTENIDO, contenido);
        values.put(Constants.N_TIEMPO_REGISTRO, T_registro);
        values.put(Constants.N_TIEMPO_ACTUALIZACION, T_actualizacion);
        long id = db.insert(Constants.TABLE_NOTAS, null, values);
        db.close();
        return id;
    }

    public ArrayList<Nota> obtenerTodasLasNotas(String orderby) {
        ArrayList<Nota> notaList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NOTAS + " ORDER BY " + orderby;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Nota modelo_nota = new Nota(
                        "" + cursor.getInt(cursor.getColumnIndex(Constants.N_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.N_TITULO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.N_CONTENIDO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.N_TIEMPO_REGISTRO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.N_TIEMPO_ACTUALIZACION)));
                notaList.add(modelo_nota);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notaList;
    }

    public void actualizarNota(String id, String titulo, String contenido, String T_registro, String T_actualizacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.N_TITULO, titulo);
        values.put(Constants.N_CONTENIDO, contenido);
        values.put(Constants.N_TIEMPO_REGISTRO, T_registro);
        values.put(Constants.N_TIEMPO_ACTUALIZACION, T_actualizacion);
        db.update(Constants.TABLE_NOTAS, values, Constants.N_ID + " = ?", new String[]{id});
        db.close();
    }

    public void eliminarNota(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NOTAS, Constants.N_ID + " = ?", new String[]{id});
        db.close();
    }

    public ArrayList<Nota> buscarNotas(String consulta) {
        ArrayList<Nota> notaList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NOTAS + " WHERE "
                + Constants.N_TITULO + " LIKE '%" + consulta + "%' OR "
                + Constants.N_CONTENIDO + " LIKE '%" + consulta + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Nota modelo_nota = new Nota(
                        "" + cursor.getInt(cursor.getColumnIndex(Constants.N_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.N_TITULO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.N_CONTENIDO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.N_TIEMPO_REGISTRO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.N_TIEMPO_ACTUALIZACION)));
                notaList.add(modelo_nota);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notaList;
    }

    public int obtenerNumeroNotas() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NOTAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public void eliminarTodasLasNotas() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Constants.TABLE_NOTAS);
        db.close();
    }

}
