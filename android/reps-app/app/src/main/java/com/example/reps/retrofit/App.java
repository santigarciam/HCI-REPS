package com.example.reps.retrofit;

import android.app.Application;

import com.example.reps.retrofit.AppPreferences;
import com.example.reps.retrofit.api.repository.ExecutionRepository;
import com.example.reps.retrofit.api.repository.FavouriteRepository;
import com.example.reps.retrofit.api.repository.RoutineRepository;
import com.example.reps.retrofit.api.repository.UserRepository;

public class App extends Application {

    private static AppPreferences preferences;
    private UserRepository userRepository;
    private FavouriteRepository favouriteRepository;
    private RoutineRepository routineRepository;
    private ExecutionRepository executionRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }
    public FavouriteRepository getFavouriteRepository() {
        return favouriteRepository;
    }
    public RoutineRepository getRoutineRepository(){
        return routineRepository;
    }

    public static AppPreferences getPreferences(){
        return preferences;
    }

    public ExecutionRepository getExecutionRepository() {
        return executionRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = new AppPreferences(this);
        userRepository = new UserRepository(this);
        favouriteRepository = new FavouriteRepository(this);
        routineRepository = new RoutineRepository(this);
        executionRepository = new ExecutionRepository(this);
    }
}
