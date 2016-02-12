package com.example.jhadi.neighborhoodapp.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jhadi.neighborhoodapp.recyclerview.FavoriteRecyclerAdapter;
import com.example.jhadi.neighborhoodapp.database.Moon;
import com.example.jhadi.neighborhoodapp.database.Planet;
import com.example.jhadi.neighborhoodapp.database.PlanetSQLiteHelper;
import com.example.jhadi.neighborhoodapp.R;
import com.example.jhadi.neighborhoodapp.SpaceObject;
import com.example.jhadi.neighborhoodapp.setup.DividerItemDecoration;
import com.example.jhadi.neighborhoodapp.setup.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private PlanetSQLiteHelper mHelper;
    private Moon mMoon;
    private Planet mPlanet;
    private RecyclerView mRecyclerView;
    private FavoriteRecyclerAdapter mAdapter;
    private List<SpaceObject> mSpaceObjectList;
    private int mFavoriteListSize;

    public FavoriteFragment() {
        // Required empty public constructor
    }
//TODO add dividers
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_favorite, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.favorite_recycle_list);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mSpaceObjectList = getFavoriteData();

        //Linear layout for recyclerview
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(48));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),R.drawable.divider));

        mAdapter = new FavoriteRecyclerAdapter(getActivity(), mSpaceObjectList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<SpaceObject> getFavoriteData() {
        mMoon = new Moon(getActivity());
        mPlanet = new Planet(getActivity());

        Cursor moonCursor = mMoon.getFavoriteMoons();
        Cursor planetCursor = mPlanet.getFavoritePlanets();

        List<SpaceObject> spaceObjects = new ArrayList<>();

        while (!moonCursor.isAfterLast()) {
            String moonName = moonCursor.getString(moonCursor.getColumnIndex(PlanetSQLiteHelper.MOON_NAME));
            String moonImage = moonCursor.getString(moonCursor.getColumnIndex(Moon.MOON_IMAGEURL));
            String moonDesc = moonCursor.getString(moonCursor.getColumnIndex(Moon.MOON_DESC));
            double moonFurthestOrbit = moonCursor.getDouble(moonCursor.getColumnIndex(Moon.MOON_APOGEE));
            double moonClosestOrbit = moonCursor.getDouble(moonCursor.getColumnIndex(Moon.MOON_PERIGEE));
            double moonGravity = moonCursor.getDouble(moonCursor.getColumnIndex(Moon.MOON_GRAVITY));
            double moonRadius = moonCursor.getDouble(moonCursor.getColumnIndex(Moon.MOON_RADIUS));
            String moonSatelliteOf = moonCursor.getString(moonCursor.getColumnIndex(Moon.MOON_SATELLITE_OF));
            spaceObjects.add(new SpaceObject(moonName, moonImage, moonDesc, moonFurthestOrbit, moonClosestOrbit, moonRadius, moonGravity, moonSatelliteOf));
            moonCursor.moveToNext();
        }
        while (!planetCursor.isAfterLast()) {
            String planetName = planetCursor.getString(planetCursor.getColumnIndex(PlanetSQLiteHelper.PLANET_NAME));
            String planetImage = planetCursor.getString(planetCursor.getColumnIndex(Planet.PLANET_IMAGE));
            String planetDesc = planetCursor.getString(planetCursor.getColumnIndex(Planet.PLANET_DESC));
            double planetFurthestOrbit = planetCursor.getDouble(planetCursor.getColumnIndex(Planet.PLANET_APHELION));
            double planetClosestOrbit = planetCursor.getDouble(planetCursor.getColumnIndex(Planet.PLANET_PERIHELION));
            double planetGravity = planetCursor.getDouble(planetCursor.getColumnIndex(Planet.PLANET_GRAVITY));
            double planetRadius = planetCursor.getDouble(planetCursor.getColumnIndex(Planet.PLANET_RADIUS));
            String planetOrbitalPeriod = planetCursor.getString(planetCursor.getColumnIndex(Planet.PLANET_ORBIT));
            spaceObjects.add(new SpaceObject(planetName, planetImage, planetDesc, planetFurthestOrbit, planetClosestOrbit, planetRadius, planetGravity, planetOrbitalPeriod));
            planetCursor.moveToNext();
        }

        //Sort all the items alphabetically
        Collections.sort(spaceObjects, new Comparator<SpaceObject>() {
            @Override
            public int compare(SpaceObject lhs, SpaceObject rhs) {
                return lhs.getmName().compareTo(rhs.getmName());
            }
        });
        moonCursor.close();
        planetCursor.close();

        return spaceObjects;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Favorite list", "Favorite fragment view.");
        //mFavoriteListSize =
        if (mFavoriteListSize > mSpaceObjectList.size()) {
            mSpaceObjectList = getFavoriteData();
            mAdapter.notifyDataSetChanged();
        }
    }
}
