package com.example.grimuare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Spell> allSpells = new ArrayList<>();
    ArrayList<ChosenSpell> chosenSpells = new ArrayList<>();

    private DrawerLayout drawerLayout;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    BrowseSpellsFragment.newInstance(chosenSpells, classImages)).commit();
            navigationView.setCheckedItem(R.id.nav_browse_spells);
        }

        assignChosenSpells();                       //CHANGE IN FUTURE
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_browse_spells)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    BrowseSpellsFragment.newInstance(chosenSpells, classImages)).commit();
        else if(item.getItemId() == R.id.nav_add_spell)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AddSpellFragment()).commit();
        else if(item.getItemId() == R.id.nav_switch_character)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ChangeCharacterFragment()).commit();
        else if(item.getItemId() == R.id.nav_add_character)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AddCharacterFragment()).commit();

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
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
}