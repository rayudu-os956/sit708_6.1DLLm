package com.example.llmexample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TaskDetailActivity extends AppCompatActivity {
    private TextView taskTitle, taskDesc;
    private ProgressBar progressBar;
    private LinearLayout questionContainer;
    private Button submitBtn;

    private final ArrayList<String> questionTexts   = new ArrayList<>();
    private final ArrayList<RadioGroup> allGroups   = new ArrayList<>();
    private final ArrayList<String> correctAnswers  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        taskTitle        = findViewById(R.id.taskTitle);
        taskDesc         = findViewById(R.id.taskDesc);
        progressBar      = findViewById(R.id.progressBar);
        questionContainer= findViewById(R.id.questionContainer);
        submitBtn        = findViewById(R.id.submitBtn);

        loadGeneratedTask();

        submitBtn.setOnClickListener(v -> {
            // collect user's selections
            ArrayList<String> selectedAnswers = new ArrayList<>();
            for (RadioGroup rg : allGroups) {
                int id = rg.getCheckedRadioButtonId();
                if (id != -1) {
                    RadioButton rb = rg.findViewById(id);
                    selectedAnswers.add(rb.getText().toString());
                } else {
                    selectedAnswers.add("No answer");
                }
            }

            // launch result screen
            Intent it = new Intent(this, AiResponseActivity.class);
            it.putStringArrayListExtra("questions", questionTexts);
            it.putStringArrayListExtra("selectedAnswers", selectedAnswers);
            it.putStringArrayListExtra("correctAnswers", correctAnswers);
            startActivity(it);
        });
    }

    private void loadGeneratedTask() {
        String url = "http://192.168.0.110:5050/getQuiz?topic=movies";
        //String url = "http://10.141.45.112:5050/getQuiz?topic=movies";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        // set title/description
                        taskTitle.setText(response.optString("title", "Quiz"));
                        taskDesc .setText(response.optString("description",""));

                        JSONArray quiz = response.getJSONArray("quiz");
                        for (int i = 0; i < quiz.length(); i++) {
                            JSONObject q = quiz.getJSONObject(i);

                            // capture real question text
                            String question = q.getString("question");
                            questionTexts.add(question);

                            // capture correct answer (letter or full text)
                            String correct = q.optString("correct_answer", "");
                            correctAnswers.add(correct);

                            // show question
                            TextView qText = new TextView(this);
                            qText.setText((i+1) + ". " + question);
                            qText.setTextSize(18f);
                            qText.setPadding(0,16,0,8);
                            questionContainer.addView(qText);

                            // show options
                            JSONArray opts = q.getJSONArray("options");
                            RadioGroup group = new RadioGroup(this);
                            group.setOrientation(RadioGroup.VERTICAL);
                            for (int j = 0; j < opts.length(); j++) {
                                RadioButton rb = new RadioButton(this);
                                rb.setText(opts.getString(j));
                                group.addView(rb);
                            }
                            questionContainer.addView(group);
                            allGroups.add(group);
                        }

                        // enable submit when done
                        submitBtn.setEnabled(true);

                    } catch (JSONException e) {
                        Toast.makeText(this,
                                "Error parsing quiz!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                        progressBar.setVisibility(ProgressBar.GONE);
                    }
                },
                error -> {
                    Toast.makeText(this,
                            "Failed to load quiz.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(ProgressBar.GONE);
                    error.printStackTrace();
                }
        );

        // 60s timeout
        req.setRetryPolicy(new DefaultRetryPolicy(
                420000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        queue.add(req);
    }
}