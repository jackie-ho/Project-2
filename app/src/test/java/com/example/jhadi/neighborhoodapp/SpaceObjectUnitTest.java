package com.example.jhadi.neighborhoodapp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by JHADI on 2/12/16.
 */
public class SpaceObjectUnitTest {

    //Testing getters from class SpaceObject

    private String mName = "Jackie";
    private String mImageUrl = "http://randomimage.com";
    private String mDescription = "This is my project unit testing the getter methods";
    private double mFurthestOrbit = 424.42;
    private double mClosestOrbit = 9942245.153151535;
    private double mGravity = 421;
    private double mRadius = 241251.75;
    private String mSatelliteOf_OrbitalPeriod = "teeest";
    private SpaceObject spaceObject;

    public SpaceObjectUnitTest() {
        spaceObject = new SpaceObject(mName, mImageUrl, mDescription, mFurthestOrbit, mClosestOrbit, mRadius, mGravity, mSatelliteOf_OrbitalPeriod);

    }

    @Test
    public void testNameGet() {
        //mName = Jackie
        String expectedValue = "Jackie";
        assertEquals(expectedValue, spaceObject.getmName());

    }

    @Test
    public void testImageUrlGet() {
        //actual value = http://randomimage.com
        String expectedValue = "http://randomimage.com";
        assertEquals(expectedValue, spaceObject.getmImageUrl());
    }

    @Test
    public void testDescGet() {
        //value = This is my project unit testing the getter methods
        String expectedValue = "This is my project unit testing the getter methods";
        assertEquals(expectedValue, spaceObject.getDescription());
    }
    @Test
    public void testFurthestOrbitGet(){
        double expectedValue = 424.42;
        assertEquals(expectedValue, spaceObject.getmFurthestOrbit(), 0.5);
    }
    @Test
    public void testClosestOrbitGet(){
        double expectedValue = 9942245.153151535;
        assertEquals(expectedValue, spaceObject.getmClosestOrbit(),0.5);
    }
    @Test
    public void testGravityGet(){
        double expectedValue = 421;
        assertEquals(expectedValue, spaceObject.getmGravity(), 0.5);
    }
    @Test
    public void testRadiusGet(){
        double expectedValue = 241251.75;
        assertEquals(expectedValue,spaceObject.getmRadius(),0.5);
    }
    @Test
    public void testSatelliteOrbitalPeriod(){
        String expectedValue = "teeest";
        assertEquals(expectedValue,spaceObject.getmSatelliteOf_OrbitalPeriod());
    }
}
