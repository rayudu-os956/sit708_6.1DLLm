//package com.example.llmexample;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class ShareProfileActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_share_profile);
//
//        Button share = findViewById(R.id.btnShare);
//        share.setOnClickListener(v -> {
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setType("text/plain");
//            intent.putExtra(Intent.EXTRA_TEXT, "Total: 10, Correct: 10, Incorrect: 0");
//            startActivity(Intent.createChooser(intent, "Share your profile"));
//        });
//    }
//}
