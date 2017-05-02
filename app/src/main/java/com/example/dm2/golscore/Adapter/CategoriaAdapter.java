package com.example.dm2.golscore.Adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dm2.golscore.Clases.Categoria;
import com.example.dm2.golscore.MainActivity;
import com.example.dm2.golscore.R;
import com.example.dm2.golscore.SubCategoriaActivity;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private List<Categoria> listaCategoria;

    public CategoriaAdapter(List<Categoria> listaCategoria) {
        this.listaCategoria = listaCategoria;
    }

    @Override
    public CategoriaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_list_item,parent,false);
        CategoriaViewHolder holder=new CategoriaViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final CategoriaViewHolder holder, final int position) {
        final Categoria c=listaCategoria.get(position);
        holder.nombreCategoriaTV.setText(c.getNombre());
        holder.nombreCategoriaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SubCategoriaActivity.class);
                intent.putExtra("idCategoria",String.valueOf(c.getId()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCategoria.size();
    }

    public static class CategoriaViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreCategoriaTV;

        public CategoriaViewHolder(View itemView) {
            super(itemView);
            nombreCategoriaTV = (TextView) itemView.findViewById(R.id.nombreCategoriaTV);
        }

        public void setNombre(String nombre) {
            nombreCategoriaTV.setText(nombre);
        }
    }
}
