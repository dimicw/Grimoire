package com.example.grimuare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SpellCardActivity extends AppCompatActivity {

    ImageButton backButton;
    ImageView backgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_card);

        backButton = findViewById(R.id.backButton);
        backgroundImage = findViewById(R.id.backgroundImage);

        backgroundImage.setImageResource(
             getIntent().getIntExtra("IMAGE", 0));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        String name = getIntent().getStringExtra("NAME");
        TextView tv = findViewById(R.id.nameCard);
        tv.setText(name);
    }
}