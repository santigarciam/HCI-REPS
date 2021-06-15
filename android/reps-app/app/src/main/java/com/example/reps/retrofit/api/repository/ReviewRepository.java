package com.example.reps.retrofit.api.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.ApiClient;
import com.example.reps.retrofit.api.ApiResponse;
import com.example.reps.retrofit.api.ApiReviewService;
import com.example.reps.retrofit.api.model.PagedList;
import com.example.reps.retrofit.api.model.ReviewResponseBody;
import com.example.reps.retrofit.api.model.Reviews;
import com.example.reps.retrofit.api.model.Routine;

public class ReviewRepository {
    private final ApiReviewService apiService;

    public ReviewRepository(App app) {
        this.apiService = ApiClient.create(app,ApiReviewService.class);
    }

    public LiveData<Resource<ReviewResponseBody>> postReview(Integer id,Reviews review) {
        return new NetworkBoundResource<ReviewResponseBody, ReviewResponseBody>() {
            @NonNull
            @Override
            protected LiveData<ApiResponse<ReviewResponseBody>> createCall() {
                return apiService.postReview(id,review);
            }
        }.asLiveData();
    }
}
