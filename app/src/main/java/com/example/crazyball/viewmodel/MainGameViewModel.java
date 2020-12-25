package com.example.crazyball.viewmodel;

import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.crazyball.R;
import com.example.crazyball.model.Ball;
import com.example.crazyball.model.tables.entities.LevelComponentEntity;
import com.example.crazyball.model.obstacles.Obstacle;

import java.util.ArrayList;

public class MainGameViewModel extends ViewModel {

    private Ball ball;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

    public MainGameViewModel() {
        ball = new Ball();
    }

    public void sensorsMoved(float deltaX, float deltaY, float currentX, float currentY) {
        ball.moveBall(deltaX, deltaY, currentX, currentY);
    }

    public MutableLiveData<Pair<Float, Float>> moveBall() {
        return ball.deltaXY;
    }

    public ArrayList<Obstacle> loadLevel(int levelId){
        // todo use dao to get level with components from repository

        return obstacles;
    }

    public void initScreen(int screenWidth, int screenHeight) {
        ball.initScreen(screenWidth, screenHeight);
    }

    public void initBall(int width, float height) {
        ball.initBallDims(width, height);
    }

    private ArrayList<Obstacle> getLevelObstacles() {
        return obstacles;
    }

}
