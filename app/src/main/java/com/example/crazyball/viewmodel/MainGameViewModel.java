package com.example.crazyball.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.crazyball.model.Ball;

public class MainGameViewModel extends ViewModel {

    private Ball ball;

    public MainGameViewModel() {
        ball = new Ball();
    }

    public void sensorsMoved(float deltaX, float deltaY, float currentX, float currentY) {
        ball.moveBall(deltaX, deltaY, currentX, currentY);
    }

    public MutableLiveData<Pair<Float, Float>> moveBall() {
        return ball.deltaXY;
    }

    public void initScreen(int screenWidth, int screenHeight) {
        ball.initScreen(screenWidth, screenHeight);
    }

    public void initBall(int width, float height) {
        ball.initBallDims(width, height);
    }
}
