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

import com.example.jhadi.neighborhoodapp.database.Moon;
import com.example.jhadi.neighborhoodapp.recyclerview.MyRecyclerAdapter;
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


public class MoonFragment extends Fragment implements SearchView.OnQueryTextListener{

    private PlanetSQLiteHelper mHelper;
    private Moon mMoon;
    private RecyclerView mMoonRecyclerView;
    private MyRecyclerAdapter mMoonAdapter;
    private List<SpaceObject> mMoonObjectList;
    private int mQueryFlag;


    public MoonFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_search, container, false);
        mMoonRecyclerView = (RecyclerView) view.findViewById(R.id.recycleListView);

        mQueryFlag = 0;
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mMoonRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));

        mMoonObjectList = getMoonData();
        mMoonRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(48));

        mMoonAdapter = new MyRecyclerAdapter(getActivity(), mMoonObjectList);
        mMoonRecyclerView.setAdapter(mMoonAdapter);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);

        final MenuItem item = menu.findItem(R.id.search_ex);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setIconified(true); //prevents soft keyboard from showing everytime the fragment is changed.
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query != null && !query.isEmpty() || mQueryFlag > 0) {
            final List<SpaceObject> filteredSpaceObjectList = filter(mMoonObjectList, query);
            mMoonAdapter.animateTo(filteredSpaceObjectList);
            mMoonRecyclerView.scrollToPosition(0);
            mQueryFlag +=1; // Prevent method from activating when user first selects searchview.
        }
        return true;
    }
    //Method for going back to the full list of space items
    @Override
    public boolean onQueryTextSubmit(String query) {
        mMoonObjectList = getMoonData();
        mMoonAdapter.animateTo(mMoonObjectList);
        mMoonRecyclerView.scrollToPosition(0);
        mQueryFlag = 0;
        return true;
    }
    private List<SpaceObject> filter(List<SpaceObject> origSpaceList, String query) {
        query = query.toLowerCase();

        final List<SpaceObject> filteredSpaceList = new ArrayList<>();
        origSpaceList = getMoonData(); //Inefficient but too buggy otherwise
        //Fill list with queried items
        for (SpaceObject spaceObject : origSpaceList) {
            final String name = spaceObject.getmName().toLowerCase();
            final String desc = spaceObject.getDescription().toLowerCase();

            if (name.contains(query) ) {
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
    private List<SpaceObject> getMoonData() {

        mMoon = new Moon(getActivity());
        mHelper = PlanetSQLiteHelper.getInstance(getActivity());

        Cursor moonCursor = mMoon.getMoons();
        moonCursor.moveToFirst();

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

        //Sort all the items alphabetically
        Collections.sort(spaceObjects, new Comparator<SpaceObject>() {
            @Override
            public int compare(SpaceObject lhs, SpaceObject rhs) {
                return lhs.getmName().compareTo(rhs.getmName());
            }
        });
        moonCursor.close();

        return spaceObjects;
    }


}
