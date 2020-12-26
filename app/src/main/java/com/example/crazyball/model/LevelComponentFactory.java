package com.example.crazyball.model;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;
import com.example.crazyball.model.obstacles.Obstacle;

public class LevelComponentFactory {

    public Obstacle createObstacle(LevelComponentEntity componentData){
//        Obstacle obstacle = new Obstacle(componentData, tileWidth, tileHeight);
        return null;
    }

    private Obstacle getWall() {
        return null;
    }
}
