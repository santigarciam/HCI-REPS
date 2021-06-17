package com.example.reps;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.Cycle;
import com.example.reps.retrofit.api.model.CycleExercise;
import com.example.reps.retrofit.api.model.Exercise;
import com.example.reps.retrofit.api.model.Routine;
import com.example.reps.retrofit.api.repository.Status;
import com.example.reps.ui.ejecucionRut.ejecucionRut;
import com.example.reps.ui.ejecucionRut.ejecucionRutina;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class VistaRutina extends Fragment {
    TextView name,owner,descr;
    RatingBar ratingBar;
    private boolean isFavAux;

    private String nombreRutina;

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
        ratingBar = view.findViewById(R.id.vista_rutina_rating);
        List<List<CycleExercise>> ejercicios = new ArrayList<>();
        List<Cycle> ciclos = new ArrayList<>();

       // Routine rut;
        VistaRutinaArgs args = VistaRutinaArgs.fromBundle(getArguments());
        //TODO: HACER QUE EL ARG SEA TODO EL ROUTINECARD!!!
        if (getArguments() != null) {
            isFavAux = args.getIsFav();
            if (isFavAux){
                ((ImageButton)view.findViewById(R.id.vista_rutina_fav_button)).setImageResource(R.drawable.baseline_favorite_black_24dp_pressed);
            }else{
                ((ImageButton)view.findViewById(R.id.vista_rutina_fav_button)).setImageResource(R.drawable.baseline_favorite_black_24dp);
            }
            Integer id = args.getIDRutina();
            app.getRoutineRepository().getRoutine(id).observe(requireActivity(),r->{
                if(r.getStatus() == Status.SUCCESS) {
                   name.setText(r.getData().getName());
                   nombreRutina = r.getData().getName();
                    owner.setText(r.getData().getUser().getUsername());
                   descr.setText(r.getData().getDetail());
                   ratingBar.setRating((float) (r.getData().getAverageRating()/2));
                   app.getRoutineRepository().getRoutineCycles(id).observe(requireActivity(),c-> {
                       if (c.getStatus() == Status.SUCCESS) {
                           ciclos.addAll(c.getData().getContent());
                           int i = 0;
                           List<Cycle> arrayList1 = new ArrayList<>();
                           CycleCardAdapter verticalAdapter = new CycleCardAdapter(arrayList1, getContext());

                           for (Cycle ciclo : ciclos) {
                               int finalI = i;
                               app.getRoutineRepository().getCycleExercise(ciclo.getId()).observe(requireActivity(), e -> {
                                   if (e.getStatus() == Status.SUCCESS) {
                                       ciclo.setCycleExercises(e.getData());

                                       RecyclerView verticalRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_vista_rutina);

                                       verticalRecyclerView.setHasFixedSize(true);
                                       verticalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//
                                       verticalRecyclerView.setAdapter(verticalAdapter);
                                       ciclo.setName(firstToUpperOtherLowerCase(ciclo.getName()));
                                       arrayList1.add(ciclo);
                                   }
                                   arrayList1.sort(new Comparator<Cycle>() {
                                       @Override
                                       public int compare(Cycle cycle, Cycle t1) {
                                           return cycle.getOrder() - t1.getOrder();
                                       }
                                   });
                                   verticalAdapter.notifyDataSetChanged();
                               });
                               i++;
                           }

                       }
                   });




                    ImageButton share_btn = view.findViewById(R.id.shareRut);

                    share_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //int position = args.getIDRutina();
                            ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
                            Uri myUri = Uri.parse("https://www.reps.com/routines/" + args.getIDRutina());
                            ClipData clip = ClipData.newRawUri("Rutine Link", myUri);
                            clipboard.setPrimaryClip(clip);

                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_TEXT, myUri.toString());
                            getContext().startActivity(Intent.createChooser(intent, requireActivity().getString(R.string.string_compartir_rutina) + " \"" + nombreRutina + "\""));
                        }

                    });

                }else if (r.getStatus() == Status.ERROR){

                    android.app.AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setTitle(requireActivity().getString(R.string.string_rutina_inexistente_titulo))
                            .setMessage(requireActivity().getString(R.string.string_rutina_inexistente_mensaje))
                            .setPositiveButton("Ok", null)
                            .show();

                    Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setBackgroundColor(getContext().getColor(R.color.our_blue));
                    button.setTextColor(getContext().getColor(R.color.white));
                    button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    getActivity().finish();
                                }
                            });
                }
            });


            ImageButton play_btn = view.findViewById(R.id.playRut);

            play_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), ejecucionRut.class);
                    intent.putExtra("RUTINA_ID", args.getIDRutina());
                    startActivity(intent);
//                    Navigation.findNavController(view).navigate(VistaRutinaDirections.actionVistaRutinaToEjecucionRut(args.getIDRutina()));
                }

            });

            ImageButton fav_btn = view.findViewById(R.id.vista_rutina_fav_button);
            fav_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: falta cambiar el true/false del routineCard
                    if (!isFavAux){
                        app.getFavouriteRepository().addFavourite(args.getIDRutina()).observe(requireActivity(), r ->{
                            if (r.getStatus() == Status.SUCCESS) {
                                ((ImageButton) fav_btn.findViewById(R.id.vista_rutina_fav_button))
                                        .setImageResource(R.drawable.baseline_favorite_black_24dp_pressed);
                                Toast.makeText(view.getContext(), requireActivity().getString(R.string.string_rutina_quote) + nombreRutina + requireActivity().getString(R.string.string_quote_agregada_a_favoritos), Toast.LENGTH_LONG).show();
                                isFavAux = !isFavAux;
                            }else{

                            }
                        });
                    }else{
                        app.getFavouriteRepository().deleteFavourite(args.getIDRutina()).observe(requireActivity(), r ->{
                            if (r.getStatus() == Status.SUCCESS) {
                                ((ImageButton) fav_btn.findViewById(R.id.vista_rutina_fav_button))
                                        .setImageResource(R.drawable.baseline_favorite_black_24dp);
                                Toast.makeText(view.getContext(), requireActivity().getString(R.string.string_rutina_quote) + nombreRutina + requireActivity().getString(R.string.string_quote_borrada_de_favoritos), Toast.LENGTH_LONG).show();

                                isFavAux = !isFavAux;
                            }else{

                            }
                        });
                    }

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


    private String firstToUpperOtherLowerCase(String s){
        String aux = s.substring(0,1);
        return  aux.toUpperCase().concat(s.substring(1).toLowerCase());

    }
}