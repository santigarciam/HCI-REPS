package com.example.reps;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.Cycle;
import com.example.reps.retrofit.api.model.CycleExercise;
import com.example.reps.retrofit.api.model.Exercise;
import com.example.reps.retrofit.api.model.Routine;
import com.example.reps.retrofit.api.repository.Status;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class VistaRutina extends Fragment {
    TextView name,owner,descr;

    public VistaRutina() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        App app = (App) requireActivity().getApplication();
        name = view.findViewById(R.id.nombreRut);
        owner = view.findViewById(R.id.autorRut);
        descr = view.findViewById(R.id.descripcionRut);
        List<List<CycleExercise>> ejercicios = new ArrayList<>();
        List<Cycle> ciclos = new ArrayList<>();
       // Routine rut;
        VistaRutinaArgs args = VistaRutinaArgs.fromBundle(getArguments());
        if (getArguments() != null) {
            Integer id = args.getIDRutina();
            app.getRoutineRepository().getRoutine(id).observe(requireActivity(),r->{
                // TODO: Cambiar esto de imprimir por el manejo de la API
                if(r.getStatus() == Status.SUCCESS) {

                   name.setText(r.getData().getName());
                    owner.setText(r.getData().getUser().getUsername());
                   descr.setText(r.getData().getDetail());
                   app.getRoutineRepository().getRoutineCycles(id).observe(requireActivity(),c-> {
                        if(c.getStatus() == Status.SUCCESS){
                            ciclos.addAll(c.getData().getContent());
                            int i =0;
                            List<Cycle> arrayList1 = new ArrayList<>();
                            CycleCardAdapter verticalAdapter = new CycleCardAdapter(arrayList1,getContext());


                            for(Cycle ciclo: ciclos) {
                                int finalI = i;
                                app.getRoutineRepository().getCycleExercise(ciclo.getId()).observe(requireActivity(), e -> {
                                    if (e.getStatus() == Status.SUCCESS) {
                                        ciclo.setCycleExercises(e.getData());

                                        RecyclerView verticalRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_vista_rutina);

                                        verticalRecyclerView.setHasFixedSize(true);
                                        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//
                                        verticalRecyclerView.setAdapter(verticalAdapter);

                                        arrayList1.add(ciclo);



                                        // TODO: aca adentro llamar para cargar en la vista los ciclos y rutinas
                                    }
                                });
                                i++;
                            }
                            verticalAdapter.notifyDataSetChanged();
                        }
                   });




                    ImageButton share_btn = view.findViewById(R.id.shareRut);

                    share_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = args.getIDRutina();
                            ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                            Uri myUri = Uri.parse("http://stackoverflow.com"); //cambiar al link de la rutina
                            ClipData clip = ClipData.newRawUri("Rutine Link", myUri);
                            clipboard.setPrimaryClip(clip);

                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_STREAM, myUri);
                            getContext().startActivity(Intent.createChooser(intent, "Compartir"));
                            //Toast.makeText(view.getContext(),"Link de rutina " + position + " copiado al portapapeles", Toast.LENGTH_LONG).show();
                        }

                    });

                }});

            ImageButton play_btn = view.findViewById(R.id.playRut);

            play_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Navigation.findNavController(view).navigate(VistaRutinaDirections.actionVistaRutinaToEjecucionRut(args.getIDRutina()));
                }

            });

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vista_rutina, container, false);
    }

}