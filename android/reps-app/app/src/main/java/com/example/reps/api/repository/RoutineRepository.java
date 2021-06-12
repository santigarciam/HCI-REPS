package com.example.reps.api.repository;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.reps.App;
import com.example.reps.api.ApiClient;
import com.example.reps.api.ApiResponse;
import com.example.reps.api.ApiRoutineService;
import com.example.reps.api.model.Credentials;
import com.example.reps.api.model.PagedList;
import com.example.reps.api.model.Routine;
import com.example.reps.api.model.Token;
import com.example.reps.api.model.User;


public class RoutineRepository {

    private final ApiRoutineService apiService;

    public RoutineRepository(App app) {
        this.apiService = ApiClient.create(app, ApiRoutineService.class);
    }

    public LiveData<Resource<Token>> login(Credentials credentials) {
        return new NetworkBoundResource<Token, Token>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Token>> createCall() {
                return apiService.login(credentials);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> logout() {
        return new NetworkBoundResource<Void, Void>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.logout();
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<Routine>>> getAll() {
        return new NetworkBoundResource<User, User>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                return apiService.getAll();
            }
        }.asLiveData();
    }
}