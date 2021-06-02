package com.myproject.novel.net;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ResponseInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        return chain.proceed(chain.request()).newBuilder()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();
    }
}