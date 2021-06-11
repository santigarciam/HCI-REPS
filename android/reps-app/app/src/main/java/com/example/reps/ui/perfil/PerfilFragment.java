package com.example.reps.ui.perfil;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.reps.FirstFragment;
import com.example.reps.R;
import com.example.reps.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private PerfilViewModel perfilViewModel;
    private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel =
                new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ///////////////////////////////////////////
        // SetUp boton de configuracion

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
                            AlertDialog dialog = new AlertDialog.Builder(getContext())
                                    .setTitle("Ayuda")
                                    .setMessage("TP3 - Grupo 8")
                                    .setPositiveButton("Ok", null)
                                    .show();
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
                                    //Navigation.findNavController(getView()).navigate(PerfilFragmentDirections.actionNavigationPerfilToMainActivity());
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity, new FirstFragment()).commit();
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

        ///////////////////////////////////////////
        // SetUp boton "Mi Progreso"

        root.findViewById(R.id.profile_progress_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).navigate(PerfilFragmentDirections.actionNavigationPerfilToMiProgresoFragment());
            }
        });

        ///////////////////////////////////////////
        // SetUp boton "Programar Entrenamiento"

        root.findViewById(R.id.profile_programar_entrenamiento_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).navigate(PerfilFragmentDirections.actionNavigationPerfilToProgramarEntrenamientoFragment());
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