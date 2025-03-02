package com.example.tamb.model;

public interface Callback {
    void onResponse(String response);
    void onError(Exception e);
}
