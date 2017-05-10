package com.example.dm2.golscore.Fragment;

import android.content.res.Configuration;
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

import com.example.dm2.golscore.Adapter.EquipoAdapter;
import com.example.dm2.golscore.Adapter.GolAdapter;
import com.example.dm2.golscore.Adapter.TarjetaAdapter;
import com.example.dm2.golscore.Clases.Categoria;
import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.Clases.Gol;
import com.example.dm2.golscore.Clases.Tarjeta;
import com.example.dm2.golscore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResumenPartidosFragment extends Fragment {

    private RecyclerView golesRV,tarjetasRV;
    private List<Gol> listaGoles;
    private List<Tarjeta> listaTarjetas;
    private GolAdapter golAdapter;
    private TarjetaAdapter tarjetaAdapter;
    private String idPartido;
    private TextView titleGoles,titleTarjetas,titleCambios;

    public ResumenPartidosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        golesRV=(RecyclerView) getActivity().findViewById(R.id.golesRV);
        tarjetasRV=(RecyclerView) getActivity().findViewById(R.id.tarjetasRV);
        titleGoles=(TextView) getActivity().findViewById(R.id.titleGoles);
        titleTarjetas=(TextView) getActivity().findViewById(R.id.titleTarjetas);
        titleCambios=(TextView) getActivity().findViewById(R.id.titleCambios);

        idPartido=getActivity().getIntent().getExtras().getString("idPartido");

        sacarGoles();

        sacarTarjetas();

    }

    public void sacarGoles(){
        DatabaseReference dbClub= FirebaseDatabase.getInstance().getReference().child("Gol");

        listaGoles=new ArrayList<Gol>();

        golesRV.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        golAdapter= new GolAdapter(listaGoles);

        golesRV.setAdapter(golAdapter);

        dbClub.orderByChild("minuto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaGoles.removeAll(listaGoles);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Gol gol=snapshot.getValue(Gol.class);
                    if(gol.getId_partido()==Integer.parseInt(idPartido))
                        listaGoles.add(gol);
                }
                golAdapter.notifyDataSetChanged();

                if (listaGoles.size()==0){
                    titleGoles.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });
    }

    public void sacarTarjetas(){
        DatabaseReference dbClub= FirebaseDatabase.getInstance().getReference().child("Tarjeta");

        listaTarjetas=new ArrayList<Tarjeta>();

        tarjetasRV.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        tarjetaAdapter= new TarjetaAdapter(listaTarjetas);

        tarjetasRV.setAdapter(tarjetaAdapter);

        dbClub.orderByChild("minuto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaTarjetas.removeAll(listaTarjetas);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Tarjeta tarjeta=snapshot.getValue(Tarjeta.class);
                    if(tarjeta.getId_partido()==Integer.parseInt(idPartido))
                        listaTarjetas.add(tarjeta);
                }
                golAdapter.notifyDataSetChanged();

                if (listaTarjetas.size()==0){
                    titleTarjetas.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resumen, container, false);
    }
}
