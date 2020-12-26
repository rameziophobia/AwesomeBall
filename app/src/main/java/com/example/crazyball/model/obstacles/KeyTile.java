package com.example.crazyball.model.obstacles;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public class KeyTile extends Obstacle {
    protected KeyTile(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        super(componentData, tileWidth, tileHeight);
    }

    @Override
    public boolean checkCollision() {
        return false;
    }

    public static Obstacle createKeyTile(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        Obstacle ob = new Wall(componentData, tileWidth, tileHeight);

        return ob;
    }

}
