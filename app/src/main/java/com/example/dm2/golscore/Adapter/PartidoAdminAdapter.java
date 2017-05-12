package com.example.dm2.golscore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.Clases.Partido;
import com.example.dm2.golscore.DetallesPartidosActivity;
import com.example.dm2.golscore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PartidoAdminAdapter extends RecyclerView.Adapter<PartidoAdminAdapter.PartidoViewHolder> {

    private List<Partido> listaPartido;
    private Calendar hoy;
    int color,colorVerde;
    Drawable backVerder;
    Context context;

    public PartidoAdminAdapter(List<Partido> listaPartido) {
        this.listaPartido = listaPartido;
    }

    @Override
    public PartidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        color=parent.getResources().getColor(R.color.colorRed);
        colorVerde=parent.getResources().getColor(R.color.colorVerde);
        backVerder=parent.getResources().getDrawable(R.drawable.background_resultado_directo);
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.partido_list_item,parent,false);
        PartidoViewHolder holder=new PartidoViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final PartidoViewHolder holder, final int position) {
        final Partido partido= listaPartido.get(position);
        DatabaseReference dbClub= FirebaseDatabase.getInstance().getReference().child("Equipo");
        dbClub.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Equipo equipo=snapshot.getValue(Equipo.class);
                    if(equipo.getId()==partido.getEq_local()){
                        holder.nombreEquipoLocalTV.setText(equipo.getNombre());
                        Glide.with(context).load(equipo.getEscudo()).into(holder.escudoEquipoLocalIV);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });
        dbClub.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Equipo equipo=snapshot.getValue(Equipo.class);
                    if(equipo.getId()==partido.getEq_visitante()){
                        holder.nombreEquipoVisitanteTV.setText(equipo.getNombre());
                        Glide.with(context).load(equipo.getEscudo()).into(holder.escudoEquipoVisitanteIV);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });
        holder.fechaTV.setText("EN JUEGO");
        holder.fechaTV.setTextColor(colorVerde);
        holder.marcadorLL.setBackground(backVerder);
        holder.golesLocalTV.setText(Integer.toString(partido.getGol_local()));
        holder.golesVisitanteTV.setText(Integer.toString(partido.getGol_visitante()));

        holder.cardPartidoCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetallesPartidosActivity.class);
                intent.putExtra("idPartido",String.valueOf(partido.getId()));
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaPartido.size();
    }

    public static class PartidoViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreEquipoLocalTV,golesLocalTV,golesVisitanteTV,nombreEquipoVisitanteTV,separadorTV,fechaTV;
        private ImageView escudoEquipoLocalIV,escudoEquipoVisitanteIV;
        private LinearLayout marcadorLL;
        private CardView cardPartidoCV;

        public PartidoViewHolder(View itemView) {
            super(itemView);
            nombreEquipoLocalTV = (TextView) itemView.findViewById(R.id.nombreEquipoLocalTV);
            escudoEquipoLocalIV=(ImageView)itemView.findViewById(R.id.escudoEquipoLocalIV);
            golesLocalTV=(TextView)itemView.findViewById(R.id.golesLocalTV);
            golesVisitanteTV=(TextView)itemView.findViewById(R.id.golesVisitanteTV);
            escudoEquipoVisitanteIV=(ImageView)itemView.findViewById(R.id.escudoEquipoVisitanteIV);
            nombreEquipoVisitanteTV=(TextView)itemView.findViewById(R.id.nombreEquipoVisitanteTV);
            separadorTV=(TextView)itemView.findViewById(R.id.separadorTV);
            fechaTV=(TextView)itemView.findViewById(R.id.fechaTV);
            marcadorLL=(LinearLayout)itemView.findViewById(R.id.marcadorLL);
            cardPartidoCV=(CardView)itemView.findViewById(R.id.cardPartidoCV);
        }

        public void setNombreEquipoLocalTV(String equipoLocalTV) {
            nombreEquipoLocalTV.setText(equipoLocalTV);
        }

        public void setGolesLocalTV(String golLocal) {
            golesLocalTV.setText(golLocal);
        }

        public void setGolesVisitanteTV(String golVisitante) {
            golesVisitanteTV.setText(golVisitante);
        }

        public void setNombreEquipoVisitanteTV(String equipoVisitanteTV) {
            nombreEquipoVisitanteTV.setText(equipoVisitanteTV);
        }

        public void setSeparadorTV(String separador) {
            separadorTV.setText(separador);
        }

        public void setFechaTV(String fecha) {
            fechaTV.setText(fecha);
        }
    }
}
