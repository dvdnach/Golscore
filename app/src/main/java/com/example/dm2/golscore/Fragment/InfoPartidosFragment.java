package com.example.dm2.golscore.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dm2.golscore.Clases.Club;
import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.Clases.Partido;
import com.example.dm2.golscore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoPartidosFragment extends Fragment {

    private TextView nombreEstadioPartidoTV;
    private LinearLayout estadioPartidoLL;

    public InfoPartidosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final String idPartido=getActivity().getIntent().getExtras().getString("idPartido");
        nombreEstadioPartidoTV=(TextView) getActivity().findViewById(R.id.nombreEstadioPartidoTV);
        estadioPartidoLL=(LinearLayout) getActivity().findViewById(R.id.estadioPartidoLL);
        estadioPartidoLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ir a localización", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseDatabase dbPartido=FirebaseDatabase.getInstance();
        dbPartido.getReference().child("Partido").addValueEventListener(new ValueEventListener() {
            int idEquipoLocal,idClub;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Partido partido=snapshot.getValue(Partido.class);
                    if(partido.getId()==Integer.parseInt(idPartido)){
                        idEquipoLocal=partido.getEq_local();
                    }
                }

                FirebaseDatabase dbEquipo=FirebaseDatabase.getInstance();
                dbEquipo.getReference().child("Equipo").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            Equipo equipo=snapshot.getValue(Equipo.class);
                            if(equipo.getId()==idEquipoLocal){
                                idClub=equipo.getId_club();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("DATABASSE ERROR",databaseError.getMessage());
                    }
                });

                FirebaseDatabase dbClub=FirebaseDatabase.getInstance();
                dbClub.getReference().child("Club").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            Club club=snapshot.getValue(Club.class);
                            if(club.getId()==idClub){
                                nombreEstadioPartidoTV.setText(club.getCampo());
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("DATABASSE ERROR",databaseError.getMessage());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASSE ERROR",databaseError.getMessage());
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_partido, container, false);
    }
}
