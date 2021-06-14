package com.example.reps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reps.retrofit.api.model.CycleExercise;
import com.example.reps.retrofit.api.model.Exercise;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExerciseCardAdapter extends RecyclerView.Adapter<ExerciseCardAdapter.ViewHolder> {

    private List<CycleExercise> ejercicios;
    private LayoutInflater mInflater;
    private final Context context;

    public ExerciseCardAdapter(List<CycleExercise> ejercicios, Context context) {
        this.ejercicios = ejercicios;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @NotNull
    @Override
    public ExerciseCardAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.exercise_card,null,false);
        return new ExerciseCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ExerciseCardAdapter.ViewHolder holder, int position) {
            holder.bindData(ejercicios.get(position));
    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,reps;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.exercise_name);
            reps = itemView.findViewById(R.id.repsExercise);
        }
        void bindData(final CycleExercise item){
           name.setText(item.getExercise().getName());
                Integer repetitions = item.getRepetitions()==0?item.getDuration():item.getRepetitions();
                if(item.getRepetitions()==0){
                    reps.setText(String.valueOf(item.getDuration()).concat("s"));
                }else{
                    reps.setText("x".concat(String.valueOf(item.getRepetitions())));
                }

        }
    }
}
