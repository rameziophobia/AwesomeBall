package com.example.crazyball.viewmodel;

import android.app.Application;

import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.crazyball.model.Ball;
import com.example.crazyball.model.LevelLoader;
import com.example.crazyball.model.LevelRepository;
import com.example.crazyball.model.obstacles.Obstacle;
import com.example.crazyball.model.tables.relations.LevelWithComponents;

import java.util.ArrayList;
import java.util.List;

public class MainGameViewModel extends AndroidViewModel {

    private final LevelRepository levelRepository;
    private final LiveData<List<LevelWithComponents>> allLevels;
    private Ball ball;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

    public MainGameViewModel(Application application) {
        super(application);
        levelRepository = new LevelRepository(application);
        allLevels = levelRepository.getAllLevels();
        ball = new Ball();
    }

    public void sensorsMoved(float deltaX, float deltaY, float currentX, float currentY) {
        ball.moveBall(deltaX, deltaY, currentX, currentY);
    }

    public MutableLiveData<Pair<Float, Float>> moveBall() {
        return ball.deltaXY;
    }

    public ArrayList<Obstacle> loadLevel(int levelId){
        this.obstacles = levelRepository.loadLevel(levelId);
        return obstacles;
    }

    public LiveData<List<LevelWithComponents>> getAllLevels() {
        return allLevels;
    }

    public void initScreen(int screenWidth, int screenHeight) {
        ball.initScreen(screenWidth, screenHeight);
        LevelLoader.getInstance().initScreen(screenWidth, screenHeight);
    }

    public void initBall(int width, float height) {
        ball.initBallDims(width, height);
    }

    private ArrayList<Obstacle> getLevelObstacles() {
        return obstacles;
    }

}
