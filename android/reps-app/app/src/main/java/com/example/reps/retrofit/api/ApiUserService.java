package com.example.reps.retrofit.api;

import androidx.lifecycle.LiveData;

import com.example.reps.retrofit.api.model.Credentials;
import com.example.reps.retrofit.api.model.Token;
import com.example.reps.retrofit.api.model.User;
import com.example.reps.retrofit.api.model.UserInformation;


import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiUserService {
    @POST("users/login")
    LiveData<ApiResponse<Token>> login(@Body Credentials credentials);

    @POST("users/logout")
    LiveData<ApiResponse<Void>> logout();

    @POST("users")
    LiveData<ApiResponse<User>> register(@Body Credentials credentials);

    @GET("users/current")
    LiveData<ApiResponse<User>> getCurrentUser();

    @PUT("users/current")
    LiveData<ApiResponse<User>> modifyCurrentUser(@Body UserInformation newUserInformation);

    //TODO hacer el request para un modify
}
