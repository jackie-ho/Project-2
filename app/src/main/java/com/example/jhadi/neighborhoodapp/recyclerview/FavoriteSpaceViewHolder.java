package com.example.jhadi.neighborhoodapp.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jhadi.neighborhoodapp.R;
import com.example.jhadi.neighborhoodapp.activity.SearchDetailActivity;
import com.example.jhadi.neighborhoodapp.database.Planet;

/**
 * Created by JHADI on 2/10/16.
 */
public class FavoriteSpaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView favoriteImage;
    public TextView favoriteName;
    public TextView favoriteOrbitDistance;
    public TextView favoriteSatelliteOrOrbitalPeriod;
    public TextView favoriteRadius;
    public TextView favoriteGravity;
    private Context context;


    public FavoriteSpaceViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        context = itemView.getContext();
        favoriteImage = (ImageView)itemView.findViewById(R.id.favorite_image);
        favoriteName = (TextView)itemView.findViewById(R.id.favorite_name);
        favoriteOrbitDistance = (TextView)itemView.findViewById(R.id.favorite_detail_text);
        favoriteSatelliteOrOrbitalPeriod = (TextView)itemView.findViewById(R.id.satellite_of_or_orbital_period);
        favoriteRadius = (TextView) itemView.findViewById(R.id.favorite_radius);
        favoriteGravity = (TextView)itemView.findViewById(R.id.favorite_gravity);
    }

    /*
     * On item selected for the favorite list.
     */
    @Override
    public void onClick(View v) {
        Boolean planetFlag;
        Planet planetSearch = new Planet(context);
        String bodyName = favoriteName.getText().toString();
        Log.d("Favorite List", "Onclick " + getLayoutPosition() + "" + bodyName);
        planetFlag = planetSearch.searchPlanetByName(bodyName);

        //Send intent to detail activity view
        Intent intent = new Intent(context, SearchDetailActivity.class);
        intent.putExtra("PLANETBOOLEAN", planetFlag);
        intent.putExtra("BODYNAME", bodyName);
        context.startActivity(intent);
    }
}
