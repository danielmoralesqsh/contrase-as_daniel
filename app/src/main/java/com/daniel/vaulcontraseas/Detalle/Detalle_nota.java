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

public class Detalle_nota extends AppCompatActivity {

    TextView D_Titulo, D_Contenido, D_Tiempo_registro, D_Tiempo_actualizacion;

    String id_nota;

    BDHelper bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_nota);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        id_nota = intent.getStringExtra("Id_nota");
        Toast.makeText(this, "Id de la nota: " + id_nota, Toast.LENGTH_SHORT).show();

        bdHelper = new BDHelper(this);

        InicializarVariables();
        MostrarInformacionNota();

        // Obtener título de la nota
        String titulo_nota = D_Titulo.getText().toString();
        assert actionBar != null;
        actionBar.setTitle(titulo_nota);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void InicializarVariables() {
        D_Titulo = findViewById(R.id.D_Titulo);
        D_Contenido = findViewById(R.id.D_Contenido);
        D_Tiempo_registro = findViewById(R.id.D_Tiempo_registro);
        D_Tiempo_actualizacion = findViewById(R.id.D_Tiempo_actualizacion);
    }

    private void MostrarInformacionNota() {
        String consulta = "SELECT * FROM " + Constants.TABLE_NOTAS + " WHERE " + Constants.N_ID + " =\"" + id_nota + "\"";

        SQLiteDatabase db = bdHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = "" + cursor.getString(cursor.getColumnIndex(Constants.N_ID));
                @SuppressLint("Range") String titulo = "" + cursor.getString(cursor.getColumnIndex(Constants.N_TITULO));
                @SuppressLint("Range") String contenido = "" + cursor.getString(cursor.getColumnIndex(Constants.N_CONTENIDO));
                @SuppressLint("Range") String t_registro = "" + cursor.getString(cursor.getColumnIndex(Constants.N_TIEMPO_REGISTRO));
                @SuppressLint("Range") String t_actualizacion = "" + cursor.getString(cursor.getColumnIndex(Constants.N_TIEMPO_ACTUALIZACION));

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
                D_Contenido.setText(contenido);
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