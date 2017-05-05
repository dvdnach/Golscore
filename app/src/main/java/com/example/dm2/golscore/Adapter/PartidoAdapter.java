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

public class PartidoAdapter extends RecyclerView.Adapter<PartidoAdapter.PartidoViewHolder> {

    private List<Gol> listaGol;

    public PartidoAdapter(List<Gol> listaGol) {
        this.listaGol = listaGol;
    }

    @Override
    public PartidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.partido_list_item,parent,false);
        PartidoViewHolder holder=new PartidoViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final PartidoViewHolder holder, final int position) {
        final Gol s= listaGol.get(position);
       /* holder.nombrePartidoTV.setText(s.getNombre());
        holder.nombrePartidoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LigaActivity.class);
                intent.putExtra("nombrePartido",String.valueOf(s.getNombre()));
                intent.putExtra("idPartido",String.valueOf(s.getId()));
                v.getContext().startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listaGol.size();
    }

    public static class PartidoViewHolder extends RecyclerView.ViewHolder{

        private TextView nombrePartidoTV;

        public PartidoViewHolder(View itemView) {
            super(itemView);
           // nombrePartidoTV = (TextView) itemView.findViewById(R.id.nombrePartidoTV);
        }

        public void setNombre(String nombre) {
            nombrePartidoTV.setText(nombre);
        }
    }
}
