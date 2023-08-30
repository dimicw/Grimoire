package com.example.grimuare;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class XmlSerializer implements Serializable {
    public static void serializeToXml(ArrayList<ChosenSpell> chosenSpells) {
        String xmlFilePath = "file:///android_asset/all_spells.xml";

        try (FileOutputStream fileOut = new FileOutputStream(xmlFilePath);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(chosenSpells);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
