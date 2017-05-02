package com.example.dm2.golscore.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dm2.golscore.Clases.Categoria;
import com.example.dm2.golscore.Clases.Subcategoria;
import com.example.dm2.golscore.LigaActivity;
import com.example.dm2.golscore.R;
import com.example.dm2.golscore.SubCategoriaActivity;

import java.util.List;

public class SubcategoriaAdapter extends RecyclerView.Adapter<SubcategoriaAdapter.SubcategoriaViewHolder> {

    private List<Subcategoria> listaSubcategoria;

    public SubcategoriaAdapter(List<Subcategoria> listaSubcategoria) {
        this.listaSubcategoria = listaSubcategoria;
    }

    @Override
    public SubcategoriaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategoria_list_item,parent,false);
        SubcategoriaViewHolder holder=new SubcategoriaViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final SubcategoriaViewHolder holder, final int position) {
        final Subcategoria s=listaSubcategoria.get(position);
        holder.nombreSubcategoriaTV.setText(s.getNombre());
        holder.nombreSubcategoriaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LigaActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaSubcategoria.size();
    }

    public static class SubcategoriaViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreSubcategoriaTV;

        public SubcategoriaViewHolder(View itemView) {
            super(itemView);
            nombreSubcategoriaTV = (TextView) itemView.findViewById(R.id.nombreSubcategoriaTV);
        }

        public void setNombre(String nombre) {
            nombreSubcategoriaTV.setText(nombre);
        }
    }
}
