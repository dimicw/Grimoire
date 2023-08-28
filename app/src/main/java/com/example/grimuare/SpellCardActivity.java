package com.example.grimuare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SpellCardActivity extends AppCompatActivity {

    Intent intent;
    Bundle bundle;

    Spell spell;

    ImageButton backButton;
    ImageView backgroundImage;
    TextView tvName, tvLevelAndSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_card);

        intent = getIntent();
        bundle = intent.getExtras();

        spell = (Spell)bundle.getSerializable("SPELL");

        backButton = findViewById(R.id.backButton);
        backgroundImage = findViewById(R.id.backgroundImage);
        tvName = findViewById(R.id.nameCard);
        tvLevelAndSchool = findViewById(R.id.levelAndSchoolCard);

        backButton.setOnClickListener(view -> onBackPressed());

        backgroundImage.setImageResource(
             getIntent().getIntExtra("IMAGE", 0));
        tvName.setText(spell.getName());
        tvLevelAndSchool.setText(spell.getLevelAndSchool());
    }
}