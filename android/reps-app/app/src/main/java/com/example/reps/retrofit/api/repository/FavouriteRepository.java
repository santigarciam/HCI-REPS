package com.example.reps.retrofit.api.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.reps.retrofit.App;

import com.example.reps.retrofit.api.ApiClient;
import com.example.reps.retrofit.api.ApiResponse;
import com.example.reps.retrofit.api.ApiSportService;
import com.example.reps.retrofit.api.model.PagedList;
import com.example.reps.retrofit.api.model.Routine;

public class FavouriteRepository {
    private final ApiSportService.ApiFavouriteService apiService;

    public FavouriteRepository(App app) {
        this.apiService = ApiClient.create(app, ApiSportService.ApiFavouriteService.class);
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

    public LiveData<Resource<Void>> addFavourite(int routineId) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.addFavourite(routineId);
            }
        }.asLiveData();
    }
    public LiveData<Resource<Void>> deleteFavourite(int routineId) {
        return new NetworkBoundResource<Void, Void>()
        {
            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return apiService.deleteFavourite(routineId);
            }
        }.asLiveData();
    }
}
