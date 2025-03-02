package com.daniel.vaulcontraseas.OpcionesPassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.daniel.vaulcontraseas.BaseDeDatos.BDHelper;
import com.daniel.vaulcontraseas.MainActivity;
import com.daniel.vaulcontraseas.R;

public class Agregar_Actualizar_Nota extends AppCompatActivity {

    EditText EtTitulo_Notas, EtContenido_Notas;

    String id, titulo, contenido, tiempo_registro, tiempo_actualizacion;

    private boolean MODO_EDICION = false;

    private BDHelper bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        InicializarVariables();
        Obtener_Informacion();
    }

    private void InicializarVariables() {
        EtTitulo_Notas = findViewById(R.id.EtTitulo_Notas);
        EtContenido_Notas = findViewById(R.id.EtContenido_Notas);

        bdHelper = new BDHelper(this);
    }

    private void Obtener_Informacion() {
        Intent intent = getIntent();
        MODO_EDICION = intent.getBooleanExtra("MODO_EDICION", false);

        if (MODO_EDICION) {
            // Modo edición
            id = intent.getStringExtra("ID");
            titulo = intent.getStringExtra("TITULO");
            contenido = intent.getStringExtra("CONTENIDO");
            tiempo_registro = intent.getStringExtra("T_REGISTRO");
            tiempo_actualizacion = intent.getStringExtra("T_ACTUALIZACION");

            // Mostrar en las vistas
            EtTitulo_Notas.setText(titulo);
            EtContenido_Notas.setText(contenido);
        } else {
            // Modo agregar nueva nota
        }
    }

    private void Agregar_Actualizar_Nota() {
        // Obtener datos de entrada
        titulo = EtTitulo_Notas.getText().toString().trim();
        contenido = EtContenido_Notas.getText().toString().trim();

        if (MODO_EDICION) {
            // Actualizar la nota
            String tiempo_actual = "" + System.currentTimeMillis();
            bdHelper.actualizarNota(
                    "" + id,
                    "" + titulo,
                    "" + contenido,
                    "" + tiempo_registro,
                    "" + tiempo_actual
            );

            Toast.makeText(this, "Nota actualizada correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Agregar_Actualizar_Nota.this, MainActivity.class);
            intent.putExtra("FRAGMENT_TO_LOAD", "F_Notas"); // Redirigir a F_Notas
            startActivity(intent);
            finish();
        } else {
            // Agregar una nueva nota
            if (!titulo.equals("")) {
                // Obtener el tiempo del dispositivo
                String tiempo = "" + System.currentTimeMillis();
                long id = bdHelper.insertarNota(
                        "" + titulo,
                        "" + contenido,
                        "" + tiempo,
                        "" + tiempo
                );

                Toast.makeText(this, "Nota guardada con éxito: " + id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Agregar_Actualizar_Nota.this, MainActivity.class);
                intent.putExtra("FRAGMENT_TO_LOAD", "F_Notas"); // Redirigir a F_Notas
                startActivity(intent);
                finish();
            } else {
                EtTitulo_Notas.setError("Campo obligatorio");
                EtTitulo_Notas.setFocusable(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_guardar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Guardar_Password) {
            Agregar_Actualizar_Nota();
        }
        return super.onOptionsItemSelected(item);
    }
}