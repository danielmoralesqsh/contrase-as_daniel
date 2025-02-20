package com.daniel.vaulcontraseas.Fragmentos;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daniel.vaulcontraseas.BaseDeDatos.BDHelper;
import com.daniel.vaulcontraseas.BaseDeDatos.Constants;
import com.daniel.vaulcontraseas.Login_usuario.Login_user;
import com.daniel.vaulcontraseas.MainActivity;
import com.daniel.vaulcontraseas.Modelo.Password;
import com.daniel.vaulcontraseas.R;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class F_Ajustes extends Fragment {

    TextView Elimimnar_todos_registros, Exportar_registros, Importar_registros, Cambiar_contraseña_maestra;
    Dialog dialog, dialogo_p_maestra;
    BDHelper bdHelper;
    String ordenarTituloASC = Constants.C_TITULO + " ASC";

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREFS = "mi_pref";
    private static final String PASSWORD = "password";
    private static final String CONFIRM_PASSWORD = "confirm_password";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_f__ajustes, container, false);

        Elimimnar_todos_registros = view.findViewById(R.id.Elimimnar_todos_registros);
        Exportar_registros = view.findViewById(R.id.Exportar_registros);
        Importar_registros = view.findViewById(R.id.Importar_registros);
        Cambiar_contraseña_maestra = view.findViewById(R.id.Cambiar_contraseña_maestra);
        dialog = new Dialog(getActivity());
        dialogo_p_maestra = new Dialog(getActivity());
        bdHelper = new BDHelper(getActivity());

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, getActivity().MODE_PRIVATE);

        Elimimnar_todos_registros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo_eliminar_registros();
            }
        });

        Exportar_registros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    ExportarRegistros();
                } else {
                    SolicitudPermisoExportar.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
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
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                            bdHelper.Eliminar_todos_registros();
                            ImportarRegistros();
                        } else {
                            SolicitudPermisoImportar.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }
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

        Cambiar_contraseña_maestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Cambiar contraseña maestra", Toast.LENGTH_SHORT).show();
                Cuadrodialogopasswordmaestra();
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

    private void Cuadrodialogopasswordmaestra() {
        //establecer las vistar
        EditText Password_maestra;
        EditText Et_nuevo_password_maestra, Et_confirmar_password_maestra;
        Button Btn_cambiar_password_maestra, Btn_cancelar_password_maestra;

        String password_maestra_recuperada = sharedPreferences.getString(PASSWORD, null);

        //hacemos la conexion con el cuadro de dialogo
        dialogo_p_maestra.setContentView(R.layout.cuadro_dialogo_password_maestra);

        //Inicializar las vistas
        Password_maestra = dialogo_p_maestra.findViewById(R.id.Password_maestra);
        Et_nuevo_password_maestra = dialogo_p_maestra.findViewById(R.id.Et_nuevo_password_maestra);
        Et_confirmar_password_maestra = dialogo_p_maestra.findViewById(R.id.Et_confirmar_password_maestra);
        Btn_cambiar_password_maestra = dialogo_p_maestra.findViewById(R.id.Btn_cambiar_password_maestra);
        Btn_cancelar_password_maestra = dialogo_p_maestra.findViewById(R.id.Btn_cancelar_password_maestra);

        Btn_cambiar_password_maestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Cambiar password maestra", Toast.LENGTH_SHORT).show();
                //Obtener los datos del edit text
                String S_nuevo_password = Et_nuevo_password_maestra.getText().toString().trim();
                String S_confirm_nuevo_password = Et_confirmar_password_maestra.getText().toString().trim();

                //Validacion de campos
                if (S_nuevo_password.equals("")){
                    Toast.makeText(getActivity(), "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
                }
                else if (S_confirm_nuevo_password.equals("")){
                    Toast.makeText(getActivity(), "Confirme su contraseña", Toast.LENGTH_SHORT).show();
                }
                else if (S_nuevo_password.length()<6){
                    Toast.makeText(getActivity(), "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                }
                else if (!S_nuevo_password.equals(S_confirm_nuevo_password)){
                    Toast.makeText(getActivity(), "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //Pasar los nuevos datos a las llaves
                    editor.putString(PASSWORD, S_nuevo_password);
                    editor.putString(CONFIRM_PASSWORD, S_confirm_nuevo_password);
                    editor.apply();
                    //Salir de la aplicacion para iniciar sesion con la nueva contraseña
                    startActivity(new Intent(getActivity(), Login_user.class));
                    getActivity().finish();
                    Toast.makeText(getActivity(), "Contraseña maestra actualizada", Toast.LENGTH_SHORT).show();
                    dialogo_p_maestra.dismiss();
                }
            }
        });

        Btn_cancelar_password_maestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
                dialogo_p_maestra.dismiss();
            }
        });

        Password_maestra.setText(password_maestra_recuperada);
        Password_maestra.setEnabled(false);
        Password_maestra.setBackgroundColor(Color.TRANSPARENT);
        Password_maestra.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        dialogo_p_maestra.show();
        dialogo_p_maestra.setCancelable(false);

    }

    //permiso para exportar registro
    private ActivityResultLauncher<String> SolicitudPermisoExportar =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), Concede_permiso_exportar ->{
                if (Concede_permiso_exportar){
                    ExportarRegistros();
                }
                else {
                    Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            });
    //permiso para importar registro
    private ActivityResultLauncher<String> SolicitudPermisoImportar =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), Concede_permiso_importar ->{
                if (Concede_permiso_importar){
                    ImportarRegistros();
                }
                else {
                    Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            });
}