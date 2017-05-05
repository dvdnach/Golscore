package com.example.dm2.golscore.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dm2.golscore.Clases.Gol;
import com.example.dm2.golscore.LigaActivity;
import com.example.dm2.golscore.R;

import java.util.List;

public class JugadorAdapter extends RecyclerView.Adapter<JugadorAdapter.JugadorViewHolder> {

    private List<Gol> listaGol;

    public JugadorAdapter(List<Gol> listaGol) {
        this.listaGol = listaGol;
    }

    @Override
    public JugadorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.jugador_list_item,parent,false);
        JugadorViewHolder holder=new JugadorViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final JugadorViewHolder holder, final int position) {
        /*final Gol s= listaGol.get(position);
        holder.nombreJugadorTV.setText(s.getNombre());
        holder.nombreJugadorTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LigaActivity.class);
                intent.putExtra("nombreJugador",String.valueOf(s.getNombre()));
                intent.putExtra("idJugador",String.valueOf(s.getId()));
                v.getContext().startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listaGol.size();
    }

    public static class JugadorViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreJugadorTV;

        public JugadorViewHolder(View itemView) {
            super(itemView);
           // nombreJugadorTV = (TextView) itemView.findViewById(R.id.nombreJugadorTV);
        }

        public void setNombre(String nombre) {
            nombreJugadorTV.setText(nombre);
        }
    }
}
