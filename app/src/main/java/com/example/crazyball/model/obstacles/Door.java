package com.example.crazyball.model.obstacles;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public class Door extends ComponentModel {
    protected Door(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        super(componentData, tileWidth, tileHeight);
    }

    @Override
    public boolean checkCollision() {
        return false;
    }

    public static ComponentModel createDoor(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        ComponentModel component = new Wall(componentData, tileWidth, tileHeight);

        return component;
    }

}
