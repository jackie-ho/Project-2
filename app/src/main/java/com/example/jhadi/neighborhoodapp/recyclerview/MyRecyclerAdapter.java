package com.example.jhadi.neighborhoodapp.recyclerview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jhadi.neighborhoodapp.R;
import com.example.jhadi.neighborhoodapp.SpaceObject;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JHADI on 2/8/16.
 */

//Adapter for recyclerview

public class MyRecyclerAdapter extends RecyclerView.Adapter<SpaceViewHolder> {
    private Context context;
    private List<SpaceObject> spaceObjectList;
    private List<SpaceObject> mFilteredList;
    private Typeface mFont;


    public MyRecyclerAdapter(Context context, List<SpaceObject> spaceObjects) {
        this.context = context;
        spaceObjectList = spaceObjects;
        mFont = Typeface.createFromAsset(context.getAssets(), "fonts/Marske.ttf");

    }

    @Override
    public SpaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//Inflate the recyclerview with the custom layout
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_layout, null);
        SpaceViewHolder scv = new SpaceViewHolder(layoutView);
        return scv;
    }

    @Override
    public void onBindViewHolder(SpaceViewHolder holder, int position) {
        //Set all the views with info
        holder.mName.setText(spaceObjectList.get(position).getmName());
        holder.mName.setTypeface(mFont);
        double furthestOrbit = spaceObjectList.get(position).getmFurthestOrbit();
        double closestOrbit = spaceObjectList.get(position).getmClosestOrbit();
        double average = (furthestOrbit+closestOrbit)/2;
        String moonOrPlanet = spaceObjectList.get(position).getmSatelliteOf_OrbitalPeriod();
        String url = spaceObjectList.get(position).getmImageUrl();
        if (moonOrPlanet.contains(".") || moonOrPlanet.contains(",")) {
            holder.distanceTextView.setText("Distance from sun: " + String.format("%.2f",average) + " AU");
            holder.textView1.setText("Orbital period: "+moonOrPlanet + " days");
        } else {
            holder.distanceTextView.setText("Distance from "+moonOrPlanet+": "+ average + " km");
            holder.textView1.setText("Radius: "+ spaceObjectList.get(position).getmRadius() + " km");
        }
        holder.distanceTextView.setTypeface(mFont);
        holder.textView1.setTypeface(mFont);
        //Picasso library performs much better with RecyclerView
        Picasso.with(context).load(url).fit().into(holder.mImageView);
    }

    //Get count of items
    @Override
    public int getItemCount() {
        return this.spaceObjectList.size();

    }

    //Methods for animating removing and adding items
    public SpaceObject removeItem(int position) {
        final SpaceObject spaceObject = spaceObjectList.remove(position);
        notifyItemRemoved(position);
        return spaceObject;
    }

    public void addItem(int position, SpaceObject spaceObject) {
        spaceObjectList.add(position, spaceObject);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final SpaceObject model = spaceObjectList.remove(fromPosition);
        spaceObjectList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void animateTo(List<SpaceObject> spaceObjects) {
        applyAndAnimateRemovals(spaceObjects);
        applyAndAnimateAdditions(spaceObjects);
        applyAndAnimateMovedItems(spaceObjects);
    }

    private void applyAndAnimateRemovals(List<SpaceObject> newSpaceObjects) {
        for (int i = spaceObjectList.size() - 1; i >= 0; i--) {
            final SpaceObject spaceObject = spaceObjectList.get(i);
            if (!newSpaceObjects.contains(spaceObject)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<SpaceObject> newSpaceObjects) {
        for (int i = 0, count = newSpaceObjects.size(); i < count; i++) {
            final SpaceObject spaceObject = newSpaceObjects.get(i);
            if (!spaceObjectList.contains(spaceObject)) {
                addItem(i, spaceObject);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<SpaceObject> newSpaceObjects) {
        for (int toPosition = newSpaceObjects.size() - 1; toPosition >= 0; toPosition--) {
            final SpaceObject spaceObject = newSpaceObjects.get(toPosition);
            final int fromPosition = spaceObjectList.indexOf(spaceObject);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

}

