package com.example.grimuare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<Spell> allSpells = new ArrayList<>();
    ArrayList<ChosenSpell> chosenSpells = new ArrayList<>();

    int[] classImages = {
            R.drawable.class_icon___artificer,  //0
            R.drawable.class_icon___bard,       //1
            R.drawable.class_icon___blood_mage, //2
            R.drawable.class_icon___cleric,     //3
            R.drawable.class_icon___druid,      //4
            R.drawable.class_icon___paladin,    //5
            R.drawable.class_icon___ranger,     //6
            R.drawable.class_icon___sorcerer,   //7
            R.drawable.class_icon___warlock,    //8
            R.drawable.class_icon___wizard      //9
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load XML for parsing
        AssetManager assetManager = getAssets();
        InputStream inputStream;
        try {
            inputStream = assetManager.open("all_spells.xml");
            allSpells = parseSpellXML(inputStream);
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        assignChosenSpells();                       //CHANGE IN FUTURE

        Spell_RecyclerViewAdapter adapter = new Spell_RecyclerViewAdapter(
                this, chosenSpells, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void assignChosenSpells() {
        for (Spell s : allSpells)
            chosenSpells.add(new ChosenSpell(s, classImages[3]));
    }

    private static ArrayList<Spell> parseSpellXML(InputStream inputStream) {
        ArrayList<Spell> spellsList = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            NodeList spellNodes = document.getElementsByTagName("spell");
            for (int i = 0; i < spellNodes.getLength(); i++) {
                Element spellElement = (Element) spellNodes.item(i);

                NodeList descriptionNodes = spellElement.getElementsByTagName("description");
                String[] descriptionStrings = new String[descriptionNodes.getLength()];

                for(int j = 0; j < descriptionNodes.getLength(); j++) {
                    Element descriptionElement = (Element) descriptionNodes.item(j);
                    descriptionStrings[j] = descriptionElement.getTextContent();
                }

                Spell spell = new Spell(
                    spellElement.getElementsByTagName("name").item(0).getTextContent(),
                    spellElement.getElementsByTagName("source").item(0).getTextContent(),
                    Integer.parseInt(spellElement.getElementsByTagName("level").item(0).getTextContent()),
                    spellElement.getElementsByTagName("school").item(0).getTextContent(),
                    spellElement.getElementsByTagName("castingTime").item(0).getTextContent(),
                    Boolean.parseBoolean(spellElement.getElementsByTagName("ritual").item(0).getTextContent()),
                    spellElement.getElementsByTagName("range").item(0).getTextContent(),
                    spellElement.getElementsByTagName("components").item(0).getTextContent(),
                    Boolean.parseBoolean(spellElement.getElementsByTagName("v").item(0).getTextContent()),
                    Boolean.parseBoolean(spellElement.getElementsByTagName("s").item(0).getTextContent()),
                    Boolean.parseBoolean(spellElement.getElementsByTagName("m").item(0).getTextContent()),
                    spellElement.getElementsByTagName("duration").item(0).getTextContent(),
                    Boolean.parseBoolean(spellElement.getElementsByTagName("concentration").item(0).getTextContent()),
                    descriptionStrings
                );

                spellsList.add(spell);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return spellsList;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, SpellCardActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("SPELL", chosenSpells.get(position));
        intent.putExtras(bundle);

        startActivity(intent);
    }
}