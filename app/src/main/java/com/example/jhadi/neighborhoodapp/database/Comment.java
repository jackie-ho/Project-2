package com.example.jhadi.neighborhoodapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

/**
 * Created by JHADI on 2/11/16.
 */
public class Comment {

    private static final String COMMENT_TABLE = "COMMENTS";
    private static final String COMMENT_ID = "_id";
    public static final String COMMENT_NAME = "NAME";
    public static final String COMMENT_COMMENT = "COMMENT";
    public static final String COMMENT_AUTHOR = "AUTHOR";
    public static final String[] COMMENT_COLUMNS = {COMMENT_ID, COMMENT_NAME, COMMENT_AUTHOR, COMMENT_COMMENT};
    private PlanetSQLiteHelper mHelper;
    private SQLiteDatabase mDatabase;
    private Context context;
    SQLiteDatabase db;

    public Comment(Context context) {
        mHelper = PlanetSQLiteHelper.getInstance(context);
        this.context = context;
    }
    //get comments from database

    public Cursor getComments(String bodyName) {
        bodyName = bodyName.toUpperCase();
        db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(COMMENT_TABLE, COMMENT_COLUMNS,
                COMMENT_NAME + " = ? ",
                new String[]{bodyName},
                null, null, null);
        return cursor;
    }

    //Insert comment to database
    public void addComment(String bodyName, String name, String comment) {
        mDatabase = mHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COMMENT_NAME, bodyName.toUpperCase());
        contentValues.put(COMMENT_AUTHOR, name);
        contentValues.put(COMMENT_COMMENT, comment);
        mDatabase.insert(COMMENT_TABLE, null, contentValues);
    }




}

