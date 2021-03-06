package com.example.dm2.golscore.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.R;

import java.util.List;

public class ClasificacionAdapter extends RecyclerView.Adapter<ClasificacionAdapter.ClasificacionViewHolder> {

    private List<Equipo> listaClasificacion;
    private Bitmap bitmap;
    private Context context;
    private int idEquipo;
    private int color;

    public ClasificacionAdapter(List<Equipo> listaClasificacion, Context context,int idEquipo) {
        this.listaClasificacion = listaClasificacion;
        this.context = context;
        this.idEquipo=idEquipo;
    }

    @Override
    public ClasificacionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.clasificacion_list_item,parent,false);
        ClasificacionViewHolder holder=new ClasificacionViewHolder(view);
        color=parent.getResources().getColor(R.color.colorPrimary);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final ClasificacionViewHolder holder, final int position) {
        final Equipo s= listaClasificacion.get(position);
        holder.posicionEquipoTV.setText(Integer.toString(listaClasificacion.size()-position));
        holder.nombreEquipoTV.setText(s.getNombre());
        holder.golesEquipoTV.setText(Integer.toString(s.getTotal_goles()));
        holder.puntosEquipoTV.setText(Integer.toString(s.getPuntos()));
        Glide.with(context).load(s.getEscudo()).into(holder.imagen);
        if(s.getId()==idEquipo){
            holder.posicionEquipoTV.setTypeface(null, Typeface.BOLD);
            holder.nombreEquipoTV.setTypeface(null, Typeface.BOLD);
            holder.golesEquipoTV.setTypeface(null, Typeface.BOLD);
            holder.puntosEquipoTV.setTypeface(null, Typeface.BOLD);
            holder.posicionEquipoTV.setTextColor(color);
            holder.nombreEquipoTV.setTextColor(color);
            holder.golesEquipoTV.setTextColor(color);
            holder.puntosEquipoTV.setTextColor(color);
        }
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
