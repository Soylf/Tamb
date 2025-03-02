package com.example.tamb.api;

import lombok.NonNull;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import com.example.tamb.model.Callback;
import com.example.tamb.model.Constants;
import com.example.tamb.model.UnsafeOkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GenerateAccessToken {
    private static final OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    public static void GenerateToken(Callback callback) {
        try {
            Request request = getRequest();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    callback.onError(e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    try (ResponseBody responseBody = response.body()) { //автоматическоое закрытие
                        if (response.isSuccessful() && responseBody != null) {
                            JSONObject jsonResponse = new JSONObject(responseBody.string());
                            String accessToken = jsonResponse.optString("access_token", null);
                            callback.onResponse(accessToken);
                        }
                    }catch (Exception e) {
                        callback.onError(new IOException("Unexpected response: " + response));
                    }
                }
            });

        } catch (Exception e) {
            callback.onError(e);

        }
    }

    private static @NonNull Request getRequest() {
        RequestBody body = new FormBody.Builder()
                .addEncoded("scope", "GIGACHAT_API_PERS")
                .build();
        return new Request.Builder()
                .url(Constants.URL_TOKEN.getValue())
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .addHeader("RqUID", Constants.RqUID.getValue())
                .addHeader("Authorization", "Basic " + Constants.AUTHORIZATION.getValue())
                .post(body)
                .build();
    }
}
