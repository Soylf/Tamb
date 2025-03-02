package com.example.tamb.model;
import android.annotation.SuppressLint;

import okhttp3.OkHttpClient;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class UnsafeOkHttpClient {

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            @SuppressLint("CustomX509TrustManager") TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());

            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier((hostname, session) -> true) // Доверяем всем хостам
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create unsafe OkHttpClient", e);
        }
    }
}