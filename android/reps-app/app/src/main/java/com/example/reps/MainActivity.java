package com.example.reps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickMeFunc(View view) {
        value++;
        final TextView mTextView = (TextView) findViewById(R.id.textViewOnClick);
        mTextView.setText("Value :" + value);
    }
}