package com.example.dm2.golscore;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dm2.golscore.Adapter.PagerPartidosAdapter;
import com.example.dm2.golscore.Clases.Equipo;
import com.example.dm2.golscore.Clases.Partido;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetallesPartidosActivity extends AppCompatActivity {

    private TabLayout informacionPartidoTL;
    private ViewPager pagerPartidoVP;
    private TextView nombreLocalDetallesTV,estadoPartidoTV,golesLocalDetallesTV,separdorDetallesTV,golesVisitanteDetallesTV,fechaDetallesTV,nombreVisitanteDetallesTV;
    private ImageView escudoVisitanteDetallesIV,escudoLocalDetallesIV;
    int color,colorVerde;
    private int idEquipoLocal;
    private int idEquipoVisitante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_partidos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String idPartido=getIntent().getExtras().getString("idPartido");

        nombreLocalDetallesTV=(TextView) findViewById(R.id.nombreLocalDetallesTV);
        estadoPartidoTV=(TextView) findViewById(R.id.estadoPartidoTV);
        golesLocalDetallesTV=(TextView) findViewById(R.id.golesLocalDetallesTV);
        separdorDetallesTV=(TextView) findViewById(R.id.separdorDetallesTV);
        golesVisitanteDetallesTV=(TextView) findViewById(R.id.golesVisitanteDetallesTV);
        fechaDetallesTV=(TextView) findViewById(R.id.fechaDetallesTV);
        nombreVisitanteDetallesTV=(TextView) findViewById(R.id.nombreVisitanteDetallesTV);
        escudoLocalDetallesIV=(ImageView) findViewById(R.id.escudoLocalDetallesIV);
        escudoVisitanteDetallesIV=(ImageView) findViewById(R.id.escudoVisitanteDetallesIV);

        color=getResources().getColor(R.color.colorRed);
        colorVerde=getResources().getColor(R.color.colorVerde);

        FirebaseDatabase dbPartido=FirebaseDatabase.getInstance();
        dbPartido.getReference().child("Partido").addValueEventListener(new ValueEventListener() {
            int idClub, idNotificacion = 0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Partido partido=snapshot.getValue(Partido.class);
                    if(partido.getId()==Integer.parseInt(idPartido)){
                        idEquipoLocal=partido.getEq_local();
                        idEquipoVisitante=partido.getEq_visitante();
                        golesLocalDetallesTV.setText(Integer.toString(partido.getGol_local()));
                        golesVisitanteDetallesTV.setText(Integer.toString(partido.getGol_visitante()));
                        fechaDetallesTV.setText(partido.getFecha()+" - "+partido.getHora());
                        if (idNotificacion>0){
                            String strTittle = "GOL";
                            String strMarcador = nombreLocalDetallesTV.getText()+" "+Integer.toString(partido.getGol_local())+" - "+Integer.toString(partido.getGol_visitante())+" "+nombreVisitanteDetallesTV.getText();
                            sendNotification(strTittle, strMarcador);
                        }
                        idNotificacion ++;
                        //Calcular el estado del partido
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
                                estadoPartidoTV.setText("En juego");
                                estadoPartidoTV.setTextColor(colorVerde);
                            } else if (dateHoy.after(datePartido)) {
                                estadoPartidoTV.setText("Finalizado");
                                estadoPartidoTV.setTextColor(color);
                            }  else{
                                estadoPartidoTV.setText("Sin comenzar");
                                golesLocalDetallesTV.setText(partido.getHora());
                                separdorDetallesTV.setVisibility(View.GONE);
                                golesVisitanteDetallesTV.setVisibility(View.GONE);
                                fechaDetallesTV.setText(partido.getFecha());
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

                FirebaseDatabase dbClub=FirebaseDatabase.getInstance();
                dbClub.getReference().child("Equipo").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                            Equipo equipo=snapshot.getValue(Equipo.class);
                            if(equipo.getId()==idEquipoLocal){
                                nombreLocalDetallesTV.setText(equipo.getNombre());
                                idClub=equipo.getId_club();
                                Glide.with(getApplicationContext()).load(equipo.getEscudo()).into(escudoLocalDetallesIV);
                            }
                            if(equipo.getId()==idEquipoVisitante){
                                nombreVisitanteDetallesTV.setText(equipo.getNombre());
                                Glide.with(getApplicationContext()).load(equipo.getEscudo()).into(escudoVisitanteDetallesIV);
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

        informacionPartidoTL=(TabLayout) findViewById(R.id.informacionPartidoTL);
        informacionPartidoTL.setTabMode(TabLayout.MODE_FIXED);
        pagerPartidoVP=(ViewPager)findViewById(R.id.pagerPartidoVP);
        PagerPartidosAdapter adapter = new PagerPartidosAdapter(getSupportFragmentManager(),
                informacionPartidoTL.getTabCount());
        pagerPartidoVP.setAdapter(adapter);
        pagerPartidoVP.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(informacionPartidoTL));
        informacionPartidoTL.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerPartidoVP.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void sendNotification(String messageTitle, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.balon);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setLargeIcon(bm);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
