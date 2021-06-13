package com.example.reps.api.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.reps.App;
import com.example.reps.api.ApiClient;
import com.example.reps.api.ApiFavouriteService;
import com.example.reps.api.ApiResponse;
import com.example.reps.api.model.PagedList;
import com.example.reps.api.model.Routine;

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
