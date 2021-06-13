package com.example.reps.api;

import android.util.Log;

import com.example.reps.api.model.Error;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ApiResponse<T> {
    private T data;
    private Error error;

    public T getData(){
        return data;
    }

    public Error getError(){
        return error;
    }

    public ApiResponse(Response<T> response){
        parseResponse(response);
    }

    public ApiResponse(Throwable t){
        this.error = buildError(t.getMessage());
    }

    private void parseResponse (Response<T> response){
        if (response.isSuccessful()){
            this.data = response.body();
            return;
        }
        if (response.errorBody() == null){
            this.error = buildError("Missing error body");
            return;
        }

        String message;
        try {
            message = response.errorBody().string();
        } catch (IOException exception){
            Log.e("API", exception.toString());
            this.error= buildError(exception.getMessage());
            return;
        }

        if (message != null && message.trim().length() > 0){
            Gson gson = new Gson();
            this.error = gson.fromJson(message, new TypeToken<ApiError>() {}.getType());
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
}
