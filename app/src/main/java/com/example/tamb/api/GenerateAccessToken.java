package com.example.tamb.api;

import lombok.NonNull;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.tamb.model.Callback;
import com.example.tamb.model.Constants;
import com.example.tamb.model.UnsafeOkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GenerateAccessToken {
    private static final OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    public static void GenerateAccessToken(Callback callback) {
        try {
            client.newCall(getRequest()).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    callback.onError(e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response.body().string());
                            String accessToken = jsonResponse.optString("access_token", null);
                            if (accessToken.isEmpty()) {
                                callback.onResponse(accessToken);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        callback.onResponse("Что-то пошло не так");
                    }
                }
            });

        } catch (Exception e) {
            callback.onError(e);
        }
    }


    private static @NonNull Request getRequest() {
        FormBody body = new FormBody.Builder()
                .add("scope", "GIGACHAT_API_PERS")
                .build();
        return new Request.Builder()
                .url(Constants.URL_TOKEN.getValue())
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .addHeader("RqUID", "5f12cd9c-9b0c-481e-8141-bde8ace079fb")
                .addHeader("Authorization", "Basic " + Constants.AUTHORIZATION.getValue())
                .post(body)
                .build();
    }

}
