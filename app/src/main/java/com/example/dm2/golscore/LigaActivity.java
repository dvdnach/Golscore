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
import android.widget.TextView;

import com.example.dm2.golscore.Adapter.ClubAdapter;
import com.example.dm2.golscore.Adapter.SubcategoriaAdapter;
import com.example.dm2.golscore.Clases.Categoria;
import com.example.dm2.golscore.Clases.Club;
import com.example.dm2.golscore.Clases.Subcategoria;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LigaActivity extends AppCompatActivity {

    private FrameLayout clubFL;
    private String nombreSucategoria,idSubcategoria;
    private RecyclerView clubRV;
    private List<Club> listaClub;
    private ClubAdapter adapter;

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
        nombreSucategoria=getIntent().getExtras().getString("nombreSubcategoria");
        idSubcategoria=getIntent().getExtras().getString("idSubcategoria");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        sacarEquipos();
    }

    public void sacarEquipos(){
        setTitle(nombreSucategoria);
        DatabaseReference dbClub=FirebaseDatabase.getInstance().getReference().child("Club");

        clubRV=(RecyclerView) findViewById(R.id.clubsRV);
        clubRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        listaClub=new ArrayList<Club>();
        adapter= new ClubAdapter(listaClub);

        clubRV.setAdapter(adapter);

        dbClub.orderByChild("Nombre").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaClub.removeAll(listaClub);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Club club=snapshot.getValue(Club.class);
                    if(club.getSubcategoria()==Integer.parseInt(idSubcategoria))
                        listaClub.add(club);
                }
                Log.e("lista",listaClub.size()+"");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });
    }

}
