package com.example.dm2.golscore.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dm2.golscore.Fragment.ClasificacionFragment;
import com.example.dm2.golscore.Fragment.InfoFragment;
import com.example.dm2.golscore.Fragment.PartidosFragment;
import com.example.dm2.golscore.Fragment.PlantillaFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                InfoFragment infoFragment = new InfoFragment();
                return infoFragment;
            case 1:
                PlantillaFragment plantillaFragment = new PlantillaFragment();
                return plantillaFragment;
            case 2:
                ClasificacionFragment clasificacionFragment = new ClasificacionFragment();
                return clasificacionFragment;
            case 3:
                PartidosFragment partidosFragment = new PartidosFragment();
                return partidosFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}