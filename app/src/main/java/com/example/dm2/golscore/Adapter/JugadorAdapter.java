package com.example.dm2.golscore.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dm2.golscore.Clases.Gol;
import com.example.dm2.golscore.Clases.Jugador;
import com.example.dm2.golscore.LigaActivity;
import com.example.dm2.golscore.R;

import java.util.List;

public class JugadorAdapter extends RecyclerView.Adapter<JugadorAdapter.JugadorViewHolder> {

    private List<Jugador> listJugador;

    public JugadorAdapter(List<Jugador> listJugador) {
        this.listJugador = listJugador;
    }

    @Override
    public JugadorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.jugador_list_item,parent,false);
        JugadorViewHolder holder=new JugadorViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final JugadorViewHolder holder, final int position) {
        final Jugador jugador= listJugador.get(position);
        holder.nombreApellidoJugadorTV.setText(jugador.getNombre()+" "+jugador.getApellido());
    }

    @Override
    public int getItemCount() {
        return listJugador.size();
    }

    public static class JugadorViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreApellidoJugadorTV;

        public JugadorViewHolder(View itemView) {
            super(itemView);
            nombreApellidoJugadorTV = (TextView) itemView.findViewById(R.id.nombreApellidoJugadorTV);
        }

        public void setNombre(String nombre) {
            nombreApellidoJugadorTV.setText(nombre);
        }
    }
}
