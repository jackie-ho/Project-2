package com.example.jhadi.neighborhoodapp.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.example.jhadi.neighborhoodapp.database.Moon;
import com.example.jhadi.neighborhoodapp.database.Planet;
import com.example.jhadi.neighborhoodapp.database.PlanetSQLiteHelper;
import com.example.jhadi.neighborhoodapp.R;
import com.example.jhadi.neighborhoodapp.SpaceObject;
import com.example.jhadi.neighborhoodapp.recyclerview.MyRecyclerAdapter;
import com.example.jhadi.neighborhoodapp.setup.VerticalSpaceItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by JHADI on 2/9/16.
 */
public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {
    private PlanetSQLiteHelper mHelper;
    private Moon mMoon;
    private Planet mPlanet;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mAdapter;
    private List<SpaceObject> mSpaceObjectList;
    private int mQueryFlag;
    SearchView searchView;


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_search, container, false);

        mMoon = new Moon(getActivity());
        mPlanet = new Planet(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleListView);
        mQueryFlag = 0;

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));

        mSpaceObjectList = getListItemData();
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(48));

        mAdapter = new MyRecyclerAdapter(getActivity(), mSpaceObjectList);
        mRecyclerView.setAdapter(mAdapter);


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);

        final MenuItem item = menu.findItem(R.id.search_ex);
        searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setIconified(true); //prevents soft keyboard from showing everytime the fragment is changed.
        searchView.setOnQueryTextListener(this);

    }


    @Override
    public boolean onQueryTextChange(String query) {
        if (query != null && !query.isEmpty() || mQueryFlag > 0) {
            List<SpaceObject> filteredSpaceObjectList = filter(mSpaceObjectList, query);
                 mAdapter.animateTo(filteredSpaceObjectList);
                 mRecyclerView.scrollToPosition(0);
                 mQueryFlag += 1; // Prevent method from activating when user first selects searchview.

        }
        return true;
    }

    //Method for going back to the full list of space items
    @Override
    public boolean onQueryTextSubmit(String query) {
        mSpaceObjectList = getListItemData();
        mAdapter.animateTo(mSpaceObjectList);
        mRecyclerView.scrollToPosition(0);
        mQueryFlag = 0;
        return true;
    }


    private List<SpaceObject> filter(List<SpaceObject> origSpaceList, String query) {
        query = query.toLowerCase().trim();
        origSpaceList = getListItemData(); //Inefficient, but too buggy otherwise.

        final List<SpaceObject> filteredSpaceObjectList = new ArrayList<>();
        //Fill list with queried items

        for (SpaceObject spaceObject : origSpaceList) {
            final String name = spaceObject.getmName().toLowerCase();

            //Checking if query term matches the names of each body
            if (name.contains(query)) {
                filteredSpaceObjectList.add(spaceObject);
            }
        }
        if (filteredSpaceObjectList.isEmpty()) {
            for (SpaceObject spaceObject : origSpaceList) {
                final String desc = spaceObject.getDescription().toLowerCase();
                if (desc.contains(query)) {
                    filteredSpaceObjectList.add(spaceObject);
                }
            }
        }

        return filteredSpaceObjectList;
    }

    private List<SpaceObject> getListItemData() {


        mHelper = PlanetSQLiteHelper.getInstance(getActivity());

        Cursor moonCursor = mMoon.getMoons();
        Cursor planetCursor = mPlanet.getPlanets();
        moonCursor.moveToFirst();
        planetCursor.moveToFirst();

        List<SpaceObject> spaceObjects = new ArrayList<SpaceObject>();

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RelativeLayout relativeLayout = (RelativeLayout)getActivity().findViewById(R.id.container);
        relativeLayout.setBackgroundResource(R.drawable.galaxy);
    }
}

