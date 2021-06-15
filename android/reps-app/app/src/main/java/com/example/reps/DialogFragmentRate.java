package com.example.reps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.Reviews;
import com.example.reps.retrofit.api.repository.Status;

import org.jetbrains.annotations.NotNull;


public class DialogFragmentRate extends androidx.fragment.app.DialogFragment {
    private App app;
private Integer rutID;
private AppCompatActivity act;

    public DialogFragmentRate(Integer idRut, AppCompatActivity activity) {
        rutID = idRut;
        act = activity;
    }

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return creatDialogRatearRut();
    }

    public void setApp(App app){
        this.app = app;
    }


    private AlertDialog creatDialogRatearRut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog,null);
        builder.setView(view);

        Button btnConfim = view.findViewById(R.id.confirmRate);
        RatingBar ratingBar = view.findViewById(R.id.ratingBarRut);
        btnConfim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reviews review = new Reviews();
                Float rat = ratingBar.getRating()*2;
                Integer rate = Math.round(rat);
                review.setScore(rate);
                Log.d("REVIEW", "onClick: "+rate);
                review.setReview(" ");
                Log.d("DIALOG FRAGMEN", "onClick: "+rutID);
                app.getReviewRepository().postReview(rutID,review).observe(requireActivity(),r->{
                    if(r.getStatus() == Status.ERROR){
                        Toast.makeText(getContext(),"Hubo un error al cargar su puntuacion.",Toast.LENGTH_LONG);
                    }else if(r.getStatus() == Status.SUCCESS){
                        dismiss();
                        act.finish();
                    }
                });
            }
        });

        Button btnCancelar = view.findViewById(R.id.cancelRate);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dismiss();
            }
        });
            return builder.create();
    }


    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
//        if(context instanceof Activity){
//            this
//        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog, container, false);
    }
}