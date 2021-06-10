package com.example.reps.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reps.R;
import com.example.reps.RoutineCard;
import com.example.reps.RoutineCardAdapter;
import com.example.reps.RoutineSection;
import com.example.reps.RoutineSectionAdapter;
import com.example.reps.databinding.FragmentDescubrirBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DescubrirFragment extends Fragment implements  SearchView.OnQueryTextListener{

    private DescubrirViewModel descubrirViewModel;
    private FragmentDescubrirBinding binding;
    private SearchView searchView;

    ////////////////
    private List<RoutineCard> rutinas;
    RecyclerView verticalRecyclerView;
    RoutineCardAdapter rAdapter;
    ////////////////////


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
        //////////////
         rAdapter = new RoutineCardAdapter(rutinas, container.getContext());
        verticalRecyclerView =(RecyclerView) rootView.findViewById(R.id.section_rout_recycler_viewDesc);

        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager( container.getContext(),LinearLayoutManager.VERTICAL,false));

        verticalRecyclerView.setAdapter(rAdapter);

    }
    /////////////////////

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        descubrirViewModel =
                new ViewModelProvider(this).get(DescubrirViewModel.class);
        binding = FragmentDescubrirBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init(root,container);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    // Se ejecuta cuando presionamos para realizar la busqueda
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    // Va escuchando cuando ingresamos letras en el text view
    @Override
    public boolean onQueryTextChange(String s) {
        rAdapter.filter(s);

        return false;
    }
}