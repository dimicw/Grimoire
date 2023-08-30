package com.example.grimuare;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class XmlDeserializer {
    public static ArrayList<ChosenSpell> deserializeFromXml(String characterOrSpells) {
        String filePath = null;

        if (characterOrSpells == "character")
            filePath = "file:///android_asset/characters.xml";
        else if (characterOrSpells == "spells")
            filePath = "file:///android_asset/all_spells.xml";


        ArrayList<ChosenSpell> chosenList = new ArrayList<>();

        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            chosenList = (ArrayList<ChosenSpell>) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return chosenList;
    }
}
