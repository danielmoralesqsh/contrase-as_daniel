package com.daniel.vaulcontraseas.Fragmentos;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.vaulcontraseas.BaseDeDatos.BDHelper;
import com.daniel.vaulcontraseas.MainActivity;
import com.daniel.vaulcontraseas.R;

public class F_Ajustes extends Fragment {

    TextView Elimimnar_todos_registros;
    Dialog dialog;
    BDHelper bdHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_f__ajustes, container, false);

        Elimimnar_todos_registros = view.findViewById(R.id.Elimimnar_todos_registros);
        dialog = new Dialog(getActivity());
        bdHelper = new BDHelper(getActivity());

        Elimimnar_todos_registros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo_eliminar_registros();
            }
        });

        return view;
    }

    private void dialogo_eliminar_registros() {

        Button Btn_Si, Btn_No;

        dialog.setContentView(R.layout.cuadro_dialogo_eliminar_registros);

        Btn_Si = dialog.findViewById(R.id.Btn_Si);
        Btn_No = dialog.findViewById(R.id.Btn_No);

        Btn_Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bdHelper.Eliminar_todos_registros();
                startActivity(new Intent(getActivity(), MainActivity.class));
                Toast.makeText(getActivity(), "Registros eliminados", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        Btn_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Cancelar", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(false);
    }
}