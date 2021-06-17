package com.example.reps;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

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

        Intent intentURL = getIntent();
        Uri dataURL = intentURL.getData()!=null? intentURL.getData() : Uri.parse("www.null.com");
        if (intentURL.getData()!=null){
            int id = Integer.parseInt(dataURL.getLastPathSegment());
            appPreferences.setSharedRutID(id);
        }

        if (appPreferences.getAuthToken() != null) {
            Intent intent = new Intent(this, LogedActivity.class);


            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            this.finish();

        } else {
            setContentView(R.layout.activity_main);
            //Log.d("FIRST_FRAGMENT", "onViewCreated: " + appPreferences.getSharedRutID());
            if (appPreferences.getSharedRutID() != -1) {

                android.app.AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.alert_titulo_login_o_register))
                        .setMessage(getString(R.string.alert_mensaje_login_o_register))
                        .setPositiveButton("Ok", null)
                        .show();

                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setBackgroundColor(this.getColor(R.color.our_blue));
                button.setTextColor(this.getColor(R.color.white));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        }

    }


}