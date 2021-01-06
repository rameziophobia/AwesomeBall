package com.example.crazyball.model.tables;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;
import com.example.crazyball.model.tables.entities.LevelEntity;
import com.example.crazyball.model.tables.relations.LevelWithComponents;

import java.util.List;

@Dao
public abstract class
LevelDao {

    @Transaction
    @Query("SELECT * FROM level")
    public abstract LiveData<List<LevelWithComponents>> getLevelsWithComponents();

    @Transaction
    @Query("SELECT * FROM level")
    public abstract LiveData<List<LevelEntity>> getLevels();

    @Transaction
    @Query("SELECT * FROM level where id=:id")
    public abstract LiveData<List<LevelWithComponents>> getLevelWithComponents(int id);

//    @Transaction
//    @Query("SELECT * FROM level where id=:id")
//    public List<LevelWithComponents> getLevelWithComponentsSynchronous(int id);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insert(LevelEntity level);

    @Query("DELETE FROM level;")
    abstract void deleteAllLevels();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertComponent(LevelComponentEntity component);

    @Transaction
    void insertLevelWithComponents(LevelEntity level, List<LevelComponentEntity> componentEntities) {
        insert(level);
        for (LevelComponentEntity component: componentEntities) {
            insertComponent(component);
        }
    }


}
