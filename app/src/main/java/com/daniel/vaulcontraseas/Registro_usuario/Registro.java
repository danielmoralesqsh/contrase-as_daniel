package com.daniel.vaulcontraseas.Registro_usuario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daniel.vaulcontraseas.Login_usuario.Login_user;
import com.daniel.vaulcontraseas.R;

public class Registro extends AppCompatActivity {

    EditText EtMPassword, EtCPassword;
    Button Btn_Registrar;

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREFS = "mi_pref";
    private static final String PASSWORD = "password";
    private static final String CONFIRM_PASSWORD = "confirm_password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        IniciarVariables();
        VerificarContraseña();

        Btn_Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string_password = EtMPassword.getText().toString().trim();
                String string_confirm_password = EtCPassword.getText().toString().trim();

                //Validacion de campos
                if (TextUtils.isEmpty(string_password)){
                    Toast.makeText(Registro.this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
                }
                else if (!esContraseñaSegura(string_password)){
                    Toast.makeText(Registro.this, "La contraseña debe tener al menos un número, una letra mayúscula y un carácter especial", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(string_confirm_password)){
                    Toast.makeText(Registro.this, "Confirme su contraseña", Toast.LENGTH_SHORT).show();
                }
                else if (!string_password.equals(string_confirm_password)){
                    Toast.makeText(Registro.this, "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Guardar datos los datos en el edit text
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(PASSWORD, string_password);
                    editor.putString(CONFIRM_PASSWORD, string_confirm_password);
                    editor.apply();

                    Toast.makeText(Registro.this, "Contraseña guardada correctamente", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Registro.this, Login_user.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void IniciarVariables() {
        EtMPassword = findViewById(R.id.EtMPassword);
        EtCPassword = findViewById(R.id.EtCPassword);
        Btn_Registrar = findViewById(R.id.Btn_Registrar);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
    }

    private void VerificarContraseña() {
        String mi_password = sharedPreferences.getString(PASSWORD, null);
        //si ya hay una contraseña guardada que mande a iniciar sesion
        if (mi_password!=null){
            Intent intent = new Intent(Registro.this, Login_user.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean esContraseñaSegura(String contraseña) {
        if (contraseña.length() < 6) {
            return false;
        }
        boolean tieneNumero = false;
        boolean tieneMayuscula = false;
        boolean tieneEspecial = false;

        for (char c : contraseña.toCharArray()) {
            if (Character.isDigit(c)) {
                tieneNumero = true;
            }
            if (Character.isUpperCase(c)) {
                tieneMayuscula = true;
            }
            if (!Character.isLetterOrDigit(c)) {
                tieneEspecial = true;
            }
        }
        return tieneNumero && tieneMayuscula && tieneEspecial;
    }
}