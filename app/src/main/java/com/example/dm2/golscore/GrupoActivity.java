package com.example.dm2.golscore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.dm2.golscore.Adapter.GrupoAdapter;
import com.example.dm2.golscore.Clases.Grupo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GrupoActivity extends AppCompatActivity {

    private RecyclerView subcategoriaRV;
    private List<Grupo> listaGrupo;
    private GrupoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categoria);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final String idCategoria=getIntent().getExtras().getString("idCategoria");
        final String nombreCategoria=getIntent().getExtras().getString("nombreCategoria");
        setTitle(nombreCategoria);

        subcategoriaRV=(RecyclerView) findViewById(R.id.subcategoriaRV);
        subcategoriaRV.setLayoutManager(new LinearLayoutManager(this));

        listaGrupo =new ArrayList<Grupo>();
        adapter= new GrupoAdapter(listaGrupo);

        subcategoriaRV.setAdapter(adapter);

        FirebaseDatabase dbSubcategoria=FirebaseDatabase.getInstance();
        dbSubcategoria.getReference().child("Grupo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaGrupo.removeAll(listaGrupo);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Grupo grupo =snapshot.getValue(Grupo.class);
                    if(grupo.getCategoria()==Integer.parseInt(idCategoria))
                        listaGrupo.add(grupo);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
