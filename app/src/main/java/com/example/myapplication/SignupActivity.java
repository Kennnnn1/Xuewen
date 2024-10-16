package com.example.myapplication; // Replace with your package name

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button signupButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        emailEditText = findViewById(R.id.emailEdittext); // Ensure this matches your XML ID
        passwordEditText = findViewById(R.id.password);   // Ensure this matches your XML ID
        signupButton = findViewById(R.id.signupButton);   // Ensure this matches your XML ID

        // Set OnClickListener to the signup button
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();  // Call the method to create a user
            }
        });
    }

    private void createUser() {
        String email = emailEditText.getText().toString().trim();  // Ensure trimming of input
        String password = passwordEditText.getText().toString().trim();

        // Validation for empty fields
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(SignupActivity.this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(SignupActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate password length (Firebase requires at least 6 characters)
        if (password.length() < 6) {
            Toast.makeText(SignupActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new user with email and password in Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success
                            Toast.makeText(SignupActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                            // Redirect to login activity after successful signup
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            finish(); // Close the current activity
                        } else {
                            // Sign up fails
                            Toast.makeText(SignupActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
