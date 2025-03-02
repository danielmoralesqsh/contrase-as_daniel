package com.daniel.vaulcontraseas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.daniel.vaulcontraseas.Fragmentos.F_Ajustes;
import com.daniel.vaulcontraseas.Fragmentos.F_Notas;
import com.daniel.vaulcontraseas.Fragmentos.F_Todas;
import com.daniel.vaulcontraseas.Login_usuario.Login_user;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;

    boolean dobletap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Verificar si hay un extra en el Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("FRAGMENT_TO_LOAD")) {
            String fragmentToLoad = intent.getStringExtra("FRAGMENT_TO_LOAD");
            if ("F_Notas".equals(fragmentToLoad)) {
                // Cargar el fragmento de notas
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new F_Notas()).commit();
                navigationView.setCheckedItem(R.id.Opcion_notas);
            } else {
                // Cargar el fragmento por defecto (F_Todas)
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new F_Todas()).commit();
                navigationView.setCheckedItem(R.id.Opcion_todas);
            }
        } else {
            // Fragmento al ejecutar la aplicación por primera vez
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new F_Todas()).commit();
                navigationView.setCheckedItem(R.id.Opcion_todas);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Opcion_todas) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new F_Todas()).commit();
        }
        if (id == R.id.Opcion_notas) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new F_Notas()).commit();
        }
        if (id == R.id.Opcion_ajustes) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new F_Ajustes()).commit();
        }
        if (id == R.id.Opciones_salir) {
            CerrarSesion();
            Toast.makeText(this, "Cerraste Sesion", Toast.LENGTH_SHORT).show();
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void CerrarSesion() {
        startActivity(new Intent(MainActivity.this, Login_user.class));
        Toast.makeText(this, "Cerraste Sesion", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        // Si dobletap es verdadero nos va a sacar de la aplicación
        if (dobletap) {
            super.onBackPressed();
            Toast.makeText(this, "Saliste del gestor", Toast.LENGTH_SHORT).show();
            return;
        }

        // Se activa al tocar una vez el botón de retroceso
        this.dobletap = true;
        Toast.makeText(this, "Presiona dos veces para salir", Toast.LENGTH_SHORT).show();

        // Después de volverse verdadero dobletap, con el siguiente código después de cierto tiempo vuelve a falso
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                dobletap = false;
            }
        }, 2000);
    }
}