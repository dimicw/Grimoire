package com.example.grimuare;

public class SpellOLD {
    private String name, school, castingTime, range, duration, material, atHigherLevels;
    private String[] description;
    private int level;
    private boolean ritual, concentration, componentsV, componentsS, componentsM,
        artificer, bard, bloodMage, cleric, druid, paladin, ranger, sorcerer, warlock, wizard;


    public SpellOLD(String name, String school, String castingTime, String range, String duration,
                    String material, String atHigherLevels, String[] description,
                    int level, boolean ritual, boolean concentration, boolean componentsV,
                    boolean componentsS, boolean componentsM, boolean artificer, boolean bard,
                    boolean bloodMage, boolean cleric, boolean druid, boolean paladin, boolean ranger,
                    boolean sorcerer, boolean warlock, boolean wizard) {
        this.name = name;
        this.school = school;
        this.castingTime = castingTime;
        this.range = range;
        this.duration = duration;
        this.material = material;
        this.atHigherLevels = atHigherLevels;
        this.description = description;
        this.level = level;
        this.ritual = ritual;
        this.concentration = concentration;
        this.componentsV = componentsV;
        this.componentsS = componentsS;
        this.componentsM = componentsM;
        this.artificer = artificer;
        this.bard = bard;
        this.bloodMage = bloodMage;
        this.cleric = cleric;
        this.druid = druid;
        this.paladin = paladin;
        this.ranger = ranger;
        this.sorcerer = sorcerer;
        this.warlock = warlock;
        this.wizard = wizard;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }

    public String getCastingTime() {
        return castingTime;
    }

    public String getRange() {
        return range;
    }

    public String getDuration() {
        return duration;
    }

    public String getMaterial() {
        return material;
    }

    public String getAtHigherLevels() {
        return atHigherLevels;
    }

    public String getDescription() {
        String singleString = "";
        for (int i = 0; i < description.length; i++)
            singleString += description[i] + "\n";
        return singleString;
    }

    public int getLevel() {
        return level;
    }

    public boolean isRitual() {
        return ritual;
    }

    public boolean isConcentration() {
        return concentration;
    }

    public boolean isComponentsV() {
        return componentsV;
    }

    public boolean isComponentsS() {
        return componentsS;
    }

    public boolean isComponentsM() {
        return componentsM;
    }

    public boolean isArtificer() {
        return artificer;
    }

    public boolean isBard() {
        return bard;
    }

    public boolean isBloodMage() {
        return bloodMage;
    }

    public boolean isCleric() {
        return cleric;
    }

    public boolean isDruid() {
        return druid;
    }

    public boolean isPaladin() {
        return paladin;
    }

    public boolean isRanger() {
        return ranger;
    }

    public boolean isSorcerer() {
        return sorcerer;
    }

    public boolean isWarlock() {
        return warlock;
    }

    public boolean isWizard() {
        return wizard;
    }
}

