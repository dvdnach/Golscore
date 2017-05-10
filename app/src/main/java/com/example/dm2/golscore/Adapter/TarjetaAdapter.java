package com.example.dm2.golscore.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dm2.golscore.Clases.Gol;
import com.example.dm2.golscore.Clases.Jugador;
import com.example.dm2.golscore.Clases.Partido;
import com.example.dm2.golscore.Clases.Tarjeta;
import com.example.dm2.golscore.LigaActivity;
import com.example.dm2.golscore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TarjetaAdapter extends RecyclerView.Adapter<TarjetaAdapter.TarjetaViewHolder> {

    private List<Tarjeta> listaTarjeta;
    private Bitmap btmAmarilla,btmRoja;

    public TarjetaAdapter(List<Tarjeta> listaTarjeta) {
        this.listaTarjeta = listaTarjeta;
    }

    @Override
    public TarjetaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_list_item,parent,false);

        btmAmarilla= BitmapFactory.decodeResource(parent.getContext().getResources(),R.drawable.amarilla);
        btmRoja=BitmapFactory.decodeResource(parent.getContext().getResources(),R.drawable.roja);
        TarjetaViewHolder holder=new TarjetaViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final TarjetaViewHolder holder, final int position) {
        final Tarjeta tarjeta= listaTarjeta.get(position);
        final int idJugador=tarjeta.getId_jugador();
        final int idPartido=tarjeta.getId_partido();
        final int minuto=tarjeta.getMinuto();
        final String color=tarjeta.getColor();
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
                                holder.tarjetaLocalTV.setText(minuto+"´ - "+nombreJugador+" "+apellidoJugador);
                                holder.tarjetaVisitanteIV.setVisibility(View.GONE);
                                if(color.equalsIgnoreCase("Amarilla")){
                                    holder.tarjetaLocalIV.setImageBitmap(btmAmarilla);
                                }else if(color.equalsIgnoreCase("Roja")){
                                    holder.tarjetaLocalIV.setImageBitmap(btmRoja);
                                }
                            }
                            if(jugar.equalsIgnoreCase("visitante")){
                                holder.tarjetaVisitanteTV.setText(minuto+"´ "+nombreJugador+" "+apellidoJugador);
                                holder.tajetaLocalLL.setVisibility(View.GONE);
                                if(color.equalsIgnoreCase("Amarilla")){
                                    holder.tarjetaLocalIV.setImageBitmap(btmAmarilla);
                                }else if(color.equalsIgnoreCase("Roja")){
                                    holder.tarjetaLocalIV.setImageBitmap(btmRoja);
                                }
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
        return listaTarjeta.size();
    }

    public static class TarjetaViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout tajetaLocalLL,tarjetaVisitanteLL;
        private TextView tarjetaLocalTV, tarjetaVisitanteTV;
        private ImageView tarjetaVisitanteIV,tarjetaLocalIV;

        public TarjetaViewHolder(View itemView) {
            super(itemView);
            tajetaLocalLL = (LinearLayout) itemView.findViewById(R.id.tarjetaLocalLL);
            tarjetaVisitanteLL=(LinearLayout) itemView.findViewById(R.id.tarjetaVisitanteLL);
            tarjetaLocalTV=(TextView) itemView.findViewById(R.id.tarjetaLocalTV);
            tarjetaVisitanteTV=(TextView) itemView.findViewById(R.id.tarjetaVisitanteTV);
            tarjetaVisitanteIV=(ImageView) itemView.findViewById(R.id.tarjetaVisitanteIV);
            tarjetaLocalIV=(ImageView) itemView.findViewById(R.id.tarjetaLocalIV);
        }

        public void setTajetaLocalTV(String tarjeta) {
            tarjetaLocalTV.setText(tarjeta);
        }

        public void setTarjetaVisitanteTV(String tarjeta) {
            tarjetaVisitanteTV.setText(tarjeta);
        }
    }
}
