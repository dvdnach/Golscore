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
import com.example.dm2.golscore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoFragment extends Fragment {

    private TextView nombreEquipoTV,nombreEstadioTV;
    private int idClub;
    private LinearLayout estadioLL;

    public InfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nombreEquipoTV=(TextView)getView().findViewById(R.id.nombreEquipoTV);
        nombreEstadioTV=(TextView) getView().findViewById(R.id.nombreEstadioTV);
        estadioLL=(LinearLayout)getView().findViewById(R.id.estadioLL);
        estadioLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ir a localización", Toast.LENGTH_SHORT).show();
            }
        });

        final String idEquipo=getActivity().getIntent().getExtras().getString("idEquipo");

        FirebaseDatabase dbEquipo=FirebaseDatabase.getInstance();
        dbEquipo.getReference().child("Equipo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Equipo equipo=snapshot.getValue(Equipo.class);
                    if(equipo.getId()==Integer.parseInt(idEquipo))
                        idClub=equipo.getId_club();
                }

                FirebaseDatabase dbClub=FirebaseDatabase.getInstance();
                dbClub.getReference().child("Club").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            Club club=snapshot.getValue(Club.class);
                            if(club.getId()==idClub){
                                nombreEquipoTV.setText(club.getNombre());
                                nombreEstadioTV.setText(club.getCampo());
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
        return inflater.inflate(R.layout.fragment_info, container, false);
    }
}