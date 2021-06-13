
//TODO: borrar esta clase antes de entregar
//package com.example.reps.retrofit.ui;
//
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.reps.databinding.ActivityMainBinding;
//import com.example.reps.retrofit.App;
//import com.example.reps.retrofit.api.model.Sport;
//
//import java.util.Random;
//
//
//
//public class MainActivity extends AppCompatActivity {
//
//    public static final String TAG = "UI";
//    private App app;
//    private ActivityMainBinding binding;
//    private Sport sport;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        app = ((App)getApplication());
//
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        binding.loginButton.setOnClickListener(v -> {
//            Credentials credentials = new Credentials("johndoe", "1234567890");
//            app.getUserRepository().login(credentials).observe(this, r -> {
//                if (r.getStatus() == Status.SUCCESS) {
//                    Log.d(TAG, getString(R.string.success));
//                    app.getPreferences().setAuthToken(r.getData().getToken());
//                    binding.result.setText(R.string.success);
//                    binding.getCurrentUserButton.setEnabled(true);
//                    binding.getAllButton.setEnabled(true);
//                    binding.getButton.setEnabled(true);
//                    binding.addButton.setEnabled(true);
//                } else {
//                    defaultResourceHandler(r);
//                }
//            });
//        });
//
//        binding.getCurrentUserButton.setOnClickListener(v -> {
//            app.getUserRepository().getCurrentUser().observe(this, r -> {
//                if (r.getStatus() == Status.SUCCESS) {
//                    Log.d(TAG, getString(R.string.success));
//                    binding.result.setText(R.string.success);
//                } else {
//                    defaultResourceHandler(r);
//                }
//            });
//        });
//
//        binding.getAllButton.setOnClickListener(v -> {
//            app.getSportRepository().getSports().observe(this, r -> {
//                if (r.getStatus() == Status.SUCCESS) {
//                    Log.d(TAG, getString(R.string.success));
//                    int count = r.getData().getContent().size();
//                    String message = getResources().getQuantityString(R.plurals.found, count, count);
//                    binding.result.setText(message);
//                } else {
//                    defaultResourceHandler(r);
//                }
//            });
//        });
//
//        binding.getButton.setOnClickListener(v -> {
//            app.getSportRepository().getSport(1).observe(this, r -> {
//                if (r.getStatus() == Status.SUCCESS) {
//                    Log.d(TAG, getString(R.string.success));
//                    sport = r.getData();
//                    binding.result.setText(R.string.success);
//                    binding.addButton.setEnabled(true);
//                } else {
//                    defaultResourceHandler(r);
//                }
//            });
//        });
//
//        binding.addButton.setOnClickListener(v -> {
//            int random = (new Random()).nextInt(15);
//            sport = new Sport("Football " + random, "Football");
//            app.getSportRepository().addSport(sport).observe(this, r -> {
//                if (r.getStatus() == Status.SUCCESS) {
//                    Log.d(TAG, getString(R.string.success));
//                    sport = r.getData();
//                    binding.result.setText(R.string.success);
//                    binding.modifyButton.setEnabled(true);
//                    binding.deleteButton.setEnabled(true);
//                } else {
//                    defaultResourceHandler(r);
//                }
//            });
//        });
//
//        binding.modifyButton.setOnClickListener(v -> {
//            int random = (new Random()).nextInt(15);
//            sport.setDetail(sport.getName() + ' ' + random);
//            app.getSportRepository().modifySport(sport).observe(this, r -> {
//                if (r.getStatus() == Status.SUCCESS) {
//                    Log.d(TAG, getString(R.string.success));
//                    binding.result.setText(R.string.success);
//                } else {
//                    defaultResourceHandler(r);
//                }
//            });
//        });
//
//        binding.deleteButton.setOnClickListener(v -> {
//            App app = ((App)getApplication());
//            app.getSportRepository().deleteSport(sport.getId()).observe(this, r -> {
//                if (r.getStatus() == Status.SUCCESS) {
//                    Log.d(TAG, getString(R.string.success));
//                    binding.result.setText(R.string.success);
//                    binding.deleteButton.setEnabled(false);
//                } else {
//                    defaultResourceHandler(r);
//                }
//            });
//        });
//    }
//
//    private void defaultResourceHandler(Resource<?> resource) {
//        switch (resource.getStatus()) {
//            case LOADING:
//                Log.d(TAG, getString(R.string.loading));
//                binding.result.setText(R.string.loading);
//                break;
//            case ERROR:
//                Error error = resource.getError();
//                String message = getString(R.string.error, error.getDescription(), error.getCode());
//                Log.d(TAG, message);
//                binding.result.setText(message);
//                break;
//        }
//    }
//}