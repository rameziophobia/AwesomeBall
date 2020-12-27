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
            //todo end game or try again
            return Pair.create(0f, 0f);
        }

        return Pair.create(deltaX, deltaY);
    }

    public static ComponentModel createTrap(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        ComponentModel component = new Trap(componentData, tileWidth, tileHeight);

        return component;
    }

}
