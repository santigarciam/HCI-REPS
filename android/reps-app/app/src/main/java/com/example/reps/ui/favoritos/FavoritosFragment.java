package com.example.reps.ui.favoritos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reps.R;
import com.example.reps.RoutineCard;
import com.example.reps.RoutineCardAdapter;
import com.example.reps.databinding.FragmentFavoritosBinding;
import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.Routine;
import com.example.reps.retrofit.api.repository.Status;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class FavoritosFragment  extends Fragment implements SearchView.OnQueryTextListener {
    private String search;
    private FragmentFavoritosBinding binding;
    private App app;
    private SearchView searchView;
    private List<RoutineCard> favRutinas;
    private RoutineCardAdapter rAdapter;
    private ShimmerFrameLayout shimmerLayout;
    public static View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavoritosBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        shimmerLayout = root.findViewById(R.id.shimmer_favoritos);

        app = (App) getActivity().getApplication();
        app.getFavouriteRepository().getFavourites().observe(getActivity(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                favRutinas = new ArrayList<>();
                List<Routine> ruts = r.getData().getContent();
                Log.d("RUTS", "init: ");
                for (Routine rut : ruts) {
                    RoutineCard rc = new RoutineCard(rut);
                    rc.setFavourite(true);
                    favRutinas.add(rc);
                }
                rAdapter = new RoutineCardAdapterFav(favRutinas, getContext(), app, getActivity());
                RecyclerView verticalRecyclerView = (RecyclerView) root.findViewById(R.id.favourite_recycler_view);
                verticalRecyclerView.setHasFixedSize(true);
                verticalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                shimmerLayout.stopShimmer();
                shimmerLayout.hideShimmer();
                shimmerLayout.setVisibility(View.GONE);
                root.findViewById(R.id.searchView).setVisibility(View.VISIBLE);
                if (favRutinas.size() == 0){
                    root.findViewById(R.id.fav_no_ruts_message).setVisibility(View.VISIBLE);
                }else{
                    root.findViewById(R.id.fav_no_ruts_message).setVisibility(View.GONE);
                }
                verticalRecyclerView.setAdapter(rAdapter);
            }else{

            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
    }

    public static View getRoot(){
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        this.search = s;
        return false;
    }

    // Va escuchando cuando ingresamos letras en el text view
    @Override
    public boolean onQueryTextChange(String s) {
        if(rAdapter!= null)
        rAdapter.filter(s);

        return false;
    }

    private static class RoutineCardAdapterFav extends RoutineCardAdapter{
        public RoutineCardAdapterFav(List<RoutineCard> routines, Context context, App app, Activity activity) {
            super(routines, context, app, activity);
        }

        @Override
        public void auxNotify(int position) {
            super.routines.remove(position);
            if (routines.size() == 0){
                root.findViewById(R.id.fav_no_ruts_message).setVisibility(View.VISIBLE);
            }else{
                root.findViewById(R.id.fav_no_ruts_message).setVisibility(View.GONE);
            }
            notifyDataSetChanged();
        }
    }
}