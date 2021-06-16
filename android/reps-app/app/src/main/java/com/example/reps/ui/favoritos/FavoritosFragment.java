package com.example.reps.ui.favoritos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

public class FavoritosFragment extends Fragment {

    private FragmentFavoritosBinding binding;
    private App app;
    private List<RoutineCard> favRutinas;
    private RoutineCardAdapter rAdapter;
    private ShimmerFrameLayout shimmerLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavoritosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
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
                verticalRecyclerView.setAdapter(rAdapter);
            }else{

            }
        });

        root.findViewById(R.id.favourite_recycler_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("FavoritosFragment", "onClick: recyclerView!!!!");
                rAdapter.notifyDataSetChanged();
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private static class RoutineCardAdapterFav extends RoutineCardAdapter{
        public RoutineCardAdapterFav(List<RoutineCard> routines, Context context, App app, Activity activity) {
            super(routines, context, app, activity);
        }

        @Override
        public void auxNotify(int position) {
            super.routines.remove(position);
            notifyDataSetChanged();
        }
    }
}