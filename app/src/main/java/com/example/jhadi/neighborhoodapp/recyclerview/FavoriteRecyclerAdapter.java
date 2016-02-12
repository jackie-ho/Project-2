package com.example.jhadi.neighborhoodapp.recyclerview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jhadi.neighborhoodapp.R;
import com.example.jhadi.neighborhoodapp.SpaceObject;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by JHADI on 2/10/16.
 */
public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteSpaceViewHolder> {
    private Context context;
    private List<SpaceObject> spaceObjectList;
    Typeface mFont;


    public FavoriteRecyclerAdapter(Context context, List<SpaceObject> spaceObjects) {
        this.context = context;
        spaceObjectList = spaceObjects;
        mFont = Typeface.createFromAsset(context.getAssets(), "fonts/Halo3.ttf");
    }

    @Override
    public FavoriteSpaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_favorite, null);
        FavoriteSpaceViewHolder fsvh = new FavoriteSpaceViewHolder(layoutView);
        return fsvh;
    }

    @Override
    public void onBindViewHolder(FavoriteSpaceViewHolder holder, int position) {


        holder.favoriteName.setText(spaceObjectList.get(position).getmName());
        holder.favoriteName.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Halo3.ttf"));
        holder.favoriteGravity.setText("Surface gravity: " + spaceObjectList.get(position).getmGravity() + " m/s^2");
        overrideFonts(context, holder.favoriteGravity);
        holder.favoriteRadius.setText("Radius: " + spaceObjectList.get(position).getmRadius() + " km");
        overrideFonts(context, holder.favoriteRadius);
        //Detect whether it is planet or moon then display appropriate property
        boolean planetOrMoon;
        try {
            double orbitalPeriod = Double.parseDouble(spaceObjectList.get(position).getmSatelliteOf_OrbitalPeriod());
            holder.favoriteSatelliteOrOrbitalPeriod.setText("Orbital period: " + orbitalPeriod + " days");
            planetOrMoon = true;
        } catch (NumberFormatException e) {
            holder.favoriteSatelliteOrOrbitalPeriod.setText("Satellite of: " + spaceObjectList.get(position).getmSatelliteOf_OrbitalPeriod());
            planetOrMoon = false;
        }
        overrideFonts(context, holder.favoriteSatelliteOrOrbitalPeriod);
        //Display appropriate text depending on whether it is planet or moon
        if (planetOrMoon) {
            holder.favoriteOrbitDistance.setText("Distance from sun: " + spaceObjectList.get(position).getmRadius() + " AU");
        } else {
            holder.favoriteOrbitDistance.setText("Orbit distance from planet: " + (int) spaceObjectList.get(position).getmRadius() + " km");
        }
        overrideFonts(context, holder.favoriteOrbitDistance);

        String url = spaceObjectList.get(position).getmImageUrl();
        //Image loader
        Picasso.with(context).load(url).fit().into(holder.favoriteImage);
    }

    @Override
    public int getItemCount() {
        return this.spaceObjectList.size();
    }
//Change fonts to custom font
    private void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Marske.ttf"));
            }
        } catch (Exception e) {
        }
    }
}
