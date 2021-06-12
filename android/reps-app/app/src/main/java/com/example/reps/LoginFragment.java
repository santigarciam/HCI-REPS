package com.example.reps;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.reps.databinding.FragmentLoginBinding;
import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.Credentials;
import com.example.reps.retrofit.repository.Status;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LoginFragment extends Fragment {
    private  App app;
    public static final String TAG = "LOGIN_FRAG";
    private FragmentLoginBinding binding;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton btn_back = view.findViewById(R.id.login_back);
        app = (App) requireActivity().getApplication();
        if(app == null){
            Log.d(TAG, "onViewCreated: Error app");
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.firstFragment);
            }
        });



        Button btn_login = view.findViewById(R.id.login_button_register);
        TextView usernameField = view.findViewById(R.id.login_input_user);
        TextView passwordField = view.findViewById(R.id.login_input_password);
        ProgressBar progressBar = view.findViewById(R.id.login_progress_bar);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                Credentials credentials = new Credentials(username,password);
                app.getUserRepository().login(credentials).observe(requireActivity(), r->{
//                    while(r.getStatus() == Status.LOADING);
                        if (r.getStatus() == Status.SUCCESS) {
                            Log.d(TAG, "Se logueo correctamente con las siguientes credentials. User: " + username + " Password: " + password);
                            app.getPreferences().setAuthToken(r.getData().getToken());
                            progressBar.setVisibility(View.INVISIBLE);
                            Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToLogedActivity());
                        }else if(r.getStatus() == Status.ERROR){
                            if (r.getError().getDetails().contains("Password does not match")){
                                passwordField.setError("Contrasena incorrecta");
                            }else{
                                usernameField.setError("Usuario incorrecto");
                            }
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            Toast.makeText(view.getContext(), "Usuario: " + r.getStatus().name() + username + " o contrasena: " + password + " incorrectos", Toast.LENGTH_LONG).show();
                        }


                });


            }
        });

    }
}
