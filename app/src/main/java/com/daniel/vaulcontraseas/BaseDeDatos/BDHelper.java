package com.daniel.vaulcontraseas.BaseDeDatos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.daniel.vaulcontraseas.Modelo.Password;

import java.util.ArrayList;

public class BDHelper extends SQLiteOpenHelper {

    public BDHelper(@Nullable Context context) {
        super(context, Constants.BD_NAME, null, Constants.BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public long insertarRegistro(String titulo, String cuenta, String nombre_usuario, String password,
                                        String sitio_web, String nota, String T_registro, String T_actualizacion){

        SQLiteDatabase db=this.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.C_TITULO, titulo);
        values.put(Constants.C_CUENTA, cuenta);
        values.put(Constants.C_NOMBRE_USUARIO, nombre_usuario);
        values.put(Constants.C_PASSWORD, password);
        values.put(Constants.C_SITIO_WEB, sitio_web);
        values.put(Constants.C_NOTA, nota);
        values.put(Constants.C_TIEMPO_REGISTRO, T_registro);
        values.put(Constants.C_TIEMPO_ACTUALIZACION, T_actualizacion);

        //insertar la fila
        long id = db.insert(Constants.TABLE_NAME, null, values);

        //cerrar la conexion a la base de datos
        db.close();

        return id;

    }

    public void actualizarRegistro (String id, String titulo, String cuenta, String nombre_usuario, String password,
                                  String sitio_web, String nota, String T_registro, String T_actualizacion){

        SQLiteDatabase db=this.getReadableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.C_TITULO, titulo);
        values.put(Constants.C_CUENTA, cuenta);
        values.put(Constants.C_NOMBRE_USUARIO, nombre_usuario);
        values.put(Constants.C_PASSWORD, password);
        values.put(Constants.C_SITIO_WEB, sitio_web);
        values.put(Constants.C_NOTA, nota);
        values.put(Constants.C_TIEMPO_REGISTRO, T_registro);
        values.put(Constants.C_TIEMPO_ACTUALIZACION, T_actualizacion);

        //actualizar la fila
        db.update(Constants.TABLE_NAME, values, Constants.C_ID + " =?", new String[]{id});

        //cerrar la conexion a la base de datos
        db.close();


    }

    public ArrayList<Password> ObtenerTodosLosRegistros(String orderby){
        ArrayList<Password> passwordList = new ArrayList<>();
        //consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderby;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
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

            }while (cursor.moveToNext());
        }
        db.close();

        return passwordList;
    }

    public ArrayList<Password> BuscarRegistros(String consulta){
        ArrayList<Password> passwordList = new ArrayList<>();
        //consulta para seleccionar el registro
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE "
                + Constants.C_TITULO + " LIKE '%" + consulta + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
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

            }while (cursor.moveToNext());
        }
        db.close();

        return passwordList;
    }

    public int ObtenerNumeroRegistros(){
        String countquery = "SELECT *FROM " + Constants.TABLE_NAME;
        SQLiteDatabase  db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countquery, null);

        int contador = cursor.getCount();

        cursor.close();

        return contador;
    }

    public void Eliminar_registro(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.C_ID+" =?", new String[]{id});
        db.close();
    }

    public void Eliminar_todos_registros(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Constants.TABLE_NAME);
        db.close();
    }

}
