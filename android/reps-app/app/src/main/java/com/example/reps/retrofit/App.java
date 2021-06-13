package com.example.reps.retrofit;

import android.app.Application;

import com.example.reps.retrofit.repository.SportRepository;
import com.example.reps.retrofit.repository.UserRepository;

// Se crea esta clase apenas se corre la aplicacion
public class App extends Application {

    private AppPreferences preferences;
    private UserRepository userRepository;
    private SportRepository sportRepository;

    public AppPreferences getPreferences() { return preferences; }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public SportRepository getSportRepository() {
        return sportRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = new AppPreferences(this);

        userRepository = new UserRepository(this);

        sportRepository = new SportRepository(this);
    }
}
