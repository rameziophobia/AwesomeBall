package com.example.crazyball.model.obstacles;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public class Wall extends ComponentModel {

    public Wall(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        super(componentData, tileWidth, tileHeight);
    }

    @Override
    public boolean checkCollision() {
        return false;
    }

    public static ComponentModel createWall(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        ComponentModel component = new Wall(componentData, tileWidth, tileHeight);

        return component;
    }
}
