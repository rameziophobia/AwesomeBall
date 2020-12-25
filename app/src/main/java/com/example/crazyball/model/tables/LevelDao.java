package com.example.crazyball.model.tables;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.crazyball.model.tables.entities.LevelEntity;
import com.example.crazyball.model.tables.relations.LevelWithComponents;

import java.util.List;

@Dao
public interface LevelDao {

    @Transaction
    @Query("SELECT * FROM level")
    public LiveData<List<LevelWithComponents>> getLevelsWithComponents();

    @Transaction
    @Query("SELECT * FROM level")
    public LiveData<List<LevelEntity>> getLevels();

    @Transaction
    @Query("SELECT * FROM level where id=:id")
    public LiveData<List<LevelWithComponents>> getLevelWithComponents(int id);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(LevelEntity level);

    @Query("DELETE FROM level;")
    void deleteAllLevels();
}
