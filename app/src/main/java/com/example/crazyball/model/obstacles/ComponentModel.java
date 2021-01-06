package com.example.crazyball.model.obstacles;

import android.widget.LinearLayout;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public abstract class ComponentModel {

    private final int startX;
    private final int startY;
    private final LinearLayout.LayoutParams layoutParams;
    private LevelComponentEntity componentData;
    private int endX;
    private int endY;
    private int width;
    private int height;
    public MutableLiveData<Boolean> isLevelFailed = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLevelWon = new MutableLiveData<>();

    protected ComponentModel(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        this.componentData = componentData;
        this.startX = componentData.getLocationX() * tileWidth;
        this.startY = componentData.getLocationY() * tileHeight;
        this.layoutParams = new LinearLayout.LayoutParams(100, 100);
    }

    public boolean collidesWith(float testLocationX, float testLocationY, float width, float height) {
        // If one rectangle is on left side of other
        if (testLocationX >= getEndX()
                || getStartX() >= testLocationX + width){
            return false;
        }

        // If one rectangle is above other
        if (testLocationY >= getEndY()
                || getStartY() >= testLocationY + height){
            return false;
        }

        return true;
    }

    protected boolean isCollisionAreaAboveThreshold(float currentX, float currentY, float width, float height, float threshold) {
        // https://www.geeksforgeeks.org/total-area-two-overlapping-rectangles/

        // Area of 1st Rectangle => ball area
        int area1 = (int) (width * height);


        // Length of intersecting part i.e
        // start from max(l1.x, l2.x) of
        // x-coordinate and end at min(r1.x,
        // r2.x) x-coordinate by subtracting
        // start from end we get required
        // lengths

        int x_dist = (int) (Math.min(currentX + width, getEndX())
                - Math.max(currentX, getStartX()));
        int y_dist = (int) (Math.min(currentY + height, getEndY())
                - Math.max(currentY, getStartY()));

        int areaI = 0;
        if(x_dist > 0 && y_dist > 0) {
            areaI = x_dist * y_dist;
        }

        return areaI >= threshold * area1;
    }


    public LevelComponentEntity getComponentData() {
        return componentData;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setWidth(int width) {
        this.width = width;
        this.endX = width + startX;
//        this.collisionRectangle = new Rect()
    }

    public void setHeight(int height) {
        this.height = height;
        this.endY = height + startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public abstract Pair<Float, Float> doCollisionBehaviour(float deltaX, float deltaY, float currentX, float currentY, float width, float height);
}
