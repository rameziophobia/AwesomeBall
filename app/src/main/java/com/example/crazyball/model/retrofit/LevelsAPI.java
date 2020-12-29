package com.example.crazyball.model.retrofit;

import com.example.crazyball.model.tables.entities.LevelEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LevelsAPI {

    @GET("levels")
    Call<List<LevelEntity>> getLevels();

    @GET("levels/{levelId}")
    Call<List<LevelEntity>> getLevels(@Path("levelId") int levelId);


}
