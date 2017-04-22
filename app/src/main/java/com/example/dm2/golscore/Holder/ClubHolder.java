package com.example.dm2.golscore.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.dm2.golscore.R;

public class ClubHolder extends RecyclerView.ViewHolder{

    private View mView;

    public ClubHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setId(String id) {
        TextView field = (TextView) mView.findViewById(R.id.idTV);
        field.setText(id);
    }

    public void setNombre(String nombre) {
        TextView field = (TextView) mView.findViewById(R.id.nombreTV);
        field.setText(nombre);
    }
}
