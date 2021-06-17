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
import android.widget.Toast;

import com.example.reps.DialogFragmentRate;
import com.example.reps.MainActivity;
import com.example.reps.R;

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
import java.util.Comparator;
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
    private Integer currentCycleRep=0;
    private Integer cantExCurrentCycle =0;
    private TextView cycleField, currentExField  ,timeRepsField,descrField,startMessageField;
    ImageButton moreInfo ;
    Button nextField;
    private boolean isInitialized = false;
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
//        binding = ActivityEjecucionRutBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_ejecucion_rut);
        cycleField = findViewById(R.id.nombreCiclo);
        currentExField = findViewById(R.id.nombre_ejercicio);
        nextField = findViewById(R.id.siguiente_ejercicoBtn);
        timeRepsField = findViewById(R.id.timeExercise);
         moreInfo = findViewById(R.id.moreInfoBtn);
         descrField = findViewById(R.id.exercise_description);
        nextField.setVisibility(View.INVISIBLE);
        TextView sigField = findViewById(R.id.siguiente_ejText);
        startMessageField = findViewById(R.id.mensaje_comienzo_field);
        startMessageField.setText(R.string.mensaje_comienzo_ejecucion);
        startMessageField.setVisibility(View.VISIBLE);

        sigField.setText("");
        app.getRoutineRepository().getRoutine(idRut).observe(this,r->{
            //Obtengo la rutina
            if(r.getStatus() == Status.SUCCESS){
                routine = r.getData();
                app.getRoutineRepository().getRoutineCycles(idRut).observe(this,c->{
                    //Obtengo los ciclos de la rutina
                    if(c.getStatus() == Status.SUCCESS){
                        routineCycles = c.getData().getContent();

                        cycleExerciseList = new ArrayList<>();
                        for(int i=0;i<routineCycles.size();i++){
                            int finalI = i;
                            Cycle cycle = routineCycles.get(i);
                            cycleExerciseList.add(new ArrayList<>());
                            app.getRoutineRepository().getCycleExercise(cycle.getId()).observe(this, e->{
                                //Obtengo los ejercicios de cada ciclo
                                if(e.getStatus() == Status.SUCCESS){
                                    cycle.setCycleExercises(e.getData());
                                    }
                            });
                        }
                    }
                });
            }

        });

        Button nextExeBTN = findViewById(R.id.siguiente_ejercicoBtn);
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


        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreInfoFlag = !moreInfoFlag;
                if(moreInfoFlag){
                    moreInfo.setRotation(180);
                    descrField.setVisibility(View.VISIBLE);
                }else{
                    moreInfo.setRotation(0);
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

        CircleImageView pauseView = findViewById(R.id.pauseBtnView);
        pauseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isInitialized){
                    prepareExecution();
                    nextField.setVisibility(View.VISIBLE);
                    TextView sigField = findViewById(R.id.siguiente_ejText);
                    String mesage = getResources().getString(R.string.siguiente_ej);
                    sigField.setText(mesage);
                    moreInfo.setVisibility(View.VISIBLE);
                    isInitialized = true;
                    pauseView.setImageResource(R.drawable.ic_pause);
                    endRoutineBtn.setVisibility(View.INVISIBLE);
                }else {
                    paused = !paused;
                    if (paused) {
                        endRoutineBtn.setVisibility(View.VISIBLE);
                        pauseView.setImageResource(R.drawable.ic_play);
                    } else {
                        endRoutineBtn.setVisibility(View.INVISIBLE);
                        pauseView.setImageResource(R.drawable.ic_pause);

                    }
                }

            }
        });


        currentExField.setText("");
        descrField.setText("");
        timeRepsField.setText("");
        cycleField.setText("");
        nextField.setText("Siguente ejercicio...");
        moreInfo.setVisibility(View.INVISIBLE);



    }
    private final List<CycleExercise> exercises = new ArrayList<>();
    private final List<Cycle> cycles = new ArrayList<>();

    private void prepareExecution() {
        routineCycles.sort(new Comparator<Cycle>() {
            @Override
            public int compare(Cycle cycle, Cycle t1) {
                return cycle.getOrder() -t1.getOrder();
            }
        });
        for(Cycle cycle:routineCycles){
            Integer repsCycle = cycle.getRepetitions() * cycle.getCycleExercises().getContent().size();
            for(int j=0;j<repsCycle;j++){
                cycles.add(cycle);
            }
        }

        cycleIterator = cycles.iterator();
        currentCycle = cycleIterator.next();
        for(Cycle cycle:routineCycles){
            Integer repetitions = cycle.getRepetitions();
            for(int i=0;i<repetitions;i++){
                for(CycleExercise exercise: cycle.getCycleExercises().getContent()){
                    exercises.add(exercise);
                }
            }
        }

        startMessageField.setVisibility(View.GONE);
        exerciseIterator =exercises.iterator();
        currentCycleRep = currentCycle.getRepetitions();
        current = exerciseIterator.next();
        next = exerciseIterator.next();
        nextField.setText(next.getExercise().getName().toUpperCase());
        currentExField.setText(current.getExercise().getName());
        descrField.setText(current.getExercise().getDetail());

        timeRepsField.setText(current.getRepetitions()==0?(current.getDuration()<10?"0"+current.getDuration()+"s":current.getDuration()+"s"):"X "+current.getRepetitions());
        cycleField.setText(firstToUpperOtherLowerCase(currentCycle.getName()));
        CircleImageView pauseView = findViewById(R.id.pauseBtnView);

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

    @Override
    public void onBackPressed() {
        mostrarDialogoSalida();
    }

    private void timerMethod(){
        this.runOnUiThread(TIMER_TI);
    }

    private Runnable TIMER_TI = new Runnable() {
        @Override
        public void run() {
            if(current!= null && current.getRepetitions()==0) {

                Integer size = timeRepsField.length();
                String time = timeRepsField.getText().toString().substring(0,size-1);
                Integer t = StringsKt.toIntOrNull(time);
                if(t==null){
                    time = timeRepsField.getText().toString().substring(0,1);
                    t = StringsKt.toIntOrNull(time);
                }
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
        app.getExecutionRepository().addRoutineExec(routine.getId(),new ExecutionInformation(2,false)).observe(this,r->{
            if(r.getStatus() == Status.SUCCESS){

            }else if(r.getStatus() == Status.ERROR){
                Log.d("EXECUTION ", r.getError().getDescription()+ " "+r.getError().getDetails());
            }
        });
        DialogFragmentRate dialog = new DialogFragmentRate(routine.getId(),this);
        dialog.setApp(app);
        dialog.show(getSupportFragmentManager(),"rate");



    }
    private  boolean finishRut = false;

    public void nextExercise(){
        if(isInitialized){
            if (exerciseIterator.hasNext()) {
                current = next;
                next = exerciseIterator.next();
                if (cycleIterator.hasNext()) {
                    currentCycle = cycleIterator.next();
                } else {
                    finishRoutine();
                }
            } else {
                if (finishRut) {
                    finishRoutine();
                } else {
                    current = next;
                    next = null;
                    finishRut = true;
                }
            }

            if (next == null) {
                nextField.setVisibility(View.INVISIBLE);
                TextView sigField = findViewById(R.id.siguiente_ejText);
                sigField.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                String mesage = getResources().getString(R.string.ultimo_ejercicio_mensaje);
                sigField.setText(mesage);
                sigField.setTextSize(24);

            } else {
                nextField.setText(next.getExercise().getName().toUpperCase());
            }
            currentExField.setText(firstToUpperOtherLowerCase(current.getExercise().getName()));
            descrField.setText(current.getExercise().getDetail());
            timeRepsField.setText(current.getRepetitions() == 0 ? (current.getDuration() < 10 ? "0" + current.getDuration() + "s" : current.getDuration() + "s") : "X " + current.getRepetitions());
            cycleField.setText(firstToUpperOtherLowerCase(currentCycle.getName()));
        }

    }

    private String firstToUpperOtherLowerCase(String s){
        String aux = s.substring(0,1);
        return  aux.toUpperCase().concat(s.substring(1).toLowerCase());

    }
}