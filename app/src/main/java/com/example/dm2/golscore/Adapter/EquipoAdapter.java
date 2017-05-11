package com.example.dm2.golscore.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.DetallesClubActivity;
import com.example.dm2.golscore.LigaActivity;
import com.example.dm2.golscore.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder> {

    private List<Equipo> listaEquipo;
    private Bitmap bitmap;

    public EquipoAdapter(List<Equipo> listaEquipo) {
        this.listaEquipo = listaEquipo;
    }

    @Override
    public EquipoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.equipo_list_item,parent,false);
        EquipoViewHolder holder=new EquipoViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final EquipoViewHolder holder, final int position) {
        final Equipo s= listaEquipo.get(position);
        holder.nombreEquipoTV.setText(s.getNombre());
        //conseguir escudos
        FirebaseStorage storage= FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl(s.getEscudo());
        gsReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imagen.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                // bitmap = BitmapFactory.decodeByteArray(, 0, bytes.length);
            }
        });

        holder.nombreEquipoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetallesClubActivity.class);
                intent.putExtra("nombreEquipo",String.valueOf(s.getNombre()));
                intent.putExtra("idEquipo",String.valueOf(s.getId()));
                v.getContext().startActivity(intent);
            }
        });
    }

    private void conseguirImagen(String nombre) {
        // Create a reference to a file from a Google Cloud Storage URI
        FirebaseStorage storage= FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl(nombre);

       /* try {
            final File localFile = File.createTempFile("images", "jpg");
            gsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}*/

        gsReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use the bytes to display the image
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
               // bitmap = BitmapFactory.decodeByteArray(, 0, bytes.length);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaEquipo.size();
    }

    public static class EquipoViewHolder extends RecyclerView.ViewHolder{

        private TextView nombreEquipoTV;
        private ImageView imagen;

        public EquipoViewHolder(View itemView) {
            super(itemView);
            nombreEquipoTV = (TextView) itemView.findViewById(R.id.nombreEquipoTV);
            imagen=(ImageView) itemView.findViewById(R.id.escudoEquipoIV);
        }
        public void setNombre(String nombre) {
            nombreEquipoTV.setText(nombre);
        }
    }
}
