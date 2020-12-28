package com.example.crazyball.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.crazyball.model.obstacles.ComponentFactory;
import com.example.crazyball.model.obstacles.ComponentModel;
import com.example.crazyball.model.obstacles.IFailable;
import com.example.crazyball.model.obstacles.IWinnable;
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


    public LiveData<ArrayList<ComponentModel>> loadLevel(LiveData<List<LevelWithComponents>> level, IFailable onLevelFailed, IWinnable onLevelWon) {
        MutableLiveData<ArrayList<ComponentModel>> components = new MutableLiveData<>();
        level.observeForever(new Observer<List<LevelWithComponents>>() {
            @Override
            public void onChanged(List<LevelWithComponents> levelWithComponents) {
                level.removeObserver(this);
                ArrayList<ComponentModel> obstaclesList = new ArrayList<>();
                for (LevelComponentEntity componentEntity : levelWithComponents.get(0).components) {
                    ComponentModel component = ComponentFactory.createComponent(componentEntity, tileWidth, tileHeight, onLevelFailed, onLevelWon);
                    obstaclesList.add(component);
                }
                components.postValue(obstaclesList);
            }
        });

        return components;
    }

}
