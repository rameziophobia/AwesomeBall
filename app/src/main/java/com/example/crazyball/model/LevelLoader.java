package com.example.crazyball.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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


    public LiveData<ArrayList<Obstacle>> loadLevel(LiveData<List<LevelWithComponents>>  level) {
        MutableLiveData<ArrayList<Obstacle>> obstacles = new MutableLiveData<>();
        level.observeForever(new Observer<List<LevelWithComponents>>() {
            @Override
            public void onChanged(List<LevelWithComponents> levelWithComponents) {
                level.removeObserver(this);
                ArrayList<Obstacle> obstaclesList = new ArrayList<>();
                for (LevelComponentEntity component : levelWithComponents.get(0).components) {
                    Obstacle ob = Obstacle.createObstacle(component, tileWidth, tileHeight);
                    obstaclesList.add(ob);
                }
                obstacles.postValue(obstaclesList);
            }
        });

        return obstacles;
    }

}
