package com.example.crazyball.model.obstacles;

import androidx.core.util.Pair;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public class Star extends ComponentModel {
    protected Star(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        super(componentData, tileWidth, tileHeight);
    }

    @Override
    public Pair<Float, Float> doCollisionBehaviour(float deltaX, float deltaY, float currentX, float currentY, float width, float height) {

        if(collidesWith(currentX, currentY, width, height)) {
            foundStar.postValue(getComponentData().getId());
        }

        return Pair.create(deltaX, deltaY);
    }

    public static Star createStar(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        return new Star(componentData, tileWidth, tileHeight);
    }

}
