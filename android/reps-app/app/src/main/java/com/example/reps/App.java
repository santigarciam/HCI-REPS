package com.example.reps;

import android.app.Application;

import com.example.reps.api.repository.FavouriteRepository;
import com.example.reps.api.repository.RoutineRepository;
import com.example.reps.api.repository.UserRepository;

public class App extends Application {

    private static AppPreferences preferences;
    private UserRepository userRepository;
    private FavouriteRepository favouriteRepository;
    private RoutineRepository routineRepository;

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

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = new AppPreferences(this);
        userRepository = new UserRepository(this);
        favouriteRepository = new FavouriteRepository(this);
        routineRepository = new RoutineRepository(this);

    }
}
