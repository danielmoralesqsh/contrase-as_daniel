package com.daniel.vaulcontraseas.Fragmentos;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daniel.vaulcontraseas.Adaptador.Adaptador_password;
import com.daniel.vaulcontraseas.BaseDeDatos.BDHelper;
import com.daniel.vaulcontraseas.BaseDeDatos.Constants;
import com.daniel.vaulcontraseas.OpcionesPassword.Agregar_Actualizar_Registro;
import com.daniel.vaulcontraseas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class F_Todas extends Fragment {

    BDHelper helper;
    RecyclerView recycler_view_registros;
    FloatingActionButton FAB_AgregarPassword;

    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f__todas, container, false);

        recycler_view_registros = view.findViewById(R.id.recycler_view_registros);
        FAB_AgregarPassword = view.findViewById(R.id.FAB_AgregarPassword);
        helper = new BDHelper(getActivity());
        dialog = new Dialog(getActivity());

        CargarRegistros();

        FAB_AgregarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Agregar_Actualizar_Registro.class);
                intent.putExtra("MODO_EDICION", false);
                startActivity(intent);
            }
        });

        return view;
    }

    private void CargarRegistros() {
        Adaptador_password adaptadorPassword = new Adaptador_password(getActivity(), helper.ObtenerTodosLosRegistros(
                Constants.C_TITULO + " ASC"));
        recycler_view_registros.setAdapter(adaptadorPassword);
    }

    private void BuscarRegistros(String consulta){
        Adaptador_password adaptador_password = new Adaptador_password(getActivity(), helper.BuscarRegistros(consulta));
        recycler_view_registros.setAdapter(adaptador_password);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragmento_todos, menu);

        MenuItem  item = menu.findItem(R.id.menu_buscar_registro);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Realizar la busqueda despues presionar el boton buscar en el teclado del telefono
                BuscarRegistros(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Realizar la busqueda mientras vamos escribiendo
                BuscarRegistros(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_Numero_registros){
            visualizar_registros();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private void visualizar_registros(){
        TextView Total;
        Button BtnTotal;

        dialog.setContentView(R.layout.cuadro_total_registros);

        Total = dialog.findViewById(R.id.Total);
        BtnTotal = dialog.findViewById(R.id.BtnTotal);

        //Obtener el valor de los registros
        int total = helper.ObtenerNumeroRegistros();
        //Convertir el total en cadena
        String total_string = String.valueOf(total);

        Total.setText(total_string);

        BtnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCancelable(false);

    }

}