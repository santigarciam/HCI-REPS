package com.example.reps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int value = 0;
    List<RoutineCard> rutinas;

    //////////////////
    RecyclerView verticalRecyclerView;
    RoutineSectionAdapter verticalAdapter;
    RecyclerView horizontalRecyclerView;
    ArrayList<RoutineSection> arrayList1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///// Para que no se vea el titel bar

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        /////

        setContentView(R.layout.activity_home);
        init();


    }

    public void init(){
        rutinas = new ArrayList<>();
        rutinas.add(new RoutineCard("Rut Tomas", "Paulo"));
        rutinas.add(new RoutineCard("Rut Lu", "Gaston"));
        rutinas.add(new RoutineCard("Rut Tomas", "Paulo"));
        rutinas.add(new RoutineCard("Rut Lu", "Gaston"));
        rutinas.add(new RoutineCard("Rut Tomas", "Paulo"));
        rutinas.add(new RoutineCard("Rut Lu", "Gaston"));
        rutinas.add(new RoutineCard("Rut Tomas", "Paulo"));
        rutinas.add(new RoutineCard("Rut Lu", "Gaston"));

        RoutineCardAdapter rAdapter = new RoutineCardAdapter(rutinas, this);
        //RecyclerView recyclerView = findViewById(R.id.section_rout_recycler_view);
        //recyclerView.setHasFixedSize(true);
        //LinearLayoutManager lManager = new LinearLayoutManager(this);
        //lManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //recyclerView.setLayoutManager(lManager);
        //recyclerView.setAdapter(rAdapter);

        ////////////////////////////////
        verticalRecyclerView = findViewById(R.id.section_rout_recycler_view);
        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        arrayList1 = new ArrayList<>();
        verticalAdapter = new RoutineSectionAdapter(arrayList1,this);
        verticalRecyclerView.setAdapter(verticalAdapter);
        RoutineSection verticalModel = new RoutineSection("Prueba 1", rutinas);
        arrayList1.add(verticalModel);
        RoutineSection verticalModel2 = new RoutineSection("Prueba 2", rutinas);
        arrayList1.add(verticalModel2);
        RoutineSection verticalModel3 = new RoutineSection("Prueba 3", rutinas);
        arrayList1.add(verticalModel3);
        verticalAdapter.notifyDataSetChanged();
    }


}