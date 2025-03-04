package com.daniel.vaulcontraseas.Adaptador;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daniel.vaulcontraseas.BaseDeDatos.BDHelper;
import com.daniel.vaulcontraseas.Detalle.Detalle_tarjeta;
import com.daniel.vaulcontraseas.MainActivity;
import com.daniel.vaulcontraseas.Modelo.Tarjeta;
import com.daniel.vaulcontraseas.OpcionesPassword.Agregar_Actualizar_Tarjeta;
import com.daniel.vaulcontraseas.R;

import java.util.ArrayList;

public class Adaptador_tarjetas extends RecyclerView.Adapter<Adaptador_tarjetas.HolderTarjeta> {

    private Context context;
    private ArrayList<Tarjeta> tarjetasList;
    private Dialog dialog;
    private BDHelper helper;

    public Adaptador_tarjetas(Context context, ArrayList<Tarjeta> tarjetasList) {
        this.context = context;
        this.tarjetasList = tarjetasList;
        dialog = new Dialog(context);
        helper = new BDHelper(context);
    }

    @NonNull
    @Override
    public HolderTarjeta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el layout del item
        View view = LayoutInflater.from(context).inflate(R.layout.item_tarjetas, parent, false);
        return new HolderTarjeta(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderTarjeta holder, @SuppressLint("RecyclerView") int position) {
        Tarjeta modelo_tarjeta = tarjetasList.get(position);
        String id = modelo_tarjeta.getId();
        String titulo = modelo_tarjeta.getTitulo();
        String numero_tarjeta = modelo_tarjeta.getNumero_tarjeta();
        String nombre_tarjeta = modelo_tarjeta.getNombre_tarjeta();
        String fecha_expiracion = modelo_tarjeta.getFecha_expiracion();
        String cvv = modelo_tarjeta.getCvv();
        String nota = modelo_tarjeta.getNota();
        String tiempo_registro = modelo_tarjeta.getT_registro();
        String tiempo_actualizacion = modelo_tarjeta.getT_actualizacion();

        // Asignamos los valores a las vistas
        holder.Item_titulo.setText(titulo);
        holder.Item_numero_tarjeta.setText(numero_tarjeta);

        // Manejo del clic en el item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cuando el usuario presiona el item, abrimos la actividad de detalle
                Intent intent = new Intent(context, Detalle_tarjeta.class);
                intent.putExtra("Id_tarjeta", id); // Enviamos el ID de la tarjeta
                context.startActivity(intent);
            }
        });

        // Manejo del clic en el botón de opciones
        holder.img_buton_mas_opciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostramos el diálogo de opciones (editar/eliminar)
                opciones_editar_eliminar(
                        "" + position,
                        "" + id,
                        "" + titulo,
                        "" + numero_tarjeta,
                        "" + nombre_tarjeta,
                        "" + fecha_expiracion,
                        "" + cvv,
                        "" + nota,
                        "" + tiempo_registro,
                        "" + tiempo_actualizacion
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        // Devolvemos el tamaño de la lista
        return tarjetasList.size();
    }

    class HolderTarjeta extends RecyclerView.ViewHolder {

        TextView Item_titulo, Item_numero_tarjeta, Item_nombre_tarjeta, Item_fecha_expiracion, Item_cvv, Item_nota;
        ImageButton img_buton_mas_opciones;

        public HolderTarjeta(@NonNull View itemView) {
            super(itemView);

            // Inicializamos las vistas
            Item_titulo = itemView.findViewById(R.id.Item_titulo_tarjeta);
            Item_numero_tarjeta = itemView.findViewById(R.id.Item_numero_tarjeta);
            Item_nombre_tarjeta = itemView.findViewById(R.id.Item_nombre_tarjeta);
            Item_fecha_expiracion = itemView.findViewById(R.id.Item_fecha_expiracion);
            Item_cvv = itemView.findViewById(R.id.Item_cvv);
            Item_nota = itemView.findViewById(R.id.Item_nota);

            img_buton_mas_opciones = itemView.findViewById(R.id.img_buton_mas_opciones);
        }
    }

    private void opciones_editar_eliminar(String position, String id, String titulo, String numero_tarjeta, String nombre_tarjeta,
                                          String fecha_expiracion, String cvv, String nota, String tiempo_registro, String tiempo_actualizacion) {
        Button Btn_Editar_tarjeta, Btn_Eliminar_tarjeta;

        // Inflamos el diálogo de opciones
        dialog.setContentView(R.layout.cuadro_editar_eliminar_tarjetas);

        Btn_Editar_tarjeta = dialog.findViewById(R.id.Btn_Editar_tarjeta);
        Btn_Eliminar_tarjeta = dialog.findViewById(R.id.Btn_Eliminar_tarjeta);

        // Manejo del clic en el botón de editar
        Btn_Editar_tarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Agregar_Actualizar_Tarjeta.class);
                intent.putExtra("POSICION", position);
                intent.putExtra("ID", id);
                intent.putExtra("TITULO", titulo);
                intent.putExtra("NUMERO_TARJETA", numero_tarjeta);
                intent.putExtra("NOMBRE_TARJETA", nombre_tarjeta);
                intent.putExtra("FECHA_EXPIRACION", fecha_expiracion);
                intent.putExtra("CVV", cvv);
                intent.putExtra("NOTA", nota);
                intent.putExtra("T_REGISTRO", tiempo_registro);
                intent.putExtra("T_ACTUALIZACION", tiempo_actualizacion);
                intent.putExtra("MODO_EDICION", true); // Indicamos que es una edición
                context.startActivity(intent);

                dialog.dismiss(); // Cerramos el diálogo
            }
        });

        // Manejo del clic en el botón de eliminar
        Btn_Eliminar_tarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.eliminarTarjeta(id); // Eliminamos la tarjeta de la base de datos

                // Crear un Intent para volver a MainActivity y cargar el fragmento F_Tarjetas
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("FRAGMENT_TO_LOAD", "F_Tarjetas"); // Indicar que se debe cargar F_Tarjetas
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // Limpiar la pila de actividades
                context.startActivity(intent); // Iniciar MainActivity

                Toast.makeText(context, "Tarjeta eliminada", Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // Cerramos el diálogo
            }
        });

        dialog.show(); // Mostramos el diálogo
        dialog.setCancelable(true); // Permitimos que se cancele al tocar fuera
    }
}