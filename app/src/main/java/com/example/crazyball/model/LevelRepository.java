package com.example.crazyball.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.crazyball.model.obstacles.ComponentModel;
import com.example.crazyball.model.retrofit.LevelsAPI;
import com.example.crazyball.model.tables.LevelComponentDao;
import com.example.crazyball.model.tables.LevelDao;
import com.example.crazyball.model.tables.LevelRoomDatabase;
import com.example.crazyball.model.tables.entities.LevelEntity;
import com.example.crazyball.model.tables.relations.LevelWithComponents;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LevelRepository {

    private final LevelDao levelDao;
    private final LiveData<List<LevelWithComponents>> allLevels;
    public LevelComponentDao componentDao;
    private LevelsAPI levelsAPI;
//    private LiveData<List>

    public LevelRepository(Application application) {
        LevelRoomDatabase db = LevelRoomDatabase.getInstance(application);
        levelDao = db.levelDao();
        componentDao = db.levelComponentDao();

        allLevels = levelDao.getLevelsWithComponents();
        initRetroFit();
    }

    private void initRetroFit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        levelsAPI = retrofit.create(LevelsAPI.class);
    }

    public LiveData<List<LevelWithComponents>> getAllLevels() {
        return allLevels;
    }


    public LiveData<ArrayList<ComponentModel>> loadLevel(int levelId) {
        return LevelLoader.getInstance().loadLevel(levelDao.getLevelWithComponents(levelId));
    }

    public LiveData<List<LevelWithComponents>> getLevelEntity(int levelId) {
        return levelDao.getLevelWithComponents(levelId);
    }

    public void downloadLevelsFromAPI() {
        Call<List<LevelEntity>> call = levelsAPI.getLevels();
        call.enqueue(new Callback<List<LevelEntity>>() {
            @Override
            public void onResponse(Call<List<LevelEntity>> call, Response<List<LevelEntity>> response) {

            }

            @Override
            public void onFailure(Call<List<LevelEntity>> call, Throwable t) {

            }
        });
    }
}
