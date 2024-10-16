package com.example.myapplication;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class dialect_information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialect_information);
        // Get the TextView that we want to animate
        TextView textView = findViewById(R.id.textView10);

        // Create an animation that moves the TextView horizontally
        ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "translationX", 0f, 500f);

        Intent intent = new Intent(this, categories.class);
        startActivity(intent);
        // Set the duration of the animation (5 seconds)
        animator.setDuration(5000); // 5000 milliseconds = 5 seconds

        // Start the animation
        animator.start();
    }
}