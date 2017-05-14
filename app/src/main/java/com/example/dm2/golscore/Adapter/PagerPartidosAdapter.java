package com.example.dm2.golscore.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.dm2.golscore.Fragment.AdminFragment;
import com.example.dm2.golscore.Fragment.InfoPartidosFragment;
import com.example.dm2.golscore.Fragment.ResumenPartidosFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            // User is signed in
                           AdminFragment adminFragment=new AdminFragment();
                            return  adminFragment;
                        } else {
                            // User is signed out
                            InfoPartidosFragment infoPartidosFragment = new InfoPartidosFragment();
                            return infoPartidosFragment;
                        }

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