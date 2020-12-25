package com.example.crazyball.model.tables.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;
import com.example.crazyball.model.tables.entities.LevelEntity;

import java.util.List;


public class LevelWithComponents {
    @Embedded
    public LevelEntity Level;
    @Relation(
            parentColumn = "id",
            entityColumn = "level_id"
    )
    public List<LevelComponentEntity> components;
}