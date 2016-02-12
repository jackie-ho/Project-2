package com.example.jhadi.neighborhoodapp.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jhadi.neighborhoodapp.database.Comment;
import com.example.jhadi.neighborhoodapp.database.Moon;
import com.example.jhadi.neighborhoodapp.database.Planet;
import com.example.jhadi.neighborhoodapp.database.PlanetSQLiteHelper;
import com.example.jhadi.neighborhoodapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class SearchDetailActivity extends AppCompatActivity {

    private String mBodyName;
    private PlanetSQLiteHelper mHelper;
    private boolean mPlanetOrMoon; // true for planet, false for moon
    EditText mCommentAuthor;
    EditText mComment;
    Comment comment;
    SimpleCursorAdapter mSimpleCursorAdapter;
    TextView commentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.DetailStyle);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        //Add back function to action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView bodyName = (TextView) findViewById(R.id.space_body_name);
        TextView bodyApogee = (TextView) findViewById(R.id.body_furthest_orbit);
        TextView bodyPerigee = (TextView) findViewById(R.id.body_closest_orbit);
        TextView bodyRadius = (TextView) findViewById(R.id.body_radius);
        TextView bodyDesc = (TextView) findViewById(R.id.body_description_content);
        TextView bodyDensity = (TextView) findViewById(R.id.body_density);
        TextView bodyOrbit = (TextView) findViewById(R.id.body_orbital_period_or_satellite);
        TextView bodyGravity = (TextView) findViewById(R.id.body_gravity);
        TextView bodyDescText = (TextView) findViewById(R.id.body_description);
        ImageView bodyImage = (ImageView)findViewById(R.id.detail_image);
        ListView commentListView = (ListView)findViewById(R.id.comment_list);
        commentCount = (TextView) findViewById(R.id.number_of_comments);
        mComment = (EditText) findViewById(R.id.comment_edit);
        Button commentButton = (Button) findViewById(R.id.submit_comment);
        mCommentAuthor = (EditText) findViewById(R.id.comment_author);

        mBodyName = getIntent().getStringExtra("BODYNAME");
        mPlanetOrMoon = getIntent().getBooleanExtra("PLANETBOOLEAN", false);

        setTitle("Detail on "+mBodyName);


        //Declare moon, planet objects
        Planet planet = new Planet(SearchDetailActivity.this);
        Moon moon = new Moon(SearchDetailActivity.this);

        //Onclick listener for comment submit button
        commentButton.setOnClickListener(submitComment);

        //Set up comment list
        comment = new Comment(SearchDetailActivity.this);
        Cursor cursor = comment.getComments(mBodyName);
        if (cursor != null) {
            String[] commentColumns = new String[]{Comment.COMMENT_AUTHOR, Comment.COMMENT_COMMENT};
            int[] commentIds = new int[]{R.id.author_name, R.id.comment_comment};
            commentCount.setText("There are "+ cursor.getCount() +" messages.");
            mSimpleCursorAdapter = new SimpleCursorAdapter(SearchDetailActivity.this, R.layout.comment_layout, cursor, commentColumns, commentIds, 0);
            commentListView.setAdapter(mSimpleCursorAdapter);

        }
        //Changes textviews based off whether it is a planet or moon
        if (mPlanetOrMoon) {

            Cursor planetCursor = planet.getPlanets();
            while (!mBodyName.equals(planetCursor.getString(planetCursor.getColumnIndex(Planet.PLANET_NAME)))) {
                planetCursor.moveToNext();
            }
            double planetAphelion = planetCursor.getDouble(planetCursor.getColumnIndex(Planet.PLANET_APHELION));
            double planetPerihelion = planetCursor.getDouble(planetCursor.getColumnIndex(Planet.PLANET_PERIHELION));
            String planetRadius = planetCursor.getString(planetCursor.getColumnIndex(Planet.PLANET_RADIUS));
            String planetDescription = planetCursor.getString(planetCursor.getColumnIndex(Planet.PLANET_DESC));
            String planetOrbit = planetCursor.getString(planetCursor.getColumnIndex(Planet.PLANET_ORBIT));
            double planetGravity = planetCursor.getDouble(planetCursor.getColumnIndex(Planet.PLANET_GRAVITY));
            double planetDensity = planetCursor.getDouble(planetCursor.getColumnIndex(Planet.PLANET_DENSITY));
            String planetName = planetCursor.getString(planetCursor.getColumnIndex(Planet.PLANET_NAME));
            String planetImageUrl = planetCursor.getString(planetCursor.getColumnIndex(Planet.PLANET_IMAGE));

            bodyName.setText("" + planetName);
            bodyApogee.setText("Aphelion    " + planetAphelion + "AU");
            bodyPerigee.setText("Perihelion  " + planetPerihelion + "AU");
            bodyRadius.setText("Radius: " + planetRadius + " km");
            bodyDesc.setText(planetDescription);
            bodyOrbit.setText("Orbital period: " + planetOrbit + " days");
            bodyGravity.setText("Surface gravity: " + planetGravity + " m/s^2");
            bodyDensity.setText("Density: " + planetDensity + "g/cm^3");
            Picasso.with(SearchDetailActivity.this).load(planetImageUrl).fit().into(bodyImage);

            planetCursor.close();

        } else {
            Cursor moonCursor = moon.getMoons();
            while (!mBodyName.equals(moonCursor.getString(moonCursor.getColumnIndex(Moon.MOON_NAME)))) {
                moonCursor.moveToNext();
            }
            int moonApogee = moonCursor.getInt(moonCursor.getColumnIndex(Moon.MOON_APOGEE));
            int moonPerigee = moonCursor.getInt(moonCursor.getColumnIndex(Moon.MOON_PERIGEE));
            String moonSatelliteOf = moonCursor.getString(moonCursor.getColumnIndex(Moon.MOON_SATELLITE_OF));
            String moonDescription = moonCursor.getString(moonCursor.getColumnIndex(Moon.MOON_DESC));
            String moonRadius = moonCursor.getString(moonCursor.getColumnIndex(Moon.MOON_RADIUS));
            String moonName = moonCursor.getString(moonCursor.getColumnIndex(Moon.MOON_NAME));
            double moonGravity = moonCursor.getDouble(moonCursor.getColumnIndex(Moon.MOON_GRAVITY));
            String moonImage = moonCursor.getString(moonCursor.getColumnIndex(Moon.MOON_IMAGEURL));

            bodyName.setText("" + moonName);
            if (moonApogee == moonPerigee) {
                bodyApogee.setText("Semi-Major Axis: " + moonApogee + " km");
                bodyPerigee.setText("");
            } else {
                bodyApogee.setText("Apogee:          " + moonApogee + " km");
                bodyPerigee.setText("Perigee:        " + moonPerigee + " km");
            }
            bodyDesc.setText(moonDescription);
            bodyRadius.setText("Radius: " + moonRadius + " km");
            bodyGravity.setText("Gravity: " + moonGravity + "m/s^2");
            bodyOrbit.setText("Satellite of: " + moonSatelliteOf);
            bodyDensity.setText("");
            Picasso.with(SearchDetailActivity.this).load(moonImage).fit().into(bodyImage);
            moonCursor.close();
        }

        //Change fonts
        overrideFonts(SearchDetailActivity.this, bodyApogee);
        overrideFonts(SearchDetailActivity.this, bodyDensity);
        overrideFonts(SearchDetailActivity.this, bodyDesc);
        overrideFonts(SearchDetailActivity.this, bodyOrbit);
        overrideFonts(SearchDetailActivity.this, bodyPerigee);
        overrideFonts(SearchDetailActivity.this, bodyRadius);
        overrideFonts(SearchDetailActivity.this, bodyDensity);
        overrideFonts(SearchDetailActivity.this, bodyGravity);
        bodyName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/xirod.ttf"));
        bodyDescText.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/xirod.ttf"));
    }
    //Method for changing fonts
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        Add planets or moons to favorites
         */

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.set_a_base:
                if (mPlanetOrMoon){
                    Planet planet = new Planet(SearchDetailActivity.this);
                    planet.addFavoritePlanet(mBodyName); //using name as key
                } else {
                    Moon moon = new Moon(SearchDetailActivity.this);
                    moon.addtoFavorite(mBodyName);
                }
            default: return false;
        }
    }

    View.OnClickListener submitComment = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String author = mCommentAuthor.getText().toString();
            String commentContent = mComment.getText().toString();
            mComment.setText("");
            Comment com = new Comment(SearchDetailActivity.this);
            //Insert into database
            com.addComment(mBodyName, author, commentContent);
            //Updated comment listview
            Toast.makeText(SearchDetailActivity.this, "Comment beamed.", Toast.LENGTH_SHORT).show();
            Cursor updatedCursor = com.getComments(mBodyName);
            commentCount.setText("There are "+ updatedCursor.getCount() +" messages.");
            mSimpleCursorAdapter.swapCursor(updatedCursor);

        }
    };

}

