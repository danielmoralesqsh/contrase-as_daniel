package com.daniel.vaulcontraseas.Fragmentos;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.vaulcontraseas.BaseDeDatos.BDHelper;
import com.daniel.vaulcontraseas.BaseDeDatos.Constants;
import com.daniel.vaulcontraseas.MainActivity;
import com.daniel.vaulcontraseas.Modelo.Password;
import com.daniel.vaulcontraseas.R;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class F_Ajustes extends Fragment {

    TextView Elimimnar_todos_registros, Exportar_registros, Importar_registros;
    Dialog dialog;
    BDHelper bdHelper;
    String ordenarTituloASC = Constants.C_TITULO + " ASC";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_f__ajustes, container, false);

        Elimimnar_todos_registros = view.findViewById(R.id.Elimimnar_todos_registros);
        Exportar_registros = view.findViewById(R.id.Exportar_registros);
        Importar_registros = view.findViewById(R.id.Importar_registros);
        dialog = new Dialog(getActivity());
        bdHelper = new BDHelper(getActivity());

        Elimimnar_todos_registros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo_eliminar_registros();
            }
        });

        Exportar_registros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExportarRegistros();
            }
        });

        Importar_registros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Importar Archihvo", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Importar registros");
                builder.setMessage("Se elmininaran los registros actuales");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bdHelper.Eliminar_todos_registros();
                        ImportarRegistros();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Importacion Cancelada", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.create().show();
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

    private void ExportarRegistros() {
        //Nombre de la carpeta
        File carpeta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "VaulContraseas");

        boolean carpeta_existe = carpeta.exists();

        if (!carpeta.exists()) {
            //si la capeta no existe creamos una nueva
            carpeta_existe = carpeta.mkdirs();
        }

        //Nombre del archivo
        String csvnombreArchivo = "VaulContraseas.csv";
        //Concatenamos la carpeta con el nombre del archivo
        String Carpeta_acrchivo = carpeta + "/" + csvnombreArchivo;

        //obtenemos el registro a exportar
        ArrayList<Password> registroList = new ArrayList<>();
        registroList.clear();
        registroList = bdHelper.ObtenerTodosLosRegistros(ordenarTituloASC);

        try {

            //Escribir en el archivo
            FileWriter fileWriter = new FileWriter(Carpeta_acrchivo);
            for (int i = 0; i < registroList.size(); i++) {
                fileWriter.append("" + registroList.get(i).getId());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getTitulo());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getCuenta());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getNombre_usuario());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getPassword());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getSitio_web());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getNota());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getT_registro());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getT_actualizacion());
                fileWriter.append("\n");
            }

            fileWriter.flush();
            fileWriter.close();
            Toast.makeText(getActivity(), "Registros exportados exitosamente", Toast.LENGTH_SHORT).show();

        }catch (Exception e){

            Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }


    }

    private void ImportarRegistros() {
        //Establecer la ruta
        String Carpeta_archivo = Environment.getExternalStorageDirectory()+ "/Download/" + "/VaulContraseas/" + "VaulContraseas.csv";
        File archivo = new File(Carpeta_archivo);
        if (archivo.exists()){
            //Si el archivo existe
            try {

                CSVReader csvReader = new CSVReader(new FileReader(archivo.getAbsoluteFile()));
                String [] nexline;
                while ((nexline = csvReader.readNext()) != null){
                    String ids = nexline[0];
                    String titulo = nexline[1];
                    String cuenta = nexline[2];
                    String nombre_usuario = nexline[3];
                    String password = nexline[4];
                    String sitio_web = nexline[5];
                    String nota = nexline[6];
                    String t_registro = nexline[7];
                    String t_actualizacion = nexline[8];

                    long id = bdHelper.insertarRegistro(
                            ""+titulo,
                            ""+cuenta,
                            ""+nombre_usuario,
                            ""+password,
                            ""+sitio_web,
                            ""+nota,
                            ""+t_registro,
                            ""+t_actualizacion);
                }

                Toast.makeText(getActivity(), "Registros importados correctamente", Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                Toast.makeText(getActivity(), "Error al importar", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getActivity(), "No existe una exportacion previa", Toast.LENGTH_SHORT).show();
        }

    }

}