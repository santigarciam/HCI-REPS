package com.example.reps.ui.ejecucionRut;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.reps.R;
import com.example.reps.databinding.ActivityLogedBinding;
import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.Cycle;
import com.example.reps.retrofit.api.model.CycleExercise;
import com.example.reps.retrofit.api.model.Exercise;
import com.example.reps.retrofit.api.model.PagedList;
import com.example.reps.retrofit.api.model.Routine;
import com.example.reps.retrofit.api.repository.Status;
import com.example.reps.databinding.ActivityEjecucionRutBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class ejecucionRut extends AppCompatActivity {

    private ActivityEjecucionRutBinding binding;
    private Routine routine;
    private CycleExercise current;
    private CycleExercise next;
    private PagedList<Cycle> routineCycles;
    private List<List<CycleExercise>> cycleExerciseList = new ArrayList<>();
    private Cycle currentCycle;
    private App app;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //routine
        app = (App) getApplication();


//        ejecucionRutArgs args = ejecucionRutArgs.fromBundle(savedInstanceState);
        binding = ActivityEjecucionRutBinding.inflate(getLayoutInflater());


        app.getRoutineRepository().getRoutine(2).observe(this,r->{
            if(r.getStatus() == Status.SUCCESS){
                routine = r.getData();
                app.getRoutineRepository().getRoutineCycles(r.getData().getId()).observe(this,c->{
                    if(c.getStatus() == Status.SUCCESS){
                        routineCycles = c.getData();

                        cycleExerciseList = new ArrayList<>();
                        for(int i=0;i<routineCycles.getContent().size();i++){
                            int finalI = i;
                            Cycle cycle = routineCycles.getContent().get(i);
                            app.getRoutineRepository().getCycleExercise(cycle.getId()).observe(this, e->{
                                if(e.getStatus() == Status.SUCCESS){
                                    cycleExerciseList.add(new ArrayList<>());
                                    cycleExerciseList.get(finalI).addAll(e.getData().getContent());
                                    if(finalI == 0){
                                        TextView cicloField = findViewById(R.id.nombreCiclo);
                                        cicloField.setText(cycle.getName());
                                        if(cycleExerciseList.get(0).size() !=0){
                                            TextView currentExField = findViewById(R.id.nombre_ejercicio);
                                            currentExField.setText(cycleExerciseList.get(0).get(0).getExercise().getName());
                                            TextView nextField = findViewById(R.id.nextExField);
                                            if(cycleExerciseList.get(0).size()>1){
                                                nextField.setText(cycleExerciseList.get(0).get(1).toString());
                                            }else{
                                                nextField.setText("FIN CICLO");
                                            }
                                        }

                                    }
                                }

                            });

                        }
                    }
                });
            }

        });


        setContentView(R.layout.activity_ejecucion_rut);
//        TextView timer = findViewById(R.id.ex_time);
//        timer.setText("00:00");
//        TextView ciclo = findViewById(R.id.nombreCiclo);
//        ciclo.setText(currentCycle.getName());
//        TextView current_ex = findViewById(R.id.nombre_ejercicio);
//        current_ex.setText(current.getExercise().getName());
//        Button next_ex = findViewById(R.id.next_ex);
//        next_ex.setText(next.getExercise().getName());

        Timer timeLeft = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

            }
        };

        timeLeft.scheduleAtFixedRate(timerTask, 100, 100);
    }

    /// funcion que espera  a qu termine el timer --> siguiente ej


}