package com.example.dm2.golscore.Fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dm2.golscore.Clases.Cambio;
import com.example.dm2.golscore.Clases.Club;
import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.Clases.Gol;
import com.example.dm2.golscore.Clases.Jugador;
import com.example.dm2.golscore.Clases.Partido;
import com.example.dm2.golscore.Clases.Tarjeta;
import com.example.dm2.golscore.LocalizacionEstadio;
import com.example.dm2.golscore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdminFragment extends Fragment {

    private Spinner spinnerLocal,spinnerVisitante,jugVisitanteEntra,jugLocalEntra;
    private String valorSpinnerLocal,valorSpinnerLocalEntra;
    private String valorSpinnerVisitante,valorSpinnerVisitanteEntra;
    private Button gol,TarjetaAmarillaB,TarjetaRojaB,cambioB;
    private DatabaseReference db;
    private Jugador jugadorSeleccionado,jugadorSeleccionadoEntra;
    private int minuto;
    private Partido partidoDispu;
    private Equipo equipoLocal,equipoVisitante;

    public AdminFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spinnerLocal=(Spinner)getView().findViewById(R.id.jugLocal);
        spinnerVisitante=(Spinner)getView().findViewById(R.id.jugVisitante);
        jugLocalEntra=(Spinner)getView().findViewById(R.id.jugLocalEntra);
        jugVisitanteEntra=(Spinner)getView().findViewById(R.id.jugVisitanteEntra);

        valorSpinnerLocal="";
        valorSpinnerVisitante="";
        final String idPartido=getActivity().getIntent().getExtras().getString("idPartido");

        final ArrayAdapter<Jugador> jugadoresLocales=new ArrayAdapter<Jugador>(getContext(),android.R.layout.simple_spinner_item);
        final ArrayAdapter<Jugador> jugadoresVisitantes=new ArrayAdapter<Jugador>(getContext(),android.R.layout.simple_spinner_item);

        spinnerLocal.setAdapter(jugadoresLocales);
        jugadoresLocales.add(new Jugador());
        spinnerVisitante.setAdapter(jugadoresVisitantes);
        jugadoresVisitantes.add(new Jugador());

        jugLocalEntra.setAdapter(jugadoresLocales);
        jugVisitanteEntra.setAdapter(jugadoresVisitantes);

        //Obtener datos del partido
        final DatabaseReference dbPartidoDispu=FirebaseDatabase.getInstance().getReference().child("Partido");
        dbPartidoDispu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Partido partido=snapshot.getValue(Partido.class);
                    if(partido.getId()==Integer.parseInt(idPartido)) {
                        partidoDispu=partido;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });

        //Obtener datos del equipo local y visitante
        final DatabaseReference dbEquipo=FirebaseDatabase.getInstance().getReference().child("Equipo");
        dbEquipo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Equipo equipo=snapshot.getValue(Equipo.class);
                    if(equipo.getId()==partidoDispu.getEq_local()) {
                        equipoLocal=equipo;
                    }
                    if(equipo.getId()==partidoDispu.getEq_visitante()) {
                        equipoVisitante=equipo;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });

        final DatabaseReference dbJugador=FirebaseDatabase.getInstance().getReference().child("Jugador");
        dbJugador.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Jugador jugador=snapshot.getValue(Jugador.class);
                    if(jugador.getId_equipo()==partidoDispu.getEq_local())
                    {
                        jugadoresLocales.add(jugador);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });

        dbJugador.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Jugador jugador=snapshot.getValue(Jugador.class);
                    if(jugador.getId_equipo()==partidoDispu.getEq_visitante()) {
                        jugadoresVisitantes.add(jugador);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });

        db=FirebaseDatabase.getInstance().getReference();

        //Obtener minuto de juego
        FirebaseDatabase dbPartido=FirebaseDatabase.getInstance();
        dbPartido.getReference().child("Partido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Partido partido=snapshot.getValue(Partido.class);
                    if(partido.getId()==Integer.parseInt(idPartido)){
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        String stringFechaPartido = partido.getFecha()+" "+partido.getHora();
                        Calendar hoy=Calendar.getInstance();
                        Date dateHoy=hoy.getTime();
                        try {
                            Date datePartido = sdf.parse(stringFechaPartido);
                            Calendar calPartidoFin = Calendar.getInstance();
                            calPartidoFin.setTime(datePartido);
                            calPartidoFin.add(Calendar.HOUR, 2);
                            Date datePartidoFin = calPartidoFin.getTime();
                            if (dateHoy.before(datePartidoFin) && dateHoy.after(datePartido)) {
                                long diferencia = dateHoy.getTime() - datePartido.getTime();
                                minuto=(int)diferencia / (1000 * 60);
                                if(minuto>45 && minuto<60)
                                    minuto=45;
                                else if(minuto>90)
                                    minuto=90;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });


        //AÑADIR GOL
        gol=(Button)getView().findViewById(R.id.pruebaGol);
        gol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.child("Partido").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.getChildren()) {

                            // Equipo e=child.getValue(Equipo.class);
                            if(child.child("id").getValue().toString().equals(idPartido))
                            {
                                String golv=child.child("gol_visitante").getValue().toString();
                                String goll=child.child("gol_local").getValue().toString();

                                if(valorSpinnerLocal.equals("Selecciona Jugador")&&valorSpinnerVisitante.equals("Selecciona Jugador"))
                                {
                                    Toast.makeText(getContext(),"Debes seleccionar un jugador de un equipo",Toast.LENGTH_LONG).show();

                                }
                                else
                                {
                                   if((!valorSpinnerLocal.equals("Selecciona Jugador"))&&(!valorSpinnerVisitante.equals("Selecciona Jugador")))
                                   {
                                       Toast.makeText(getContext(),"No puedes seleccionar dos jugadores",Toast.LENGTH_LONG).show();
                                   }
                                   else
                                   {
                                       String key = db.child("Gol").push().getKey();
                                       if(valorSpinnerLocal.equals("Selecciona Jugador"))
                                       {
                                           int d=Integer.parseInt(golv);
                                           int num=d+1;
                                           child.child("gol_visitante").getRef().setValue(num);
                                           db.child("Gol").child(key).setValue(new Gol(27,jugadorSeleccionado.getId(),Integer.parseInt(idPartido),minuto));
                                           //sumar goles totales del equipo
                                           int goles=equipoVisitante.getTotal_goles();
                                           goles++;
                                           db.child("Equipo").child(Integer.toString(equipoVisitante.getId())).setValue(new Equipo(equipoVisitante.getCategoria(),equipoVisitante.getEscudo(),
                                                   equipoVisitante.getGenero(),equipoVisitante.getGrupo(),equipoVisitante.getId(),equipoVisitante.getId_club(),equipoVisitante.getNombre(),
                                                   equipoVisitante.getPuntos(),goles));
                                       }
                                       else
                                       {
                                           int d=Integer.parseInt(goll);
                                           int num=d+1;
                                           child.child("gol_local").getRef().setValue(num);
                                           db.child("Gol").child(key).setValue(new Gol(17,jugadorSeleccionado.getId(),Integer.parseInt(idPartido),minuto));
                                           //sumar goles totales del equipo
                                           int goles=equipoLocal.getTotal_goles();
                                           goles++;
                                           db.child("Equipo").child(Integer.toString(equipoLocal.getId())).setValue(new Equipo(equipoLocal.getCategoria(),equipoLocal.getEscudo(),
                                                   equipoLocal.getGenero(),equipoLocal.getGrupo(),equipoLocal.getId(),equipoLocal.getId_club(),equipoLocal.getNombre(),
                                                   equipoLocal.getPuntos(),goles));
                                       }
                                       spinnerLocal.setSelection(0);
                                       spinnerVisitante.setSelection(0);
                                   }
                                }

                                break;
                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("Error", "getUser:onCancelled", databaseError.toException());
                    }
                });

            }
        });

        //AÑADIR TARJETA AMARILLA
        TarjetaAmarillaB=(Button)getView().findViewById(R.id.TarjetaAmarillaB);
        TarjetaAmarillaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.child("Tarjeta").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.getChildren()) {

                                if(valorSpinnerLocal.equals("Selecciona Jugador")&&valorSpinnerVisitante.equals("Selecciona Jugador"))
                                {
                                    Toast.makeText(getContext(),"Debes seleccionar un jugador de un equipo",Toast.LENGTH_LONG).show();

                                }
                                else
                                {
                                    if((!valorSpinnerLocal.equals("Selecciona Jugador"))&&(!valorSpinnerVisitante.equals("Selecciona Jugador")))
                                    {
                                        Toast.makeText(getContext(),"No puedes seleccionar dos jugadores",Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        String key = db.child("Tarjeta").push().getKey();
                                        db.child("Tarjeta").child(key).setValue(new Tarjeta(0,"Amarilla",jugadorSeleccionado.getId(),Integer.parseInt(idPartido),minuto));
                                        Toast.makeText(getContext(), "Añadida tarjeta Amarilla", Toast.LENGTH_SHORT).show();
                                        spinnerLocal.setSelection(0);
                                        spinnerVisitante.setSelection(0);
                                    }
                                }

                                break;
                            }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("Error", "getUser:onCancelled", databaseError.toException());
                    }
                });

            }
        });

        //AÑADIR TARJETA ROJA
        TarjetaRojaB=(Button)getView().findViewById(R.id.TarjetaRojaB);
        TarjetaRojaB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.child("Tarjeta").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.getChildren()) {

                            if(valorSpinnerLocal.equals("Selecciona Jugador")&&valorSpinnerVisitante.equals("Selecciona Jugador"))
                            {
                                Toast.makeText(getContext(),"Debes seleccionar un jugador de un equipo",Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                if((!valorSpinnerLocal.equals("Selecciona Jugador"))&&(!valorSpinnerVisitante.equals("Selecciona Jugador")))
                                {
                                    Toast.makeText(getContext(),"No puedes seleccionar dos jugadores",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    String key = db.child("Tarjeta").push().getKey();
                                    db.child("Tarjeta").child(key).setValue(new Tarjeta(0,"Roja",jugadorSeleccionado.getId(),Integer.parseInt(idPartido),minuto));
                                    Toast.makeText(getContext(), "Añadida tarjeta Roja", Toast.LENGTH_SHORT).show();
                                    spinnerLocal.setSelection(0);
                                    spinnerVisitante.setSelection(0);
                                }
                            }

                            break;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("Error", "getUser:onCancelled", databaseError.toException());
                    }
                });

            }
        });


        //HACER CAMBIO
        cambioB=(Button)getView().findViewById(R.id.cambioB);
        cambioB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.child("Cambio").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.getChildren()) {

                            if(valorSpinnerLocal.equals("Selecciona Jugador")&&valorSpinnerVisitante.equals("Selecciona Jugador") || valorSpinnerLocalEntra.equals("Selecciona Jugador")&&valorSpinnerVisitanteEntra.equals("Selecciona Jugador"))
                            {
                                Toast.makeText(getContext(),"Debes seleccionar un jugador de un equipo",Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                if((!valorSpinnerLocal.equals("Selecciona Jugador"))&&(!valorSpinnerVisitante.equals("Selecciona Jugador")) ||(!valorSpinnerLocalEntra.equals("Selecciona Jugador"))&&(!valorSpinnerVisitanteEntra.equals("Selecciona Jugador")) )
                                {
                                    Toast.makeText(getContext(),"No puedes seleccionar dos jugadores",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    String key = db.child("Cambio").push().getKey();
                                    db.child("Cambio").child(key).setValue(new Cambio(0,jugadorSeleccionadoEntra.getId(),jugadorSeleccionado.getId_equipo(),Integer.parseInt(idPartido),minuto));

                                    Toast.makeText(getContext(), "Cambio realizado", Toast.LENGTH_SHORT).show();
                                    spinnerLocal.setSelection(0);
                                    spinnerVisitante.setSelection(0);
                                    jugLocalEntra.setSelection(0);
                                    jugVisitanteEntra.setSelection(0);
                                }
                            }

                            break;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("Error", "getUser:onCancelled", databaseError.toException());
                    }
                });

            }
        });

        spinnerLocal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valorSpinnerLocal = adapterView.getItemAtPosition(i).toString();
                jugadorSeleccionado=(Jugador) adapterView.getItemAtPosition(i);
            }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });

        spinnerVisitante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valorSpinnerVisitante = adapterView.getItemAtPosition(i).toString();
                jugadorSeleccionado=(Jugador) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        jugLocalEntra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valorSpinnerLocalEntra = adapterView.getItemAtPosition(i).toString();
                jugadorSeleccionadoEntra=(Jugador) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        jugVisitanteEntra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valorSpinnerVisitanteEntra = adapterView.getItemAtPosition(i).toString();
                jugadorSeleccionadoEntra=(Jugador) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }
}
