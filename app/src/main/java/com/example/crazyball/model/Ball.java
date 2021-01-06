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
    private double nextDeltaY;
    private double nextDeltaX;

    public Ball() {
        this.deltaXY = new MutableLiveData<>();
        lastCoord = Pair.create(0f, 0f);
        this.deltaXY.postValue(lastCoord);
    }

    public void moveBall(float currentX, float currentY) {
        float deltaY = (float) (nextDeltaY * 70);
        float deltaX = (float) (nextDeltaX * 70);

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
            deltaX -= (screenWidth - testLocationX);
        }

        if(testLocationY < 0){
            deltaY -= testLocationY;
        }

        if(testLocationY + height > screenHeight){
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

    public void updateNextSensorReading(double theta, double psi) {
        this.nextDeltaY = theta;
        this.nextDeltaX = psi;
    }
}
