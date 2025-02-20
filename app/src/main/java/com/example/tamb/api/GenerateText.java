package com.example.tamb.api;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GenerateText {
    private static final OkHttpClient client = new OkHttpClient();

    public static String sendRequest(String text){
        final String[] responseText = {"Что-то пошло не так )_)"};
        String url = "https://ngw.devices.sberbank.ru:9443/api/v2/oauth";
        RequestBody body = new FormBody.Builder()
                .add("scope", "GIGACHAT_API_PERS")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .addHeader("RqUID", "b3aaa1c9-8cd4-40a5-93d7-b6453b7de9f5")
                .addHeader("Authorization", "Basic <>")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() { //Используем асинхронко чтобы andrioa не висло
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    responseText[0] = response.body().string();
                }
            }
        });

        return responseText[0];
    }
}
