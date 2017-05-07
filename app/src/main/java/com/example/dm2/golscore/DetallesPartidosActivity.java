package com.example.dm2.golscore;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.dm2.golscore.Adapter.PagerAdapter;
import com.example.dm2.golscore.Adapter.PagerPartidosAdapter;

public class DetallesPartidosActivity extends AppCompatActivity {

    private TabLayout informacionPartidoTL;
    private ViewPager pagerPartidoVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_partidos);
        String idPartido=getIntent().getExtras().getString("idPartido");
        Log.e("idPartido",idPartido);

        informacionPartidoTL=(TabLayout) findViewById(R.id.informacionPartidoTL);
        informacionPartidoTL.setTabMode(TabLayout.MODE_SCROLLABLE);

        pagerPartidoVP=(ViewPager)findViewById(R.id.pagerPartidoVP);

        PagerPartidosAdapter adapter = new PagerPartidosAdapter(getSupportFragmentManager(), informacionPartidoTL.getTabCount());
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
}
