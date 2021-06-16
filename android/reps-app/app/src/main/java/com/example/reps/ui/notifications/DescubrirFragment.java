package com.example.reps.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.SearchView;
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

    private DescubrirViewModel descubrirViewModel;
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

        app.getRoutineRepository().getAll("").observe(requireActivity(),r->{
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
        descubrirViewModel =
                new ViewModelProvider(this).get(DescubrirViewModel.class);
        binding = FragmentDescubrirBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init(root,container);




        root.findViewById(R.id.filterDescubrir).setOnClickListener(view -> {
//            Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.filter_order_menu_animation);
            PopupMenu popup = new PopupMenu(getContext(), view);

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    if (menuItem.getItemId() == R.id.filterOpt1) {
                        Toast.makeText(view.getContext(), "Opcion 1 seleccionada", Toast.LENGTH_LONG).show();
                    }else if (menuItem.getItemId() == R.id.filterOpt2) {
                        Toast.makeText(view.getContext(), "Opcion 2 seleccionada", Toast.LENGTH_LONG).show();
                    }else  if (menuItem.getItemId() == R.id.filterOpt3) {
                        Toast.makeText(view.getContext(), "Opcion 2 seleccionada", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
            });
            popup.inflate(R.menu.filter_descubrir_menu);
            popup.show();
        });

        root.findViewById(R.id.ordenarDescubrir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(getContext(), view);

                popup.setOnMenuItemClickListener(menuItem -> {

                    if (menuItem.getItemId() == R.id.orderOpt1) {
                        Toast.makeText(view.getContext(), "Opcion 1 order seleccionada", Toast.LENGTH_LONG).show();
                    }else  if (menuItem.getItemId() == R.id.orderOpt2) {
                        Toast.makeText(view.getContext(), "Opcion 2 order seleccionada", Toast.LENGTH_LONG).show();
                    }else  if (menuItem.getItemId() == R.id.orderOpt3) {
                        Toast.makeText(view.getContext(), "Opcion 3 order seleccionada", Toast.LENGTH_LONG).show();
                    }else  if (menuItem.getItemId() == R.id.orderOpt4) {
                        Toast.makeText(view.getContext(), "Opcion 4 order seleccionada", Toast.LENGTH_LONG).show();
                    }
                    return true;
                });
                popup.inflate(R.menu.order_descubrir_menu);
                popup.show();
            }
        });
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