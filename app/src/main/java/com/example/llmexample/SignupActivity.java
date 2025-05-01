package com.example.llmexample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText username, email, cemail, password, cpassword, phone;
    private Button createBtn;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Note: layout file name unchanged from your code
        setContentView(R.layout.actvity_signup);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        cemail = findViewById(R.id.cemail);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.cpassword);
        phone = findViewById(R.id.phone);
        createBtn = findViewById(R.id.createBtn);

        db = AppDatabase.getInstance(this);

        createBtn.setOnClickListener(v -> {
            String userText = username.getText().toString().trim();
            String emailText = email.getText().toString().trim();
            String cemailText = cemail.getText().toString().trim();
            String passText = password.getText().toString().trim();
            String cpassText = cpassword.getText().toString().trim();
            String phoneText = phone.getText().toString().trim();

            // Validate matching email and password
            if (!emailText.equals(cemailText) || !passText.equals(cpassText)) {
                Toast.makeText(SignupActivity.this, "Email or Password mismatch", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(userText, emailText, passText, phoneText);

            // Run database insert off the main thread
            new Thread(() -> {
                db.userDao().insert(user);
                runOnUiThread(() -> {
                    Toast.makeText(SignupActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, InterestsActivity.class);
                    startActivity(intent);
                    finish();
                });
            }).start();
        });
    }
}
