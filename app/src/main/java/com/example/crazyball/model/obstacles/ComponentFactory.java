package com.example.crazyball.model.obstacles;

import com.example.crazyball.model.EComponentType;
import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public class ComponentFactory {
    public static ComponentModel createComponent(LevelComponentEntity componentData, int tileWidth, int tileHeight) {
        ComponentModel ob;
        switch (EComponentType.valueOf(componentData.getType())) {
            case wall:
                ob = Wall.createWall(componentData, tileWidth, tileHeight);
                break;
            case trap:
                ob = Trap.createTrap(componentData, tileWidth, tileHeight);
                break;
            case key:
                ob = KeyTile.createKeyTile(componentData, tileWidth, tileHeight);
                break;
            case door:
                ob = Door.createDoor(componentData, tileWidth, tileHeight);
                break;
            case target:
                ob = (Target)Target.createTarget(componentData, tileWidth, tileHeight);
                break;
            default:
                throw new IllegalStateException("Unexpected value in obstacle creation: " + componentData.getType());
        }
        return ob;
    }
}
