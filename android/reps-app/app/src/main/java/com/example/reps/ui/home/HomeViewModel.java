package com.example.reps.ui.home;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reps.LoadingDialog;
import com.example.reps.LogedActivity;
import com.example.reps.R;
import com.example.reps.RoutineCard;
import com.example.reps.RoutineCardAdapter;
import com.example.reps.RoutineSection;
import com.example.reps.RoutineSectionAdapter;
import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.ContentExecution;
import com.example.reps.retrofit.api.model.Routine;
import com.example.reps.retrofit.api.repository.Status;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class HomeViewModel extends ViewModel {

    int value = 0;
    private List<RoutineCard> rutinas;
    private Set<RoutineCard> rutinasRecientes;
    private Map<String,List<RoutineCard>> rutinasByDifficulty;

    private ShimmerFrameLayout shimmerLayout;

    private App app;

    RecyclerView verticalRecyclerView;
    RoutineSectionAdapter verticalAdapter;
    // private  RecyclerView horizontalRecyclerView;
    private ArrayList<RoutineSection> arrayList1;
    // private  LogedActivity lgA;

    public HomeViewModel() {
    }

    public void init(View rootView, ViewGroup container, FragmentActivity activity) {

        app = (App) activity.getApplication();
        rutinas = new ArrayList<>();
        shimmerLayout = rootView.findViewById(R.id.shimmer_home);
        app.getRoutineRepository().getAll().observe(activity, r -> {
            if (r.getStatus() == Status.SUCCESS) {

                List<Routine> ruts = r.getData().getContent();
                // 0:rookie, 1:beginner, 2:intermediate, 3:advanced, 4:expert
                rutinasByDifficulty = new HashMap<>();

                for (Routine rut : ruts) {
                    rutinas.add(new RoutineCard(rut));
                    rutinasByDifficulty.putIfAbsent(rut.getDifficulty(),new ArrayList<>());
                    rutinasByDifficulty.get(rut.getDifficulty()).add(new RoutineCard(rut));
                }

                app.getExecutionRepository().getCurrentUserExecutions(" ").observe(activity, h -> {
                    if (h.getStatus() == Status.SUCCESS) {
                        rutinasRecientes = new HashSet<>();
                        List<ContentExecution> rutsEjecutadas = h.getData().getContent();
                        Log.d("RUTS", "init: ");
                        for (ContentExecution rut : rutsEjecutadas) {
                            rutinasRecientes.add(new RoutineCard(rut.getRoutine()));
                        }

                        app.getFavouriteRepository().getFavourites().observe(activity, t -> {
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

                                for (Routine favRut : t.getData().getContent()){
                                    for (RoutineCard rut : rutinasRecientes){
                                        if (favRut.getId() == rut.getId()){
                                            rut.setFavourite(true);
                                            break;
                                        }
                                    }
                                }

                                arrayList1 = new ArrayList<>();
                                verticalRecyclerView = (RecyclerView) rootView.findViewById(R.id.section_rout_recycler_view);

                                verticalRecyclerView.setHasFixedSize(true);
                                verticalRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));
                                verticalAdapter = new RoutineSectionAdapter(arrayList1, container.getContext(), app, activity);
                                verticalRecyclerView.setAdapter(verticalAdapter);

                                RoutineSection verticalModelRutsRecientes = new RoutineSection(activity.getString(R.string.routines_section_titulo_rutinas_recientes), new ArrayList<>(rutinasRecientes));
                                arrayList1.add(verticalModelRutsRecientes);

                                int i = 0;
                                for (List<RoutineCard> difficultyRoutineArr : rutinasByDifficulty.values()){
                                    RoutineSection verticalModel = new RoutineSection(difficultyRoutineArr.get(0).getDifficulty().toUpperCase(), difficultyRoutineArr);
                                    arrayList1.add(verticalModel);
                                }
                                shimmerLayout.stopShimmer();
                                shimmerLayout.hideShimmer();
                                shimmerLayout.setVisibility(View.GONE);
                                verticalAdapter.notifyDataSetChanged();

                            }
                        });
                    }else{

                    }
                });
            }
        });
    }

}