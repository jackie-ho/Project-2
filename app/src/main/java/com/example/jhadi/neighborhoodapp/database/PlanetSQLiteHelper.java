package com.example.jhadi.neighborhoodapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jhadi.neighborhoodapp.SpaceObject;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JHADI on 2/4/16.
 */
public class PlanetSQLiteHelper extends SQLiteAssetHelper {

    private static final int DATABASE_VERSION = 9;
    private static final String PLANET_TABLE_NAME = "PLANETS";
    private static final String MOON_TABLE_NAME = "MOONS";
    private static final String PLANET_ID = "_id";
    private static final String DATABASE_NAME = "SPACE_DB.db";
    public static final String PLANET_NAME = "NAME";
    private static final String PLANET_APHELION = "APHELION";
    private static final String PLANET_PERIHELION = "PERIHELION";
    private static final String PLANET_DESC = "DESCRIPTION";
    private static final String PLANET_GRAVITY = "GRAVITY";
    private static final String PLANET_RADIUS = "RADIUS";
    private static final String PLANET_FAVORITE = "FAVORITE";
    private static final String PLANET_ORBIT = "ORBITAL_PERIOD";
    private static final String PLANET_DENSITY = "DENSITY";

    private static final String MOON_ID = "_id";
    public static final String MOON_NAME = "NAME";
    private static final String MOON_APOGEE = "APOGEE";
    private static final String MOON_PERIGEE = "PERIGEE";
    private static final String MOON_DESC = "DESCRIPTION";
    private static final String MOON_SATELLITE_OF = "SATELLITE_OF";
    private static final String MOON_GRAVITY = "GRAVITY";
    private static final String MOON_RADIUS = "RADIUS";
    private static final String MOON_FAVORITE = "FAVORITE";
    private static final String MOON_IMAGEURL = "IMAGEURL";

    private static final String[] MOON_COLUMNS = {MOON_ID, MOON_NAME, MOON_DESC, MOON_APOGEE, MOON_PERIGEE, MOON_SATELLITE_OF, MOON_RADIUS,MOON_GRAVITY,MOON_FAVORITE, MOON_IMAGEURL};

    private static PlanetSQLiteHelper instance;

    private PlanetSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade(); //Forced upgrade of database
    }
    public static PlanetSQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new PlanetSQLiteHelper(context);
        }
        return instance;
    }


}
