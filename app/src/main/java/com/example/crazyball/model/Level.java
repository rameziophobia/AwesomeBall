package com.example.crazyball.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

@Entity(tableName = "level")
public class Level {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    // todo add foreign key for obstacles
//    private

}
