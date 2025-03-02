package com.example.tamb.model;

public enum Constants {
    URL_CHAT("https://gigachat.devices.sberbank.ru/api/v1/chat/completions"),
    URL_TOKEN("https://ngw.devices.sberbank.ru:9443/api/v2/oauth"),
    RqUID("f464c28a-d642-487e-a489-0cc62a2acc52"),
    MODEL("GigaChat"),
    AUTHORIZATION(""),
    ACCESS_TOKEN("");
    private String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String val) {
        value = val;
    }
}