package com.daniel.vaulcontraseas;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.daniel.vaulcontraseas.Fragmentos.F_Ajustes;
import com.daniel.vaulcontraseas.Fragmentos.F_Todas;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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

        //Fragmento al ejecutar la aplicacion
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new F_Todas()).commit();
            navigationView.setCheckedItem(R.id.Opcion_todas);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Opcion_todas){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new F_Todas()).commit();
        }
        if (id == R.id.Opcion_ajustes){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new F_Ajustes()).commit();
        }
        if (id == R.id.Opciones_salir){
            Toast.makeText(this, "Cerraste Sesion", Toast.LENGTH_SHORT).show();
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {

        //Si dobletap es verdader nos va a sacar  de la aplicaciaon

        if (dobletap){
            super.onBackPressed();
            Toast.makeText(this, "Saliste del gestor", Toast.LENGTH_SHORT).show();
            return;
        }

        //se activa al tocar una ves el boton de retroceso
        this.dobletap=true;
        Toast.makeText(this, "Presion dos veces para salir", Toast.LENGTH_SHORT).show();

        //despues de volverse verdadero dobletap con el siguiente codigo despues de cierto tiempo vuelve a falso
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                dobletap = false;
            }
        }, 2000);
    }
}