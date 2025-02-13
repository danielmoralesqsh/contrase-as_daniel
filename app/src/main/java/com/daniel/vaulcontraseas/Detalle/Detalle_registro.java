package com.daniel.vaulcontraseas.Detalle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daniel.vaulcontraseas.BaseDeDatos.BDHelper;
import com.daniel.vaulcontraseas.BaseDeDatos.Constants;
import com.daniel.vaulcontraseas.R;

import java.util.Calendar;
import java.util.Locale;

public class Detalle_registro extends AppCompatActivity {

    TextView D_Titulo, D_Cuenta, D_Nombre_usuario, D_Sitio_web, D_Nota,
            D_Tiempo_registro, D_Tiempo_actualizacion;

    EditText D_Password;

    ImageButton Im_ir_pagina;

    String id_registro;

    BDHelper bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_registro);

        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        id_registro = intent.getStringExtra("Id_registro");
        Toast.makeText(this, "Id  del registro: " + id_registro, Toast.LENGTH_SHORT).show();

        bdHelper = new BDHelper(this);

        InicializarVariables();
        MostrarInformacionRegistro();

        //Obtener titulo de un registro
        String titulo_registro = D_Titulo.getText().toString();
        assert actionBar != null;
        actionBar.setTitle(titulo_registro);;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Im_ir_pagina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url_pagina = D_Sitio_web.getText().toString().trim();

                if (!url_pagina.equals("")){

                    abrirPaginaWeb(url_pagina);

                }else {
                    Toast.makeText(Detalle_registro.this, "No existe URL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void InicializarVariables(){
        D_Titulo = findViewById(R.id.D_Titulo);
        D_Cuenta = findViewById(R.id.D_Cuenta);
        D_Nombre_usuario = findViewById(R.id.D_Nombre_usuario);
        D_Password = findViewById(R.id.D_Password);
        D_Sitio_web = findViewById(R.id.D_Sitio_web);
        D_Nota = findViewById(R.id.D_Nota);
        D_Tiempo_registro = findViewById(R.id.D_Tiempo_registro);
        D_Tiempo_actualizacion = findViewById(R.id.D_Tiempo_actualizacion);

        Im_ir_pagina = findViewById(R.id.Im_ir_pagina);

    }

    private void MostrarInformacionRegistro(){
        String consulta = "SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID + " =\"" + id_registro+"\"";

        SQLiteDatabase db = bdHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(consulta, null);

        if (cursor.moveToFirst()){
            do {
                @SuppressLint("Range") String id = "" + cursor.getString(cursor.getColumnIndex(Constants.C_ID));
                @SuppressLint("Range") String titulo = "" + cursor.getString(cursor.getColumnIndex(Constants.C_TITULO));
                @SuppressLint("Range") String cuenta = "" + cursor.getString(cursor.getColumnIndex(Constants.C_CUENTA));
                @SuppressLint("Range") String usuario = "" + cursor.getString(cursor.getColumnIndex(Constants.C_NOMBRE_USUARIO));
                @SuppressLint("Range") String contraseña = "" + cursor.getString(cursor.getColumnIndex(Constants.C_PASSWORD));
                @SuppressLint("Range") String sitio_web = "" + cursor.getString(cursor.getColumnIndex(Constants.C_SITIO_WEB));
                @SuppressLint("Range") String nota = "" + cursor.getString(cursor.getColumnIndex(Constants.C_NOTA));
                @SuppressLint("Range") String t_registro = "" + cursor.getString(cursor.getColumnIndex(Constants.C_TIEMPO_REGISTRO));
                @SuppressLint("Range") String t_actualizacion = "" + cursor.getString(cursor.getColumnIndex(Constants.C_TIEMPO_ACTUALIZACION));

                //Convertir los tiempos en DD/MM/AAAA

                //registro
                Calendar calendar_t_r = Calendar.getInstance(Locale.getDefault());
                calendar_t_r.setTimeInMillis(Long.parseLong(t_registro));
                String tiempo_registro = "" + DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar_t_r);

                //actualizacion
                Calendar calendar_t_a = Calendar.getInstance(Locale.getDefault());
                calendar_t_a.setTimeInMillis(Long.parseLong(t_actualizacion));
                String tiempo_actualizacion = "" + DateFormat.format("dd/MM/yyyy hh:mm:aa", calendar_t_a);

                //colocar la informacion en  las vistas
                D_Titulo.setText(titulo);
                D_Cuenta.setText(cuenta);
                D_Nombre_usuario.setText(usuario);
                D_Password.setText(contraseña);
                D_Password.setEnabled(false);
                D_Password.setBackgroundColor(Color.TRANSPARENT);
                D_Password.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);
                D_Sitio_web.setText(sitio_web);
                D_Nota.setText(nota);
                D_Tiempo_registro.setText(tiempo_registro);
                D_Tiempo_actualizacion.setText(tiempo_actualizacion);

            }while (cursor.moveToNext());
        }

        db.close();
    }

    private void abrirPaginaWeb(String urlPagina) {

        Intent navergar = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+urlPagina));
        startActivity(navergar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}