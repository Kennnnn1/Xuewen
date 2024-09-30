package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class categories extends AppCompatActivity {


    TextView seemore1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        seemore1 = findViewById(R.id.seemore1);

        seemore1.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), categories2.class);
            startActivity(intent);
        });
    }
}