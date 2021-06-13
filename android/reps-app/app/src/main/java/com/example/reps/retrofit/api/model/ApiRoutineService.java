package com.example.reps.api;

import androidx.lifecycle.LiveData;

import com.example.reps.api.model.Cycle;
import com.example.reps.api.model.CycleExercise;
import com.example.reps.api.model.PagedList;
import com.example.reps.api.model.Routine;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiRoutineService {

    @GET("routines?{params}")
    LiveData<ApiResponse<PagedList<Routine>>> getAll(@Path("params") String params);

    @GET("routines/{routineId}")
    LiveData<ApiResponse<Routine>> getRoutine(@Path("routineId") int routineId);

    //cycles
    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<PagedList<Cycle>>> getRoutineCycles(@Path("routineId") int routineId);

    @GET("routines/{routineId}/cycles/{cycleId}")
    LiveData<ApiResponse<Cycle>> getCycle(@Path("routineId") int routineId, @Path("cycleId") int cycleId);

    @GET("cycles/{cycleId}/exercises")
    LiveData<ApiResponse<PagedList<CycleExercise>>> getCycle(@Path("cycleId") int cycleId);





}
