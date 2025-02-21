package com.example.tamb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tamb.R;
import com.example.tamb.api.GenerateText;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void TestOnClick(View view) {
        TextView testTextView = findViewById(R.id.testTextView);
        testTextView.setText("Ожидание ответа...");

        GenerateText.sendRequest("Привет", new GenerateText.Callback() {
            @Override
            public void onResponse(String response) {
                runOnUiThread(() -> testTextView.setText(response));
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> testTextView.setText("Ошибка: " + e.getMessage()));
            }
        });
    }

}