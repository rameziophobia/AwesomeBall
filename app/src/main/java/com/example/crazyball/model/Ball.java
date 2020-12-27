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

        for(ComponentModel componentModel: levelObstacles) {

            // checks if there isnt a collision => continue loop
            // If one rectangle is on left side of other
            if (testLocationX >= componentModel.getEndX()
                    || componentModel.getStartX() >= testLocationX + width){
                continue;
            }

            // If one rectangle is above other
            if (testLocationY >= componentModel.getEndY()
                    || componentModel.getStartY() >= testLocationY + height){
                continue;
            }

            boolean isIntersectingOnX =
                    currentX + width <= componentModel.getStartX()
                            || currentX >= componentModel.getEndX();


            // test component left side collision
            if(testLocationX + width > componentModel.getStartX() && currentX + width < componentModel.getStartX()){
                if(isIntersectingOnX) {
                    deltaX = 0;
                }
            }

            // test component right side collision
            if(testLocationX < componentModel.getEndX() && currentX  > componentModel.getEndX()){
                if(isIntersectingOnX) {
                    deltaX = 0;
                }
            }

            // test component bottom side collision
            if(testLocationY < componentModel.getEndY() && currentY > componentModel.getEndY()){
                if(!isIntersectingOnX) {
                    deltaY = 0;
                }
            }

            // test component top side collision
            if(testLocationY + height > componentModel.getStartX() && currentY < componentModel.getStartY()){
                if(!isIntersectingOnX) {
                    deltaY = 0;
                }
            }
        }

        lastCoord = Pair.create(deltaX, deltaY);
        this.deltaXY.postValue(lastCoord);

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
}
