package com.example.jhadi.neighborhoodapp;

/**
 * Created by JHADI on 2/8/16.
 */

//Class of the list layout for recyclerview

public class SpaceObject {

    private String mName;
    private String mImageUrl;
    private String mDescription;
    private double mFurthestOrbit;
    private double mClosestOrbit;
    private double mGravity;
    private double mRadius;
    private String mSatelliteOf_OrbitalPeriod;


    public SpaceObject(String name, String url, String desc, double mFurthestOrbit, double mClosestOrbit, double mRadius, double mGravity, String mSatelliteOf_OrbitalPeriod){
        mName = name;
        mImageUrl = url;
        mDescription = desc;
        this.mFurthestOrbit = mFurthestOrbit;
        this.mClosestOrbit = mClosestOrbit;
        this.mGravity = mGravity;
        this.mRadius = mRadius;
        this.mSatelliteOf_OrbitalPeriod = mSatelliteOf_OrbitalPeriod;
    }

    public String getmName() {
        return mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getDescription(){ return mDescription;}


    public double getmFurthestOrbit() {
        return mFurthestOrbit;
    }

    public double getmClosestOrbit() {
        return mClosestOrbit;
    }

    public double getmGravity() {
        return mGravity;
    }

    public double getmRadius() {
        return mRadius;
    }

    public String getmSatelliteOf_OrbitalPeriod(){
        return  mSatelliteOf_OrbitalPeriod;
    }

}
