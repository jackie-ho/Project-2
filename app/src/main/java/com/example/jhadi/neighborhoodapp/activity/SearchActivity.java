package com.example.jhadi.neighborhoodapp.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.FragmentManager;
import android.view.inputmethod.InputMethodManager;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.example.jhadi.neighborhoodapp.database.Moon;
import com.example.jhadi.neighborhoodapp.database.Planet;
import com.example.jhadi.neighborhoodapp.database.PlanetSQLiteHelper;
import com.example.jhadi.neighborhoodapp.PagerAdapter;
import com.example.jhadi.neighborhoodapp.R;
import com.example.jhadi.neighborhoodapp.SpaceObject;
import com.example.jhadi.neighborhoodapp.recyclerview.MyRecyclerAdapter;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private PlanetSQLiteHelper mHelper;
    private Moon mMoon;
    private Planet mPlanet;
    private List<SpaceObject> spaceObjects;
    private MyRecyclerAdapter myRecyclerAdapter;
    private RecyclerView recyclerView;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tabs);

        //Search toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        FragmentManager fragmentManager = getSupportFragmentManager();
        PagerAdapter pagerAdapter = new PagerAdapter(fragmentManager);

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
    }

}