package com.example.dm2.golscore.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dm2.golscore.Adapter.JugadorAdapter;
import com.example.dm2.golscore.Clases.Jugador;
import com.example.dm2.golscore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlantillaFragment extends Fragment {

    private RecyclerView porterosRV,defensasRV,mediosRV,delanterosRV;
    private TextView porteros,defensas,medios,delanteros;
    private String idEquipo;
    private List<Jugador> listaJugador,listaDefensas,listaMedios,listaDelanteros;
    private JugadorAdapter adapter,adapterDefensas,adapterMedios,adapterDelanteros;

    public PlantillaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        porterosRV=(RecyclerView)getActivity().findViewById(R.id.porterosRV);
        defensasRV=(RecyclerView)getActivity().findViewById(R.id.defensasRV);
        mediosRV=(RecyclerView)getActivity().findViewById(R.id.mediosRV);
        delanterosRV=(RecyclerView)getActivity().findViewById(R.id.delanterosRV);
        porteros=(TextView)getActivity().findViewById(R.id.titlePorteros);
        defensas=(TextView)getActivity().findViewById(R.id.titleDefensas);
        medios=(TextView)getActivity().findViewById(R.id.titleMedios);
        delanteros=(TextView)getActivity().findViewById(R.id.titleDelanteros);

        idEquipo=getActivity().getIntent().getExtras().getString("idEquipo");

        listaJugador=new ArrayList<Jugador>();
        listaDefensas=new ArrayList<Jugador>();
        listaMedios=new ArrayList<Jugador>();
        listaDelanteros=new ArrayList<Jugador>();

        obtenerPorteros();
        obtenerDefensas();
        obtenerMedios();

        obtenerDelanteros();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plantilla, container, false);
    }

    public void obtenerPorteros(){
        DatabaseReference dbJugador= FirebaseDatabase.getInstance().getReference().child("Jugador");

        porterosRV.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapter= new JugadorAdapter(listaJugador);

        porterosRV.setAdapter(adapter);

        dbJugador.orderByChild("posicion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaJugador.removeAll(listaJugador);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Jugador jugador=snapshot.getValue(Jugador.class);
                    if(jugador.getId_equipo()==Integer.parseInt(idEquipo) && jugador.getPosicion().equalsIgnoreCase("portero"))
                        listaJugador.add(jugador);
                }
                adapter.notifyDataSetChanged();

                if (listaJugador.size()==0){
                    porteros.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });
    }

    public void obtenerDefensas(){
        DatabaseReference dbJugador= FirebaseDatabase.getInstance().getReference().child("Jugador");

        defensasRV.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapterDefensas= new JugadorAdapter(listaDefensas);

        defensasRV.setAdapter(adapterDefensas);

        dbJugador.orderByChild("posicion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaDefensas.removeAll(listaDefensas);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Jugador jugador=snapshot.getValue(Jugador.class);
                    if(jugador.getId_equipo()==Integer.parseInt(idEquipo) && jugador.getPosicion().equalsIgnoreCase("defensa"))
                        listaDefensas.add(jugador);
                }
                adapterDefensas.notifyDataSetChanged();

                if (listaDefensas.size()==0){
                    defensas.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });
    }

    public void obtenerMedios(){
        DatabaseReference dbJugador= FirebaseDatabase.getInstance().getReference().child("Jugador");

        mediosRV.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapterMedios= new JugadorAdapter(listaMedios);

        mediosRV.setAdapter(adapterMedios);

        dbJugador.orderByChild("posicion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaMedios.removeAll(listaMedios);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Jugador jugador=snapshot.getValue(Jugador.class);
                    if(jugador.getId_equipo()==Integer.parseInt(idEquipo) && jugador.getPosicion().equalsIgnoreCase("medio"))
                        listaMedios.add(jugador);
                }
                adapterMedios.notifyDataSetChanged();

                if (listaMedios.size()==0){
                    medios.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });
    }

    public void obtenerDelanteros(){
        DatabaseReference dbJugador= FirebaseDatabase.getInstance().getReference().child("Jugador");

        delanterosRV.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapterDelanteros= new JugadorAdapter(listaDelanteros);

        delanterosRV.setAdapter(adapterDelanteros);

        dbJugador.orderByChild("posicion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaDelanteros.removeAll(listaDelanteros);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Jugador jugador=snapshot.getValue(Jugador.class);
                    if(jugador.getId_equipo()==Integer.parseInt(idEquipo) && jugador.getPosicion().equalsIgnoreCase("delantero"))
                        listaDelanteros.add(jugador);
                }
                adapterDelanteros.notifyDataSetChanged();

                if (listaDelanteros.size()==0){
                    delanteros.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });
    }
}
