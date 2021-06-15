package com.example.reps;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.Navigation;

import com.example.reps.retrofit.AppPreferences;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///// Para que no se vea el titel bar

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        /////
        AppPreferences appPreferences = new AppPreferences(this);
        if (appPreferences.getAuthToken() != null) {
            Intent intent = new Intent(this, LogedActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            this.finish();
            //Navigation.findNavController(view).navigate(FirstFragmentDirections.actionFirstFragmentToLogedActivity());
        } else {
            setContentView(R.layout.activity_main);
        }

//        Uri uri = getIntent().getData();
//        if (uri != null){
//            // https://www.reps.com/routines/{id}/
//        String path = uri.toString();
//            Log.d("MainActivity", "link recivido: " + path);
//        Bundle bundle = new Bundle();
//        bundle.putInt("ID_rutina", Integer.parseInt(path.substring(30, 31)));
//            bundle.putBoolean("isFav",false);
//            Navigation.findNavController(this, R.id.mainActivity).navigate(R.id.vista_rutina, bundle);

//        PendingIntent pendingIntent = new NavDeepLinkBuilder(this)
//                .setGraph(R.navigation.mobile_navigation)
//                .setDestination(R.id.vista_rutina)
//                .setArguments(bundle)
//                .createPendingIntent();
        //}
        
    }




}