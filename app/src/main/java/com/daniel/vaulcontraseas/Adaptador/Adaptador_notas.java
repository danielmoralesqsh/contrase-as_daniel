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
import com.daniel.vaulcontraseas.Detalle.Detalle_nota;
import com.daniel.vaulcontraseas.MainActivity;
import com.daniel.vaulcontraseas.Modelo.Nota;
import com.daniel.vaulcontraseas.OpcionesPassword.Agregar_Actualizar_Nota;
import com.daniel.vaulcontraseas.R;

import java.util.ArrayList;

public class Adaptador_notas extends RecyclerView.Adapter<Adaptador_notas.HolderNota> {

    private Context context;
    private ArrayList<Nota> notasList;
    private Dialog dialog;
    private BDHelper helper;

    public Adaptador_notas(Context context, ArrayList<Nota> notasList) {
        this.context = context;
        this.notasList = notasList;
        dialog = new Dialog(context);
        helper = new BDHelper(context);
    }

    @NonNull
    @Override
    public HolderNota onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el item
        View view = LayoutInflater.from(context).inflate(R.layout.item_nota, parent, false);
        return new HolderNota(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderNota holder, @SuppressLint("RecyclerView") int position) {
        Nota modelo_nota = notasList.get(position);
        String id = modelo_nota.getId();
        String titulo = modelo_nota.getTitulo();
        String contenido = modelo_nota.getContenido();
        String tiempo_registro = modelo_nota.getT_registro();
        String tiempo_actualizacion = modelo_nota.getT_actualizacion();

        holder.Item_titulo.setText(titulo);
        holder.Item_contenido.setText(contenido);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cuando el usuario presione en el item
                Intent intent = new Intent(context, Detalle_nota.class);
                // Enviamos el dato id a la actividad detalle
                intent.putExtra("Id_nota", id);
                context.startActivity(intent);
            }
        });

        holder.img_buton_mas_opciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cuando el usuario presione las opciones
                opciones_editar_eliminar(
                        "" + position,
                        "" + id,
                        "" + titulo,
                        "" + contenido,
                        "" + tiempo_registro,
                        "" + tiempo_actualizacion
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        // Este método devuelve el tamaño de la lista
        return notasList.size();
    }

    class HolderNota extends RecyclerView.ViewHolder {

        TextView Item_titulo, Item_contenido;
        ImageButton img_buton_mas_opciones;

        public HolderNota(@NonNull View itemView) {
            super(itemView);

            Item_titulo = itemView.findViewById(R.id.Item_titulo);
            Item_contenido = itemView.findViewById(R.id.Item_contenido);
            img_buton_mas_opciones = itemView.findViewById(R.id.img_buton_mas_opciones);
        }
    }

    private void opciones_editar_eliminar(String position, String id, String titulo, String contenido,
                                          String tiempo_registro, String tiempo_actualizacion) {
        Button Btn_Editar_nota, Btn_Eliminar_nota;

        dialog.setContentView(R.layout.cuadro_editar_eliminar_nota);

        Btn_Editar_nota = dialog.findViewById(R.id.Btn_Editar_nota);
        Btn_Eliminar_nota = dialog.findViewById(R.id.Btn_Eliminar_nota);

        Btn_Editar_nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Agregar_Actualizar_Nota.class);
                intent.putExtra("POSICION", position);
                intent.putExtra("ID", id);
                intent.putExtra("TITULO", titulo);
                intent.putExtra("CONTENIDO", contenido);
                intent.putExtra("T_REGISTRO", tiempo_registro);
                intent.putExtra("T_ACTUALIZACION", tiempo_actualizacion);
                intent.putExtra("MODO_EDICION", true);
                context.startActivity(intent);

                dialog.dismiss();
            }
        });

        Btn_Eliminar_nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.eliminarNota(id);
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("FRAGMENT_TO_LOAD", "F_Notas"); // Redirigir a F_Notas
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Toast.makeText(context, "Nota eliminada", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCancelable(true);
    }
}