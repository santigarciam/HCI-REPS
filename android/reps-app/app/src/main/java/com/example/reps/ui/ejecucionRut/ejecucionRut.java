package com.example.reps.ui.ejecucionRut;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.reps.DialogFragmentRate;
import com.example.reps.MainActivity;
import com.example.reps.R;
import com.example.reps.databinding.ActivityLogedBinding;
import com.example.reps.retrofit.App;
import com.example.reps.retrofit.AppPreferences;
import com.example.reps.retrofit.api.model.Cycle;
import com.example.reps.retrofit.api.model.CycleExercise;
import com.example.reps.retrofit.api.model.ExecutionInformation;
import com.example.reps.retrofit.api.model.Exercise;
import com.example.reps.retrofit.api.model.PagedList;
import com.example.reps.retrofit.api.model.Routine;
import com.example.reps.retrofit.api.repository.Status;
import com.example.reps.databinding.ActivityEjecucionRutBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.text.StringsKt;


public class ejecucionRut extends AppCompatActivity {

    private ActivityEjecucionRutBinding binding;
    private Routine routine;
    private CycleExercise current,next;
    private TextView cycleField, currentExField ,nextField ,timeRepsField;
    ImageButton moreInfo ;
    TextView descrField ;
    private Iterator<CycleExercise> exerciseIterator;
    private Iterator<Cycle> cycleIterator;
    private List<Cycle> routineCycles;
    private boolean paused = false;
    private boolean moreInfoFlag = false;
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
         moreInfo = findViewById(R.id.moreInfoBtn);
         descrField = findViewById(R.id.exercise_description);

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
                                            String descr = getString(R.string.descripcion_ej);
                                            descrField.setText(descr.concat(": ").concat(current.getExercise().getDetail()));
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
                if(!paused){
                    timerMethod();
                }

            }
        },0,1000);

        //Button pauseBtn = findViewById(R.id.pauseBtn);
        CircleImageView pauseView = findViewById(R.id.pauseBtnView);
        pauseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paused = !paused;
                if(paused){
                    //TODO: cambiar por boton de pausa y mejorar el de play
                    pauseView.setImageResource(R.drawable.ic_pause);
                }else{
                    pauseView.setImageResource(R.drawable.ic_play);
                }

            }
        });

        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreInfoFlag = !moreInfoFlag;
                if(moreInfoFlag){
//                    int ident = getResources().getIdentifier("Reference:\t@android:drawable/arrow_down_float","drawable",getPackageName());
//                    moreInfo.setImageResource(ident);
                    descrField.setVisibility(View.VISIBLE);
                }else{
//                    moreInfo.setImageResource(R);
                    descrField.setVisibility(View.INVISIBLE);
                }
            }
        });

        Button endRoutineBtn = findViewById(R.id.endBtn);
        endRoutineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoSalida();
            }
        });

    }

    private void mostrarDialogoSalida(){
        android.app.AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Terminar Rutina")
                .setMessage("¿Seguro que deseas terminar la rutina: ".concat(routine.getName()).concat(" ?"))
                .setPositiveButton("Si", null)
                .setNegativeButton("Cancelar", null)
                .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setBackgroundColor(getColor(R.color.grey));
        positiveButton.setTextColor(getColor(R.color.white));
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finishRoutine();
            }
        });

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setBackgroundColor(getColor(R.color.our_blue));
        negativeButton.setTextColor(getColor(R.color.white));
        negativeButton.setPadding(0,0,5,0);
        negativeButton
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


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


    public void finishRoutine(){
        app.getExecutionRepository().addRoutineExec(routine.getId(),new ExecutionInformation(0,false)).observe(this,r->{
            if(r.getStatus() == Status.SUCCESS){

            }
        });
        DialogFragmentRate dialog = new DialogFragmentRate(routine.getId(),this);
        dialog.setApp(app);
        dialog.show(getSupportFragmentManager(),"rate");


    }
    private  boolean flag=false;
    public void nextExercise(){

        if(flag){
            finishRoutine();
        }
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


        if(!exerciseIterator.hasNext() && !cycleIterator.hasNext()){
            flag=true;
            nextField.setText("");
        }else{
            nextField.setText(next.getExercise().getName());
        }

        currentExField.setText(current.getExercise().getName());
        descrField.setText(current.getExercise().getDetail());
        timeRepsField.setText(current.getRepetitions()==0?current.getDuration()+"s":"X "+current.getRepetitions());
        cycleField.setText(currentCycle.getName());
    }

    /// funcion que espera  a qu termine el timer --> siguiente ej


}