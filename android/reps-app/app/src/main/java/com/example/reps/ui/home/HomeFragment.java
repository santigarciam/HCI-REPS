package com.example.reps.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reps.LogedActivity;
import com.example.reps.R;
import com.example.reps.RoutineCard;
import com.example.reps.RoutineCardAdapter;
import com.example.reps.RoutineSection;
import com.example.reps.RoutineSectionAdapter;
import com.example.reps.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    int value = 0;
    List<RoutineCard> rutinas;

    //////////////////
    RecyclerView verticalRecyclerView;
    RoutineSectionAdapter verticalAdapter;
    RecyclerView horizontalRecyclerView;
    ArrayList<RoutineSection> arrayList1;
    LogedActivity lgA;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init(root,container);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void init(View rootView,ViewGroup container){
        rutinas = new ArrayList<>();
        rutinas.add(new RoutineCard("Rut Tomas", "Paulo"));
        rutinas.add(new RoutineCard("Rut Lu", "Gaston"));
        rutinas.add(new RoutineCard("Rut Tomas", "Paulo"));
        rutinas.add(new RoutineCard("Rut Lu", "Gaston"));
        rutinas.add(new RoutineCard("Rut Tomas", "Paulo"));
        rutinas.add(new RoutineCard("Rut Lu", "Gaston"));
        rutinas.add(new RoutineCard("Rut Tomas", "Paulo"));
        rutinas.add(new RoutineCard("Rut Lu", "Gaston"));

        RoutineCardAdapter rAdapter = new RoutineCardAdapter(rutinas, this.getContext());
        verticalRecyclerView =(RecyclerView) rootView.findViewById(R.id.section_rout_recycler_view);

        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));

        arrayList1 = new ArrayList<>();
        verticalAdapter = new RoutineSectionAdapter(arrayList1,this.getContext());
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