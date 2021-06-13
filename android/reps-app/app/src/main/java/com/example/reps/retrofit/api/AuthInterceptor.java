package com.example.reps.retrofit.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.reps.retrofit.App;
import com.example.reps.retrofit.AppPreferences;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private final AppPreferences preferences;

    public AuthInterceptor(App application) {
        preferences = application.getPreferences();
    }

    @NotNull
    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        if (preferences.getAuthToken() != null) {
            Log.d("AuthInterceptor", "intercept: ");
            request.addHeader("Authorization", "Bearer " + preferences.getAuthToken());
        }
        return chain.proceed(request.build());
    }
}
