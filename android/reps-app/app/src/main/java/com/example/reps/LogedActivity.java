package com.example.reps;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reps.databinding.ActivityLogedBinding;

import java.util.ArrayList;
import java.util.List;

public class LogedActivity extends AppCompatActivity {

    private ActivityLogedBinding binding;
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

        binding = ActivityLogedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_descubrir, R.id.navigation_favoritos,R.id.navigation_perfil)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_loged);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
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