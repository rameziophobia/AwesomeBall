package com.example.crazyball.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.crazyball.model.obstacles.ComponentModel;
import com.example.crazyball.model.obstacles.IFailable;
import com.example.crazyball.model.obstacles.IWinnable;
import com.example.crazyball.model.tables.LevelComponentDao;
import com.example.crazyball.model.tables.LevelDao;
import com.example.crazyball.model.tables.LevelRoomDatabase;
import com.example.crazyball.model.tables.relations.LevelWithComponents;

import java.util.ArrayList;
import java.util.List;

public class LevelRepository {

    private final LevelDao levelDao;
    private final LiveData<List<LevelWithComponents>> allLevels;
    public LevelComponentDao componentDao;
//    private LiveData<List>

    public LevelRepository(Application application) {
        LevelRoomDatabase db = LevelRoomDatabase.getInstance(application);
        levelDao = db.levelDao();
        componentDao = db.levelComponentDao();

        allLevels = levelDao.getLevelsWithComponents();
    }

    public LiveData<List<LevelWithComponents>> getAllLevels() {
        Log.d("repo", "levels retreived");
        return allLevels;
    }


    public LiveData<ArrayList<ComponentModel>> loadLevel(int levelId, IFailable onLevelFailed, IWinnable onLevelWon) {
        return LevelLoader.getInstance().loadLevel(levelDao.getLevelWithComponents(levelId),  onLevelFailed, onLevelWon);
    }
}
