package com.example.dm2.golscore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class LocalizacionEstadio extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mapa;
    private int vistaActual;
    private Button animar,posInicial;
    private DatabaseReference db;
    private Estadio est;
    private String equipo;
    private Button btnVista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacion_estadio);

        btnVista=(Button)findViewById(R.id.btnVista);
        Bundle bundle = getIntent().getExtras();
        //equipo="S.D Alsasua";
       // equipo=bundle.getString("Equipo");

        MapFragment mapFragment=(MapFragment)getFragmentManager().findFragmentById(R.id.map);

        db= FirebaseDatabase.getInstance().getReference();
        mapFragment.getMapAsync(this);
        coordenadas();

       /* animar=(Button)findViewById(R.id.btnAnimar);
        animar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animarInsti();
            }
        });*/

       /* posInicial=(Button)findViewById(R.id.btnPosInicial);
        posInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng insti=new LatLng(0,0);
                CameraPosition camPos=new CameraPosition.Builder().target(insti).zoom(2).bearing(0).tilt(0).build();

                CameraUpdate camUpd3= CameraUpdateFactory.newCameraPosition(camPos);
                mapa.animateCamera(camUpd3);
            }
        });*/

    }

    private void animarCampo(LatLng campo) {
        LatLng c=campo;
        CameraPosition camPos=new CameraPosition.Builder().target(c).zoom(15).bearing(0).build();

        CameraUpdate camUpd3= CameraUpdateFactory.newCameraPosition(camPos);
        mapa.animateCamera(camUpd3);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa=googleMap;
        mapa.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
               /* Toast.makeText(LocalizacionEstadio.this,"Lat"+cameraPosition.target.latitude+"lon:"+cameraPosition.target.longitude+" zoom"
                        +cameraPosition.zoom+"orientation"+cameraPosition.bearing+"angulo"+cameraPosition.tilt,Toast.LENGTH_LONG).show();*/
            }
        });
        vistaActual= GoogleMap.MAP_TYPE_SATELLITE;
        mapa.setMapType(vistaActual);
        mapa.getUiSettings().setZoomControlsEnabled(true);
    }

    private void coordenadas() {
        db.child("Estadio_Loc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get user value
                // Estadio est=dataSnapshot.getValue(Estadio.class);
                      /*  est.setLatitud(40.22);
                        est.setLongitud(2);*/
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    // Estadio e=child.getValue(Estadio.class);
                    if(child.child("Equipo").getValue().toString().equals(equipo))
                    {
                        double lat= Double.parseDouble(child.child("Latitud").getValue().toString());
                        double lon=Double.parseDouble(child.child("Longitud").getValue().toString());
                        LatLng campo=new LatLng(lat,lon);
                        mapa.addMarker(new MarkerOptions().position(campo).title(equipo));
                        mapa.moveCamera(CameraUpdateFactory.newLatLng(campo));
                        animarCampo(campo);
                        break;
                    }


                    //Toast.makeText(getApplicationContext(),child.child("Equipo").getValue().toString(),Toast.LENGTH_LONG).show();
                }

                        /*for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Toast.makeText(getApplicationContext(),child.toString(),Toast.LENGTH_LONG).show();
                        }*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Error", "getUser:onCancelled", databaseError.toException());
                Toast.makeText(getApplicationContext(),databaseError.toException().getMessage(),Toast.LENGTH_LONG).show();
                // ...
            }
        });
    }

    public void cambiarVista(View v)
    {
        if(vistaActual== GoogleMap.MAP_TYPE_SATELLITE)
        {
            vistaActual= GoogleMap.MAP_TYPE_NORMAL;
            mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            btnVista.setText("Vista Satélite");
        }
        else
        {
            vistaActual= GoogleMap.MAP_TYPE_SATELLITE;
            mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            btnVista.setText("Vista Mapa");
        }

    }
}
