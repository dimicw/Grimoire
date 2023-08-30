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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        implements NavigationView.OnNavigationItemSelectedListener,
        ChangeCharacterFragment.CharacterClickListener,
        AddSpellFragment.SpellClickListener {

    private ArrayList<Spell> allSpells = new ArrayList<>();
    private ArrayList<ChosenSpell> chosenSpells = new ArrayList<>();
    private ArrayList<Character> allCharacters = new ArrayList<>();

    private int currentCharacterId;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private View headerView;
    private TextView headerName, headerClass;
    private ImageView headerImage;

    private int[] classImages = {
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
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);
        headerName = headerView.findViewById(R.id.header_name);
        headerClass = headerView.findViewById(R.id.header_class);
        headerImage = headerView.findViewById(R.id.header_image);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        addSampleCharacter();                       // TODO: CHANGE IN FUTURE
        changeCharacter(0);

        if(savedInstanceState == null) {
            openBrowseSpells();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_browse_spells)
            openBrowseSpells();
        else if(item.getItemId() == R.id.nav_add_spell) {
            ArrayList<ChosenSpell> allAsChosen = new ArrayList<>();
            for (Spell spell : allSpells)
                allAsChosen.add(new ChosenSpell(spell, R.drawable.big_book));

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    AddSpellFragment.newInstance(allAsChosen, this)).commit();
        }
        else if(item.getItemId() == R.id.nav_switch_character)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    ChangeCharacterFragment.newInstance(allCharacters, this)).commit();
        else if(item.getItemId() == R.id.nav_add_character)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AddCharacterFragment()).commit();
        else if(item.getItemId() == R.id.nav_add_nonclass_spell);
        else if(item.getItemId() == R.id.nav_browse_all_spells) {
            chosenSpells = new ArrayList<>();
            for(Spell spell : allSpells)
                chosenSpells.add(new ChosenSpell(spell, R.drawable.big_book));
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    BrowseSpellsFragment.newInstance(chosenSpells, classImages)).commit();
        }

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


    private void openBrowseSpells() {
        chosenSpells = new ArrayList<>();
        for (int i = 0; i < allSpells.size(); i++)
            for (BoundSpell boundSpell : allCharacters.get(currentCharacterId).getBoundSpells())
                if (i == boundSpell.getSpellId())
                    chosenSpells.add(
                            new ChosenSpell(allSpells.get(i), boundSpell.getSpellImage())
                    );

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                BrowseSpellsFragment.newInstance(chosenSpells, classImages)).commit();
        navigationView.setCheckedItem(R.id.nav_browse_spells);
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

    public void addSampleCharacter() {
        allCharacters.add(new Character("Example character 1", 8,8,8,
                8,8,8,
                1, "Cleric", classImages[3]));
        allCharacters.get(0).addSpell(3, classImages[3]);
        allCharacters.get(0).addSpell(4, classImages[3]);
        allCharacters.get(0).addSpell(10, classImages[3]);
        allCharacters.get(0).addSpell(32, classImages[2]);
        allCharacters.get(0).addSpell(123, classImages[4]);


        allCharacters.add(new Character("Random Elf Druid", 8,8,8,
                8, 8,8,
                10, "Druid", classImages[4]));
        allCharacters.get(1).addSpell(30, classImages[4]);
        allCharacters.get(1).addSpell(40, classImages[4]);
        allCharacters.get(1).addSpell(100, classImages[4]);
        allCharacters.get(1).addSpell(320, classImages[1]);
        allCharacters.get(1).addSpell(12, classImages[1]);
    }

    @Override
    public void onCharacterClick(int position) {
        changeCharacter(position);
        openBrowseSpells();
    }

    @Override
    public void onSpellClick(int position) {
        boolean hasSpell = false;

        for (BoundSpell boundSpell : allCharacters.get(currentCharacterId).getBoundSpells())
            if (position == boundSpell.getSpellId()) {
                hasSpell = true;
                break;
            }

        if (hasSpell)
            Toast.makeText(this, "You already have this spell", Toast.LENGTH_SHORT).show();
        else
            allCharacters.get(currentCharacterId)
                    .addSpell(position, allCharacters.get(currentCharacterId).getImage()); //TODO: change image

        openBrowseSpells();
    }

    private void changeCharacter(int id) {
        currentCharacterId = id;
        getSupportActionBar().setTitle(allCharacters.get(currentCharacterId).getName());

        headerName.setText(allCharacters.get(currentCharacterId).getName());
        headerClass.setText(allCharacters.get(currentCharacterId).getMainClass());
        headerImage.setImageResource(allCharacters.get(currentCharacterId).getImage());
    }
}