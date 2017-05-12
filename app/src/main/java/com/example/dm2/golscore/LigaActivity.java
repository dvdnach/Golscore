package com.example.dm2.golscore;

import android.content.res.Configuration;
import android.icu.text.MessagePattern;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.dm2.golscore.Adapter.ClasificacionAdapter;
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

    private FrameLayout clubFL,clasificacionFL;
    private LinearLayout partidosFL;
    private String nombreGrupo,idGrupo;
    private RecyclerView clubRV,partidosRV, clasificacionRV;
    private List<Equipo> listaEquipo;
    private List<Equipo> listaClasificacion;
    private List<Partido> listaPartido;
    private EquipoAdapter adapter;
    private PartidoAdapter adapterPartido;
    private ClasificacionAdapter adapterClasificacion;
    private TabLayout partidosJornadasTL;

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
                    obtenerClasificacion();
                    clubFL.setVisibility(View.GONE);
                    partidosFL.setVisibility(View.GONE);
                    clasificacionFL.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }

    };

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liga);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        clubFL=(FrameLayout)findViewById(R.id.clubFL);
        partidosFL=(LinearLayout)findViewById(R.id.partidosFL);
        clasificacionFL=(FrameLayout)findViewById(R.id.clasificacionFL);
        nombreGrupo=getIntent().getExtras().getString("nombreGrupo");
        idGrupo=getIntent().getExtras().getString("idGrupo");

        listaEquipo=new ArrayList<Equipo>();

        partidosJornadasTL=(TabLayout)findViewById(R.id.partidosJornadasTL);
        partidosJornadasTL.setTabGravity(TabLayout.GRAVITY_FILL);
        partidosJornadasTL.setTabMode(TabLayout.MODE_SCROLLABLE);
        partidosJornadasTL.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                obtenerPartidos();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        sacarEquipos();
    }

    public void sacarEquipos(){
        setTitle(nombreGrupo);
        DatabaseReference dbClub=FirebaseDatabase.getInstance().getReference().child("Equipo");

        clubRV=(RecyclerView) findViewById(R.id.clubsRV);
        clubRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter= new EquipoAdapter(listaEquipo, this.getApplicationContext());

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
                if(listaEquipo.size()>0){
                    partidosJornadasTL.removeAllTabs();
                    for (int x=0;x<listaEquipo.size()*2-2;x++){
                        partidosJornadasTL.addTab(partidosJornadasTL.newTab().setText("J. "+(x+1)));
                    }
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
                    if(partido.getGrupo()==Integer.parseInt(idGrupo) && partido.getJornada()==(partidosJornadasTL.getSelectedTabPosition()+1)){
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

    public void obtenerClasificacion () {
        setTitle(nombreGrupo);
        DatabaseReference dbClub=FirebaseDatabase.getInstance().getReference().child("Equipo");

        clasificacionRV=(RecyclerView) findViewById(R.id.clasificacionRV);
        clasificacionRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        listaClasificacion=new ArrayList<Equipo>();
        adapterClasificacion= new ClasificacionAdapter(listaClasificacion, this.getApplicationContext());

        clasificacionRV.setAdapter(adapterClasificacion);

        dbClub.orderByChild("puntos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaClasificacion.removeAll(listaClasificacion);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Equipo equipo=snapshot.getValue(Equipo.class);
                    if(equipo.getGrupo()==Integer.parseInt(idGrupo)){
                        listaClasificacion.add(equipo);
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