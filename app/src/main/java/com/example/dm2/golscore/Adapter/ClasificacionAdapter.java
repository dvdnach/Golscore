package com.example.dm2.golscore.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.DetallesClubActivity;
import com.example.dm2.golscore.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ClasificacionAdapter extends RecyclerView.Adapter<ClasificacionAdapter.ClasificacionViewHolder> {

    private List<Equipo> listaClasificacion;
    private Bitmap bitmap;

    public ClasificacionAdapter(List<Equipo> listaClasificacion) {
        this.listaClasificacion = listaClasificacion;
    }

    @Override
    public ClasificacionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.clasificacion_list_item,parent,false);
        ClasificacionViewHolder holder=new ClasificacionViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final ClasificacionViewHolder holder, final int position) {
        final Equipo s= listaClasificacion.get(position);
        holder.posicionEquipoTV.setText(Integer.toString(listaClasificacion.size()-position));
        holder.nombreEquipoTV.setText(s.getNombre());
        holder.golesEquipoTV.setText(Integer.toString(s.getTotal_goles()));
        holder.puntosEquipoTV.setText(Integer.toString(s.getPuntos()));
        //conseguir escudos
        FirebaseStorage storage= FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl(s.getEscudo());
        gsReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imagen.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                // bitmap = BitmapFactory.decodeByteArray(, 0, bytes.length);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaClasificacion.size();
    }

    public static class ClasificacionViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreEquipoTV, golesEquipoTV, puntosEquipoTV, posicionEquipoTV;
        private ImageView imagen;

        public ClasificacionViewHolder(View itemView) {
            super(itemView);
            nombreEquipoTV = (TextView) itemView.findViewById(R.id.nombreEquipoTV);
            imagen=(ImageView) itemView.findViewById(R.id.escudoEquipoIV);
            golesEquipoTV = (TextView) itemView.findViewById(R.id.golesEquipoTV);
            puntosEquipoTV = (TextView) itemView.findViewById(R.id.puntosEquipoTV);
            posicionEquipoTV=(TextView) itemView.findViewById(R.id.posicionEquipoTV);

        }
        public void setNombre(String nombre) {
            nombreEquipoTV.setText(nombre);
        }

        public void setGolesEquipoTV(String goles) {
            golesEquipoTV.setText(goles);
        }

        public void setPuntosEquipoTV(int puntos) {
            puntosEquipoTV.setText(Integer.toString(puntos));
        }
        public void setPosicionEquipoTV(int posicion) {
            posicionEquipoTV.setText(Integer.toString(posicion));
        }
    }
}
