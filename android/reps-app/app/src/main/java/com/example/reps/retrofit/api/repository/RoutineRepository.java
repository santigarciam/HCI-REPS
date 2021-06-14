package com.example.reps.retrofit.api.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.reps.retrofit.App;

import com.example.reps.retrofit.api.ApiClient;
import com.example.reps.retrofit.api.ApiResponse;
import com.example.reps.retrofit.api.model.Cycle;
import com.example.reps.retrofit.api.model.PagedList;
import com.example.reps.retrofit.api.model.Routine;


public class RoutineRepository {

    private final ApiResponse.ApiRoutineService apiService;

    public RoutineRepository(App app) {
        this.apiService = ApiClient.create(app, ApiResponse.ApiRoutineService.class);
    }

    public LiveData<Resource<PagedList<Routine>>> getAll(String params) {
        return new NetworkBoundResource<PagedList<Routine>, PagedList<Routine>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
//                return apiService.getAll(params);
                return apiService.getAll();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Routine>> getRoutine(int routineId) {
        return new NetworkBoundResource<Routine, Routine>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Routine>> createCall() {
                return apiService.getRoutine(routineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<Cycle>>> getRoutineCycles(int routineId) {
        return new NetworkBoundResource<PagedList<Cycle>, PagedList<Cycle>>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Cycle>>> createCall() {
                return apiService.getRoutineCycles(routineId);
            }
        }.asLiveData();
    }



}