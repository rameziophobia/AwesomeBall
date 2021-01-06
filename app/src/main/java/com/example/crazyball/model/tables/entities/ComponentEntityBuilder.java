package com.example.crazyball.model.tables.entities;

import com.example.crazyball.model.EComponentType;

public class ComponentEntityBuilder {

    private final long levelId;
    private int locationX;
    private int locationY;
    private String type;
    private int imageId;

    public ComponentEntityBuilder(long levelId) {
        this.levelId = levelId;
    }

    public ComponentEntityBuilder setLocationX(int x) {
        locationX = x;
        return this;
    }

    public ComponentEntityBuilder setLocationY(int y) {
        locationY = y;
        return this;
    }

    public ComponentEntityBuilder setImageId(int imageId) {
        this.imageId = imageId;
        return this;
    }

    public ComponentEntityBuilder setType(EComponentType type) {
        this.type = type.getTypeString();
        return this;
    }

    public long getLevelId() {
        return levelId;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public String getType() {
        return type;
    }

    public LevelComponentEntity build() {
        LevelComponentEntity levelComponentEntity;
        levelComponentEntity = new LevelComponentEntity(this);
        return levelComponentEntity;
    }

    public int getImageId() {
        return imageId;
    }
}
