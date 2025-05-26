package com.example.llmexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private TextView usernameView, totalView, correctView, incorrectView, notificationView, upgradeStatusView;
    private Button shareBtn, upgradeBtn, historyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        usernameView = findViewById(R.id.usernameView);
        totalView = findViewById(R.id.totalQuestionsView);
        correctView = findViewById(R.id.correctAnswersView);
        incorrectView = findViewById(R.id.incorrectAnswersView);
        notificationView = findViewById(R.id.notificationText);
        shareBtn = findViewById(R.id.shareBtn);
        upgradeBtn = findViewById(R.id.upgradeBtn);
        historyBtn = findViewById(R.id.historyBtn);
        upgradeStatusView = findViewById(R.id.upgradeStatusView);

        String username = SharedPrefManager.getInstance(this).getUsername();
        int total = 10, correct = 10, incorrect = 0; // replace with real values

        usernameView.setText("Hello, " + username);
        totalView.setText("Total Questions: " + total);
        correctView.setText("Correct Answers: " + correct);
        incorrectView.setText("Incorrect Answers: " + incorrect);
        notificationView.setText("✔ Display any important notifications here");

        String level = SharedPrefManager.getInstance(this).getUpgradeLevel();
        if (level != null && !level.isEmpty()) {
            upgradeStatusView.setText("Plan: " + level);
        } else {
            upgradeStatusView.setText("Plan: Free");
        }

        shareBtn.setOnClickListener(v -> {
            String shareText = "Check out my quiz stats! Total: " + total +
                    ", Correct: " + correct + ", Incorrect: " + incorrect;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(intent, "Share via"));
        });
        Button taskBtn = findViewById(R.id.taskBtn);
        taskBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, TaskDetailActivity.class);
            intent.putExtra("topic", "movies");  // or whichever topic you want to load
            startActivity(intent);
        });

        upgradeBtn.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, UpgradeAccountActivity.class)));

        historyBtn.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, AiResponseActivity.class)));
    }
}