package com.example.jhadi.neighborhoodapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by JHADI on 2/7/16.
 */
public class Moon {

    PlanetSQLiteHelper mHelper;
    SQLiteDatabase mDatabase;

    private static final String MOON_TABLE_NAME = "MOONS";
    public static final String MOON_ID = "_id";
    public static final String MOON_NAME = "NAME";
    public static final String MOON_APOGEE = "APOGEE";
    public static final String MOON_PERIGEE = "PERIGEE";
    public static final String MOON_DESC = "DESCRIPTION";
    public static final String MOON_SATELLITE_OF = "SATELLITE_OF";
    public static final String MOON_GRAVITY = "GRAVITY";
    public static final String MOON_RADIUS = "RADIUS";
    public static final String MOON_FAVORITE = "FAVORITE";
    public static final String MOON_IMAGEURL = "IMAGE";
    private static final String[] MOON_COLUMNS = {MOON_ID, MOON_NAME, MOON_DESC, MOON_APOGEE, MOON_PERIGEE, MOON_SATELLITE_OF, MOON_RADIUS,MOON_GRAVITY,MOON_FAVORITE, MOON_IMAGEURL};

    private Context context;

    public Moon(Context context) {
        mHelper = PlanetSQLiteHelper.getInstance(context);
        mDatabase = mHelper.getWritableDatabase();
        this.context = context;
    }
///Return names and urls of the moons.
    public Cursor getAllMoonsByNameAndImage(){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query("MOONS", new String[]{MOON_NAME, MOON_DESC, MOON_IMAGEURL}, null, null, null, null, null);
        return cursor;
    }

    //Not necessary
    public void deleteMoon (String moon){

        mDatabase.delete("MOONS", "NAME LIKE ?" , new String[]{ moon });
    }

    //Not necessary
    public void createMoon (String name, String desc, int apogee, int perigee, String satellite, String radius, double gravity){
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("DESCRIPTION", desc);
        values.put("APOGEE", apogee);
        values.put("PERIGEE", perigee);
        values.put("SATELLITE_OF", satellite);
        values.put("RADIUS",radius);
        values.put("GRAVITY",gravity);
        values.put("FAVORITE",0);
        values.put("IMAGEURL", "");
        mDatabase.insert("MOONS", null, values);
    }

    public String getName(){
        return MOON_NAME;
    }

    //Get all the moons
    public Cursor getMoons(){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(MOON_TABLE_NAME, MOON_COLUMNS, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }
    //Display favorited moons
    public Cursor getFavoriteMoons(){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(MOON_TABLE_NAME, MOON_COLUMNS, MOON_FAVORITE+ " = ?", new String[]{"1"},null,null,null);
        cursor.moveToFirst();
        return cursor;
    }
    //Add moon to favorite list
    public void addtoFavorite(String moon){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(MOON_TABLE_NAME, new String[]{MOON_FAVORITE}, MOON_NAME+" = ?", new String[]{moon},null,null,null);
        cursor.moveToFirst();
        if (cursor.getInt(cursor.getColumnIndex(MOON_FAVORITE)) == 1){
            Toast.makeText(context, "Outpost has already been established at this location.", Toast.LENGTH_SHORT).show();
        }
        else {
            ContentValues values = new ContentValues();
            values.put(MOON_FAVORITE, 1);
            db.update(MOON_TABLE_NAME, values, MOON_NAME + " = ?", new String[]{moon});
            Toast.makeText(context, "Establishing outpost.", Toast.LENGTH_SHORT).show();
        }
    }

}
