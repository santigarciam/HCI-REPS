package com.example.reps.ui.home;

import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reps.R;
import com.example.reps.RoutineCard;
import com.example.reps.RoutineCardAdapter;
import com.example.reps.RoutineSection;
import com.example.reps.RoutineSectionAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    int value = 0;
        private List<RoutineCard> rutinas;
          RecyclerView verticalRecyclerView;
         RoutineSectionAdapter verticalAdapter;
       // private  RecyclerView horizontalRecyclerView;
        private ArrayList<RoutineSection> arrayList1;
   // private  LogedActivity lgA;


    public HomeViewModel() {
    }

    public void init(View rootView, ViewGroup container){
        rutinas = new ArrayList<>();
        rutinas.add(new RoutineCard("Rut Tomas", "Paulo"));
        rutinas.add(new RoutineCard("Rut Lu", "Gaston"));
        rutinas.add(new RoutineCard("Rut Tomas", "Paulo"));
        rutinas.add(new RoutineCard("Rut Lu", "Gaston"));
        rutinas.add(new RoutineCard("Rut Tomas", "Paulo"));
        rutinas.add(new RoutineCard("Rut Lu", "Gaston"));
        rutinas.add(new RoutineCard("Rut Tomas", "Paulo"));
        rutinas.add(new RoutineCard("Rut Lu", "Gaston"));

        RoutineCardAdapter rAdapter = new RoutineCardAdapter(rutinas, container.getContext());
        verticalRecyclerView =(RecyclerView) rootView.findViewById(R.id.section_rout_recycler_view);

        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager( container.getContext(),LinearLayoutManager.VERTICAL,true));

        arrayList1 = new ArrayList<>();
        verticalAdapter = new RoutineSectionAdapter(arrayList1, container.getContext());
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