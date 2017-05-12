package com.example.dm2.golscore.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dm2.golscore.Clases.Club;
import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.Clases.Gol;
import com.example.dm2.golscore.Clases.Jugador;
import com.example.dm2.golscore.Clases.Partido;
import com.example.dm2.golscore.LigaActivity;
import com.example.dm2.golscore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GolAdapter extends RecyclerView.Adapter<GolAdapter.GolViewHolder> {

    private List<Gol> listaGol;

    public GolAdapter(List<Gol> listaGol) {
        this.listaGol = listaGol;
    }

    @Override
    public GolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.gol_list_item,parent,false);
        GolViewHolder holder=new GolViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final GolViewHolder holder, final int position) {
        final Gol gol= listaGol.get(position);
        final int idJugador=gol.getId_jugador();
        final int minuto=gol.getMinuto();
        final int idPartido=gol.getId_partido();

        //Obtener nombre jugador
        FirebaseDatabase dbPartido=FirebaseDatabase.getInstance();
        dbPartido.getReference().child("Jugador").addValueEventListener(new ValueEventListener() {
            String nombreJugador,apellidoJugador,jugar;
            int idEquipo;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Jugador jugador=snapshot.getValue(Jugador.class);
                    if(jugador.getId()==idJugador){
                        nombreJugador=jugador.getNombre();
                        apellidoJugador=jugador.getApellido();
                        idEquipo=jugador.getId_equipo();
                    }
                }

                //Obtener el partido
                FirebaseDatabase dbEquipo=FirebaseDatabase.getInstance();
                dbEquipo.getReference().child("Partido").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            Partido partido=snapshot.getValue(Partido.class);
                            if(partido.getId()==idPartido){
                                if(partido.getEq_local()==idEquipo){
                                    jugar="local";
                                }else if(partido.getEq_visitante()==idEquipo){
                                    jugar="visitante";
                                }
                            }
                        }
                        if(jugar!=null){
                            if(jugar.equalsIgnoreCase("local")){
                                holder.goleadorLocalTV.setText(minuto+"' - "+nombreJugador+" "+apellidoJugador);
                                holder.goleadorVisitanteLL.setVisibility(View.GONE);
                            }
                            if(jugar.equalsIgnoreCase("visitante")){
                                holder.goleadorVisitanteTV.setText(minuto+"' "+nombreJugador+" "+apellidoJugador);
                                holder.goleadorLocalLL.setVisibility(View.GONE);
                            }
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("DATABASSE ERROR",databaseError.getMessage());
                    }

                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaGol.size();
    }

    public static class GolViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout goleadorLocalLL,goleadorVisitanteLL;
        private TextView goleadorLocalTV, goleadorVisitanteTV;

        public GolViewHolder(View itemView) {
            super(itemView);
            goleadorLocalLL = (LinearLayout) itemView.findViewById(R.id.goleadorLocalLL);
            goleadorVisitanteLL=(LinearLayout) itemView.findViewById(R.id.goleadorVisitanteLL);
            goleadorLocalTV=(TextView) itemView.findViewById(R.id.goleadorLocalTV);
            goleadorVisitanteTV=(TextView) itemView.findViewById(R.id.goleadorVisitanteTV);
        }

        public void setGoleadorLocalTV(String gol) {
            goleadorLocalTV.setText(gol);
        }

        public void setGoleadorVisitanteTV(String gol) {
            goleadorVisitanteTV.setText(gol);
        }
    }
}
