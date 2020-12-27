package com.example.crazyball.model.obstacles;

import androidx.core.util.Pair;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public class KeyTile extends ComponentModel {
    protected KeyTile(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        super(componentData, tileWidth, tileHeight);
    }

    @Override
    public Pair<Float, Float> doCollisionBehaviour(float deltaX, float deltaY, float currentX, float currentY, float width, float height) {
        return Pair.create(deltaX, deltaY);
    }

    public static ComponentModel createKeyTile(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        ComponentModel component = new KeyTile(componentData, tileWidth, tileHeight);

        return component;
    }

}
