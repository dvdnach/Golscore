package com.example.dm2.golscore.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dm2.golscore.Clases.Cambio;
import com.example.dm2.golscore.R;

import java.util.List;

public class CambioAdapter extends RecyclerView.Adapter<CambioAdapter.CambioViewHolder> {

    private List<Cambio> listaCambio;

    public CambioAdapter(List<Cambio> listaCambio) {
        this.listaCambio = listaCambio;
    }

    @Override
    public CambioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cambio_list_item,parent,false);
        CambioViewHolder holder=new CambioViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final CambioViewHolder holder, final int position) {
       /* final Cambio c= listaCambio.get(position);
        holder.nombreCambioTV.setText(c.getNombre());
        holder.nombreCambioTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SubCambioActivity.class);
                intent.putExtra("idCambio",String.valueOf(c.getId()));
                v.getContext().startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listaCambio.size();
    }

    public static class CambioViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreCambioTV;

        public CambioViewHolder(View itemView) {
            super(itemView);
           // nombreCambioTV = (TextView) itemView.findViewById(R.id.nombreCambioTV);
        }

        public void setNombre(String nombre) {
            nombreCambioTV.setText(nombre);
        }
    }
}
