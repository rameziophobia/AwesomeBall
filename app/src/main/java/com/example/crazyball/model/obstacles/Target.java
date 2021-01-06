package com.example.crazyball.model.obstacles;

import androidx.core.util.Pair;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public class Target extends ComponentModel {

    protected Target(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        super(componentData, tileWidth, tileHeight);
    }

    @Override
    public Pair<Float, Float> doCollisionBehaviour(float deltaX, float deltaY, float currentX,
                                                   float currentY, float width, float height) {

        if(collidesWith(currentX, currentY, width, height) && isCollisionAreaAboveThreshold(currentX, currentY, width, height, 0.3f)) {
            isLevelWon.postValue(true);
            return Pair.create(0f, 0f);
        }

        return Pair.create(deltaX, deltaY);
    }

    public static ComponentModel createTarget(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        ComponentModel component = new Target(componentData, tileWidth, tileHeight);

        return component;
    }

}
