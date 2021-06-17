package com.example.reps.ui.perfil;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.reps.FirstFragment;
import com.example.reps.MainActivity;
import com.example.reps.R;
import com.example.reps.databinding.FragmentPerfilBinding;
import com.example.reps.retrofit.App;
import com.example.reps.retrofit.AppPreferences;
import com.example.reps.retrofit.api.model.User;
import com.example.reps.retrofit.api.repository.Status;
import com.facebook.shimmer.ShimmerFrameLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilFragment extends Fragment {

    private PerfilViewModel perfilViewModel;
    private FragmentPerfilBinding binding;
    private App app;

    private ShimmerFrameLayout shimmerLayout;

    private Handler handler = new Handler();

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        shimmerLayout = root.findViewById(R.id.shimmer_perfil);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shimmerLayout.stopShimmer();
                shimmerLayout.hideShimmer();
                shimmerLayout.setVisibility(View.GONE);

                root.findViewById(R.id.profile_avatar_image).setVisibility(View.VISIBLE);
                root.findViewById(R.id.profile_username).setVisibility(View.VISIBLE);
                root.findViewById(R.id.profile_config_button).setVisibility(View.VISIBLE);
                root.findViewById(R.id.profile_nombre_apellido).setVisibility(View.VISIBLE);
                root.findViewById(R.id.profile_programar_entrenamiento_button).setVisibility(View.VISIBLE);
                root.findViewById(R.id.profile_progress_button).setVisibility(View.VISIBLE);
                root.findViewById(R.id.divider2).setVisibility(View.VISIBLE);
            }
        }, 1000);

        ///////////////////////////////////////////
        // SetUp informacion del usuario de la api
        app = (App) getActivity().getApplication();
        app.getUserRepository().getCurrentUser().observe(getActivity(), r -> {
            if (r.getStatus() == Status.SUCCESS) {
                User currentUser = r.getData();
                Log.d("PROFILE", "onCreateView: " + currentUser.getFirstName());
                String nombreCompleto = currentUser.getFirstName() + " " + currentUser.getLastName();
                ((TextView)root.findViewById(R.id.profile_nombre_apellido)).setText(nombreCompleto);
                ((TextView)root.findViewById(R.id.profile_username)).setText(currentUser.getUsername());
                if (!currentUser.getGender().equals("male")){
                    ((CircleImageView)root.findViewById(R.id.profile_avatar_image)).setImageDrawable(getResources().getDrawable(R.drawable.profile_avatar_female));
                }
            }else if(r.getStatus() == Status.ERROR){
                Log.d("PROFILE", "onCreateView: ERROR");
            }
        });

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
                                    .setTitle(requireActivity().getString(R.string.alert_titulo_ayuda))
                                    .setMessage(requireActivity().getString(R.string.alert_mensaje_ayuda))
                                    .setPositiveButton("Ok", null)
                                    .show();
                            Button psBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            psBtn.setBackgroundColor(getContext().getColor(R.color.our_blue));
                            psBtn.setTextColor(getContext().getColor(R.color.white));
                        }else if(menuItem.getItemId() == R.id.menu_profile_log_out){
                            AlertDialog dialog = new AlertDialog.Builder(getContext())
                                    .setTitle(requireActivity().getString(R.string.alert_titulo_cerrar_sesion))
                                    .setMessage(requireActivity().getString(R.string.alert_mensaje_cerrar_sesion))
                                    .setPositiveButton(requireActivity().getString(R.string.string_alert_terminar_rutina_si), null)
                                    .setNegativeButton(requireActivity().getString(R.string.editProfile_cancelar_button), null)
                                    .show();

                            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            positiveButton.setBackgroundColor(getContext().getColor(R.color.grey));
                            positiveButton.setTextColor(getContext().getColor(R.color.white));
                            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                            negativeButton.setBackgroundColor(getContext().getColor(R.color.our_blue));
                            negativeButton.setTextColor(getContext().getColor(R.color.white));
                            positiveButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //TODO: borrar el token guardado
                                    //Navigation.findNavController(getView()).navigate(PerfilFragmentDirections.actionNavigationPerfilToMainActivity());
                                    //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.firstFragment, new FirstFragment()).commit();

                                    AppPreferences appPreferences = new AppPreferences(getContext());
                                    appPreferences.setAuthToken(null);
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    getActivity().finish();
                                    dialog.dismiss();

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