package com.example.reps.retrofit.api;
import androidx.lifecycle.LiveData;

import com.example.reps.retrofit.api.ApiResponse;
import com.example.reps.retrofit.api.model.PagedList;
import com.example.reps.retrofit.api.model.Routine;

import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiFavouriteService {

    @POST("favourites/{routineId}/")
    LiveData<ApiResponse<ResponseBody>> addFavourite(@Path("routineId") int routineId);

    @DELETE("favourites/{routineId}/")
    LiveData<ApiResponse<ResponseBody>> deleteFavourite(@Path("routineId") int routineId);

    @GET("favourites")
    LiveData<ApiResponse<PagedList<Routine>>> getFavourites();
}