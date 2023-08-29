package com.example.grimuare;

public class Character {
    private int strength, dexterity, constitution, intelligence, wisdom, charisma;
    private int level;
    private String mainClass;
    private int image;

    public Character(int strength, int dexterity, int constitution, int intelligence, int wisdom,
                     int charisma, int level, String mainClass, int image) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.level = level;
        this.mainClass = mainClass;
        this.image = image;
    }

    public int getStrength() {
        return strength;
    }
    public int getDexterity() {
        return dexterity;
    }
    public int getConstitution() {
        return constitution;
    }
    public int getIntelligence() {
        return intelligence;
    }
    public int getWisdom() {
        return wisdom;
    }
    public int getCharisma() {
        return charisma;
    }
    public int getLevel() {
        return level;
    }
    public int getImage() {
        return image;
    }
    public String getMainClass() {
        return mainClass;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }
    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }
    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setImage(int image) {
        this.image = image;
    }
    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public int getModifier(String ability) {
        int modifier = 0;
        switch(ability) {
            case "str":
                modifier = getAbilityModifier(this.strength);
                break;
            case "dex":
                modifier = getAbilityModifier(this.dexterity);
                break;
            case "con":
                modifier = getAbilityModifier(this.constitution);
                break;
            case "int":
                modifier = getAbilityModifier(this.intelligence);
                break;
            case "wis":
                modifier = getAbilityModifier(this.wisdom);
                break;
            case "cha":
                modifier = getAbilityModifier(this.charisma);
                break;
        }
        modifier += 2 + (this.level - 1) / 4;

        return modifier;
    }

    private int getAbilityModifier(int value) {
        return value / 2 - 5;
    }
}
