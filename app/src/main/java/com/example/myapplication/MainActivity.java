package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the TextView that we want to animate
        TextView textView = findViewById(R.id.text12);

        // Create an animation that moves the TextView horizontally
        ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "translationX", 0f, 500f);

        Intent intent = new Intent(this, log.class);
        startActivity(intent);
        // Set the duration of the animation (5 seconds)
        animator.setDuration(5000); // 5000 milliseconds = 5 seconds

        // Start the animation
        animator.start();
    }
}
