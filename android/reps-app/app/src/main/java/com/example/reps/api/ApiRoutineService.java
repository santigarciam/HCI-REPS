package com.example.reps.api;

import androidx.lifecycle.LiveData;

import com.example.reps.api.model.PagedList;
import com.example.reps.api.model.Routine;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiRoutineService {

    @GET("routines")
    LiveData<ApiResponse<PagedList<Routine>>> getAll();

    @GET("routines/{routineId}")
    LiveData<ApiResponse<Routine>> getRoutine(@Path("routineId") int routineId);

    //cycles
    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<Routine>> getRoutineCycles(@Path("routineId") int routineId);

    @GET("routines/{routineId}/cycles/{cycleId}")
    LiveData<ApiResponse<Routine>> getCycle(@Path("routineId") int routineId, @Path("cycleId") int cycleId);




}
