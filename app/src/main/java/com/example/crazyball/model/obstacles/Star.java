package com.example.crazyball.model.obstacles;

import androidx.core.util.Pair;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public class Trap extends ComponentModel {
    protected Trap(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        super(componentData, tileWidth, tileHeight);
    }

    @Override
    public Pair<Float, Float> doCollisionBehaviour(float deltaX, float deltaY, float currentX, float currentY, float width, float height) {

        if(collidesWith(currentX, currentY, width, height)) {
            isLevelFailed.postValue(true);
            return Pair.create(0f, 0f);
        }

        return Pair.create(deltaX, deltaY);
    }

    public static Trap createTrap(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        return new Trap(componentData, tileWidth, tileHeight);
    }

}
