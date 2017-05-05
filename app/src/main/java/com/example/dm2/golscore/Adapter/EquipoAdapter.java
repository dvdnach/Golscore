package com.example.dm2.golscore.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.Clases.Gol;
import com.example.dm2.golscore.LigaActivity;
import com.example.dm2.golscore.R;

import java.util.List;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder> {

    private List<Equipo> listaEquipo;

    public EquipoAdapter(List<Equipo> listaEquipo) {
        this.listaEquipo = listaEquipo;
    }

    @Override
    public EquipoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.equipo_list_item,parent,false);
        EquipoViewHolder holder=new EquipoViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final EquipoViewHolder holder, final int position) {
        final Equipo s= listaEquipo.get(position);
        holder.nombreEquipoTV.setText(s.getCategoria());
        holder.nombreEquipoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LigaActivity.class);
                intent.putExtra("nombreEquipo",String.valueOf(s.getNombre()));
                intent.putExtra("idEquipo",String.valueOf(s.getId()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaEquipo.size();
    }

    public static class EquipoViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreEquipoTV;

        public EquipoViewHolder(View itemView) {
            super(itemView);
            nombreEquipoTV = (TextView) itemView.findViewById(R.id.nombreEquipoTV);
        }

        public void setNombre(String nombre) {
            nombreEquipoTV.setText(nombre);
        }
    }
}
