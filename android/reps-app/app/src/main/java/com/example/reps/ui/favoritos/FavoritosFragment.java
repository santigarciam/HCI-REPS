package com.example.reps.ui.favoritos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.reps.databinding.FragmentFavoritosBinding;

public class FavoritosFragment extends Fragment {

    private FavoritosViewModel favoritosViewModel;
    private FragmentFavoritosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoritosViewModel =
                new ViewModelProvider(this).get(FavoritosViewModel.class);

        binding = FragmentFavoritosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Ejemplo de como usar el viewModel
//        final TextView textView = binding.textFavoritos;
//        favoritosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        ////////////////////
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}