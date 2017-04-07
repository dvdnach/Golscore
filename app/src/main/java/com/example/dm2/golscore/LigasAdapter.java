package com.example.dm2.golscore;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class LigasAdapter extends RecyclerView.Adapter<LigasAdapter.LigasViewHolder> {

    private ArrayList<Liga> datos;

    //...

    public LigasAdapter(ArrayList<Liga> datos) {
        this.datos = datos;
    }

    @Override
    public LigasViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_main, viewGroup, false);

        LigasViewHolder tvh = new LigasViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(LigasViewHolder viewHolder, int pos) {
        Liga item = datos.get(pos);

        viewHolder.bindLiga(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class LigasViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitulo;
       // private TextView txtSubtitulo;

        public LigasViewHolder(View itemView) {
            super(itemView);

            txtTitulo = (TextView)itemView.findViewById(R.id.idTV);
            //txtSubtitulo = (TextView)itemView.findViewById(R.id.LblSubTitulo);
        }

        public void bindLiga(Liga l) {
            txtTitulo.setText(l.getNombreLiga());

        }
    }
}