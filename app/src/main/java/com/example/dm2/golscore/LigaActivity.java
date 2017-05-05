package com.example.dm2.golscore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.dm2.golscore.Adapter.EquipoAdapter;
import com.example.dm2.golscore.Clases.Equipo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LigaActivity extends AppCompatActivity {

    private FrameLayout clubFL;
    private String nombreGrupo,idGrupo;
    private RecyclerView clubRV;
    private List<Equipo> listaEquipo;
    private EquipoAdapter adapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_club:
                    sacarEquipos();
                    return true;
                case R.id.navigation_partidos:

                    return true;
                case R.id.navigation_clasificacion:

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

}
