package com.example.crazyball;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class FullscreenActivity extends AppCompatActivity {

    private static final int  UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private final Runnable mHideRunnable = this::hide;

    private SensorManager sensorManager;
    private ImageView ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        mContentView = findViewById(R.id.fullscreen_content);
        sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        ball = findViewById(R.id.ball);
        sensorManager.registerListener(sensorListener,  sensor, SensorManager.SENSOR_DELAY_NORMAL);
        springAnimationX = new SpringAnimation(ball, DynamicAnimation.TRANSLATION_X);
        springAnimationY = new SpringAnimation(ball, DynamicAnimation.TRANSLATION_Y);
    }

    private int sensorReadingNumber = 0;
    SpringAnimation springAnimationX;
    SpringAnimation springAnimationY;

    SensorEventListener sensorListener = new SensorEventListener() {

        float deltaX = 0;
        float deltaY = 0;

        public void onSensorChanged(SensorEvent event) {
            sensorReadingNumber++;
            if(sensorReadingNumber % 3 == 0) {
                // use the aggregated values of deltas to normalize the effect of false readings
                springAnimationX
                        .animateToFinalPosition(ball.getTranslationX() - deltaX * 100);
                springAnimationY
                        .animateToFinalPosition(ball.getTranslationY() + deltaY * 100);
                deltaX = 0;
                deltaY = 0;
            }
            if(sensorReadingNumber % 10 == 0) {
                Log.d("sensor","========= ACCELEROMETER SENSOR X value = "+ event.values[0] + "\n");
                Log.d("sensor","========= ACCELEROMETER SENSOR Y value = "+ event.values[1] + "\n");
                Log.d("sensor","========= ACCELEROMETER SENSOR Z value = "+ event.values[2] + "\n");
                Log.d("sensor","========= _____________________________________\n");
            } else {
                deltaX += event.values[0];
                deltaY += event.values[1];
            }
        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        delayedHide(100);
    }


    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }


    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}