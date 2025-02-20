package com.daniel.vaulcontraseas.Login_usuario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import com.daniel.vaulcontraseas.MainActivity;
import com.daniel.vaulcontraseas.R;

public class Login_user extends AppCompatActivity {

    EditText EtMPassword;
    Button Btn_ingresar, BtnInicioSesionBiometrico;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREFS = "mi_pref";
    private static final String PASSWORD = "password";
    private static final String CONFIRM_PASSWORD = "confirm_password";

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        IniciarVariables();

        Btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string_password = EtMPassword.getText().toString().trim();
                //Obtener la contraseña almacenada
                String passwordStored = sharedPreferences.getString(PASSWORD,null);

                if (passwordStored == null) {
                    Toast.makeText(Login_user.this, "No hay contraseña registrada", Toast.LENGTH_SHORT).show();
                } else if (string_password.equals("")) {
                    Toast.makeText(Login_user.this, "Campo Obligatorio", Toast.LENGTH_SHORT).show();
                } else if (!string_password.equals(passwordStored)) {
                    Toast.makeText(Login_user.this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Login_user.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        biometricPrompt = new BiometricPrompt(Login_user.this, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(Login_user.this, "No existen huellas dactilares registradas", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(Login_user.this, "Autenticacion correcta, Bienvenido/a", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login_user.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(Login_user.this, "Fallo de autenticacion", Toast.LENGTH_SHORT).show();
            }
        });
        //Comportamiento del aviso biometrico
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticacion Biometrica")
                .setNegativeButtonText("Cancelar")
                .build();

        BtnInicioSesionBiometrico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }

    private void IniciarVariables(){
        EtMPassword = findViewById(R.id.EtMPassword);
        Btn_ingresar = findViewById(R.id.Btn_ingresar);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        BtnInicioSesionBiometrico = findViewById(R.id.BtnInicioSesionBiometrico);
    }
}