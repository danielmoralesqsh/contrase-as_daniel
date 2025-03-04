package com.daniel.vaulcontraseas.Detalle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.daniel.vaulcontraseas.BaseDeDatos.BDHelper;
import com.daniel.vaulcontraseas.BaseDeDatos.Constants;
import com.daniel.vaulcontraseas.R;

import java.util.Calendar;
import java.util.Locale;

public class Detalle_tarjeta extends AppCompatActivity {

    TextView D_Titulo, D_Numero_Tarjeta, D_Nombre_Tarjeta, D_Fecha_Expiracion, D_CVV, D_Nota, D_Tiempo_registro, D_Tiempo_actualizacion;

    String id_tarjeta;

    BDHelper bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_tarjeta);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        id_tarjeta = intent.getStringExtra("Id_tarjeta");
        Toast.makeText(this, "Id de la tarjeta: " + id_tarjeta, Toast.LENGTH_SHORT).show();

        bdHelper = new BDHelper(this);

        InicializarVariables();
        MostrarInformacionTarjeta();

        // Obtener título de la tarjeta
        String titulo_tarjeta = D_Titulo.getText().toString();
        assert actionBar != null;
        actionBar.setTitle(titulo_tarjeta);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void InicializarVariables() {
        D_Titulo = findViewById(R.id.D_Titulo_Tarjeta);
        D_Numero_Tarjeta = findViewById(R.id.D_Numero_Tarjeta);
        D_Nombre_Tarjeta = findViewById(R.id.D_Nombre_Tarjeta);
        D_Fecha_Expiracion = findViewById(R.id.D_Fecha_Expiracion);
        D_CVV = findViewById(R.id.D_CVV);
        D_Nota = findViewById(R.id.D_Nota_Tarjeta);
        D_Tiempo_registro = findViewById(R.id.D_Tiempo_registro);
        D_Tiempo_actualizacion = findViewById(R.id.D_Tiempo_actualizacion);
    }

    private void MostrarInformacionTarjeta() {
        String consulta = "SELECT * FROM " + Constants.TABLE_TARJETAS + " WHERE " + Constants.T_ID + " =\"" + id_tarjeta + "\"";

        SQLiteDatabase db = bdHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = "" + cursor.getString(cursor.getColumnIndex(Constants.T_ID));
                @SuppressLint("Range") String titulo = "" + cursor.getString(cursor.getColumnIndex(Constants.T_TITULO));
                @SuppressLint("Range") String numero_tarjeta = "" + cursor.getString(cursor.getColumnIndex(Constants.T_NUMERO_TARJETA));
                @SuppressLint("Range") String nombre_tarjeta = "" + cursor.getString(cursor.getColumnIndex(Constants.T_NOMBRE_TARJETA));
                @SuppressLint("Range") String fecha_expiracion = "" + cursor.getString(cursor.getColumnIndex(Constants.T_FECHA_EXPIRACION));
                @SuppressLint("Range") String cvv = "" + cursor.getString(cursor.getColumnIndex(Constants.T_CVV));
                @SuppressLint("Range") String nota = "" + cursor.getString(cursor.getColumnIndex(Constants.T_NOTA));
                @SuppressLint("Range") String t_registro = "" + cursor.getString(cursor.getColumnIndex(Constants.T_TIEMPO_REGISTRO));
                @SuppressLint("Range") String t_actualizacion = "" + cursor.getString(cursor.getColumnIndex(Constants.T_TIEMPO_ACTUALIZACION));

                // Convertir los tiempos en DD/MM/AAAA

                // Registro
                Calendar calendar_t_r = Calendar.getInstance(Locale.getDefault());
                calendar_t_r.setTimeInMillis(Long.parseLong(t_registro));
                String tiempo_registro = "" + DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar_t_r);

                // Actualización
                Calendar calendar_t_a = Calendar.getInstance(Locale.getDefault());
                calendar_t_a.setTimeInMillis(Long.parseLong(t_actualizacion));
                String tiempo_actualizacion = "" + DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar_t_a);

                // Colocar la información en las vistas
                D_Titulo.setText(titulo);
                D_Numero_Tarjeta.setText(numero_tarjeta);
                D_Nombre_Tarjeta.setText(nombre_tarjeta);
                D_Fecha_Expiracion.setText(fecha_expiracion);
                D_CVV.setText(cvv);
                D_Nota.setText(nota);
                D_Tiempo_registro.setText(tiempo_registro);
                D_Tiempo_actualizacion.setText(tiempo_actualizacion);

            } while (cursor.moveToNext());
        }

        db.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}