package com.example.reps.retrofit.api.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.reps.retrofit.App;

import com.example.reps.retrofit.api.ApiClient;
import com.example.reps.retrofit.api.ApiFavouriteService;
import com.example.reps.retrofit.api.ApiResponse;
import com.example.reps.retrofit.api.ApiSportService;
import com.example.reps.retrofit.api.model.PagedList;
import com.example.reps.retrofit.api.model.Routine;

import okhttp3.ResponseBody;

public class FavouriteRepository {
    private final ApiFavouriteService apiService;

    public FavouriteRepository(App app) {
        this.apiService = ApiClient.create(app, ApiFavouriteService.class);
    }

    public LiveData<Resource<PagedList<Routine>>> getFavourites() {
        return new NetworkBoundResource<PagedList<Routine>, PagedList<Routine>>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<PagedList<Routine>>> createCall() {
                return apiService.getFavourites();
            }
        }.asLiveData();
    }

    public LiveData<Resource<ResponseBody>> addFavourite(int routineId) {
        return new NetworkBoundResource<ResponseBody, ResponseBody>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return apiService.addFavourite(routineId);
            }
        }.asLiveData();
    }
    public LiveData<Resource<ResponseBody>> deleteFavourite(int routineId) {
        return new NetworkBoundResource<ResponseBody, ResponseBody>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return apiService.deleteFavourite(routineId);
            }
        }.asLiveData();
    }
}
