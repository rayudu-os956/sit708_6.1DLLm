package com.example.llmexample;

import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    TextView greetingText;
    Button taskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ImageView profileImage = findViewById(R.id.profileImage);
        Intent intent = getIntent();
        String imageUri = intent.getStringExtra("profileImage");

        if (imageUri != null) {
            profileImage.setImageURI(Uri.parse(imageUri));
        }

        greetingText = findViewById(R.id.greeting);
        taskBtn = findViewById(R.id.taskBtn);

        SharedPrefManager prefManager = new SharedPrefManager(this);
        String username = prefManager.getUsername();
        greetingText.setText("Hello, " + username);

        taskBtn.setOnClickListener(v -> startActivity(new Intent(this, TaskDetailActivity.class)));
    }
}