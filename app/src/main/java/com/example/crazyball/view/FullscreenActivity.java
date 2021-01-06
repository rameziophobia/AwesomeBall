package com.example.crazyball.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.util.Pair;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Insets;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.widget.ImageView;

import com.example.crazyball.R;
import com.example.crazyball.model.obstacles.ComponentModel;
import com.example.crazyball.model.obstacles.IFailable;
import com.example.crazyball.model.obstacles.IWinnable;
import com.example.crazyball.viewmodel.MainGameViewModel;

import java.util.ArrayList;


public class FullscreenActivity extends AppCompatActivity {


    private SensorManager sensorManager;
    private ImageView ballImageView;
    private MainGameViewModel gameViewModel;
    private View mContentView;

    private int sensorReadingNumber = 0;
    private SpringAnimation springAnimationX;
    private SpringAnimation springAnimationY;
    private int levelId;
    private IFailable onLevelFailed;
    private IWinnable onLevelWon;
    private Sensor rotationVectorSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        levelId = intent.getIntExtra("levelId", 0);

        setContentView(R.layout.activity_fullscreen);
        mContentView = findViewById(R.id.game_constraint_layout);

        setOnLevelWonCallback();
        setOnLevelFailedCallback();

        gameViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(MainGameViewModel.class);

        gameViewModel.initScreen(getScreenWidth(), getScreenHeight());
        loadLevelImages();

        sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        ballImageView = findViewById(R.id.ball);


        ballImageView.post(() -> {
            gameViewModel.initBall(ballImageView.getWidth(), ballImageView.getHeight());
        });



        gameViewModel.moveBall().observe(this, this::onModelBallChanged);

        springAnimationX = new SpringAnimation(ballImageView, DynamicAnimation.TRANSLATION_X);
        springAnimationY = new SpringAnimation(ballImageView, DynamicAnimation.TRANSLATION_Y);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener, rotationVectorSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopBallMovements();
    }

    private void setOnLevelFailedCallback() {
        onLevelFailed = () -> {
            stopBallMovements();
            sensorManager.unregisterListener(sensorListener);
            Intent intent = new Intent(this, LevelResultActivity.class);
            intent.putExtra("hasWon", false);
            intent.putExtra("currentLevel", levelId);
            intent.putExtra("score", 0);
            startActivity(intent);
        };
    }

    private void setOnLevelWonCallback() {
        onLevelWon = () -> {
            stopBallMovements();
            sensorManager.unregisterListener(sensorListener);
            Intent intent = new Intent(this, LevelResultActivity.class);
            intent.putExtra("hasWon", true);
            intent.putExtra("currentLevel", levelId);
            intent.putExtra("score", 1000);
            startActivity(intent);
        };
    }

    private void stopBallMovements() {
        sensorManager.unregisterListener(sensorListener);
        springAnimationY.cancel();
        springAnimationX.cancel();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PauseLevelActivity.class);
        intent.putExtra("currentLevel", levelId);
        startActivity(intent);
    }

    private void loadLevelImages() {
        final LiveData<ArrayList<ComponentModel>> componentModelListLiveData = gameViewModel.loadLevel(levelId, onLevelFailed, onLevelWon);
        componentModelListLiveData.observe(this, new Observer<ArrayList<ComponentModel>>() {
            @Override
            public void onChanged(ArrayList<ComponentModel> componentModels) {
                {
                    componentModelListLiveData.removeObserver(this);
                    ConstraintLayout layout = findViewById(R.id.game_constraint_layout);
                    ConstraintSet set = new ConstraintSet();
                    for (ComponentModel componentModel : componentModels) {
                        addComponentToLayout(layout, set, componentModel);
                    }
                }
            }
        });
    }

    private void addComponentToLayout(ConstraintLayout layout, ConstraintSet set, ComponentModel componentModel) {
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(componentModel.getComponentData().getImageId());
        imageView.setId(componentModel.getComponentData().getId());
        layout.addView(imageView, 0);

        set.clone(layout);
        set.connect(imageView.getId(), ConstraintSet.TOP,
                layout.getId(), ConstraintSet.TOP,
                componentModel.getStartY());

        set.connect(imageView.getId(), ConstraintSet.LEFT,
                layout.getId(), ConstraintSet.LEFT,
                componentModel.getStartX());

        set.applyTo(layout);

        imageView.post(() -> {
            componentModel.setWidth(imageView.getWidth());
            componentModel.setHeight(imageView.getHeight());
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        hideSystemUI();
    }


    private void hideSystemUI() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
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


    SensorEventListener sensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent event) {
            sensorReadingNumber++;
            if(sensorReadingNumber % 100 == 0) {
                Log.d("sensor_read","========= SENSOR X1 value = "+ event.values[0] + "\n");
                Log.d("sensor_read","========= SENSOR Y1 value = "+ event.values[1] + "\n");
                Log.d("sensor_read","========= SENSOR Z1 value = "+ event.values[2] + "\n");
                Log.d("sensor_read","========= _____________________________________\n");
            }

            float[] rotation_matrix = new float[9];
            SensorManager.getRotationMatrixFromVector(rotation_matrix, event.values);
            gameViewModel.sensorsMoved(rotation_matrix);
            gameViewModel.sensorsMoved(ballImageView.getX(), ballImageView.getY());
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