package com.example.tamb.model;

import okhttp3.OkHttpClient;

public class UnsafeOkHttpClient {
    public static OkHttpClient getUnsafeOkHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }
}
