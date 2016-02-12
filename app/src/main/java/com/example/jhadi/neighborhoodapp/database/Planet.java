package com.example.jhadi.neighborhoodapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by JHADI on 2/5/16.
 */
public class Planet {

    private static final String PLANET_TABLE_NAME = "PLANETS";
    private static final String PLANET_ID = "_id";
    public static final String PLANET_NAME = "NAME";
    public static final String PLANET_APHELION = "APHELION";
    public static final String PLANET_PERIHELION = "PERIHELION";
    public static final String PLANET_DESC = "DESCRIPTION";
    public static final String PLANET_GRAVITY = "GRAVITY";
    public static final String PLANET_RADIUS = "RADIUS";
    public static final String PLANET_FAVORITE = "FAVORITE";
    public static final String PLANET_ORBIT = "ORBITAL_PERIOD";
    public static final String PLANET_DENSITY = "DENSITY";
    public static final String PLANET_IMAGE = "IMAGE";
    private static final String[] PLANET_COLUMNS = {PLANET_ID, PLANET_NAME, PLANET_DESC, PLANET_APHELION, PLANET_PERIHELION, PLANET_ORBIT, PLANET_RADIUS, PLANET_DENSITY, PLANET_GRAVITY, PLANET_FAVORITE, PLANET_IMAGE};

    PlanetSQLiteHelper mHelper;
    SQLiteDatabase mDatabase;
    private Context context;


    public Planet(Context context) {
        mHelper = PlanetSQLiteHelper.getInstance(context);
        mDatabase = mHelper.getWritableDatabase();
        this.context = context;
    }

    //Not used
    public void deletePlanet (String planet){

        mDatabase.delete("PLANETS", "NAME LIKE ?", new String[]{planet});
    }

    //Not used
    public void createPlanet (String name, String desc, double aphelion, double perihelion, int orbit, double radius, double gravity){
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("DESCRIPTION", desc);
        values.put("APHELION", aphelion);
        values.put("PERIHELION", perihelion);
        values.put("ORBIT",orbit);
        values.put("RADIUS",radius);
        values.put("GRAVITY",gravity);
        values.put("FAVORITE",0);
        mDatabase.insert("PLANETS", null, values);
    }

    //Database with the name and url
    public Cursor getAllPlanetsByNameAndImage(){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(PLANET_TABLE_NAME, new String[]{PLANET_NAME, PLANET_DESC, PLANET_IMAGE},null,null,null,null,null);
        return cursor;
    }

    //Detect whether space body is a planet or moon
    public boolean searchPlanetByName(String str){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(PLANET_TABLE_NAME, new String[]{PLANET_NAME}, PLANET_NAME + " LIKE ?", new String[]{str},null,null,null);
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }

    //Get all the planets in database
    public Cursor getPlanets(){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(PLANET_TABLE_NAME, PLANET_COLUMNS, null, null, null, null, null);
        cursor.moveToFirst();
        return cursor;
    }

    //Method for displaying favorited Planets
    public  Cursor getFavoritePlanets(){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(PLANET_TABLE_NAME, PLANET_COLUMNS, PLANET_FAVORITE+" = ? ",new String[]{"1"},null,null,null);
        cursor.moveToFirst();
        return cursor;
    }

    //Make a planet a favorite
    public void addFavoritePlanet(String planet){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(PLANET_TABLE_NAME, new String[]{PLANET_FAVORITE}, PLANET_NAME+ " = ?", new String[]{planet},null,null,null);
        cursor.moveToFirst();
        //Check if location has been already added to favorites
        if (cursor.getInt(cursor.getColumnIndex(PLANET_FAVORITE)) == 1){
            Toast.makeText(context,"Base has been already set at this location." ,Toast.LENGTH_SHORT).show();
        } else {
            ContentValues values = new ContentValues();
            values.put(PLANET_FAVORITE, 1);
            db.update(PLANET_TABLE_NAME, values, PLANET_NAME + " = ?", new String[]{planet});
            Toast.makeText(context, "Outpost marked.", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }

}
