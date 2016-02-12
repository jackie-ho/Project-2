package com.example.jhadi.neighborhoodapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.jhadi.neighborhoodapp.fragments.FavoriteFragment;
import com.example.jhadi.neighborhoodapp.fragments.MoonFragment;
import com.example.jhadi.neighborhoodapp.fragments.PlanetFragment;
import com.example.jhadi.neighborhoodapp.fragments.SearchFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JHADI on 2/10/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter{
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public PagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position){
            case 0: frag = new SearchFragment();
                break;
            case 1: frag = new MoonFragment();
                break;
            case 2: frag = new PlanetFragment();
                break;
            case 3: frag = new FavoriteFragment();
                break;
    }
        return frag;
    }

    @Override
    public int getCount() {
        return 4;
    }

    //Add fragment to the tabbed list
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    //Set page title for tabs

    @Override
    public CharSequence getPageTitle(int position) {
        String title = " ";

        switch (position) {
            case 0:
                title = "All";
                break;
            case 1:
                title = "Moons";
                break;
            case 2:
                title = "Planets";
                break;
            case 3:
                title = "Favorites";
                break;
        }
        return title;
    }
}

