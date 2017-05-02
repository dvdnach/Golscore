package com.example.dm2.golscore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.dm2.golscore.Adapter.SubcategoriaAdapter;
import com.example.dm2.golscore.Clases.Subcategoria;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SubCategoriaActivity extends AppCompatActivity {

    private RecyclerView subcategoriaRV;
    private List<Subcategoria> listaSubcategoria;
    private SubcategoriaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categoria);
        final String idCategoria=getIntent().getExtras().getString("idCategoria");
        Log.e("idCategoria",idCategoria);

        subcategoriaRV=(RecyclerView) findViewById(R.id.subcategoriaRV);
        subcategoriaRV.setLayoutManager(new LinearLayoutManager(this));

        listaSubcategoria=new ArrayList<Subcategoria>();
        adapter= new SubcategoriaAdapter(listaSubcategoria);

        subcategoriaRV.setAdapter(adapter);

        FirebaseDatabase dbSubcategoria=FirebaseDatabase.getInstance();
        dbSubcategoria.getReference().child("SubCategoria").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaSubcategoria.removeAll(listaSubcategoria);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Subcategoria subcategoria=snapshot.getValue(Subcategoria.class);
                    if(subcategoria.getCategoria()==Integer.parseInt(idCategoria))
                        listaSubcategoria.add(subcategoria);
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
