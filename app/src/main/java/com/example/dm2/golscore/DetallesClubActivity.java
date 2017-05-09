package com.example.dm2.golscore;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dm2.golscore.Adapter.PagerAdapter;

public class DetallesClubActivity extends AppCompatActivity {

    private TabLayout informacionTL;
    private ViewPager pagerVP;
    private String nombreEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_club);

        nombreEquipo=getIntent().getExtras().getString("nombreEquipo");
        String idEquipo=getIntent().getExtras().getString("idEquipo");
        setTitle(nombreEquipo);

        informacionTL=(TabLayout) findViewById(R.id.informacionTL);
        informacionTL.setTabMode(TabLayout.MODE_SCROLLABLE);

        pagerVP = (ViewPager) findViewById(R.id.pagerVP);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), informacionTL.getTabCount());
        pagerVP.setAdapter(adapter);
        pagerVP.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(informacionTL));
        informacionTL.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerVP.setCurrentItem(tab.getPosition());
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
