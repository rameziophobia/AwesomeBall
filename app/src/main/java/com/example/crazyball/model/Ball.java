package com.example.crazyball.model;

import android.util.Log;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.crazyball.model.obstacles.ComponentModel;

import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Ball {
    private Pair<Float, Float> lastCoord;
    public MutableLiveData<Pair<Float, Float>> deltaXY;
    private int screenHeight;
    private int screenWidth;
    private float width;
    private float height;
    private ArrayList<ComponentModel> levelObstacles = new ArrayList<>();
    private double nextDeltaX;
    private double nextDeltaY;

    public Ball() {
        this.deltaXY = new MutableLiveData<>();
        lastCoord = Pair.create(0f, 0f);
        this.deltaXY.postValue(lastCoord);
    }

    public void moveBall(float deltaY, float deltaX, float currentX, float currentY) {
        deltaX = -deltaX * 100;
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

        if(testLocationX> screenWidth){
            Log.d("location", "current x " + currentX + "test x" + testLocationX + "width " + this.width);
            deltaX -= (screenWidth - testLocationX);
        }

        if(testLocationY < 0){
            Log.d("location", "current y " + currentY + "test y" + testLocationY + "hiht " + this.height);

            deltaY -= testLocationY;
        }

        if(testLocationY + height > screenHeight){
            Log.d("location", "current y " + currentY + "test y" + testLocationY + "hiht " + this.height +  " max height" + screenHeight);
            float v = screenHeight - currentY;
            Log.d("location", "new delta y " + (screenHeight - currentY) + " old delta y" + deltaY );
            deltaY = screenHeight - currentY - height;
        }

        lastCoord = Pair.create(deltaX, deltaY);
        for(ComponentModel componentModel: levelObstacles) {
            if(componentModel.collidesWith(testLocationX, testLocationY, width, height)){
                lastCoord = componentModel.doCollisionBehaviour(deltaX, deltaY, currentX, currentY, width, height);
            }
        }

        this.deltaXY.postValue(lastCoord);
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

    public void addObstacles(LiveData<ArrayList<ComponentModel>> obstacles) {
        obstacles.observeForever(new Observer<ArrayList<ComponentModel>>() {
            @Override
            public void onChanged(ArrayList<ComponentModel> componentModels) {
                {
                    obstacles.removeObserver(this);
                    levelObstacles = componentModels;
                }
            }
        });
    }

    public void updateNextSensorReading(double x, double y) {
        this.nextDeltaX = x;
        this.nextDeltaY = y;
    }
}
