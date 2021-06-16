package com.example.reps.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reps.R;
import com.example.reps.RoutineCard;
import com.example.reps.RoutineCardAdapter;
import com.example.reps.databinding.FragmentDescubrirBinding;
import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.Routine;
import com.example.reps.retrofit.api.repository.Status;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DescubrirFragment extends Fragment implements  SearchView.OnQueryTextListener{

    private FragmentDescubrirBinding binding;
    private SearchView searchView;
    private App app;

    private String params = "";
    private String order = "";
    private String search = "";
    private String difficulty = "";
    private String direc = "asc";

    ////////////////
    private List<RoutineCard> rutinas=new ArrayList<>();
    RecyclerView verticalRecyclerView;
    RoutineCardAdapter rAdapter;
    ////////////////////

    public void init(View rootView, ViewGroup container){

        app.getRoutineRepository().getAll().observe(requireActivity(),r->{
            if(r.getStatus() == Status.SUCCESS){
                for(Routine routine: r.getData().getContent()){
                    rutinas.add(new RoutineCard(routine));
                }
                verticalRecyclerView = (RecyclerView) rootView.findViewById(R.id.descubrir_recycler_view);
//                            Log.d(TAG, "onViewCreated: ");
                rAdapter = new RoutineCardAdapter(rutinas,getContext(),app,requireActivity());
                verticalRecyclerView.setHasFixedSize(true);
                verticalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                verticalRecyclerView.setAdapter(rAdapter);
                rAdapter.notifyDataSetChanged();
            }
        });


    }
    /////////////////////

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        app = (App) requireActivity().getApplication();

        binding = FragmentDescubrirBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init(root,container);



        Spinner filterDescubrir = root.findViewById(R.id.filterDescubrir)  ;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.filterOpt, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterDescubrir.setAdapter(adapter);
        filterDescubrir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(),text, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner ordenarDescubrir = root.findViewById(R.id.ordenarDescubrir)  ;
        ArrayAdapter<CharSequence> adapterOrder = ArrayAdapter.createFromResource(this.getContext(), R.array.orderOpt, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ordenarDescubrir.setAdapter(adapterOrder);
        ordenarDescubrir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String opt = adapterView.getItemAtPosition(i).toString();
                List<String> opts = new ArrayList<>();
                rutinas.clear();
                opts.add(0, "");
                opts.add(1, "date");
                opts.add(2, "averageRating");
                opts.add(3, "difficulty");
                opts.add(4, "categoryId");

                if (i != 0) {
                    app.getRoutineRepository().getAll(opts.get(i), "asc").observe(requireActivity(), r -> {
                                if (r.getStatus() == Status.SUCCESS) {
                                    for (Routine routine : r.getData().getContent()) {
                                        rutinas.add(new RoutineCard(routine));
                                    }
                                    rAdapter.notifyDataSetChanged();
                                }
                            }
                    );
                } else {
                    app.getRoutineRepository().getAll().observe(requireActivity(), r -> {
                                if (r.getStatus() == Status.SUCCESS) {
                                    for (Routine routine : r.getData().getContent()) {
                                        rutinas.add(new RoutineCard(routine));
                                    }
                                    rAdapter.notifyDataSetChanged();
                                }
                            }
                    );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



//        root.findViewById(R.id.ordenarDescubrir).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                PopupMenu popup = new PopupMenu(getContext(), view);
//
//                rutinas.clear();
//
//                popup.setOnMenuItemClickListener(menuItem -> {
//                    if (menuItem.getItemId() == R.id.orderOpt1) {
//
//                        app.getRoutineRepository().getAll("date","desc").observe(requireActivity(),r->{
//                            if(r.getStatus() == Status.SUCCESS){
//                                for(Routine routine:r.getData().getContent()){
//                                    rutinas.add(new RoutineCard(routine));
//                                }
//                                rAdapter.notifyDataSetChanged();
//                            }
//                        });
//                    }else  if (menuItem.getItemId() == R.id.orderOpt2) {
//                        app.getRoutineRepository().getAll("averageRating","asc").observe(requireActivity(),r->{
//                            if(r.getStatus() == Status.SUCCESS){
//                                for(Routine routine:r.getData().getContent()){
//                                    rutinas.add(new RoutineCard(routine));
//                                }
//                                rAdapter.notifyDataSetChanged();
//                            }
//                        });
//
//                    }else  if (menuItem.getItemId() == R.id.orderOpt3) {
//                        app.getRoutineRepository().getAll("difficulty","asc").observe(requireActivity(),r->{
//                            if(r.getStatus() == Status.SUCCESS){
//                                for(Routine routine:r.getData().getContent()){
//                                    rutinas.add(new RoutineCard(routine));
//                                }
//                                rAdapter.notifyDataSetChanged();
//                            }
//                        });
//                    }else  if (menuItem.getItemId() == R.id.orderOpt4) {
//                        app.getRoutineRepository().getAll("categoryId","asc").observe(requireActivity(),r->{
//                            if(r.getStatus() == Status.SUCCESS){
//                                for(Routine routine:r.getData().getContent()){
//                                    rutinas.add(new RoutineCard(routine));
//                                }
//                                rAdapter.notifyDataSetChanged();
//                            }
//                        });
//                    }
//                    return true;
//                });
//                popup.inflate(R.menu.order_descubrir_menu);
//                popup.show();
//            }
//        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    // Se ejecuta cuando presionamos para realizar la busqueda
    @Override
    public boolean onQueryTextSubmit(String s) {
        this.search = s;
        filterRoutines();
        return false;
    }

    // Va escuchando cuando ingresamos letras en el text view
    @Override
    public boolean onQueryTextChange(String s) {
        rAdapter.filter(s);

        return false;
    }

    public void filterRoutines(){
        if (this.difficulty!= ""){
            if (this.params!=""){
                this.params += "&";
            }
            this.params += "difficulty=" + this.difficulty;
        }
        if (this.search!= ""){
            if (this.params!=""){
                this.params += "&";
            }
            this.params += "search=" + this.search;
        }
        if (this.order!= ""){
            if (this.params!=""){
                this.params += "&";
            }
            this.params += "orderBy=" + this.order;
        }
        if (this.params!=""){
            this.params += "&";
        }
        this.params += "direction=" + this.direc;
        //llamada a la api
        this.params="";
    }
}