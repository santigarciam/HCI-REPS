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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.CredentialRegister;
import com.example.reps.retrofit.api.repository.Status;

import org.jetbrains.annotations.NotNull;

public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment";
    private RegisterViewModel registerViewModel;
    private MainActivity mainActivity;
    private App app;


    public RegisterFragment() {
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        app = (App) requireActivity().getApplication();

        final NavController navController = Navigation.findNavController(view);

        Button btn_register = view.findViewById(R.id.register_button_register);
        TextView mailField = view.findViewById(R.id.register_input_email);
        TextView usernameField = view.findViewById(R.id.register_input_user);
        TextView passwordField = view.findViewById(R.id.register_input_password);
        TextView passwordCheckField = view.findViewById(R.id.register_input_rep_password);
        ProgressBar progressBar = view.findViewById(R.id.register_prog);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = mailField.getText().toString();
                if(mail.length()==0){
                    mailField.setError(getString(R.string.campo_requerido));
                }
                String username = usernameField.getText().toString();
                if(username.length()==0){
                    usernameField.setError(getString(R.string.campo_requerido));
                }
                String password = passwordField.getText().toString();
                String passwordCheck = passwordCheckField.getText().toString();
                CredentialRegister credentials = new CredentialRegister(username,password,"Jhon","Doe","male",03022000, mail,"101010","url",null);
                if(password.length()<8){
                    passwordField.setError(getString(R.string.error_register_largo_contra));
                }else {
                    if (passwordCheck.equals(password) && username.length()!=0 && mail.length()!=0) {
                        progressBar.setVisibility(View.VISIBLE);
                        app.getUserRepository().register(credentials).observe(requireActivity(), r -> {
                            if (r.getStatus() == Status.SUCCESS) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Log.d(TAG, "Se registro"+username+password);
                                //Log.d(TAG, "onClick: ");
                                Navigation.findNavController(view).navigate(RegisterFragmentDirections.actionRegisterFragmentToVerificationCode(mail, username, password));
                            } else if (r.getStatus() == Status.ERROR) {
                                progressBar.setVisibility(View.INVISIBLE);
                                for(String error:r.getError().getDetails()){
                                    if(error.contains("constraint failed: User.email")){
                                        mailField.setError(getString(R.string.error_mail_constraint));
                                    }else if(error.contains(" User.username")){
                                        usernameField.setError(getString(R.string.error_username_constraint));
                                    }else if(error.contains("validation for format email")){
                                        mailField.setError("");
                                    }
                                }
                            }else{
                                Log.d(TAG, "LOADING: ");
                                progressBar.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        passwordField.setError(getString(R.string.error_register_dif_contra));
                        passwordCheckField.setError(getString(R.string.error_register_dif_contra));
                    }
                }

            }
        });

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        // Esto sirve para cuando se rota la pantalla
//        TextView mail = getView().findViewById(R.id.register_input_email);
//        registerViewModel.getCurrentMail().setValue(mail.getText().toString());
//        TextView userName = getView().findViewById(R.id.register_input_user);
//        registerViewModel.getUserName().setValue(userName.getText().toString());
//        TextView password = getView().findViewById(R.id.register_input_password);
//        registerViewModel.getPassword().setValue(password.getText().toString());



    }
}