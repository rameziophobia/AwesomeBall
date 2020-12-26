package com.example.crazyball.view;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Insets;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.ImageView;

import com.example.crazyball.R;
import com.example.crazyball.viewmodel.MainGameViewModel;


public class FullscreenActivity extends AppCompatActivity {


    private SensorManager sensorManager;
    private ImageView ballImageView;
    private MainGameViewModel gameViewModel;
    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        mContentView = findViewById(R.id.fullscreen_content);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        ballImageView = findViewById(R.id.ball);

        gameViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(MainGameViewModel.class);

        ballImageView.post(() -> {
            gameViewModel.initBall(ballImageView.getWidth(), ballImageView.getHeight());
        });


        gameViewModel.initScreen(getScreenWidth(), getScreenHeight());

        int wid = getScreenWidth();
        int height = getScreenHeight();
        Log.d("bounds", "are " +wid+ " " + height);
        //todo screen size and check borders

        gameViewModel.moveBall().observe(this, this::onModelBallChanged);

        sensorManager.registerListener(sensorListener,  sensor, SensorManager.SENSOR_DELAY_NORMAL);
        springAnimationX = new SpringAnimation(ballImageView, DynamicAnimation.TRANSLATION_X);
        springAnimationY = new SpringAnimation(ballImageView, DynamicAnimation.TRANSLATION_Y);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    public int getScreenWidth() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            return windowMetrics.getBounds().width() - insets.left - insets.right;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.widthPixels;
        }
    }

    public int getScreenHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets()
                    .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            return windowMetrics.getBounds().height() - insets.top - insets.bottom;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.heightPixels;
        }
    }



    private int sensorReadingNumber = 0;
    SpringAnimation springAnimationX;
    SpringAnimation springAnimationY;

    SensorEventListener sensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent event) {
            sensorReadingNumber++;
            if(sensorReadingNumber % 10 == 0) {
                Log.d("sensor","========= ACCELEROMETER SENSOR X value = "+ event.values[0] + "\n");
                Log.d("sensor","========= ACCELEROMETER SENSOR Y value = "+ event.values[1] + "\n");
                Log.d("sensor","========= ACCELEROMETER SENSOR Z value = "+ event.values[2] + "\n");
                Log.d("sensor","========= _____________________________________\n");
            }

            gameViewModel.sensorsMoved(event.values[0], event.values[1], ballImageView.getX(), ballImageView.getY());
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void animateBall(Pair<Float, Float> XYPair) {
        springAnimationX
                .animateToFinalPosition(ballImageView.getTranslationX() - XYPair.first);
        springAnimationY
                .animateToFinalPosition(ballImageView.getTranslationY() + XYPair.second);
    }

    private void onModelBallChanged(Pair<Float, Float> XYPair) {
        animateBall(XYPair);
    }
}