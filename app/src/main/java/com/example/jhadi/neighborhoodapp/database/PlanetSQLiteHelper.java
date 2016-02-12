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
        setForcedUpgrade();

    }

    public static PlanetSQLiteHelper getInstance(Context context) {
        if (instance == null) {
            instance = new PlanetSQLiteHelper(context);
        }
        return instance;
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE " + PLANET_TABLE_NAME + " (" +
//                PLANET_ID + " INTEGER PRIMARY KEY, " +
//                PLANET_NAME + " TEXT, " +
//                PLANET_DESC + " TEXT, " +
//                PLANET_APHELION + " NUMERIC, " +
//                PLANET_PERIHELION + " NUMERIC, " +
//                PLANET_ORBIT + " INTEGER, " +
//                PLANET_RADIUS + " NUMERIC, " +
//                PLANET_GRAVITY + " NUMERIC, " +
//                PLANET_FAVORITE + " INTEGER)");
//        db.execSQL("CREATE TABLE " + MOON_TABLE_NAME + " (" +
//                MOON_ID + " INTEGER PRIMARY KEY, " +
//                MOON_NAME + " TEXT, " +
//                MOON_DESC + " TEXT, " +
//                MOON_APOGEE + " INTEGER, " +
//                MOON_PERIGEE + " INTEGER, " +
//                MOON_SATELLITE_OF + " TEXT, " +
//                MOON_RADIUS + " TEXT, " +
//                MOON_GRAVITY + " NUMERIC, " +
//                MOON_FAVORITE + " INTEGER, " +
//                  )");
//
//    }
//
//    public List<SpaceObject> searchSpaceObjects(String query){
//        List<SpaceObject> spaceObjects = new ArrayList<SpaceObject>();
//        //Search if it is text only
//        if (query != null && !query.trim().isEmpty() && !query.matches(".*\\d+.*")) {
//            SQLiteDatabase db = getReadableDatabase();
//            String selection = MOON_NAME + " LIKE ? OR " + MOON_DESC + "LIKE ?";
//            String[] selectionArgs = new String[]{"%" + query + "%", "%" + query + "%"};
//
//            Cursor moonCursor = db.query(MOON_TABLE_NAME, new String[]{MOON_NAME, MOON_DESC, MOON_IMAGEURL}, selection, selectionArgs, null, null, null);
//            moonCursor.moveToFirst();
//
//
//            while (!moonCursor.isAfterLast()) {
//                String moonName = moonCursor.getString(moonCursor.getColumnIndex(MOON_NAME));
//                String moonImage = moonCursor.getString(moonCursor.getColumnIndex(MOON_IMAGEURL));
//                String moonDesc = moonCursor.getString(moonCursor.getColumnIndex(MOON_DESC));
//                spaceObjects.add(new SpaceObject(moonName, moonImage, moonDesc));
//                moonCursor.moveToNext();
//            }
//        }
//            return spaceObjects;
//
//    }


//    @Override
//         public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXIST" + PLANET_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXIST" + MOON_TABLE_NAME);
//        onCreate(db);
//
//    }
//    public Cursor getAllMoons(){
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.query(MOON_TABLE_NAME, MOON_COLUMNS, null, null, null, null, null);
//        return cursor;
//    }


}
