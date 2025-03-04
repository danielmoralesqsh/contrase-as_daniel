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
import android.widget.Toast;

import com.daniel.vaulcontraseas.Adaptador.Adaptador_tarjetas;
import com.daniel.vaulcontraseas.BaseDeDatos.BDHelper;
import com.daniel.vaulcontraseas.BaseDeDatos.Constants;
import com.daniel.vaulcontraseas.OpcionesPassword.Agregar_Actualizar_Tarjeta;
import com.daniel.vaulcontraseas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class F_Tarjetas extends Fragment {

    BDHelper helper;
    RecyclerView recycler_view_tarjetas;
    FloatingActionButton FAB_AgregarTarjeta;

    Dialog dialog, dialog_ordenar;

    String ordenarNuevos = Constants.T_TIEMPO_REGISTRO + " DESC";
    String ordenarAntiguos = Constants.T_TIEMPO_REGISTRO + " ASC";
    String ordenarTituloAsc = Constants.T_TITULO + " ASC";
    String ordenarTituloDesc = Constants.T_TITULO + " DESC";

    String EstadoOrden = ordenarTituloAsc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f__tarjetas, container, false);

        recycler_view_tarjetas = view.findViewById(R.id.recycler_view_tarjetas);
        FAB_AgregarTarjeta = view.findViewById(R.id.FAB_AgregarTarjeta);
        helper = new BDHelper(getActivity());
        dialog = new Dialog(getActivity());
        dialog_ordenar = new Dialog(getActivity());

        CargarTarjetas(ordenarTituloAsc);

        FAB_AgregarTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Agregar_Actualizar_Tarjeta.class);
                intent.putExtra("MODO_EDICION", false);
                startActivity(intent);
            }
        });

        return view;
    }

    private void CargarTarjetas(String orderby) {
        EstadoOrden = orderby;
        Adaptador_tarjetas adaptadorTarjetas = new Adaptador_tarjetas(getActivity(), helper.obtenerTodasLasTarjetas(orderby));
        recycler_view_tarjetas.setAdapter(adaptadorTarjetas);
    }

    private void BuscarTarjetas(String consulta) {
        Adaptador_tarjetas adaptadorTarjetas = new Adaptador_tarjetas(getActivity(), helper.buscarTarjetas(consulta));
        recycler_view_tarjetas.setAdapter(adaptadorTarjetas);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragmento_tarjetas, menu);

        MenuItem item = menu.findItem(R.id.menu_buscar_tarjeta);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Realizar la búsqueda después de presionar el botón buscar en el teclado del teléfono
                BuscarTarjetas(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Realizar la búsqueda mientras escribimos
                BuscarTarjetas(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_ordenar_tarjetas) {
            ordenar_tarjetas();
            return true;
        }
        if (id == R.id.menu_numero_tarjetas) {
            visualizar_tarjetas();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        CargarTarjetas(EstadoOrden);
        super.onResume();
    }

    private void visualizar_tarjetas() {
        TextView Total;
        Button BtnTotal;

        dialog.setContentView(R.layout.cuadro_total_tarjetas);

        Total = dialog.findViewById(R.id.Total_Tarjetas);
        BtnTotal = dialog.findViewById(R.id.BtnTotal_Tarjetas);

        // Obtener el valor de los registros
        int total = helper.obtenerNumeroTarjetas();
        // Convertir el total en cadena
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

    private void ordenar_tarjetas() {
        Button BtnNuevos, BtnAntiguos, BtnTituloAsc, BtnTituloDesc;

        dialog_ordenar.setContentView(R.layout.cuadro_dialogo_ordenar_tarjetas);

        BtnNuevos = dialog_ordenar.findViewById(R.id.Btn_Nuevos_Tarjetas);
        BtnAntiguos = dialog_ordenar.findViewById(R.id.Btn_Pasados_Tarjetas);
        BtnTituloAsc = dialog_ordenar.findViewById(R.id.Btn_Titulo_asc_Tarjetas);
        BtnTituloDesc = dialog_ordenar.findViewById(R.id.Btn_Titulo_desc_Tarjetas);

        BtnNuevos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarTarjetas(ordenarNuevos);
                dialog_ordenar.dismiss();
            }
        });
        BtnAntiguos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarTarjetas(ordenarAntiguos);
                dialog_ordenar.dismiss();
            }
        });
        BtnTituloAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarTarjetas(ordenarTituloAsc);
                dialog_ordenar.dismiss();
            }
        });
        BtnTituloDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarTarjetas(ordenarTituloDesc);
                dialog_ordenar.dismiss();
            }
        });

        dialog_ordenar.show();
        dialog_ordenar.setCancelable(true);
    }
}