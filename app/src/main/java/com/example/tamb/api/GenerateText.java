package com.example.tamb.api;

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

public class GenerateText {
    private static final String MODEL = "GigaChat";
    private static final String TOKEN = "<ZTBjNGJkMTYtMjgzZS00MDdkLWI1MmMtZDRiZGEzZDY0OWNhOmQ4ZWVlNGQ3LTFhMDktNDE5Mi1iNGZhLTI3ZTRiMzllOGNkNA==>";
    private static final String URL = "https://gigachat.devices.sberbank.ru/api/v1/chat/completions"; //https://gigachat.devices.sberbank.ru/api/v1/chat/completions
    private static final OkHttpClient client = new OkHttpClient();

    public interface Callback {
        void onResponse(String response);
        void onError(Exception e);
    }

    public static void sendRequest(String text, Callback callback) {
        try {
            JSONObject jsonBody = getJsonObject(text);
            RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.get("application/json"));
            Request request = new Request.Builder()
                    .url(URL)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new okhttp3.Callback() { //Делаем новый поток чтобы не ловить ошибку связанную с основным потоком
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    callback.onError(e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if(response.isSuccessful() && response.body() != null) {
                        callback.onResponse(response.body().string());
                    }else {
                        callback.onResponse("Что-то пошло не так");
                    }
                }
            });

        } catch (Exception e) {
            callback.onError(e);
        }
    }


    private static @NonNull JSONObject getJsonObject(String text) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("model", MODEL);
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