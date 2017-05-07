package com.example.dm2.golscore.Adapter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.Clases.Gol;
import com.example.dm2.golscore.Clases.Partido;
import com.example.dm2.golscore.LigaActivity;
import com.example.dm2.golscore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PartidoAdapter extends RecyclerView.Adapter<PartidoAdapter.PartidoViewHolder> {

    private List<Partido> listaPartido;
    private Calendar hoy;
    public PartidoAdapter(List<Partido> listaPartido) {
        this.listaPartido = listaPartido;
    }

    @Override
    public PartidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.partido_list_item,parent,false);
        PartidoViewHolder holder=new PartidoViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final PartidoViewHolder holder, final int position) {
        final Partido partido= listaPartido.get(position);
        hoy=Calendar.getInstance();
        Date dateHoy=hoy.getTime();
        DatabaseReference dbClub= FirebaseDatabase.getInstance().getReference().child("Equipo");
        dbClub.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Equipo equipo=snapshot.getValue(Equipo.class);
                    if(equipo.getId()==partido.getEq_local()){
                        holder.nombreEquipoLocalTV.setText(equipo.getNombre());
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
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });
        holder.fechaTV.setText(partido.getFecha());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String stringFechaPartido = partido.getFecha()+" "+partido.getHora();
        try {
            Date datePartido = sdf.parse(stringFechaPartido);
            if(dateHoy.after(datePartido)){
                holder.golesLocalTV.setText(Integer.toString(partido.getGol_local()));
                holder.golesVisitanteTV.setText(Integer.toString(partido.getGol_visitante()));
            }else{
                holder.golesLocalTV.setText(partido.getHora());
                holder.separadorTV.setText("");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*holder..setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LigaActivity.class);
                intent.putExtra("nombrePartido",String.valueOf(s.getNombre()));
                intent.putExtra("idPartido",String.valueOf(s.getId()));
                v.getContext().startActivity(intent);
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return listaPartido.size();
    }

    public static class PartidoViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreEquipoLocalTV,golesLocalTV,golesVisitanteTV,nombreEquipoVisitanteTV,separadorTV,fechaTV;
        private ImageView escudoEquipoLocalIV,escudoEquipoVisitanteIV;

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
        }

        public void setNombreEquipoLocalTV(String equipoLocalTV) {
            nombreEquipoLocalTV.setText(equipoLocalTV);
        }

        public void setEscudoEquipoLocalIV(ImageView escudoEquipoLocalIV) {
            this.escudoEquipoLocalIV = escudoEquipoLocalIV;
        }

        public void setGolesLocalTV(String golLocal) {
            golesLocalTV.setText(golLocal);
        }

        public void setGolesVisitanteTV(String golVisitante) {
            golesVisitanteTV.setText(golVisitante);
        }

        public void setEscudoEquipoVisitanteIV(ImageView escudoEquipoVisitanteIV) {
            this.escudoEquipoVisitanteIV = escudoEquipoVisitanteIV;
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
