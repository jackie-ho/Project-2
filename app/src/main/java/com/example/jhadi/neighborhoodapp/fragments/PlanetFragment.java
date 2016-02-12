package com.example.jhadi.neighborhoodapp.fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
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

import com.example.jhadi.neighborhoodapp.recyclerview.MyRecyclerAdapter;
import com.example.jhadi.neighborhoodapp.database.Planet;
import com.example.jhadi.neighborhoodapp.database.PlanetSQLiteHelper;
import com.example.jhadi.neighborhoodapp.R;
import com.example.jhadi.neighborhoodapp.SpaceObject;
import com.example.jhadi.neighborhoodapp.setup.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanetFragment extends Fragment implements SearchView.OnQueryTextListener {
    private PlanetSQLiteHelper mHelper;
    private Planet mPlanet;
    private RecyclerView mPlanetRecyclerView;
    private MyRecyclerAdapter mPlanetAdapter;
    private List<SpaceObject> mPlanetObjectList;
    private int mQueryFlag;

    public PlanetFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate recycler view
        final View view = inflater.inflate(R.layout.activity_search, container, false);
        mPlanetRecyclerView = (RecyclerView) view.findViewById(R.id.recycleListView);
        mQueryFlag = 0;

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Keep searchbar on action bar
        setHasOptionsMenu(true);

        //Layout manager for reyclerview
        mPlanetRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));

        mPlanetObjectList = getPlanetData();
        mPlanetRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(48));

        mPlanetAdapter = new MyRecyclerAdapter(getActivity(), mPlanetObjectList);
        mPlanetRecyclerView.setAdapter(mPlanetAdapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);

        //Define searchview in the menu
        final MenuItem item = menu.findItem(R.id.search_ex);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setIconified(true); //prevents soft keyboard from showing every time the fragment is changed.
        //add query abilities on search
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query != null && !query.isEmpty() || mQueryFlag > 0) {
            final List<SpaceObject> filteredSpaceObjectList = filter(mPlanetObjectList, query);
            mPlanetAdapter.animateTo(filteredSpaceObjectList);
            mPlanetRecyclerView.scrollToPosition(0);
            mQueryFlag +=1; // Prevent method from activating when user first selects searchview.
        }
        return true;
    }

    //Method for going back to the full list of space items
    @Override
    public boolean onQueryTextSubmit(String query) {
        mPlanetObjectList = getPlanetData();
        mPlanetAdapter.animateTo(mPlanetObjectList);
        mPlanetRecyclerView.scrollToPosition(0);
        mQueryFlag = 0;
        return true;
    }

    private List<SpaceObject> filter(List<SpaceObject> origSpaceList, String query) {
        query = query.toLowerCase();

        final List<SpaceObject> filteredSpaceList = new ArrayList<>();
        origSpaceList = getPlanetData(); //Inefficient but too buggy otherwise


        //Fill list with queried items
        for (SpaceObject spaceObject : origSpaceList) {
            final String name = spaceObject.getmName().toLowerCase();


            if (name.contains(query)) {
                filteredSpaceList.add(spaceObject);
            }
        }
        //Checking description for query terms
        if (filteredSpaceList.isEmpty()) {
            for (SpaceObject spaceObject : origSpaceList) {
                final String desc = spaceObject.getDescription().toLowerCase();
                if (desc.contains(query)) {
                    filteredSpaceList.add(spaceObject);
                }
            }
        }
        return filteredSpaceList;
    }

    private List<SpaceObject> getPlanetData() {

        mPlanet = new Planet(getActivity());

        Cursor planetCursor = mPlanet.getPlanets();
        planetCursor.moveToFirst();

        List<SpaceObject> spaceObjects = new ArrayList<SpaceObject>();

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
        planetCursor.close();

        return spaceObjects;
    }
}
