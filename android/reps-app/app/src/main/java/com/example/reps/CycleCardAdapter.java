package com.example.reps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reps.retrofit.api.model.Cycle;
import com.example.reps.retrofit.api.model.CycleExercise;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CycleCardAdapter extends RecyclerView.Adapter<CycleCardAdapter.ViewHolder> {
    private List<Cycle> cycles;
    private LayoutInflater mInflater;
    private Context context;

    public CycleCardAdapter(List<Cycle> cycles, Context context) {
        this.cycles = cycles;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cycle_card,null,false);
        return new CycleCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Cycle cycle = cycles.get(position);
        holder.cycleNameField.setText(cycle.getName());
        holder.repsCycle.setText("X".concat(Integer.toString(cycle.getRepetitions())));

        List<CycleExercise> exercises=cycle.getCycleExercises().getContent();
//        if(cycle.getCycleExercises() !=null){
//            exercises.addAll(cycle.getCycleExercises().getContent());
//        }
        ExerciseCardAdapter exerciseCardAdapter = new ExerciseCardAdapter(exercises,context);

        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        holder.recyclerView.setAdapter(exerciseCardAdapter);


    }

    @Override
    public int getItemCount() {
        return cycles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cycleNameField;
        RecyclerView recyclerView;
        TextView repsCycle;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cycleNameField = itemView.findViewById(R.id.cycleNameField);
            recyclerView = itemView.findViewById(R.id.cycle_recycler_view);
            repsCycle = itemView.findViewById(R.id.repetitionsCycle);
        }
    }
}
