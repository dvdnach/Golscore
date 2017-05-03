package com.example.dm2.golscore;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

public class DetallesClubActivity extends AppCompatActivity {

    private TabHost tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_club);

        tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("info");
        spec.setContent(R.id.tabInfo);
        spec.setIndicator("INFO");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("plantilla");
        spec.setContent(R.id.tabPlantilla);
        spec.setIndicator("PLANTILLA");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("clasificación");
        spec.setContent(R.id.tabClasificación);
        spec.setIndicator("CLASIFICACIÓN");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("partidos");
        spec.setContent(R.id.tabPartidos);
        spec.setIndicator("PARTIDOS");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("estadisticas");
        spec.setContent(R.id.tabEstadisticas);
        spec.setIndicator("ESTADÍSTICAS");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);
    }
}
