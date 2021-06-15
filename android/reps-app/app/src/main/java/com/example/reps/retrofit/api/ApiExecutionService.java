package com.example.reps.retrofit.api;

import androidx.lifecycle.LiveData;

import com.example.reps.retrofit.api.model.ExecutionInformation;
import com.example.reps.retrofit.api.model.ExecutionResponseBody;
import com.example.reps.retrofit.api.model.PagedList;
import com.example.reps.retrofit.api.model.Routine;
import com.example.reps.retrofit.api.model.UserRoutineExecution;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiExecutionService {

    //TODO descomentar y hacer andar con params
    //@GET("users/current/executions?{params}")
    //LiveData<ApiResponse<UserRoutineExecution>> getCurrentUserExecutions(@Path("params") String params);
    // --------------- QUERY PARAMS!!!

    @GET("users/current/executions")
    LiveData<ApiResponse<UserRoutineExecution>> getCurrentUserExecutions();

    @POST("executions/{routineId}")
    LiveData<ApiResponse<ExecutionResponseBody>> addRoutineExecution(@Path("routineId") int routineId, @Body ExecutionInformation execInfo);

}
