package com.example.tamb.api;

import com.example.tamb.model.Callback;
import com.example.tamb.model.Constants;
import com.example.tamb.model.UnsafeOkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import lombok.NonNull;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GenerateText {
    private static final OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    public static void sendRequest(String text, Callback callback) {
        try {
            JSONObject jsonBody = getJsonObject(text);
            RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.get("application/json"));
            Request request = new Request.Builder()
                    .url(Constants.URL_CHAT.getValue())
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + Constants.ACCESS_TOKEN.getValue())
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new okhttp3.Callback() { //Делаем новый поток чтобы не ловить ошибку связанную с основным потоком
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    callback.onError(e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    try (ResponseBody responseBody = response.body()){
                        if (response.isSuccessful() && responseBody != null) {
                            JSONObject jsonResponse = new JSONObject(responseBody.string());
                            String content = jsonResponse.getJSONArray("choices")
                                    .getJSONObject(0)
                                    .getJSONObject("message")
                                    .getString("content");
                            callback.onResponse(content);
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


    private static @NonNull JSONObject getJsonObject(String text) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("model", Constants.MODEL.getValue());
        jsonBody.put("n", 1);
        jsonBody.put("stream", false);
        jsonBody.put("max_tokens", 512);
        jsonBody.put("repetition_penalty", 1);
        jsonBody.put("update_interval", 0);

        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", text);
        messages.put(message);
        jsonBody.put("messages", messages);
        return jsonBody;
    }
}