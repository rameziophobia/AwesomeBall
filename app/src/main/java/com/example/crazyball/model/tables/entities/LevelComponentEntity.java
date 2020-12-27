package com.example.crazyball.model.tables.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "level_component",
        foreignKeys = @ForeignKey(entity = LevelEntity.class,
        parentColumns = "id",
        childColumns = "level_id",
        onDelete = ForeignKey.CASCADE))

public class LevelComponentEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;

    @NonNull
    @ColumnInfo(name = "level_id")
    private final long levelId;

    @NonNull
    @ColumnInfo(name = "type")
    private final String type;

    @NonNull
    @ColumnInfo(name = "location_x")
    private final int locationX;

    @NonNull
    @ColumnInfo(name = "location_y")
    private final int locationY;

    @NonNull
    @ColumnInfo(name = "image_id")

    private final int imageId;

    public LevelComponentEntity(@NonNull String type, int locationX, int locationY, int imageId, long levelId) {
        this.type = type;
        this.locationX = locationX;
        this.locationY = locationY;
        this.imageId = imageId;
        this.levelId = levelId;
    }


    @NonNull
    public String getType() {
        return type;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public int getImageId() {
        return imageId;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public long getLevelId() {
        return levelId;
    }
}