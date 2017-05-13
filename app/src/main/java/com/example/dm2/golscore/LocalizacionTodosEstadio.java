package com.example.dm2.golscore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dm2.golscore.Clases.Club;
import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.Clases.Estadio;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LocalizacionTodosEstadio extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mapa;
    private int vistaActual;
    private Button animar,posInicial;
    private DatabaseReference db;
    private Estadio est;
    private int idclub;
    private Button btnVista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacion_estadio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnVista=(Button)findViewById(R.id.btnVista);

        MapFragment mapFragment=(MapFragment)getFragmentManager().findFragmentById(R.id.map);

        db= FirebaseDatabase.getInstance().getReference();
        mapFragment.getMapAsync(this);
        coordenadas();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa=googleMap;
        mapa.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }
        });
        vistaActual= GoogleMap.MAP_TYPE_NORMAL;
        mapa.setMapType(vistaActual);
        mapa.getUiSettings().setZoomControlsEnabled(true);
    }

    private void coordenadas() {
        db.child("Club").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                     Club c=child.getValue(Club.class);
                        double lat=c.getLatitud();
                        double lon=c.getLongitud();
                        LatLng campo=new LatLng(lat,lon);
                        mapa.addMarker(new MarkerOptions().position(campo).title(child.child("campo").getValue().toString()));
                        mapa.moveCamera(CameraUpdateFactory.newLatLng(campo));
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Error", "getUser:onCancelled", databaseError.toException());
                Toast.makeText(getApplicationContext(),databaseError.toException().getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void cambiarVista(View v)
    {
        if(vistaActual== GoogleMap.MAP_TYPE_SATELLITE)
        {
            vistaActual= GoogleMap.MAP_TYPE_NORMAL;
            mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            btnVista.setText("Cambiar a Vista Satélite");
        }
        else
        {
            vistaActual= GoogleMap.MAP_TYPE_SATELLITE;
            mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            btnVista.setText("Cambiar a Vista Mapa");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
