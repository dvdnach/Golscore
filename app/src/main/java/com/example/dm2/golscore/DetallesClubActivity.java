package com.example.dm2.golscore;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

public class DetallesClubActivity extends AppCompatActivity {

    private TabLayout informacionTL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_club);

        String nombreEquipo=getIntent().getExtras().getString("nombreEquipo");
        setTitle(nombreEquipo);

        informacionTL=(TabLayout) findViewById(R.id.informacionTL);
        informacionTL.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
