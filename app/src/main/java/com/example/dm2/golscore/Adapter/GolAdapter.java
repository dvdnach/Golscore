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

public class GolAdapter extends RecyclerView.Adapter<GolAdapter.GolViewHolder> {

    private List<Gol> listaGol;

    public GolAdapter(List<Gol> listaGol) {
        this.listaGol = listaGol;
    }

    @Override
    public GolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.gol_list_item,parent,false);
        GolViewHolder holder=new GolViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final GolViewHolder holder, final int position) {
      /*  final Gol s= listaGol.get(position);
        holder.nombreGolTV.setText(s.getNombre());
        holder.nombreGolTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LigaActivity.class);
                intent.putExtra("nombreGol",String.valueOf(s.getNombre()));
                intent.putExtra("idGol",String.valueOf(s.getId()));
                v.getContext().startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listaGol.size();
    }

    public static class GolViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreGolTV;

        public GolViewHolder(View itemView) {
            super(itemView);
           // nombreGolTV = (TextView) itemView.findViewById(R.id.nombreGolTV);
        }

        public void setNombre(String nombre) {
            nombreGolTV.setText(nombre);
        }
    }
}
