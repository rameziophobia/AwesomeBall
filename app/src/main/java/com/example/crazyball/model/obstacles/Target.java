package com.example.crazyball.model.obstacles;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public class Target extends ComponentModel {
    protected Target(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        super(componentData, tileWidth, tileHeight);
    }

    @Override
    public boolean checkCollision() {
        return false;
    }

    public static ComponentModel createTarget(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        ComponentModel component = new Wall(componentData, tileWidth, tileHeight);

        return component;
    }

}
