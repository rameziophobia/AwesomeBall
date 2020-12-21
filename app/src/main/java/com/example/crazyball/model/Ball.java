package com.example.crazyball.model;

import android.util.Log;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

public class Ball {
    private Pair<Float, Float> lastCoord;
    public MutableLiveData<Pair<Float, Float>> deltaXY;
    private int screenHeight;
    private int screenWidth;
    private float width;
    private float height;

    public Ball() {
        this.deltaXY = new MutableLiveData<>();
        lastCoord = Pair.create(0f, 0f);
        this.deltaXY.postValue(lastCoord);
    }

    public void moveBall(float deltaX, float deltaY, float currentX, float currentY) {
        deltaX = deltaX * 100;
        deltaY = deltaY * 100;

        Log.d("sensor", "received dx " + deltaX + "received dy " + deltaY);
        if (sensorDidNotMove(deltaX, deltaY)) {
            return;
        }

        float testLocationX = currentX - deltaX;
        float testLocationY = currentY + deltaY;

        // next 4 ifs check for the screen borders
        if(testLocationX < 0){
            deltaX += testLocationX;
        }

        if(testLocationX + this.width> screenWidth){
            Log.d("location", "current x " + currentX + "test x" + testLocationX + "width " + this.width);
            deltaX -= (screenWidth - testLocationX - this.width);
        }

        if(testLocationY < 0){
            Log.d("location", "current y " + currentY + "test y" + testLocationY + "hiht " + this.height);

            deltaY -= testLocationY;
        }

        if(testLocationY > screenHeight){
            Log.d("location", "current y " + currentY + "test y" + testLocationY + "hiht " + this.height +  " max height" + screenHeight);
            float v = screenHeight - currentY;
            Log.d("location", "new delta y " + (screenHeight - currentY) + " old delta y" + deltaY );
            deltaY = screenHeight - currentY;
        }

        // todo check borders + ball size

        lastCoord = Pair.create(deltaX, deltaY);
        this.deltaXY.postValue(lastCoord);
//


        // todo check collision logic
        // todo inform view model
    }

    private boolean sensorDidNotMove(float deltaX, float deltaY) {
        return (Math.abs(deltaX - lastCoord.first) + Math.abs(deltaY - lastCoord.second) < 0.01);
    }

    public void initScreen(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void initBallDims(int width, float height) {
        this.width = width;
        this.height = height;
    }
}
