package com.example.dm2.golscore.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dm2.golscore.Clases.Grupo;
import com.example.dm2.golscore.LigaActivity;
import com.example.dm2.golscore.R;

import java.util.List;

public class GrupoAdapter extends RecyclerView.Adapter<GrupoAdapter.GrupoViewHolder> {

    private List<Grupo> listaGrupo;

    public GrupoAdapter(List<Grupo> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    @Override
    public GrupoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grupo_list_item,parent,false);
        GrupoViewHolder holder=new GrupoViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final GrupoViewHolder holder, final int position) {
        final Grupo s= listaGrupo.get(position);
        holder.nombreGrupoTV.setText(s.getNombre());
        holder.nombreGrupoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LigaActivity.class);
                intent.putExtra("nombreGrupo",String.valueOf(s.getNombre()));
                intent.putExtra("idGrupo",String.valueOf(s.getId()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaGrupo.size();
    }

    public static class GrupoViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreGrupoTV;

        public GrupoViewHolder(View itemView) {
            super(itemView);
            nombreGrupoTV = (TextView) itemView.findViewById(R.id.nombreGrupoTV);
        }

        public void setNombre(String nombre) {
            nombreGrupoTV.setText(nombre);
        }
    }
}
