package com.example.crazyball.model.obstacles;

import androidx.core.util.Pair;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public class Wall extends ComponentModel {

    public Wall(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        super(componentData, tileWidth, tileHeight);
    }

    @Override
    public Pair<Float, Float> doCollisionBehaviour(float deltaX, float deltaY, float currentX, float currentY, float width, float height) {
        float testLocationX = currentX - deltaX;
        float testLocationY = currentY + deltaY;

        boolean isIntersectingOnX =
                currentX + width <= getStartX()
                        || currentX >= getEndX();


        // test component left side collision
        if(testLocationX + width > getStartX() && currentX + width < getStartX()){
            if(isIntersectingOnX) {
                deltaX = 0f;
            }
        }

        // test component right side collision
        if(testLocationX < getEndX() && currentX  > getEndX()){
            if(isIntersectingOnX) {
                deltaX = 0f;
            }
        }

        // test component bottom side collision
        if(testLocationY < getEndY() && currentY > getEndY()){
            if(!isIntersectingOnX) {
                deltaY = 0f;
            }
        }

        // test component top side collision
        if(testLocationY + height > getStartX() && currentY < getStartY()){
            if(!isIntersectingOnX) {
                deltaY = 0f;
            }
        }
        return Pair.create(deltaX, deltaY);
    }

    public static ComponentModel createWall(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        ComponentModel component = new Wall(componentData, tileWidth, tileHeight);

        return component;
    }
}
