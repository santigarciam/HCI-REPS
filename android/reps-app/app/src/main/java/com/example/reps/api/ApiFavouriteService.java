package com.example.reps.api;
import androidx.lifecycle.LiveData;

import com.example.reps.api.model.PagedList;
import com.example.reps.api.model.Routine;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiFavouriteService {

    @POST("favourites/{routineId}")
    LiveData<ApiResponse<Void>> addFavourite(@Path("routineId") int routineId);

    @DELETE("favourites/{routineId}")
    LiveData<ApiResponse<Void>> deleteFavourite(@Path("routineId") int routineId);

    @GET("favourites")
    LiveData<ApiResponse<PagedList<Routine>>> getFavourites();
}