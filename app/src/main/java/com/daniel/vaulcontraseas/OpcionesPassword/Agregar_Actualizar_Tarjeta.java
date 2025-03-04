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

public class Agregar_Actualizar_Tarjeta extends AppCompatActivity {

    EditText EtTitulo_Tarjeta, EtNumero_Tarjeta, EtNombre_Tarjeta, EtFecha_Expiracion, EtCVV, EtNota_Tarjeta;

    String id, titulo, numero_tarjeta, nombre_tarjeta, fecha_expiracion, cvv, nota, tiempo_registro, tiempo_actualizacion;

    private boolean MODO_EDICION = false;

    private BDHelper bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tarjeta);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        InicializarVariables();
        Obtener_Informacion();
    }

    private void InicializarVariables() {
        EtTitulo_Tarjeta = findViewById(R.id.EtTitulo_Tarjeta);
        EtNumero_Tarjeta = findViewById(R.id.EtNumero_Tarjeta);
        EtNombre_Tarjeta = findViewById(R.id.EtNombre_Tarjeta);
        EtFecha_Expiracion = findViewById(R.id.EtFecha_Expiracion);
        EtCVV = findViewById(R.id.EtCVV);
        EtNota_Tarjeta = findViewById(R.id.EtNota_Tarjeta);

        bdHelper = new BDHelper(this);
    }

    private void Obtener_Informacion() {
        Intent intent = getIntent();
        MODO_EDICION = intent.getBooleanExtra("MODO_EDICION", false);

        if (MODO_EDICION) {
            // Modo edición
            id = intent.getStringExtra("ID");
            titulo = intent.getStringExtra("TITULO");
            numero_tarjeta = intent.getStringExtra("NUMERO_TARJETA");
            nombre_tarjeta = intent.getStringExtra("NOMBRE_TARJETA");
            fecha_expiracion = intent.getStringExtra("FECHA_EXPIRACION");
            cvv = intent.getStringExtra("CVV");
            nota = intent.getStringExtra("NOTA");
            tiempo_registro = intent.getStringExtra("T_REGISTRO");
            tiempo_actualizacion = intent.getStringExtra("T_ACTUALIZACION");

            // Mostrar en las vistas
            EtTitulo_Tarjeta.setText(titulo);
            EtNumero_Tarjeta.setText(numero_tarjeta);
            EtNombre_Tarjeta.setText(nombre_tarjeta);
            EtFecha_Expiracion.setText(fecha_expiracion);
            EtCVV.setText(cvv);
            EtNota_Tarjeta.setText(nota);
        } else {
            // Modo agregar nueva tarjeta
        }
    }

    private void Agregar_Actualizar_Tarjeta() {
        // Obtener datos de entrada
        titulo = EtTitulo_Tarjeta.getText().toString().trim();
        numero_tarjeta = EtNumero_Tarjeta.getText().toString().trim();
        nombre_tarjeta = EtNombre_Tarjeta.getText().toString().trim();
        fecha_expiracion = EtFecha_Expiracion.getText().toString().trim();
        cvv = EtCVV.getText().toString().trim();
        nota = EtNota_Tarjeta.getText().toString().trim();

        if (MODO_EDICION) {
            // Actualizar la tarjeta
            String tiempo_actual = "" + System.currentTimeMillis();
            bdHelper.actualizarTarjeta(
                    "" + id,
                    "" + titulo,
                    "" + numero_tarjeta,
                    "" + nombre_tarjeta,
                    "" + fecha_expiracion,
                    "" + cvv,
                    "" + nota,
                    "" + tiempo_registro,
                    "" + tiempo_actual
            );

            Toast.makeText(this, "Tarjeta actualizada correctamente", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Agregar_Actualizar_Tarjeta.this, MainActivity.class);
            intent.putExtra("FRAGMENT_TO_LOAD", "F_Tarjetas"); // Redirigir a F_Tarjetas
            startActivity(intent);
            finish();
        } else {
            // Agregar una nueva tarjeta
            if (!titulo.equals("")){
                // Obtener el tiempo del dispositivo
                String tiempo = "" + System.currentTimeMillis();
                long id = bdHelper.insertarTarjeta(
                        "" + titulo,
                        "" + numero_tarjeta,
                        "" + nombre_tarjeta,
                        "" + fecha_expiracion,
                        "" + cvv,
                        "" + nota,
                        "" + tiempo,
                        "" + tiempo
                );

                Toast.makeText(this, "Tarjeta guardada con éxito: " + id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Agregar_Actualizar_Tarjeta.this, MainActivity.class);
                intent.putExtra("FRAGMENT_TO_LOAD", "F_Tarjetas"); // Redirigir a F_Tarjetas
                startActivity(intent);
                finish();
            } else {
                EtTitulo_Tarjeta.setError("Campo obligatorio");
                EtTitulo_Tarjeta.setFocusable(true);
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
            Agregar_Actualizar_Tarjeta();
        }
        return super.onOptionsItemSelected(item);
    }
}