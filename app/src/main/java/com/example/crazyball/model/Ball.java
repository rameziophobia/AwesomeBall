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
        deltaX = deltaX * width;
        deltaY = deltaY * height;

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
            // todo encapsulate these in a CollisionRectangle class

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

//            max(0, currentX + )
            float xIntersection = max(0, componentModel.getEndX() - currentX) // if
                    + max(0, currentX + width - componentModel.getStartX());

            if(currentX < componentModel.getStartX()) {
                if (currentX + width > componentModel.getStartX()){
                    xIntersection = 1;
                } else {
                    xIntersection = 0;
                }
            }

            if(currentX > componentModel.getStartX()) {
                if (currentX + width > componentModel.getStartX()){
                    xIntersection = 1;
                } else {
                    xIntersection = 0;
                }
            }

            float yIntersection = max(0, componentModel.getEndY() - currentY)
                    + max(0, currentY + height - componentModel.getStartY());

            // test component left side collision
            if(testLocationX + width > componentModel.getStartX() && currentX + width < componentModel.getStartX()){
                float oldDelta = deltaX;
//                deltaX = min(deltaX - 1.5f * (componentModel.getStartX() - (testLocationX + width)), 0);
//                if(currentX + width >= componentModel.getStartX() - 2) {
                    deltaX = 0;
//                }
                if(deltaX == 0 ){
                    Log.d("stop", "old Delta" + oldDelta + " new delta" + deltaX + " currentX+w" + (currentX + width) + " testlocX" + testLocationX);
                }
                Log.d("collision", "old Delta" + oldDelta + " new delta" + deltaX + " currentX+w" + (currentX + width) + " testlocX" + testLocationX);
            }

            // test component right side collision
            if(testLocationX < componentModel.getEndX() && currentX  > componentModel.getEndX()){
                deltaX = 0;
            }

            // test component bottom side collision
            if(testLocationY < componentModel.getEndY() && currentY > componentModel.getEndY()){
                deltaY = 0;
            }

            // test component top side collision
            if(testLocationY + height > componentModel.getStartX() && currentY < componentModel.getStartY()){
                float oldDelta = deltaY;
                deltaY = max(deltaY - 1 * (- componentModel.getStartY() + (testLocationY + height)), 0);
                deltaY = 0;
//                if(currentY + height >= componentModel.getStartY() - 2) {
//                    deltaY = 0;
//                }
//                if(deltaY == 0 ){
//                    Log.d("stop", "old Delta" + oldDelta + " new delta" + deltaY + " currentY+w" + (currentX + width) + " testlocY" + testLocationX);
//                }
//                Log.d("collision", "old Delta" + oldDelta + " new delta" + deltaY + " currentY+w" + (currentX + width) + " testlocY" + testLocationX);
            }



//
//            if(testLocationX + this.width> screenWidth){
//                Log.d("location", "current x " + currentX + "test x" + testLocationX + "width " + this.width);
//                deltaX -= (screenWidth - testLocationX - this.width);
//            }
//
//            if(testLocationY < componentModel.getStartY()){
//                Log.d("location", "current y " + currentY + "test y" + testLocationY + "hiht " + this.height);
//
//                deltaY -= testLocationY;
//            }
//
//            if(testLocationY > screenHeight){
//                Log.d("location", "current y " + currentY + "test y" + testLocationY + "hiht " + this.height +  " max height" + screenHeight);
//                float v = screenHeight - currentY;
//                Log.d("location", "new delta y " + (screenHeight - currentY) + " old delta y" + deltaY );
//                deltaY = screenHeight - currentY;
//            }
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
