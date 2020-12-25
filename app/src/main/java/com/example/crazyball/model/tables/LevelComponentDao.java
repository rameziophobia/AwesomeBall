package com.example.crazyball.model.tables;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

@Dao
public interface LevelComponentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LevelComponentEntity component);

    @Query("DELETE FROM level_component where id=:id")
    void deleteById(int id);

}

