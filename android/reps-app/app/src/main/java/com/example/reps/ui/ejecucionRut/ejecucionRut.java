package com.example.reps.ui.ejecucionRut;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.reps.R;
import com.example.reps.databinding.ActivityLogedBinding;
import com.example.reps.retrofit.api.model.Cycle;
import com.example.reps.retrofit.api.model.CycleExercise;
import com.example.reps.retrofit.api.model.Exercise;
import com.example.reps.retrofit.api.model.PagedList;

import java.util.Timer;
import java.util.TimerTask;


public class ejecucionRut extends AppCompatActivity {

    private CycleExercise current;
    private CycleExercise next;
    private PagedList<Cycle> routineCycles;
    private Cycle currentCycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //routine
        setContentView(R.layout.activity_ejecucion_rut);

        TextView timer = findViewById(R.id.ex_time);
        timer.setText("00:00");

        TextView ciclo = findViewById(R.id.nombreCiclo);
        ciclo.setText(currentCycle.getName());
        TextView current_ex = findViewById(R.id.nombre_ejercicio);
        current_ex.setText(current.getExercise().getName());
        Button next_ex = findViewById(R.id.next_ex);
        next_ex.setText(next.getExercise().getName());

        Timer timeLeft = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

            }
        };

        timeLeft.scheduleAtFixedRate(timerTask, 100, 100);
    }

}