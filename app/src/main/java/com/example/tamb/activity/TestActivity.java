package com.example.tamb.activity;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tamb.R;
import com.example.tamb.api.GenerateAccessToken;
import com.example.tamb.api.GenerateText;
import com.example.tamb.model.Callback;
import com.example.tamb.model.Constants;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

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
        GenerateAccessToken.GenerateToken(new Callback() {
            @Override
            public void onResponse(String response) {
                Constants.ACCESS_TOKEN.setValue(response);
                Snackbar snackbar = Snackbar.make(view, "Ключ сохранен", Snackbar.LENGTH_LONG)
                        .setAction("Ключ...", v -> {
                            Snackbar.make(view, response, Snackbar.LENGTH_SHORT).show();
                        });
                snackbar.show();
            }

            @Override
            public void onError(Exception e) {
                Snackbar snackbar = Snackbar.make(view, "Что-то пошло не так", Snackbar.LENGTH_LONG)
                                .setAction("...", v -> {
                                    Snackbar.make(view, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_SHORT).show();
                                });
                Log.e(TAG, "onError: ", e);
                snackbar.show();
            }
        });
    }
}