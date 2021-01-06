package com.example.crazyball.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.crazyball.model.Ball;
import com.example.crazyball.model.LevelLoader;
import com.example.crazyball.model.LevelRepository;
import com.example.crazyball.model.obstacles.ComponentModel;
import com.example.crazyball.model.obstacles.IFailable;
import com.example.crazyball.model.obstacles.IWinnable;
import com.example.crazyball.model.tables.relations.LevelWithComponents;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.asin;
import static java.lang.Math.atan2;

public class MainGameViewModel extends AndroidViewModel {

    private final LevelRepository levelRepository;
    private final LiveData<List<LevelWithComponents>> allLevels;
    private Ball ball;
    private LiveData<ArrayList<ComponentModel>> obstacles;

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

    public LiveData<ArrayList<ComponentModel>> loadLevel(int levelId, IFailable onLevelFailed, IWinnable onLevelWon){
        this.obstacles = levelRepository.loadLevel(levelId,  onLevelFailed, onLevelWon);
        ball.addObstacles(obstacles);
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

    private LiveData<ArrayList<ComponentModel>> getLevelObstacles() {
        return obstacles;
    }

    public void sensorsMoved(float[] rotation_matrix) {
//        double phi = atan2(-rotation_matrix[3], rotation_matrix[0]);
        double theta = asin(rotation_matrix[6]);
        double psi = atan2(-rotation_matrix[7], rotation_matrix[8]);
        // positive towards down left
        double x = theta;
        double y = psi;

//        Log.d("sensor_read","========= SENSOR X2 value = "+ phi + "\n");
        Log.d("sensor_read","========= SENSOR Y2 value = " + theta + "\n");
        Log.d("sensor_read","========= SENSOR Z2 value = " + psi + "\n");
        Log.d("sensor_read","========= _____________________________________\n");

        ball.updateNextSensorReading(x, y);

    }


}
