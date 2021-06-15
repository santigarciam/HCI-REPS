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
import com.example.reps.retrofit.api.model.Routine;
import com.example.reps.retrofit.api.repository.Status;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    int value = 0;
    private List<RoutineCard> rutinas;
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
        app.getRoutineRepository().getAll(" ").observe(activity, r -> {
            if (r.getStatus() == Status.SUCCESS) {
                List<Routine> ruts = r.getData().getContent();
                for (Routine rut : ruts) {
                    rutinas.add(new RoutineCard(rut));
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

                        RoutineCardAdapter rAdapter = new RoutineCardAdapter(rutinas, container.getContext(), app, activity);
                        verticalRecyclerView = (RecyclerView) rootView.findViewById(R.id.section_rout_recycler_view);

                        verticalRecyclerView.setHasFixedSize(true);
                        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));

                        arrayList1 = new ArrayList<>();
                        verticalAdapter = new RoutineSectionAdapter(arrayList1, container.getContext(), app, activity);
                        verticalRecyclerView.setAdapter(verticalAdapter);
                        RoutineSection verticalModel = new RoutineSection("Prueba 1", rutinas);
                        arrayList1.add(verticalModel);
                        RoutineSection verticalModel2 = new RoutineSection("Prueba 2", rutinas);
                        arrayList1.add(verticalModel2);
                        RoutineSection verticalModel3 = new RoutineSection("Prueba 3", rutinas);
                        arrayList1.add(verticalModel3);
                        verticalAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }

}