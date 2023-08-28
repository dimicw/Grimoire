package com.example.grimuare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SpellCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_card);

        String name = getIntent().getStringExtra("NAME");
        TextView tv = findViewById(R.id.textView);
        tv.setText(name);
    }
}