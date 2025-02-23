package com.example.tamb.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tamb.R;
import com.example.tamb.api.GenerateAccessToken;
import com.example.tamb.api.GenerateText;
import com.example.tamb.model.Callback;
import com.google.android.material.snackbar.Snackbar;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void TestOnClick(View view) {
        TextView testTextView = findViewById(R.id.testTextView);
        testTextView.setText("Ожидание ответа...");

        GenerateText.sendRequest("Привет", new Callback(){
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

    public void CreateTokenOnClick(View view) {
        GenerateAccessToken.GenerateAccessToken(new Callback() {
            @Override
            public void onResponse(String response) {
                GenerateText.setToken(response);

                Snackbar snackbar = Snackbar.make(view, "Ключ сохранен", Snackbar.LENGTH_LONG);
                snackbar.setAction("Ключ...", v -> {
                    snackbar.setTextColor(0XFFEB3B);
                    Toast toast = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG);
                    toast.show();
                });
                snackbar.show();
            }

            @Override
            public void onError(Exception e) {
                Snackbar snackbar = Snackbar.make(view, "Что-то пошло не так", Snackbar.LENGTH_LONG);
                snackbar.setAction("Ошибка...", v -> {
                    snackbar.setTextColor(0XFFEB3B);
                    Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                });
                snackbar.show();
            }
        });
    }
}