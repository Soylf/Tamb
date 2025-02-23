package com.example.tamb.model;

public enum Constants {
    URL_CHAT("https://gigachat.devices.sberbank.ru/api/v1/chat/completions"),
    URL_TOKEN("https://ngw.devices.sberbank.ru:9443/api/v2/oauth"),
    MODEL("GigaChat"),
    AUTHORIZATION("ZTBjNGJkMTYtMjgzZS00MDdkLWI1MmMtZDRiZGEzZDY0OWNhOjVmMDBlYzBkLTlhYmUtNDMyNi04MDUzLTQ5NDVhNDc0OGI2YQ==");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}