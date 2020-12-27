package com.example.crazyball.model.obstacles;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public class KeyTile extends ComponentModel {
    protected KeyTile(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        super(componentData, tileWidth, tileHeight);
    }

    @Override
    public boolean checkCollision() {
        return false;
    }

    public static ComponentModel createKeyTile(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        ComponentModel component = new Wall(componentData, tileWidth, tileHeight);

        return component;
    }

}