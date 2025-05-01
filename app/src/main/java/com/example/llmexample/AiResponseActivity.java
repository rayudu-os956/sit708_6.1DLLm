package com.example.llmexample;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class AiResponseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_response);

        LinearLayout container = findViewById(R.id.responseContainer);
        Button continueBtn     = findViewById(R.id.continueBtn);

        // read passed-in lists
        ArrayList<String> questions       =
                getIntent().getStringArrayListExtra("questions");
        ArrayList<String> selectedAnswers =
                getIntent().getStringArrayListExtra("selectedAnswers");
        ArrayList<String> correctAnswers  =
                getIntent().getStringArrayListExtra("correctAnswers");

        if (selectedAnswers != null && correctAnswers != null) {
            for (int i = 0; i < selectedAnswers.size(); i++) {
                // build a blue card for each Q
                CardView card = new CardView(this);
                card.setCardBackgroundColor(Color.parseColor("#3399FF"));
                card.setRadius(12f);
                card.setUseCompatPadding(true);

                LinearLayout.LayoutParams lp =
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                lp.setMargins(0, 8, 0, 8);
                card.setLayoutParams(lp);

                // text inside the card
                TextView tv = new TextView(this);
                String num     = (i+1) + ". ";
                String qText   = (questions != null && questions.size()>i)
                        ? questions.get(i) + "\n"
                        : "";
                String userAns = selectedAnswers.get(i);
                String corrAns = correctAnswers.get(i);

                tv.setText(
                        num + qText +
                                "Your answer:   " + userAns + "\n" +
                                "Correct answer: " + corrAns
                );
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(16f);
                tv.setPadding(16,16,16,16);

                card.addView(tv);
                container.addView(card);
            }
        }

        continueBtn.setOnClickListener(v -> finish());
    }
}