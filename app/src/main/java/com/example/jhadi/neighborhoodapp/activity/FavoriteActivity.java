package com.example.jhadi.neighborhoodapp.activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jhadi.neighborhoodapp.database.Moon;
import com.example.jhadi.neighborhoodapp.database.Planet;
import com.example.jhadi.neighborhoodapp.database.PlanetSQLiteHelper;
import com.example.jhadi.neighborhoodapp.R;
import com.example.jhadi.neighborhoodapp.SpaceObject;
import com.example.jhadi.neighborhoodapp.recyclerview.FavoriteRecyclerAdapter;
import com.example.jhadi.neighborhoodapp.setup.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {


//    private PlanetSQLiteHelper mHelper;
//    private RecyclerView mRecyclerView;
//    private FavoriteRecyclerAdapter mFavoriteRecyclerAdapter;
//    private List<SpaceObject> mFavoriteSpaceObjectList;
//    private Moon mMoon;
//    private Planet mPlanet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
/*
* Not in use, activity
 */




//        //Database helper
//        mHelper = PlanetSQLiteHelper.getInstance(FavoriteActivity.this);
//
//        //Initialize recyclerview
//        mRecyclerView = (RecyclerView)findViewById(R.id.favorite_recycle_list);
//        mRecyclerView.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FavoriteActivity.this);
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(48));
//
//        //Fill list with favorited items
//        mFavoriteSpaceObjectList = getFavoriteData();
//
//        //Set adapter to recyclerview
//        mFavoriteRecyclerAdapter = new FavoriteRecyclerAdapter(FavoriteActivity.this, mFavoriteSpaceObjectList);
//        mRecyclerView.setAdapter(mFavoriteRecyclerAdapter);
//
//
//
//    }
//
//
//
//    //Method for finding favorited items
//    private List<SpaceObject> getFavoriteData() {
//        mMoon = new Moon(FavoriteActivity.this);
//        mPlanet = new Planet(FavoriteActivity.this);
//
//        Cursor moonCursor = mMoon.getFavoriteMoons();
//        Cursor planetCursor = mPlanet.getFavoritePlanets();
//
//        List<SpaceObject> spaceObjects = new ArrayList<>();
//
//        while (!moonCursor.isAfterLast()) {
//            String moonName = moonCursor.getString(moonCursor.getColumnIndex(PlanetSQLiteHelper.MOON_NAME));
//            String moonImage = moonCursor.getString(moonCursor.getColumnIndex(Moon.MOON_IMAGEURL));
//            String moonDesc = moonCursor.getString(moonCursor.getColumnIndex(Moon.MOON_DESC));
//            spaceObjects.add(new SpaceObject(moonName, moonImage, moonDesc));
//            moonCursor.moveToNext();
//        }
//        while (!planetCursor.isAfterLast()) {
//            String planetName = planetCursor.getString(planetCursor.getColumnIndex(PlanetSQLiteHelper.PLANET_NAME));
//            String planetImage = planetCursor.getString(planetCursor.getColumnIndex(Planet.PLANET_IMAGE));
//            String planetDesc = planetCursor.getString(planetCursor.getColumnIndex(Planet.PLANET_DESC));
//            spaceObjects.add(new SpaceObject(planetName, planetImage, planetDesc));
//            planetCursor.moveToNext();
//        }
//
//        //Sort all the items alphabetically
//        Collections.sort(spaceObjects, new Comparator<SpaceObject>() {
//            @Override
//            public int compare(SpaceObject lhs, SpaceObject rhs) {
//                return lhs.getmName().compareTo(rhs.getmName());
//            }
//        });
//        moonCursor.close();
//        planetCursor.close();
//
//        return spaceObjects;
//    }

    }}

