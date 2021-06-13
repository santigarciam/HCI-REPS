package com.example.reps.retrofit.api;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.reps.retrofit.api.model.Cycle;
import com.example.reps.retrofit.api.model.CycleExercise;
import com.example.reps.retrofit.api.model.PagedList;
import com.example.reps.retrofit.api.model.Routine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example.reps.retrofit.api.model.Error;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class ApiResponse<T> {

    private T data;
    private Error error;

    public T getData() {
        return data;
    }

    public Error getError(){
        return error;
    }

    public ApiResponse(Response<T> response) {
        parseResponse(response);
    }

    public ApiResponse(Throwable throwable) {
        this.error = buildError(throwable.getMessage());
    }

    private void parseResponse(Response<T> response) {
        if (response.isSuccessful()) {
            this.data = response.body();
            return;
        }

        if (response.errorBody() == null) {
            this.error = buildError("Missing error body");
            return;
        }

        String message;

        try {
            message = response.errorBody().string();
        } catch (IOException exception) {
            Log.e("API", exception.toString());
            this.error = buildError(exception.getMessage());
            return;
        }

        if (message != null && message.trim().length() > 0) {
            Gson gson = new Gson();
            this.error =  gson.fromJson(message, new TypeToken<Error>() {}.getType());
        }
    }

    private Error buildError (String message){
        Error error = new Error(Error.LOCAL_UNEXPECTED_ERROR, "Unexpected Error");
        if (message != null){
            List<String> details = new ArrayList<>();
            details.add(message);
            error.setDetails(details);
        }
        return error;
    }

    public static interface ApiRoutineService {

//        @GET("routines?{params}")
//        LiveData<ApiResponse<PagedList<Routine>>> getAll(@Path("params") String params);
        @GET("routines")
        LiveData<ApiResponse<PagedList<Routine>>> getAll();

        @GET("routines/{routineId}")
        LiveData<ApiResponse<Routine>> getRoutine(@Path("routineId") int routineId);

        //cycles
        @GET("routines/{routineId}/cycles")
        LiveData<ApiResponse<PagedList<Cycle>>> getRoutineCycles(@Path("routineId") int routineId);

        @GET("routines/{routineId}/cycles/{cycleId}")
        LiveData<ApiResponse<Cycle>> getCycle(@Path("routineId") int routineId, @Path("cycleId") int cycleId);

        @GET("cycles/{cycleId}/exercises")
        LiveData<ApiResponse<PagedList<CycleExercise>>> getCycle(@Path("cycleId") int cycleId);

        @GET("cycles/{cycleId}/exercises")
        LiveData<ApiResponse<PagedList<CycleExercise>>> getCycleExercises(@Path("cycleId") int cycleId);






    }
}