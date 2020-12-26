package com.example.crazyball.model.obstacles;

import android.widget.LinearLayout;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public abstract class Obstacle {

    private final int startX;
    private final int startY;
    private final LinearLayout.LayoutParams layoutParams;
    private LevelComponentEntity componentData;

    protected Obstacle(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        this.componentData = componentData;
        this.startX = componentData.getLocationX() * tileWidth;
        this.startY = componentData.getLocationY() * tileHeight;
        this.layoutParams = new LinearLayout.LayoutParams(100, 100);
    }

    public static Obstacle createObstacle(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        Obstacle ob;
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
}
