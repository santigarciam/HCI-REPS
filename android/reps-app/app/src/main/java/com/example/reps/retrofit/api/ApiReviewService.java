package com.example.reps.retrofit.api;

import androidx.lifecycle.LiveData;

import com.example.reps.retrofit.api.model.ReviewResponseBody;
import com.example.reps.retrofit.api.model.Reviews;
import com.example.reps.retrofit.api.model.UserRoutineExecution;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiReviewService {

    @POST("reviews/{routineId}")
    LiveData<ApiResponse<ReviewResponseBody>> postReview(@Path("routineId") int routineId, @Body Reviews reviews);

}
