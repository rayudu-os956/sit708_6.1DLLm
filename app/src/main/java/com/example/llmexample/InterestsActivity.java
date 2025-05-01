package com.example.llmexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class InterestsActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private Button nextBtn;
    private List<CheckBox> checkBoxList = new ArrayList<>();
    private static final int MAX_SELECTION = 10;
    private SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        gridLayout = findViewById(R.id.gridLayout);
        nextBtn = findViewById(R.id.nextBtn);
        prefManager = new SharedPrefManager(this);

        // Initialize checkboxes
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View view = gridLayout.getChildAt(i);
            if (view instanceof CheckBox) {
                checkBoxList.add((CheckBox) view);
            }
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> selectedInterests = new ArrayList<>();
                for (CheckBox cb : checkBoxList) {
                    if (cb.isChecked()) {
                        selectedInterests.add(cb.getText().toString());
                    }
                }

                if (selectedInterests.isEmpty()) {
                    Toast.makeText(InterestsActivity.this, "Please select at least one interest.", Toast.LENGTH_SHORT).show();
                } else if (selectedInterests.size() > MAX_SELECTION) {
                    Toast.makeText(InterestsActivity.this, "You can select up to 10 interests only.", Toast.LENGTH_SHORT).show();
                } else {
                    // Save selected interests to SharedPreferences
                    prefManager.saveStringList("user_interests", selectedInterests);

                    // Proceed to the next activity
                    Intent intent = new Intent(InterestsActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}