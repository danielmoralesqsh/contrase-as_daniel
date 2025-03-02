package com.daniel.vaulcontraseas.Fragmentos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.daniel.vaulcontraseas.BaseDeDatos.BDHelper;
import com.daniel.vaulcontraseas.BaseDeDatos.Constants;
import com.daniel.vaulcontraseas.Login_usuario.Login_user;
import com.daniel.vaulcontraseas.MainActivity;
import com.daniel.vaulcontraseas.Modelo.Password;
import com.daniel.vaulcontraseas.Modelo.Nota;
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
                ExportarRegistros(); // Llamada directa sin verificar permisos
            }
        });

        Importar_registros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Importar registros");
                builder.setMessage("Se eliminarán los registros actuales de contraseñas y notas");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bdHelper.Eliminar_todos_registros();
                        bdHelper.eliminarTodasLasNotas();
                        ImportarRegistros(); // Llamada directa sin verificar permisos
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Importación cancelada", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.create().show();
            }
        });

        Cambiar_contraseña_maestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                bdHelper.eliminarTodasLasNotas();
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
        // Nombre de la carpeta
        File carpeta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "VaulContraseas");

        boolean carpeta_existe = carpeta.exists();

        if (!carpeta.exists()) {
            // Si la carpeta no existe, creamos una nueva
            carpeta_existe = carpeta.mkdirs();
        }

        // Nombre del archivo para contraseñas
        String csvnombreArchivo = "VaulContraseas.csv";
        // Nombre del archivo para notas
        String csvnombreArchivoNotas = "VaulNotas.csv";

        // Concatenamos la carpeta con el nombre del archivo
        String Carpeta_archivo = carpeta + "/" + csvnombreArchivo;
        String Carpeta_archivo_notas = carpeta + "/" + csvnombreArchivoNotas;

        // Exportar registros de contraseñas
        exportarTabla(Constants.TABLE_NAME, Carpeta_archivo, "Contraseñas");

        // Exportar registros de notas
        exportarTabla(Constants.TABLE_NOTAS, Carpeta_archivo_notas, "Notas");
    }

    private void exportarTabla(String tabla, String rutaArchivo, String tipoDatos) {
        // Obtener los registros de la tabla
        ArrayList<?> registros;
        if (tabla.equals(Constants.TABLE_NAME)) {
            registros = bdHelper.ObtenerTodosLosRegistros(ordenarTituloASC);
        } else {
            registros = bdHelper.obtenerTodasLasNotas(ordenarTituloASC);
        }

        try {
            // Escribir en el archivo
            FileWriter fileWriter = new FileWriter(rutaArchivo);
            for (int i = 0; i < registros.size(); i++) {
                if (tabla.equals(Constants.TABLE_NAME)) {
                    Password registro = (Password) registros.get(i);
                    fileWriter.append("" + registro.getId());
                    fileWriter.append(",");
                    fileWriter.append("" + registro.getTitulo());
                    fileWriter.append(",");
                    fileWriter.append("" + registro.getCuenta());
                    fileWriter.append(",");
                    fileWriter.append("" + registro.getNombre_usuario());
                    fileWriter.append(",");
                    fileWriter.append("" + registro.getPassword());
                    fileWriter.append(",");
                    fileWriter.append("" + registro.getSitio_web());
                    fileWriter.append(",");
                    fileWriter.append("" + registro.getNota());
                    fileWriter.append(",");
                    fileWriter.append("" + registro.getT_registro());
                    fileWriter.append(",");
                    fileWriter.append("" + registro.getT_actualizacion());
                } else {
                    Nota nota = (Nota) registros.get(i);
                    fileWriter.append("" + nota.getId());
                    fileWriter.append(",");
                    fileWriter.append("" + nota.getTitulo());
                    fileWriter.append(",");
                    fileWriter.append("" + nota.getContenido());
                    fileWriter.append(",");
                    fileWriter.append("" + nota.getT_registro());
                    fileWriter.append(",");
                    fileWriter.append("" + nota.getT_actualizacion());
                }
                fileWriter.append("\n");
            }

            fileWriter.flush();
            fileWriter.close();
            Toast.makeText(getActivity(), tipoDatos + " exportados exitosamente", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error al exportar " + tipoDatos + ": " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void ImportarRegistros() {
        // Establecer la ruta para contraseñas
        String Carpeta_archivo = Environment.getExternalStorageDirectory() + "/Download/VaulContraseas/VaulContraseas.csv";
        // Establecer la ruta para notas
        String Carpeta_archivo_notas = Environment.getExternalStorageDirectory() + "/Download/VaulContraseas/VaulNotas.csv";

        // Importar registros de contraseñas
        importarTabla(Constants.TABLE_NAME, Carpeta_archivo, "Contraseñas");

        // Importar registros de notas
        importarTabla(Constants.TABLE_NOTAS, Carpeta_archivo_notas, "Notas");
    }

    private void importarTabla(String tabla, String rutaArchivo, String tipoDatos) {
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            try {
                CSVReader csvReader = new CSVReader(new FileReader(archivo.getAbsoluteFile()));
                String[] nexline;
                while ((nexline = csvReader.readNext()) != null) {
                    if (tabla.equals(Constants.TABLE_NAME)) {
                        // Importar registros de contraseñas
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
                                titulo, cuenta, nombre_usuario, password,
                                sitio_web, nota, t_registro, t_actualizacion);
                    } else {
                        // Importar registros de notas
                        String ids = nexline[0];
                        String titulo = nexline[1];
                        String contenido = nexline[2];
                        String t_registro = nexline[3];
                        String t_actualizacion = nexline[4];

                        long id = bdHelper.insertarNota(
                                titulo, contenido, t_registro, t_actualizacion);
                    }
                }

                Toast.makeText(getActivity(), tipoDatos + " importados correctamente", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(getActivity(), "Error al importar " + tipoDatos + ": " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "No existe una exportación previa de " + tipoDatos, Toast.LENGTH_SHORT).show();
        }
    }

    private void Cuadrodialogopasswordmaestra() {
        // Establecer las vistas
        EditText Password_maestra;
        EditText Et_nuevo_password_maestra, Et_confirmar_password_maestra;
        Button Btn_cambiar_password_maestra, Btn_cancelar_password_maestra;

        String password_maestra_recuperada = sharedPreferences.getString(PASSWORD, null);

        // Hacemos la conexión con el cuadro de diálogo
        dialogo_p_maestra.setContentView(R.layout.cuadro_dialogo_password_maestra);

        // Inicializar las vistas
        Password_maestra = dialogo_p_maestra.findViewById(R.id.Password_maestra);
        Et_nuevo_password_maestra = dialogo_p_maestra.findViewById(R.id.Et_nuevo_password_maestra);
        Et_confirmar_password_maestra = dialogo_p_maestra.findViewById(R.id.Et_confirmar_password_maestra);
        Btn_cambiar_password_maestra = dialogo_p_maestra.findViewById(R.id.Btn_cambiar_password_maestra);
        Btn_cancelar_password_maestra = dialogo_p_maestra.findViewById(R.id.Btn_cancelar_password_maestra);

        Btn_cambiar_password_maestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos del EditText
                String S_nuevo_password = Et_nuevo_password_maestra.getText().toString().trim();
                String S_confirm_nuevo_password = Et_confirmar_password_maestra.getText().toString().trim();

                // Validación de campos
                if (S_nuevo_password.equals("")) {
                    Toast.makeText(getActivity(), "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
                } else if (S_confirm_nuevo_password.equals("")) {
                    Toast.makeText(getActivity(), "Confirme su contraseña", Toast.LENGTH_SHORT).show();
                } else if (S_nuevo_password.length() < 6) {
                    Toast.makeText(getActivity(), "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                } else if (!S_nuevo_password.matches(".*[A-Z].*")) {
                    Toast.makeText(getActivity(), "La contraseña debe contener al menos una letra mayúscula", Toast.LENGTH_SHORT).show();
                } else if (!S_nuevo_password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
                    Toast.makeText(getActivity(), "La contraseña debe contener al menos un carácter especial", Toast.LENGTH_SHORT).show();
                } else if (!S_nuevo_password.equals(S_confirm_nuevo_password)) {
                    Toast.makeText(getActivity(), "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    // Pasar los nuevos datos a las llaves
                    editor.putString(PASSWORD, S_nuevo_password);
                    editor.putString(CONFIRM_PASSWORD, S_confirm_nuevo_password);
                    editor.apply();
                    // Salir de la aplicación para iniciar sesión con la nueva contraseña
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
}