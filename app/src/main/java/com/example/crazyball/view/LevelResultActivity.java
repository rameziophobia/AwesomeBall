package com.example.crazyball.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crazyball.R;

import java.util.Random;

public class LevelResultActivity extends AppCompatActivity {

    private long currentLevel;
    private int score;
    private ConstraintLayout starsConstraintLayout;
    private int numStars;
    private boolean hasWon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_result_menu);
        starsConstraintLayout = findViewById(R.id.result_constraint_layout);

        Intent intent = getIntent();
        this.currentLevel = intent.getLongExtra("currentLevel", 0);
        this.score = intent.getIntExtra("currentLevel", 0);
        hasWon = intent.getBooleanExtra("hasWon", true);

        starsConstraintLayout.post(() -> {
            if(hasWon) {
                changeTitleToWin();
                startStarsAnimation();
            } else {
                changeTitleToLoss();
            }
            startScoreAnimation();
        });
    }

    private void startScoreAnimation() {
        // todo score animation
    }

    private void changeTitleToLoss() {
        TextView title = findViewById(R.id.result_status_textView);
        title.setText(R.string.lossTitle);
    }

    private void startStarsAnimation() {
        // todo get num stars
        numStars = 3;

        animateStars(0);
    }

    private void animateStars(int currentStarIndex) {
        if (currentStarIndex >= numStars) {
            return;
        }

        ImageView coloredStarImageView =  getColoredStarImageView(currentStarIndex);
        ImageView GreyStarImageView = getGreyStarImageView(currentStarIndex);

        coloredStarImageView.setVisibility(View.VISIBLE);

        int[] greyStarCoordinates = new int[2];
        int[] coloredStarCoordinates = new int[2];
        GreyStarImageView.getLocationOnScreen(greyStarCoordinates);
        coloredStarImageView.getLocationOnScreen(coloredStarCoordinates);

        SpringAnimation springAnimationX = new SpringAnimation(coloredStarImageView, DynamicAnimation.TRANSLATION_X);
        SpringAnimation springAnimationY = new SpringAnimation(coloredStarImageView, DynamicAnimation.TRANSLATION_Y);

        springAnimationX.animateToFinalPosition(greyStarCoordinates[0] - coloredStarCoordinates[0]);
        springAnimationY.animateToFinalPosition(greyStarCoordinates[1] - coloredStarCoordinates[1]);

        springAnimationX.addEndListener((animation, canceled, value, velocity) -> {
            animateStars(currentStarIndex + 1);
        });
    }

    private ImageView getColoredStarImageView(int currentStarIndex) {
        switch (currentStarIndex) {
            case 0:
                return findViewById(R.id.first_purple_star_imageView);
            case 1:
                return findViewById(R.id.second_purple_star_imageView);
            case 2:
                return findViewById(R.id.third_purple_star_imageView);
        }
        return null;
    }

    private ImageView getGreyStarImageView(int currentStarIndex) {
        switch (currentStarIndex) {
            case 0:
                return findViewById(R.id.first_star_imageView);
            case 1:
                return findViewById(R.id.second_star_imageView);
            case 2:
                return findViewById(R.id.third_star_imageView);
        }
        return null;
    }


    private void changeTitleToWin() {
        WinningWords[] winningWords = WinningWords.values();
        int randomNum = new Random().nextInt(winningWords.length);
        WinningWords randomWord = winningWords[randomNum];

        TextView title = findViewById(R.id.result_status_textView);
        title.setText(randomWord.getWinningString());

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        findViewById(R.id.level_result_layout).setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }



    @Override
    public void onBackPressed() {

    }

    public void onNextButtonPressed(View view) {
    }

    public void onTryAgainButtonPressed(View view) {
        Intent intent = new Intent(this, LevelSelector.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void onMainMenuButtonPressed(View view) {
        Intent intent = new Intent(this, LevelSelector.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}