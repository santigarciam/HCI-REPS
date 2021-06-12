package com.example.reps.retrofit.api;

import androidx.lifecycle.LiveData;

import com.example.reps.retrofit.api.model.CredentialRegister;
import com.example.reps.retrofit.api.model.Credentials;
import com.example.reps.retrofit.api.model.Token;
import com.example.reps.retrofit.api.model.User;
import com.example.reps.retrofit.api.model.VerificationCodeModel;


import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiUserService {
    @POST("users/login")
    LiveData<ApiResponse<Token>> login(@Body Credentials credentials);

    @POST("users/logout")
    LiveData<ApiResponse<Void>> logout();

    @POST("users/verify_email")
    LiveData<ApiResponse<Token>> verifyCode(@Body VerificationCodeModel verificationCodeModel);

    @POST("users/resend_verification")
    LiveData<ApiResponse<Token>> resendCode(@Body VerificationCodeModel verificationCodeModel);

    @POST("users")
    LiveData<ApiResponse<User>> register(@Body CredentialRegister credentials);

    @GET("users/current")
    LiveData<ApiResponse<User>> getCurrentUser();

    //TODO hacer el request para un modify
}
