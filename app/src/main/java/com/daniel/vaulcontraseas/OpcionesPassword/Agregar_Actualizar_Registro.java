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

public class Agregar_Actualizar_Registro extends AppCompatActivity {

    EditText EtTitulo, EtCuenta, EtNombreUsuario, EtPassword, EtSitioWeb, EtNota;

    String id, titulo, cuenta, nombre_usuario, password, sitio_web, nota, tiempo_registro, tiempo_actualizacion;

    private boolean MODO_EDICION = false;

    private BDHelper bdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_password);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        InicializarVariables();
        Obtener_Informacion();
    }

    private void InicializarVariables(){
        EtTitulo = findViewById(R.id.EtTitulo);
        EtCuenta = findViewById(R.id.EtCuenta);
        EtNombreUsuario = findViewById(R.id.EtNombreUsuario);
        EtPassword = findViewById(R.id.EtPassword);
        EtSitioWeb = findViewById(R.id.EtSitioWeb);
        EtNota = findViewById(R.id.EtNota);

        bdHelper = new BDHelper(this);
    }

    private void  Obtener_Informacion(){
        Intent intent = getIntent();
        MODO_EDICION = intent.getBooleanExtra("MODO_EDICION", false);

        if (MODO_EDICION){
            //verdad
            id = intent.getStringExtra("ID");
            titulo = intent.getStringExtra("TITULO");
            cuenta = intent.getStringExtra("CUENTA");
            nombre_usuario = intent.getStringExtra("NOMBRE_USUARIO");
            password = intent.getStringExtra("PASSWORD");
            sitio_web = intent.getStringExtra("SITIO_WEB");
            nota = intent.getStringExtra("NOTA");
            tiempo_registro = intent.getStringExtra("T_REGISTRO");
            tiempo_actualizacion = intent.getStringExtra("T_ACTUALIZACION");

            //mostrar en las vistas
            EtTitulo.setText(titulo);
            EtCuenta.setText(cuenta);
            EtNombreUsuario.setText(nombre_usuario);
            EtPassword.setText(password);
            EtSitioWeb.setText(sitio_web);
            EtNota.setText(nota);
        }
        else{
            //falso, se va a agregar un nuevo registro
        }
    }

    private void Agregar_Actualizar_R(){
        //obtener datos de entrada
        titulo = EtTitulo.getText().toString().trim();
        cuenta = EtCuenta.getText().toString().trim();
        nombre_usuario = EtNombreUsuario.getText().toString().trim();
        password = EtPassword.getText().toString().trim();
        sitio_web = EtSitioWeb.getText().toString().trim();
        nota = EtNota.getText().toString().trim();

        if (MODO_EDICION){
            //Actualizar el registro
            //Tiempo del dispositivo
            String tiempo_actual = ""+ System.currentTimeMillis();
            bdHelper.actualizarRegistro(
                    "" + id,
                    "" + titulo,
                    "" + cuenta,
                    "" + nombre_usuario,
                    "" + password,
                    "" + sitio_web,
                    "" +  nota,
                    "" + tiempo_registro,
                    "" + tiempo_actual
            );

            Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Agregar_Actualizar_Registro.this, MainActivity.class));
            finish();

        }else {
            //Agregar un nuevo registro
            if (!titulo.equals("")){
                //obtener el timpo del dispositivo
                String tiempo = ""+System.currentTimeMillis();
                long id = bdHelper.insertarRegistro(
                        ""+ titulo,
                        ""+ cuenta,
                        ""+ nombre_usuario,
                        ""+ password,
                        ""+ sitio_web,
                        ""+ nota,
                        ""+ tiempo,
                        ""+ tiempo
                );

                Toast.makeText(this, "Se ha guardado con exito: "+id, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Agregar_Actualizar_Registro.this, MainActivity.class));
                finish();
            }
            else {
                EtTitulo.setError("Campo Obligatario");
                EtTitulo.setFocusable(true);
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
        if (item.getItemId() == R.id.Guardar_Password){
            Agregar_Actualizar_R();
        }
        return super.onOptionsItemSelected(item);
    }
}