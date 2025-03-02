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

import com.daniel.vaulcontraseas.Adaptador.Adaptador_notas;
import com.daniel.vaulcontraseas.BaseDeDatos.BDHelper;
import com.daniel.vaulcontraseas.BaseDeDatos.Constants;
import com.daniel.vaulcontraseas.OpcionesPassword.Agregar_Actualizar_Nota;
import com.daniel.vaulcontraseas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class F_Notas extends Fragment {

    BDHelper helper;
    RecyclerView recycler_view_notas;
    FloatingActionButton FAB_AgregarNota;

    Dialog dialog, dialog_ordenar;

    String ordenarNuevos = Constants.N_TIEMPO_REGISTRO + " DESC";
    String ordenarAntiguos = Constants.N_TIEMPO_REGISTRO + " ASC";
    String ordenarTituloAsc = Constants.N_TITULO + " ASC";
    String ordenarTituloDesc = Constants.N_TITULO + " DESC";

    String EstadoOrden = ordenarTituloAsc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f__notas, container, false);

        recycler_view_notas = view.findViewById(R.id.recycler_view_notas);
        FAB_AgregarNota = view.findViewById(R.id.FAB_AgregarPasswordNotas);
        helper = new BDHelper(getActivity());
        dialog = new Dialog(getActivity());
        dialog_ordenar = new Dialog(getActivity());

        CargarNotas(ordenarTituloAsc);

        FAB_AgregarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Agregar_Actualizar_Nota.class);
                intent.putExtra("MODO_EDICION", false);
                startActivity(intent);
            }
        });

        return view;
    }

    private void CargarNotas(String orderby) {
        EstadoOrden = orderby;
        Adaptador_notas adaptadorNotas = new Adaptador_notas(getActivity(), helper.obtenerTodasLasNotas(orderby));
        recycler_view_notas.setAdapter(adaptadorNotas);
    }

    private void BuscarNotas(String consulta) {
        Adaptador_notas adaptadorNotas = new Adaptador_notas(getActivity(), helper.buscarNotas(consulta));
        recycler_view_notas.setAdapter(adaptadorNotas);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragmento_notas, menu);

        MenuItem item = menu.findItem(R.id.menu_buscar_nota);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Realizar la búsqueda después de presionar el botón buscar en el teclado del teléfono
                BuscarNotas(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Realizar la búsqueda mientras escribimos
                BuscarNotas(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_ordenar_notas) {
            ordenar_notas();
            return true;
        }
        if (id == R.id.menu_numero_notas) {
            visualizar_notas();
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
        CargarNotas(EstadoOrden);
        super.onResume();
    }

    private void visualizar_notas() {
        TextView Total;
        Button BtnTotal;

        dialog.setContentView(R.layout.cuadro_total_notas);

        Total = dialog.findViewById(R.id.Total_Notas);
        BtnTotal = dialog.findViewById(R.id.BtnTotal_Notas);

        // Obtener el valor de los registros
        int total = helper.obtenerNumeroNotas();
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

    private void ordenar_notas() {
        Button BtnNuevos, BtnAntiguos, BtnTituloAsc, BtnTituloDesc;

        dialog_ordenar.setContentView(R.layout.cuadro_dialogo_ordenar_notas);

        BtnNuevos = dialog_ordenar.findViewById(R.id.Btn_Nuevos_Notas);
        BtnAntiguos = dialog_ordenar.findViewById(R.id.Btn_Pasados_Notas);
        BtnTituloAsc = dialog_ordenar.findViewById(R.id.Btn_Titulo_asc_Notas);
        BtnTituloDesc = dialog_ordenar.findViewById(R.id.Btn_Titulo_desc_Notas);

        BtnNuevos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarNotas(ordenarNuevos);
                dialog_ordenar.dismiss();
            }
        });
        BtnAntiguos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarNotas(ordenarAntiguos);
                dialog_ordenar.dismiss();
            }
        });
        BtnTituloAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarNotas(ordenarTituloAsc);
                dialog_ordenar.dismiss();
            }
        });
        BtnTituloDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarNotas(ordenarTituloDesc);
                dialog_ordenar.dismiss();
            }
        });

        dialog_ordenar.show();
        dialog_ordenar.setCancelable(true);
    }
}