package com.example.grimuare;

public class SpellModel {
    private String name, levelAndSchool;
    private int image;

    public SpellModel(String name, String levelAndSchool, int image) {
        this.name = name;
        this.levelAndSchool = levelAndSchool;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getLevelAndSchool() {
        return levelAndSchool;
    }

    public int getImage() {
        return image;
    }
}
