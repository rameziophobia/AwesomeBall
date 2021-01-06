package com.example.crazyball.model;

import android.util.Log;

import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;

import com.example.crazyball.model.tables.relations.LevelWithComponents;

import java.util.List;

public class ScoreCalculator {

    private LevelWithComponents levelEntity;
    private final MutableLiveData<Pair<Integer, Integer>> scoreStarsPair = new MutableLiveData<>();

    public void setLevelEntity(List<LevelWithComponents> levelWithComponents) {
        levelEntity = levelWithComponents.get(0);
    }

    public void setScoreData(long elapsedTime, int starsCollected) {
        int starScore = 3;
        int score = (int) (10000 - (elapsedTime / 20)); // todo smth from entity
        if(false){
            // todo time in level entity < elapsed time
            starScore--;
        }

        if(starsCollected != 3){
            starScore--;
        }

        for (int i = 1; i <= starsCollected; i++) {
            score += 1000 * i;
        }

        scoreStarsPair.postValue(Pair.create(score, starScore));
    }

    public MutableLiveData<Pair<Integer, Integer>> getScoreStarsPair() {
        return scoreStarsPair;
    }
}
