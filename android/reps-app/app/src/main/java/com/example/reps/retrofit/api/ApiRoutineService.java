package com.example.reps.retrofit.api;

import androidx.lifecycle.LiveData;


import com.example.reps.retrofit.api.ApiResponse;
import com.example.reps.retrofit.api.model.Cycle;
import com.example.reps.retrofit.api.model.CycleExercise;
import com.example.reps.retrofit.api.model.PagedList;
import com.example.reps.retrofit.api.model.Routine;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiRoutineService {

//    @GET("routines?{params}")
//    LiveData<ApiResponse<PagedList<Routine>>> getAll(@Query("params") String params);

    @GET("routines/{routineId}")
    LiveData<ApiResponse<Routine>> getRoutine(@Path("routineId") int routineId);

    //cycles
    @GET("routines/{routineId}/cycles")
    LiveData<ApiResponse<PagedList<Cycle>>> getRoutineCycles(@Path("routineId") int routineId);

    @GET("routines/{routineId}/cycles/{cycleId}")
    LiveData<ApiResponse<Cycle>> getCycle(@Path("routineId") int routineId, @Path("cycleId") int cycleId);

    @GET("cycles/{cycleId}/exercises")
    LiveData<ApiResponse<PagedList<CycleExercise>>> getCycleExercises(@Path("cycleId") int cycleId);



////////////////? CREO que este archivo no se usa!


}