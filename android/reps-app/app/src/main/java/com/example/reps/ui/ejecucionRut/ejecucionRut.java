package com.example.reps.ui.ejecucionRut;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kotlin.text.StringsKt;


public class ejecucionRut extends AppCompatActivity {

    private ActivityEjecucionRutBinding binding;
    private Routine routine;
    private CycleExercise current,next;
    private TextView cycleField, currentExField ,nextField ,timeRepsField;
    private Iterator<CycleExercise> exerciseIterator;
    private Iterator<Cycle> cycleIterator;
    private List<Cycle> routineCycles;
    private List<List<CycleExercise>> cycleExerciseList = new ArrayList<>();
    private Cycle currentCycle;
    private App app;
    private CycleExercise changeCycleEx = new CycleExercise(0,0,0,new Exercise(0,"Cambio ciclo","","",0,0));
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //routine
        app = (App) getApplication();
        int idRut = getIntent().getIntExtra("RUTINA_ID",-1);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

//        ejecucionRutArgs args = ejecucionRutArgs.fromBundle(savedInstanceState);
        binding = ActivityEjecucionRutBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_ejecucion_rut);
        cycleField = findViewById(R.id.nombreCiclo);
        currentExField = findViewById(R.id.nombre_ejercicio);
        nextField = findViewById(R.id.nextExField);
        timeRepsField = findViewById(R.id.timeExercise);

        app.getRoutineRepository().getRoutine(idRut).observe(this,r->{
            if(r.getStatus() == Status.SUCCESS){
                routine = r.getData();
                app.getRoutineRepository().getRoutineCycles(r.getData().getId()).observe(this,c->{
                    if(c.getStatus() == Status.SUCCESS){
                        routineCycles = c.getData().getContent();

                        cycleExerciseList = new ArrayList<>();
                        for(int i=0;i<routineCycles.size();i++){
                            int finalI = i;
                            Cycle cycle = routineCycles.get(i);
                            cycleExerciseList.add(new ArrayList<>());
                            app.getRoutineRepository().getCycleExercise(cycle.getId()).observe(this, e->{
                                if(e.getStatus() == Status.SUCCESS){
                                    cycle.setCycleExercises(e.getData());
                                    cycleExerciseList.get(finalI).addAll(e.getData().getContent());
                                    if(finalI == 0){
                                        cycleField.setText(cycle.getName());
                                        currentCycle = currentCycle;
                                        if(cycleExerciseList.get(0).size() !=0){
                                            exerciseIterator = cycleExerciseList.get(0).iterator();
                                            if(exerciseIterator.hasNext()){
                                                current = exerciseIterator.next();
                                                timeRepsField.setText(current.getRepetitions()==0?current.getDuration()+"s":"x"+current.getRepetitions());
                                                if(exerciseIterator.hasNext()){
                                                    next = exerciseIterator.next();
                                                }else{
                                                    next = changeCycleEx;
                                                }
                                            }
                                            currentExField.setText(current.getExercise().getName());
                                            nextField.setText(next.getExercise().getName());
                                        }

                                    }
                                }

                            });

                        }
                        cycleIterator = routineCycles.iterator();
                        currentCycle = cycleIterator.next();
                    }
                });
            }

        });

        Button nextExeBTN = findViewById(R.id.siguiente_ejercico);
        nextExeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextExercise();
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timerMethod();
            }
        },0,1000);


    }

    private void timerMethod(){
        this.runOnUiThread(TIMER_TI);
    }

    private Runnable TIMER_TI = new Runnable() {
        @Override
        public void run() {
            if(current!= null && current.getRepetitions()==0) {
                String time = timeRepsField.getText().toString().substring(0,2);
                Integer t = StringsKt.toIntOrNull(time);
                t -=1;
                if(t<10){
                    timeRepsField.setText("0"+t.toString() + "s");
                }else {
                    timeRepsField.setText(t.toString() + "s");
                }
                if(t==0){
                    nextExercise();
                }
            }
        }
    };


    public void nextExercise(){

        if(next.getExercise().getName().equals(changeCycleEx.getExercise().getName())){ // Si es el de cambio
            if(cycleIterator.hasNext()){
                currentCycle = cycleIterator.next();
                exerciseIterator = currentCycle.getCycleExercises().getContent().iterator();
                next = exerciseIterator.next();
            }else{
                current = changeCycleEx;
            }
        }else { // no es el de cambio
            current = next;
            if(exerciseIterator.hasNext()){
                next = exerciseIterator.next();
            }else{
                if(cycleIterator.hasNext()){
                    currentCycle = cycleIterator.next();
                    exerciseIterator = currentCycle.getCycleExercises().getContent().iterator();
                    next = exerciseIterator.next();
                }
            }
        }


        currentExField.setText(current.getExercise().getName());
        timeRepsField.setText(current.getRepetitions()==0?current.getDuration()+"s":"x"+current.getRepetitions());
        nextField.setText(next.getExercise().getName());
        cycleField.setText(currentCycle.getName());
    }

    /// funcion que espera  a qu termine el timer --> siguiente ej


}