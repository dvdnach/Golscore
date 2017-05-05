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

public class TarjetaAdapter extends RecyclerView.Adapter<TarjetaAdapter.TarjetaViewHolder> {

    private List<Gol> listaGol;

    public TarjetaAdapter(List<Gol> listaGol) {
        this.listaGol = listaGol;
    }

    @Override
    public TarjetaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_list_item,parent,false);
        TarjetaViewHolder holder=new TarjetaViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final TarjetaViewHolder holder, final int position) {
        final Gol s= listaGol.get(position);
       /* holder.nombreTarjetaTV.setText(s.getNombre());
        holder.nombreTarjetaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LigaActivity.class);
                intent.putExtra("nombreTarjeta",String.valueOf(s.getNombre()));
                intent.putExtra("idTarjeta",String.valueOf(s.getId()));
                v.getContext().startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listaGol.size();
    }

    public static class TarjetaViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreTarjetaTV;

        public TarjetaViewHolder(View itemView) {
            super(itemView);
            //nombreTarjetaTV = (TextView) itemView.findViewById(R.id.nombreTarjetaTV);
        }

        public void setNombre(String nombre) {
            nombreTarjetaTV.setText(nombre);
        }
    }
}
