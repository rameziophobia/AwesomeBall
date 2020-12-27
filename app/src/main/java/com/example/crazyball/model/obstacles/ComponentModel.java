package com.example.crazyball.model.obstacles;

import android.graphics.Rect;
import android.graphics.drawable.shapes.RectShape;
import android.widget.LinearLayout;

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

    protected ComponentModel(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        this.componentData = componentData;
        this.startX = componentData.getLocationX() * tileWidth;
        this.startY = componentData.getLocationY() * tileHeight;
        this.layoutParams = new LinearLayout.LayoutParams(100, 100);
    }

    public static ComponentModel createComponent(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        ComponentModel ob;
        switch (componentData.getType()) {
            case "wall":
                ob = Wall.createWall(componentData, tileWidth, tileHeight);
                break;
            case "trap":
                ob = Trap.createTrap(componentData, tileWidth, tileHeight);
                break;
            case "key":
                ob = KeyTile.createKeyTile(componentData, tileWidth, tileHeight);
                break;
            case "door":
                ob = Door.createDoor(componentData, tileWidth, tileHeight);
                break;
            default:
                throw new IllegalStateException("Unexpected value in obstacle creation: " + componentData.getType());
        }
        return ob;
    }

    public abstract boolean checkCollision();

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
}
