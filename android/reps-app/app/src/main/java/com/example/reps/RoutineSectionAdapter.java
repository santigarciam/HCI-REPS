package com.example.reps;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reps.retrofit.App;
import com.example.reps.ui.home.HomeFragmentDirections;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RoutineSectionAdapter extends RecyclerView.Adapter<RoutineSectionAdapter.ViewHolder>{

    private List<RoutineSection> routineSections;
    private LayoutInflater mInflater;
    private Context context;
    private App app;
    private Activity activity;

    public RoutineSectionAdapter(List<RoutineSection> routineSections, Context context, App app, Activity activity) {
        this.routineSections = routineSections;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.app = app;
        this.activity = activity;
    }

    @NonNull
    @NotNull
    @Override
    public RoutineSectionAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.section_of_routines, null,false);
        return new RoutineSectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RoutineSectionAdapter.ViewHolder holder, int position) {
        RoutineSection rSection = routineSections.get(position);
        String title = rSection.getSectionTitle();
        List<RoutineCard> singleItem = rSection.getListOfRoutines();

        holder.sectionTitle.setText(title);
        RoutineCardAdapterSmall routineList = new RoutineCardAdapterSmall(singleItem, context, app, activity);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.recyclerView.setAdapter(routineList);

    }

    @Override
    public int getItemCount() {
        return routineSections.size();
    }

    public void setRoutineCards(List<RoutineSection> rut) { routineSections = rut; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sectionTitle; // AGREGAR LAS VARIABLES DEL ROUTINE CARD QUE FALTAN
        RecyclerView recyclerView;
        Button moreRoutines;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            sectionTitle = itemView.findViewById(R.id.section_rout_title);
            recyclerView = itemView.findViewById(R.id.section_rout_recycler_view);
            moreRoutines = itemView.findViewById(R.id.section_rout_button);

            moreRoutines.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(HomeFragmentDirections.actionNavigationHomeToFragmentFullSection(sectionTitle.getText().toString()));
                }
            });
        }

    }

    private static class RoutineCardAdapterSmall extends RoutineCardAdapter{
        public RoutineCardAdapterSmall(List<RoutineCard> routines, Context context, App app, Activity activity) {
            super(routines, context, app, activity);
        }

        @NonNull
        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.routine_card_small, null, false);
            return new RoutineCardAdapter.ViewHolder(view);
        }
    }
}
