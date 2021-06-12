package com.example.reps.api.repository;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.reps.App;
import com.example.reps.api.ApiClient;
import com.example.reps.api.ApiResponse;
import com.example.reps.api.ApiRoutineService;
import com.example.reps.api.model.PagedList;
import com.example.reps.api.model.Routine;


public class RoutineRepository {

    private final ApiRoutineService apiService;

    public RoutineRepository(App app) {
        this.apiService = ApiClient.create(app, ApiRoutineService.class);
    }

    public LiveData<Resource<PagedList<Routine>>> getAll() {
        return new NetworkBoundResource<PagedList<Routine>, PagedList<Routine>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
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

    // no me contestaron que hacer con los ciclos

}