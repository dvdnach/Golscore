package com.example.dm2.golscore.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dm2.golscore.Fragment.InfoPartidosFragment;
import com.example.dm2.golscore.Fragment.ResumenPartidosFragment;

public class PagerPartidosAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerPartidosAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                InfoPartidosFragment infoPartidosFragment = new InfoPartidosFragment();
                return infoPartidosFragment;
            case 1:
                ResumenPartidosFragment resumenPartidosFragment = new ResumenPartidosFragment();
                return resumenPartidosFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}