package com.example.dm2.golscore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.dm2.golscore.Adapter.PartidoAdapter;
import com.example.dm2.golscore.Adapter.PartidoAdminAdapter;
import com.example.dm2.golscore.Clases.Partido;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PantallaAfterLogin extends AppCompatActivity {

    private RecyclerView partidosAdminRV;
    private List<Partido> listaPartido;
    private PartidoAdminAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_after_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        partidosAdminRV=(RecyclerView) findViewById(R.id.partidosAdminRV);

        DatabaseReference dbClub= FirebaseDatabase.getInstance().getReference().child("Partido");

        partidosAdminRV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        listaPartido=new ArrayList<Partido>();
        adapter= new PartidoAdminAdapter(listaPartido);

        partidosAdminRV.setAdapter(adapter);

       Calendar hoy=Calendar.getInstance();
        final Date dateHoy=hoy.getTime();

        dbClub.orderByChild("fecha").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaPartido.removeAll(listaPartido);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Partido partido=snapshot.getValue(Partido.class);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String stringFechaPartido = partido.getFecha()+" "+partido.getHora();
                    try {
                        Date datePartido = sdf.parse(stringFechaPartido);
                        Calendar calPartidoFin = Calendar.getInstance();
                        calPartidoFin.setTime(datePartido);
                        calPartidoFin.add(Calendar.HOUR, 2);
                        Date datePartidoFin = calPartidoFin.getTime();
                        if (dateHoy.before(datePartidoFin) && dateHoy.after(datePartido)) {
                            listaPartido.add(partido);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    public void salir(View v)
    {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}
