package com.example.crazyball.viewmodel;

import android.app.Application;

import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.crazyball.model.LevelRepository;
import com.example.crazyball.model.ScoreCalculator;
import com.example.crazyball.model.tables.relations.LevelWithComponents;

import java.util.List;

public class ScoreViewModel extends AndroidViewModel {

    private final LevelRepository levelRepository;
    private final ScoreCalculator scoreCalculator;

    public ScoreViewModel(Application application) {
        super(application);
        levelRepository = new LevelRepository(application);
        scoreCalculator = new ScoreCalculator();
    }

    public void loadLevel(int levelId){
        levelRepository.getLevelEntity(levelId).observeForever(scoreCalculator::setLevelEntity);
    }

    public LiveData<List<LevelWithComponents>> checkIfLevelExists(int levelId){
        return levelRepository.getLevelEntity(levelId);
    }

    public void sendScoreData(long elapsedTime, int starsCollected) {
        scoreCalculator.setScoreData(elapsedTime, starsCollected);
    }

    public MutableLiveData<Pair<Integer, Integer>> getScore() {
        return scoreCalculator.getScoreStarsPair();
    }

}
