
package com.example.jhadi.neighborhoodapp.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jhadi.neighborhoodapp.database.Planet;
import com.example.jhadi.neighborhoodapp.R;
import com.example.jhadi.neighborhoodapp.activity.SearchDetailActivity;

/**
 * Created by JHADI on 2/8/16.
 */
public class SpaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView mImageView;
    public TextView mName;
    public TextView textView1;
    public TextView distanceTextView;
    private Context context;

    public SpaceViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        context = itemView.getContext();
        mImageView = (ImageView) itemView.findViewById(R.id.list_image);
        mName = (TextView) itemView.findViewById(R.id.body_name);
        textView1 = (TextView) itemView.findViewById(R.id.detail_text);
        distanceTextView = (TextView) itemView.findViewById(R.id.distance_from);

    }

    @Override
    public void onClick(View v) {

        //Check whether the click is on a planet or on a moon
        Boolean planetFlag;
        Planet planetSearch = new Planet(context);
        String bodyName = mName.getText().toString();
        Log.d("RecyclerView", "Onclick " + getLayoutPosition() + "" + bodyName);
        planetFlag = planetSearch.searchPlanetByName(bodyName);

        //Send intent to detail activity view
        Intent intent = new Intent(context, SearchDetailActivity.class);
        intent.putExtra("PLANETBOOLEAN", planetFlag);
        intent.putExtra("BODYNAME", bodyName);
        context.startActivity(intent);

    }
}