package com.example.dm2.golscore.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dm2.golscore.Clases.Club;
import com.example.dm2.golscore.DetallesClubActivity;
import com.example.dm2.golscore.R;

import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ClubViewHolder> {

    private List<Club> listaClub;

    public ClubAdapter(List<Club> listaClub) {
        this.listaClub = listaClub;
    }

    @Override
    public ClubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.club_list_item,parent,false);
        ClubViewHolder holder=new ClubViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final ClubViewHolder holder, final int position) {
        final Club club=listaClub.get(position);
        holder.nombreClubTV.setText(club.getNombre());
        holder.nombreClubTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetallesClubActivity.class);
                intent.putExtra("idClub",String.valueOf(club.getId()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaClub.size();
    }

    public static class ClubViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreClubTV;

        public ClubViewHolder(View itemView) {
            super(itemView);
            nombreClubTV = (TextView) itemView.findViewById(R.id.nombreClubTV);
        }

        public void setNombre(String nombre) {
            nombreClubTV.setText(nombre);
        }
    }
}
