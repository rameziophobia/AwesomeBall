package com.example.crazyball.model;

import com.example.crazyball.model.obstacles.Obstacle;
import com.example.crazyball.model.tables.entities.LevelComponentEntity;
import com.example.crazyball.model.tables.relations.LevelWithComponents;

import java.util.ArrayList;
import java.util.List;

public class LevelLoader {

    public static LevelLoader INSTANCE;
    private int screenWidth;
    private int screenHeight;
    private int tileWidth;
    private int tileHeight;

    private final int NUM_TILES_PER_COLUMN = 36;
    private final int NUM_TILES_PER_ROW = 16;

    public static LevelLoader getInstance() {
        if (INSTANCE == null) {
            synchronized (LevelLoader.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LevelLoader();
                }
            }
        }

        return INSTANCE;
    }

    public void initScreen(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.tileWidth = screenWidth / NUM_TILES_PER_ROW;
        this.tileHeight = screenHeight / NUM_TILES_PER_COLUMN;
    }


    public ArrayList<Obstacle> loadLevel(LevelWithComponents level) {
        ArrayList<Obstacle> obstacles = new ArrayList<>();
        for (LevelComponentEntity component : level.components) {
            Obstacle ob = Obstacle.createObstacle(component, tileWidth, tileHeight);
            obstacles.add(ob);
        }
        return obstacles;
    }
}
