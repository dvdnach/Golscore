package com.example.dm2.golscore.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.dm2.golscore.R;

public class ClubHolder extends RecyclerView.ViewHolder{

    private final TextView idTv,nombreTV;

    public ClubHolder(View itemView) {
        super(itemView);
        idTv = (TextView) itemView.findViewById(R.id.idTV);
        nombreTV= (TextView) itemView.findViewById(R.id.nombreTV);
    }

    public void setId(String id) {
        idTv.setText(id);
    }

    public void setNombre(String nombre) {
        nombreTV.setText(nombre);
    }
}
