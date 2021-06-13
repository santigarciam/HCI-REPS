package com.example.reps.api;

import com.example.reps.retrofit.App;
import com.example.reps.retrofit.AppPreferences;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final AppPreferences preferences;

    public AuthInterceptor(App application){
        preferences = application.getPreferences();
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();

        if (preferences.getAuthToken() != null)
            request.addHeader("Authorization", "Bearer" + preferences.getAuthToken());

        return chain.proceed(request.build());
    }

}
