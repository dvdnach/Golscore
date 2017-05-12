package com.example.dm2.golscore.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.DetallesClubActivity;
import com.example.dm2.golscore.LigaActivity;
import com.example.dm2.golscore.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder> {

    private List<Equipo> listaEquipo;
    private Bitmap bitmap;
    private Context context;

    public EquipoAdapter(List<Equipo> listaEquipo, Context context) {
        this.listaEquipo = listaEquipo;
        this.context = context;
    }

    @Override
    public EquipoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.equipo_list_item,parent,false);
        EquipoViewHolder holder=new EquipoViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final EquipoViewHolder holder, final int position) {
        final Equipo s= listaEquipo.get(position);
        holder.nombreEquipoTV.setText(s.getNombre());
        Glide.with(context).load(s.getEscudo()).into(holder.imagen);

        holder.equipoLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetallesClubActivity.class);
                intent.putExtra("nombreEquipo",String.valueOf(s.getNombre()));
                intent.putExtra("idEquipo",String.valueOf(s.getId()));
                intent.putExtra("idGrupo",String.valueOf(s.getGrupo()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaEquipo.size();
    }

    public static class EquipoViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreEquipoTV;
        private ImageView imagen;
        private LinearLayout equipoLL;

        public EquipoViewHolder(View itemView) {
            super(itemView);
            nombreEquipoTV = (TextView) itemView.findViewById(R.id.nombreEquipoTV);
            imagen=(ImageView) itemView.findViewById(R.id.escudoEquipoIV);
            equipoLL=(LinearLayout) itemView.findViewById(R.id.equipoLL);
        }
        public void setNombre(String nombre) {
            nombreEquipoTV.setText(nombre);
        }
    }
}
