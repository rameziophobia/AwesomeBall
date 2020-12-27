package com.example.crazyball.model.obstacles;

import androidx.core.util.Pair;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public class Target extends ComponentModel {
    protected Target(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        super(componentData, tileWidth, tileHeight);
    }

    @Override
    public Pair<Float, Float> doCollisionBehaviour(float deltaX, float deltaY, float currentX, float currentY, float width, float height) {
        return null;
    }

    public static ComponentModel createTarget(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        ComponentModel component = new Target(componentData, tileWidth, tileHeight);

        return component;
    }

}
