package com.example.llmexample;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UpgradeAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_account);

        Button starterBtn = findViewById(R.id.btnStarter);
        Button intermediateBtn = findViewById(R.id.btnIntermediate);
        Button advancedBtn = findViewById(R.id.btnAdvanced);

        View popupView = LayoutInflater.from(this).inflate(R.layout.fake_payment_popup, null);
        AlertDialog fakePaymentDialog = new AlertDialog.Builder(this).setView(popupView).create();

        View.OnClickListener showPopupWithSave = v -> {
            String selected = ((Button) v).getText().toString().split(" ")[0];
            SharedPrefManager.getInstance(this).saveUpgradeLevel(selected);
            fakePaymentDialog.show();
        };

        starterBtn.setOnClickListener(showPopupWithSave);
        intermediateBtn.setOnClickListener(showPopupWithSave);
        advancedBtn.setOnClickListener(showPopupWithSave);
    }
}