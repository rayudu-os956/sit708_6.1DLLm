package com.example.llmexample;

import java.io.Serializable;
import java.util.List;

public class QuizQuestion implements Serializable {
    private String question;
    private List<String> options;
    private String correctAnswer;
    private String userAnswer;

    public QuizQuestion(String question, List<String> options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() { return question; }
    public List<String> getOptions() { return options; }
    public String getCorrectAnswer() { return correctAnswer; }
    public String getUserAnswer() { return userAnswer; }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
