package com.example.dm2.golscore.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dm2.golscore.Adapter.PartidoAdapter;
import com.example.dm2.golscore.Clases.Partido;
import com.example.dm2.golscore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PartidosFragment extends Fragment {

    private RecyclerView partidosEquipoRV;
    private List<Partido> listaPartido;
    private PartidoAdapter adapter;

    public PartidosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DatabaseReference dbClub= FirebaseDatabase.getInstance().getReference().child("Partido");

        partidosEquipoRV=(RecyclerView)getView().findViewById(R.id.partidosEquipoRV);
        partidosEquipoRV.setLayoutManager(new LinearLayoutManager(getContext()));

        listaPartido=new ArrayList<Partido>();
        adapter= new PartidoAdapter(listaPartido);

        partidosEquipoRV.setAdapter(adapter);

        final String idEquipo=getActivity().getIntent().getExtras().getString("idEquipo");

        dbClub.orderByChild("fecha").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaPartido.removeAll(listaPartido);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Partido partido=snapshot.getValue(Partido.class);
                    if(partido.getEq_local()==Integer.parseInt(idEquipo) || partido.getEq_visitante()==Integer.parseInt(idEquipo)){
                        listaPartido.add(partido);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_partidos, container, false);
    }
}
