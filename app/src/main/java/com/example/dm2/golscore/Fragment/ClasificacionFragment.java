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

import com.example.dm2.golscore.Adapter.ClasificacionAdapter;
import com.example.dm2.golscore.Adapter.PartidoAdapter;
import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.Clases.Partido;
import com.example.dm2.golscore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClasificacionFragment extends Fragment {

    private RecyclerView clasificacionEquipoRV;
    private List<Equipo> listaClasificacion;
    private ClasificacionAdapter adapter;

    public ClasificacionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DatabaseReference dbClub= FirebaseDatabase.getInstance().getReference().child("Equipo");
        clasificacionEquipoRV=(RecyclerView)getView().findViewById(R.id.clasificacionEquipoRV);
        LinearLayoutManager llm=new LinearLayoutManager(getContext());
        llm.setReverseLayout(true);
        clasificacionEquipoRV.setLayoutManager(llm);

        listaClasificacion=new ArrayList<Equipo>();
        adapter= new ClasificacionAdapter(listaClasificacion);

        clasificacionEquipoRV.setAdapter(adapter);

        final String idGrupo=getActivity().getIntent().getExtras().getString("idGrupo");

        dbClub.orderByChild("puntos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaClasificacion.removeAll(listaClasificacion);
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Equipo equipo=snapshot.getValue(Equipo.class);
                    if(equipo.getGrupo()==Integer.parseInt(idGrupo)){
                        listaClasificacion.add(equipo);
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
        return inflater.inflate(R.layout.fragment_clasificacion, container, false);
    }
}
