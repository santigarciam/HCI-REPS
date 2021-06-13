package com.example.reps.api.repository;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.reps.App;
import com.example.reps.api.ApiClient;
import com.example.reps.api.ApiResponse;
import com.example.reps.api.ApiRoutineService;
import com.example.reps.api.model.Cycle;
import com.example.reps.api.model.CycleExercise;
import com.example.reps.api.model.PagedList;
import com.example.reps.api.model.Routine;

import retrofit2.http.GET;
import retrofit2.http.Path;


public class RoutineRepository {

    private final ApiRoutineService apiService;

    public RoutineRepository(App app) {
        this.apiService = ApiClient.create(app, ApiRoutineService.class);
    }

    public LiveData<Resource<PagedList<Routine>>> getAll(String params) {
        return new NetworkBoundResource<PagedList<Routine>, PagedList<Routine>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                return apiService.getAll(params);
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



}