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
import com.example.dm2.golscore.Clases.Club;
import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.Clases.Gol;
import com.example.dm2.golscore.Clases.Jugador;
import com.example.dm2.golscore.LocalizacionEstadio;
import com.example.dm2.golscore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AdminFragment extends Fragment {

    private TextView nombreEquipoTV,nombreEstadioTV;
    private int idClub;
    private LinearLayout estadioLL;
    private Spinner spinnerLocal,spinnerVisitante;
    private String valorSpinnerLocal;
    private String valorSpinnerVisitante;
    private Button gol;
    private DatabaseReference db;
    int idEquipoVisitante;
    int idEquipoLocal;
    private Jugador jugadorSeleccionado;

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


        valorSpinnerLocal="";
        valorSpinnerVisitante="";
        final String idPartido=getActivity().getIntent().getExtras().getString("idPartido");

        final ArrayAdapter<Jugador> jugadoresLocales=new ArrayAdapter<Jugador>(getContext(),android.R.layout.simple_spinner_item);
        final ArrayAdapter<Jugador> jugadoresVisitantes=new ArrayAdapter<Jugador>(getContext(),android.R.layout.simple_spinner_item);

        spinnerLocal.setAdapter(jugadoresLocales);
        jugadoresLocales.add(new Jugador());
        spinnerVisitante.setAdapter(jugadoresVisitantes);
        jugadoresVisitantes.add(new Jugador());

        final DatabaseReference dbJugador=FirebaseDatabase.getInstance().getReference().child("Jugador");
        dbJugador.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //listaDefensas.removeAll(listaDefensas);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Jugador jugador=snapshot.getValue(Jugador.class);
                    if(jugador.getId_equipo()==1)
                    {
                        jugadoresLocales.add(jugador);
                    }

                }
                //adapterDefensas.notifyDataSetChanged();

                /*if (listaDefensas.size()==0){
                    defensas.setVisibility(View.GONE);
                }*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });

        dbJugador.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //listaDefensas.removeAll(listaDefensas);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Jugador jugador=snapshot.getValue(Jugador.class);
                    if(jugador.getId_equipo()==2)
                    {
                        jugadoresVisitantes.add(jugador);
                    }

                }
                //adapterDefensas.notifyDataSetChanged();

                /*if (listaDefensas.size()==0){
                    defensas.setVisibility(View.GONE);
                }*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });

        db=FirebaseDatabase.getInstance().getReference();
        gol=(Button)getView().findViewById(R.id.pruebaGol);
        gol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarSpinner();
                db.child("Partido").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        // Equipo est=dataSnapshot.getValue(Equipo.class);
                      /*  est.setLatitud(40.22);
                        est.setLongitud(2);*/

                        for (DataSnapshot child : dataSnapshot.getChildren()) {

                            // Equipo e=child.getValue(Equipo.class);
                            if(child.child("id").getValue().toString().equals(idPartido))
                            {
                                String golv=child.child("gol_visitante").getValue().toString();
                                int d=Integer.parseInt(golv);
                                int num=d+1;
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
                                       if(valorSpinnerLocal.equals("Selecciona Jugador"))
                                       {
                                           child.child("gol_visitante").getRef().setValue(num);
                                           db.child("Gol").child("27").setValue(new Gol(27,jugadorSeleccionado.getId(),Integer.parseInt(idPartido),59));
                                       }
                                       else
                                       {
                                           child.child("gol_local").getRef().setValue(num);
                                           db.child("Gol").child("17").setValue(new Gol(45,jugadorSeleccionado.getId(),Integer.parseInt(idPartido),45));
                                           //new ResumenPartidosFragment();
                                       }

                                   }
                                }

                                break;
                                //db.child("Gol").setValue(new Gol())
                               /* Map<String, Object> hopperUpdates = new HashMap<String, Object>();
                                hopperUpdates.put("gol_visitante",String.valueOf(num));
                                hopperRef.updateChildren(hopperUpdates);*/

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
                      //  Toast.makeText(getApplicationContext(),databaseError.toException().getMessage(),Toast.LENGTH_LONG).show();
                        // ...
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


    }

    private void comprobarSpinner() {
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
