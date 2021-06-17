package com.example.reps.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
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
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DescubrirFragment extends Fragment implements  SearchView.OnQueryTextListener{

    private FragmentDescubrirBinding binding;
    private SearchView searchView;
    private App app;
    private String orderBy = "";
    private String filterBy = "";
    private String search = "";

    private ShimmerFrameLayout shimmerLayout;

    private boolean isAsc = true;


    ////////////////
    private List<RoutineCard> rutinas=new ArrayList<>();
    RecyclerView verticalRecyclerView;
    RoutineCardAdapter rAdapter;
    ////////////////////

    public void init(View rootView){
        Log.d("Retrieve ruts", "init: ");
        shimmerLayout = rootView.findViewById(R.id.shimmer_descubrir);
        rutinas = new ArrayList<>();
        app.getRoutineRepository().getAll().observe(requireActivity(),r->{
            if(r.getStatus() == Status.SUCCESS){
                for(Routine routine: r.getData().getContent()){
                    rutinas.add(new RoutineCard(routine));
                }
                app.getFavouriteRepository().getFavourites().observe(requireActivity(),f->{
                    if(f.getStatus() == Status.SUCCESS){
                        for (RoutineCard rut : rutinas){
                            for (Routine favRut : f.getData().getContent()){
                                if (favRut.getId() == rut.getId()){
                                    rut.setFavourite(true);
                                }
                            }
                        }
                        verticalRecyclerView = (RecyclerView) rootView.findViewById(R.id.descubrir_recycler_view);
                        rAdapter = new RoutineCardAdapter(rutinas,getContext(),app,requireActivity());
                        verticalRecyclerView.setHasFixedSize(true);
                        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        verticalRecyclerView.setAdapter(rAdapter);
                        rootView.findViewById(R.id.searchView).setVisibility(View.VISIBLE);
                        rootView.findViewById(R.id.ordenarDescubrir).setVisibility(View.VISIBLE);
                        rootView.findViewById(R.id.filterDescubrir).setVisibility(View.VISIBLE);
                        rootView.findViewById(R.id.ascOrDescBtn).setVisibility(View.VISIBLE);
                        if(shimmerLayout!=null) {
                            shimmerLayout.stopShimmer();
                            shimmerLayout.hideShimmer();
                            shimmerLayout.setVisibility(View.GONE);

                        }
                        rAdapter.notifyDataSetChanged();
                    }
                });

            }
        });



    }
    /////////////////////

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        init(view);
        ImageButton isAscOrDescBtn = view.findViewById(R.id.ascOrDescBtn);
        if(isAsc){
            isAscOrDescBtn.setRotation(270);
        }else{
            isAscOrDescBtn.setRotation(90);
        }
        isAscOrDescBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAsc = !isAsc;
                if(isAsc){
                    isAscOrDescBtn.setRotation(270);
                    Toast.makeText(view.getContext(), requireActivity().getString(R.string.ordenado_asc), Toast.LENGTH_LONG).show();
                }else{
                    isAscOrDescBtn.setRotation(90);
                    Toast.makeText(view.getContext(), requireActivity().getString(R.string.ordenado_desc), Toast.LENGTH_LONG).show();
                }

                retrieveRoutines();
            }
        });




    }

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        app = (App) requireActivity().getApplication();

        binding = FragmentDescubrirBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        init(root);



        Button filterBtn = root.findViewById(R.id.filterDescubrir);
       filterBtn.setOnClickListener(view -> {
//            Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.filter_order_menu_animation);
            PopupMenu popup = new PopupMenu(getContext(), view);

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

//                    rookie, beginner, intermediate, advanced, expert
                    if (menuItem.getItemId() == R.id.filterOpt0) {
                        filterBy = "rookie";
                        filterBtn.setText(getString(R.string.filterOptRookie));
                    }else if (menuItem.getItemId() == R.id.filterOpt1) {
                        filterBy = "beginner";
                        filterBtn.setText(getString(R.string.filterOptBegginer));
                    }else  if (menuItem.getItemId() == R.id.filterOpt2) {
                        filterBy = "intermediate";
                        filterBtn.setText(getString(R.string.filterOptInter));
                    }else if (menuItem.getItemId() == R.id.filterOpt3) {
                        filterBy = "advanced";
                        filterBtn.setText(getString(R.string.filterOptAdv));
                    }else  if (menuItem.getItemId() == R.id.filterOpt4) {
                        filterBy = "expert";
                        filterBtn.setText(getString(R.string.filterOptExpert));
                    }

                   retrieveRoutines();



                    return true;
                }
            });
            popup.inflate(R.menu.filter_descubrir_menu);
            popup.show();
        });
        Button orderBtn =  root.findViewById(R.id.ordenarDescubrir);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(getContext(), view);

                rutinas=new ArrayList<>();
                popup.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == R.id.orderOpt1) {
                        orderBtn.setText(getString(R.string.orderOptDate));
                        orderBy = "date";
                    }else  if (menuItem.getItemId() == R.id.orderOpt2) {
                        orderBtn.setText(getString(R.string.orderOptrating));
                        orderBy = "averageRating";
                    }else  if (menuItem.getItemId() == R.id.orderOpt3) {
                        orderBtn.setText(getString(R.string.orderOptDifficulty));
                        orderBy = "difficulty";
                    }else  if (menuItem.getItemId() == R.id.orderOpt4) {
                        orderBtn.setText(getString(R.string.orderOptCat));
                        orderBy="categoryId";

                    }
                    retrieveRoutines();
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

    private void retrieveRoutines(){
        Log.d("Retrieve ruts", "retrieveRoutines: ");
        String ascOrDesc = isAsc?"asc":"desc";

            TextView noMatch = requireActivity().findViewById(R.id.noRutsSearch);
            noMatch.setVisibility(View.INVISIBLE);

        rutinas = new ArrayList<>();
        final String finalToFilter = filterBy;
        app.getFavouriteRepository().getFavourites().observe(requireActivity(),f->{
            if(f.getStatus() == Status.SUCCESS){
                if(!orderBy.equals("")){
                app.getRoutineRepository().getAll(orderBy,ascOrDesc).observe(requireActivity(), r->{
                    if(r.getStatus() == Status.SUCCESS){
                        if(filterBy.equals("")){
                            for(Routine rut: r.getData().getContent()){
                                rutinas.add(new RoutineCard(rut));
                            }
                        }else{
                            for(Routine rut: r.getData().getContent()){
                                if(rut.getDifficulty().equals(finalToFilter)){
                                    rutinas.add(new RoutineCard(rut));
                                }
                            }
                        }
                        for (RoutineCard rut : rutinas){
                            for (Routine favRut : f.getData().getContent()){
                                if (favRut.getId() == rut.getId()){
                                    rut.setFavourite(true);
                                }
                            }
                        }
                        if(rutinas.isEmpty()){
//                             noMatch = requireActivity().findViewById(R.id.noRutsSearch);
                            noMatch.setVisibility(View.VISIBLE);
                        }
                        rAdapter.setRoutines(rutinas);
                        rAdapter.notifyDataSetChanged();
                    }
                });
            }else{
                    app.getRoutineRepository().getAll().observe(requireActivity(), r->{
                        if(r.getStatus() == Status.SUCCESS){
                            if(filterBy.equals("")){
                                for(Routine rut: r.getData().getContent()){
                                    rutinas.add(new RoutineCard(rut));
                                }
                            }else{
                                for(Routine rut: r.getData().getContent()){
                                    if(rut.getDifficulty().equals(finalToFilter)){
                                        rutinas.add(new RoutineCard(rut));
                                    }
                                }
                            }
                            for (RoutineCard rut : rutinas){
                                for (Routine favRut : f.getData().getContent()){
                                    if (favRut.getId() == rut.getId()){
                                        rut.setFavourite(true);
                                    }
                                }
                            }
                            if(rutinas.isEmpty()){
//                             noMatch = requireActivity().findViewById(R.id.noRutsSearch);
                                noMatch.setVisibility(View.VISIBLE);
                            }
                            rAdapter.setRoutines(rutinas);
                            rAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

    }



    // Se ejecuta cuando presionamos para realizar la busqueda
    @Override
    public boolean onQueryTextSubmit(String s) {
        this.search = s;
        if(rutinas.isEmpty()){
            TextView  noMatch = requireActivity().findViewById(R.id.noRutsSearch);

            noMatch.setVisibility(View.VISIBLE);
        }
        return false;
    }

    // Va escuchando cuando ingresamos letras en el text view
    @Override
    public boolean onQueryTextChange(String s) {
        if(rAdapter !=null){
            rAdapter.filter(s);
        }
        if(rutinas!=null && rutinas.isEmpty()){
            TextView  noMatch = requireActivity().findViewById(R.id.noRutsSearch);
            noMatch.setVisibility(View.VISIBLE);
        }

        return false;
    }

}
