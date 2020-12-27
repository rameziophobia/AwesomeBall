package com.example.crazyball.model.obstacles;

import androidx.core.util.Pair;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public class Door extends ComponentModel {
    protected Door(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        super(componentData, tileWidth, tileHeight);
    }

    @Override
    public Pair<Float, Float> doCollisionBehaviour(float deltaX, float deltaY, float currentX, float currentY, float width, float height) {
        return null;
    }

    public static ComponentModel createDoor(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        ComponentModel component = new Wall(componentData, tileWidth, tileHeight);

        return component;
    }

}
