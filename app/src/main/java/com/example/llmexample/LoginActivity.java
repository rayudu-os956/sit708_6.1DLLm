package com.example.llmexample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameField, passwordField;
    private Button loginBtn, signupBtn;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signupBtn);

        // Initialize Room database instance
        db = AppDatabase.getInstance(this);

        loginBtn.setOnClickListener(v -> {
            String user = usernameField.getText().toString().trim();
            String pass = passwordField.getText().toString().trim();

            // Perform database query on background thread
            new Thread(() -> {
                User u = db.userDao().login(user, pass);

                runOnUiThread(() -> {
                    if (u != null) {
                        // Save username in shared preferences
                        SharedPrefManager prefManager = new SharedPrefManager(LoginActivity.this);
                        prefManager.saveUsername(u.username);

                        // Navigate to Dashboard
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed: Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });

        signupBtn.setOnClickListener(v -> {
            // Navigate to Signup screen
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }
}
