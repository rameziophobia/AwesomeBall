package com.example.crazyball.model.obstacles;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public class Trap extends Obstacle {
    protected Trap(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        super(componentData, tileWidth, tileHeight);
    }

    public static Obstacle createTrap(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        Obstacle ob = new Wall(componentData, tileWidth, tileHeight);

        return ob;
    }

}
