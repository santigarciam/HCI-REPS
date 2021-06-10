package com.example.reps.ui.perfil;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.reps.R;
import com.example.reps.databinding.FragmentDescubrirBinding;
import com.example.reps.databinding.FragmentPerfilBinding;
import com.example.reps.ui.home.HomeFragmentDirections;

public class PerfilFragment extends Fragment {

    private PerfilViewModel perfilViewModel;
    private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textPerfil;
//        perfilViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        root.findViewById(R.id.profile_config_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getContext(), view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.menu_profile_edit_profile){
                            Navigation.findNavController(view).navigate(PerfilFragmentDirections.actionNavigationPerfilToEditProfileFragment2());
                        }else if(menuItem.getItemId() == R.id.menu_profile_help){
                            //hacer
                            return false;
                        }else if(menuItem.getItemId() == R.id.menu_profile_log_out){
                            AlertDialog dialog = new AlertDialog.Builder(getContext())
                                    .setTitle("Cerrar Sesion")
                                    .setMessage("¿Seguro que quieres cerrar sesion?")
                                    .setPositiveButton("Si", null)
                                    .setNegativeButton("Cancelar", null)
                                    .show();

                            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            positiveButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //TODO: borrar el token guardado
                                    Navigation.findNavController(view).navigate(PerfilFragmentDirections.actionNavigationPerfilToMainActivity());

                                }
                            });
                            }
                        return false;
                    }
                });
                popup.inflate(R.menu.profile_config_button_menu);
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
}