package com.example.crazyball.model.obstacles;

import com.example.crazyball.model.tables.entities.LevelComponentEntity;

public class ComponentFactory {
    public static ComponentModel createComponent(LevelComponentEntity componentData, int tileWidth, int tileHeight, IFailable failableListener, IWinnable winnableListener) {
        ComponentModel ob;
        switch (componentData.getType()) {
            case "wall":
                ob = Wall.createWall(componentData, tileWidth, tileHeight);
                break;
            case "trap":
                Trap trap = Trap.createTrap(componentData, tileWidth, tileHeight);
                trap.setFailableListener(failableListener);
                ob = trap;
                break;
            case "key":
                ob = KeyTile.createKeyTile(componentData, tileWidth, tileHeight);
                break;
            case "door":
                ob = Door.createDoor(componentData, tileWidth, tileHeight);
                break;
            case "target":
                Target target = (Target)Target.createTarget(componentData, tileWidth, tileHeight);
                target.setWinnableListener(winnableListener);
                ob = target;
                break;
            default:
                throw new IllegalStateException("Unexpected value in obstacle creation: " + componentData.getType());
        }
        return ob;
    }
}
