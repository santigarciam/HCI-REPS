package com.example.reps;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.Routine;
import com.example.reps.retrofit.api.repository.Status;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FullSection extends Fragment {



    private List<RoutineCard> rutinas;

    private App app;

    private Map<String,List<RoutineCard>> rutinasByDifficulty;

    RecyclerView verticalRecyclerView;
    RoutineSectionAdapter verticalAdapter;
    RoutineCardAdapter rAdapter;
    // private  RecyclerView horizontalRecyclerView;
    private ArrayList<RoutineSection> arrayList1;
    // private  LogedActivity lgA;
    public FullSection() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        App app = (App) requireActivity().getApplication();
        if (getArguments() != null) {
            FullSectionArgs args = FullSectionArgs.fromBundle(getArguments());
            String titulo = args.getSectionTitle();
            Log.d("HOME_VIEW_MODEL", "rut id fav: " + titulo);
            ((TextView)view.findViewById(R.id.full_section_rout_title)).setText(titulo);


            rutinas = new ArrayList<>();
            app.getRoutineRepository().getAll(" ").observe(requireActivity(), r -> {
                if (r.getStatus() == Status.SUCCESS) {

                    List<Routine> ruts = r.getData().getContent();
                    // 0:rookie, 1:beginner, 2:intermediate, 3:advanced, 4:expert
                    rutinasByDifficulty = new HashMap<>();

                    for (Routine rut : ruts) {
                        rutinas.add(new RoutineCard(rut));
                        rutinasByDifficulty.putIfAbsent(rut.getDifficulty(),new ArrayList<>());
                        rutinasByDifficulty.get(rut.getDifficulty()).add(new RoutineCard(rut));
                    }

                    app.getFavouriteRepository().getFavourites().observe(requireActivity(), t -> {
                        if (t.getStatus() == Status.SUCCESS){
                            for (RoutineCard rut : rutinas){
                                for (Routine favRut : t.getData().getContent()){
                                    if (favRut.getId() == rut.getId()){
                                        Log.d("HOME_VIEW_MODEL", "rut id fav: " + favRut.getId());
                                        rut.setFavourite(true);
                                    }
                                }
                            }

                            for (Routine favRut : t.getData().getContent()){
                                for (RoutineCard rut : rutinasByDifficulty.get(favRut.getDifficulty())){
                                    if (favRut.getId() == rut.getId()){
                                        rut.setFavourite(true);
                                        break;
                                    }
                                }
                            }
                            arrayList1 = new ArrayList<>();
                            verticalRecyclerView = (RecyclerView) view.findViewById(R.id.full_section_rout_recycler_view);
//                            Log.d(TAG, "onViewCreated: ");
                            rAdapter = new RoutineCardAdapter(rutinasByDifficulty.getOrDefault(titulo.toLowerCase(),new ArrayList<>()), getContext(),app,requireActivity());
                            verticalRecyclerView.setHasFixedSize(true);
                            verticalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            verticalRecyclerView.setAdapter(rAdapter);
                            rAdapter.notifyDataSetChanged();
                        }
                    });

                }
            });

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_full_section, container, false);
    }
}