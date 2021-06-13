package com.example.reps.api;

import androidx.lifecycle.LiveData;


import com.example.reps.retrofit.api.ApiResponse;
import com.example.reps.retrofit.api.model.Credentials;
import com.example.reps.retrofit.api.model.Token;
import com.example.reps.retrofit.api.model.User;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiUserService {

    @POST("users")
    LiveData<ApiResponse<User>> register(@Body User newUser);

    //ver si hacemos lo del verify email o no si lo hacemos hay q agregar (registrarse es opcional)

    @POST("users/login")
    LiveData<ApiResponse<Token>> login(@Body Credentials credentials);

    @POST("users/logout")
    LiveData<ApiResponse<Void>> logout ();

    @GET("users/current")
    LiveData<ApiResponse<User>> getCurrent();
}
