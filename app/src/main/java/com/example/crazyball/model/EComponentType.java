package com.example.crazyball.model;

public enum EComponentType {
    trap("trap"),
    wall("wall"),
    key("key"),
    door("door"),
    target("target"),
    star("star"),
    ;

    private final String typeString;

    EComponentType(String winningString) {
        this.typeString = winningString;
    }

    public String getTypeString() {
        return typeString;
    }
}