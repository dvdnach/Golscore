package com.example.dm2.golscore;

import android.content.res.Configuration;
import android.icu.text.MessagePattern;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.dm2.golscore.Adapter.EquipoAdapter;
import com.example.dm2.golscore.Adapter.PartidoAdapter;
import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.Clases.Partido;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LigaActivity extends AppCompatActivity {

    private FrameLayout clubFL,partidosFL,clasificacionFL;
    private String nombreGrupo,idGrupo;
    private RecyclerView clubRV,partidosRV;
    private List<Equipo> listaEquipo;
    private List<Partido> listaPartido;
    private EquipoAdapter adapter;
    private PartidoAdapter adapterPartido;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_club:
                    sacarEquipos();
                    clubFL.setVisibility(View.VISIBLE);
                    partidosFL.setVisibility(View.GONE);
                    clasificacionFL.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_partidos:
                    obtenerPartidos();
                    clubFL.setVisibility(View.GONE);
                    partidosFL.setVisibility(View.VISIBLE);
                    clasificacionFL.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_clasificacion:
                    clubFL.setVisibility(View.GONE);
                    partidosFL.setVisibility(View.GONE);
                    clasificacionFL.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liga);

        clubFL=(FrameLayout)findViewById(R.id.clubFL);
        partidosFL=(FrameLayout)findViewById(R.id.partidosFL);
        clasificacionFL=(FrameLayout)findViewById(R.id.clasificacionFL);
        nombreGrupo=getIntent().getExtras().getString("nombreGrupo");
        idGrupo=getIntent().getExtras().getString("idGrupo");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        sacarEquipos();
    }

    public void sacarEquipos(){
        setTitle(nombreGrupo);
        DatabaseReference dbClub=FirebaseDatabase.getInstance().getReference().child("Equipo");

        clubRV=(RecyclerView) findViewById(R.id.clubsRV);
        clubRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        listaEquipo=new ArrayList<Equipo>();
        adapter= new EquipoAdapter(listaEquipo);

        clubRV.setAdapter(adapter);

        dbClub.orderByChild("nombre").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaEquipo.removeAll(listaEquipo);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Equipo equipo=snapshot.getValue(Equipo.class);
                    if(equipo.getGrupo()==Integer.parseInt(idGrupo))
                        listaEquipo.add(equipo);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });
    }

    public void obtenerPartidos(){
        setTitle(nombreGrupo);
        DatabaseReference dbClub=FirebaseDatabase.getInstance().getReference().child("Partido");

        partidosRV=(RecyclerView) findViewById(R.id.partidosRV);
        partidosRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        listaPartido=new ArrayList<Partido>();
        adapterPartido= new PartidoAdapter(listaPartido);

        partidosRV.setAdapter(adapterPartido);

        dbClub.orderByChild("fecha").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaPartido.removeAll(listaPartido);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Partido partido=snapshot.getValue(Partido.class);
                    if(partido.getGrupo()==Integer.parseInt(idGrupo)){
                        listaPartido.add(partido);
                    }
                }
                adapterPartido.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });
    }
}