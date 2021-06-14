package com.example.reps.ui.perfil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reps.R;
import com.example.reps.RoutineCard;
import com.example.reps.RoutineCardAdapter;
import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.ContentExecution;
import com.example.reps.retrofit.api.model.Routine;
import com.example.reps.retrofit.api.repository.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MiProgresoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MiProgresoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MiProgresoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MiProgresoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MiProgresoFragment newInstance(String param1, String param2) {
        MiProgresoFragment fragment = new MiProgresoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private App app;
    private List<RoutineCard> historialRutinas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mi_progreso, container, false);

        app = (App) getActivity().getApplication();
        app.getExecutionRepository().getCurrentUserExecutions(" ").observe(getActivity(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                historialRutinas = new ArrayList<>();
                List<ContentExecution> ruts = r.getData().getContent();
                Log.d("RUTS", "init: ");
                for (ContentExecution rut : ruts) {
                    historialRutinas.add(new RoutineCard(rut.getRoutine()));
                }
                RoutineCardAdapter rAdapter = new RoutineCardAdapter(historialRutinas, getContext());
                RecyclerView verticalRecyclerView = (RecyclerView) root.findViewById(R.id.miProgreso_recycler_view);
                verticalRecyclerView.setHasFixedSize(true);
                verticalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                verticalRecyclerView.setAdapter(rAdapter);
            }else{

            }
        });

        return root;
    }
}