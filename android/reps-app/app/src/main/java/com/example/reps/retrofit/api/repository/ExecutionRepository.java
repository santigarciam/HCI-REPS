package com.example.reps.retrofit.api.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.ApiClient;
import com.example.reps.retrofit.api.ApiExecutionService;
import com.example.reps.retrofit.api.ApiResponse;
import com.example.reps.retrofit.api.model.Cycle;
import com.example.reps.retrofit.api.model.ExecutionInformation;
import com.example.reps.retrofit.api.model.ExecutionResponseBody;
import com.example.reps.retrofit.api.model.PagedList;
import com.example.reps.retrofit.api.model.Routine;
import com.example.reps.retrofit.api.model.UserRoutineExecution;

import org.jetbrains.annotations.NotNull;

public class ExecutionRepository {

    private final ApiExecutionService apiService;

    public ExecutionRepository(App app) {
        this.apiService = ApiClient.create(app, ApiExecutionService.class);
    }

    public LiveData<Resource<UserRoutineExecution>> getCurrentUserExecutions(String params) {
        return new NetworkBoundResource<UserRoutineExecution, UserRoutineExecution>() {
            @NonNull
            @NotNull
            @Override
            protected LiveData<ApiResponse<UserRoutineExecution>> createCall() {
                //TODO descomentar y hacer andar con params
                //return apiService.getCurrentUserExecutions(params);
                return apiService.getCurrentUserExecutions();
            }
        }.asLiveData();
    }

    public LiveData<Resource<ExecutionResponseBody>> addRoutineExec(int routineId, ExecutionInformation execInfo) {
        return new NetworkBoundResource<ExecutionResponseBody, ExecutionResponseBody>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<ExecutionResponseBody>> createCall() {
                return apiService.addRoutineExecution(routineId, execInfo);
            }
        }.asLiveData();
    }
}
