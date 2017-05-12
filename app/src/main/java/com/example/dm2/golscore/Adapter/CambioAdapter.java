package com.example.dm2.golscore.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dm2.golscore.Clases.Cambio;
import com.example.dm2.golscore.Clases.Gol;
import com.example.dm2.golscore.Clases.Jugador;
import com.example.dm2.golscore.Clases.Partido;
import com.example.dm2.golscore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        final Cambio cambio= listaCambio.get(position);
        final int idJugadorEntra=cambio.getId_jugador_entra();
        final int idJugadorSale=cambio.getId_jugador_sale();
        final int minuto=cambio.getMinuto();
        final int idPartido=cambio.getId_partido();

        //Obtener nombre jugador
        FirebaseDatabase dbPartido=FirebaseDatabase.getInstance();
        dbPartido.getReference().child("Jugador").addValueEventListener(new ValueEventListener() {
            String nombreJugadorEntra,apellidoJugadorEntra,nombreJugadorSale,apellidoJugadorSale,jugar;
            int idEquipo;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Jugador jugador=snapshot.getValue(Jugador.class);
                    if(jugador.getId()==idJugadorEntra){
                        nombreJugadorEntra=jugador.getNombre();
                        apellidoJugadorEntra=jugador.getApellido();
                        idEquipo=jugador.getId_equipo();
                    }
                    if(jugador.getId()==idJugadorSale){
                        nombreJugadorSale=jugador.getNombre();
                        apellidoJugadorSale=jugador.getApellido();
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
                                holder.cambioEntraLocalTV.setText(minuto+"' - "+nombreJugadorEntra+" "+apellidoJugadorEntra);
                                holder.cambioSaleLocalTV.setText(minuto+"' - "+nombreJugadorSale+" "+apellidoJugadorSale);
                                holder.cambioVisitanteLL.setVisibility(View.GONE);
                            }
                            if(jugar.equalsIgnoreCase("visitante")){
                                holder.cambioEntraVisitanteTV.setText(minuto+"' - "+nombreJugadorEntra+" "+apellidoJugadorEntra);
                                holder.cambioSaleVisitanteTV.setText(minuto+"' - "+nombreJugadorSale+" "+apellidoJugadorSale);
                                holder.cambioLocalLL.setVisibility(View.GONE);
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
        return listaCambio.size();
    }

    public static class CambioViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout cambioLocalLL,cambioVisitanteLL;
        private TextView cambioSaleLocalTV,cambioEntraLocalTV,cambioSaleVisitanteTV,cambioEntraVisitanteTV;

        public CambioViewHolder(View itemView) {
            super(itemView);
            cambioLocalLL = (LinearLayout) itemView.findViewById(R.id.cambioLocalLL);
            cambioVisitanteLL=(LinearLayout) itemView.findViewById(R.id.cambioVisitanteLL);
            cambioSaleLocalTV=(TextView) itemView.findViewById(R.id.cambioSaleLocalTV);
            cambioEntraLocalTV=(TextView) itemView.findViewById(R.id.cambioEntraLocalTV);
            cambioSaleVisitanteTV=(TextView) itemView.findViewById(R.id.cambioSaleVisitanteTV);
            cambioEntraVisitanteTV=(TextView) itemView.findViewById(R.id.cambioEntraVisitanteTV);
        }

        public void setCambioEntraLocalTV(String cambio) {
            cambioEntraLocalTV.setText(cambio);
        }

        public void setCambioEntraVisitanteTV(String cambio) {
            cambioEntraVisitanteTV.setText(cambio);
        }

        public void setCambioSaleLocalTV(String cambio) {
            cambioSaleLocalTV.setText(cambio);
        }

        public void setCambioSaleVisitanteTV(String cambio) {
            cambioSaleVisitanteTV.setText(cambio);
        }

    }
}
