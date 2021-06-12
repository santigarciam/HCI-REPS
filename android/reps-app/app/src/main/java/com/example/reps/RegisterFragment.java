package com.example.reps;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.example.reps.retrofit.api.model.Credentials;
import com.example.reps.retrofit.api.model.User;
import com.example.reps.retrofit.repository.Status;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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
        // TODO Aca esto no sirve pero puede servir para cuando querramos cortar cuando no esta logueado


//        registerViewModel.getToken().observe(getViewLifecycleOwner(), (Observer<MutableLiveData<Integer>>) user -> {
//            if (token == null) {
//                showWelcomeMessage();
//            } else {
//                navController.navigate(R.id.login_fragment);
//            }
//        });

        ImageButton btn_back = view.findViewById(R.id.register_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.firstFragment);
            }
        });

        Button btn_register = view.findViewById(R.id.register_button_register);
        TextView mailField = view.findViewById(R.id.register_input_email);
        TextView usernameField = view.findViewById(R.id.register_input_user);
        TextView passwordField = view.findViewById(R.id.register_input_password);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = mailField.getText().toString();
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                CredentialRegister credentials = new CredentialRegister(username,password,"Jhon","Doe","male",03022000, mail,"101010","url",null);

                app.getUserRepository().register(credentials).observe(requireActivity(),r->{
                    if(r.getStatus() == Status.SUCCESS){
                        Log.d(TAG,"Se registro");
                        Toast.makeText(view.getContext(),"Se registro",Toast.LENGTH_LONG).show();
                        Navigation.findNavController(view).navigate(RegisterFragmentDirections.actionRegisterFragmentToVerificationCode(mail));
                    }else if (r.getStatus() == Status.ERROR){
                        Toast.makeText(view.getContext(),"NO Se registro"+r.getError().getDescription(),Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(view.getContext(),"NO Se registro"+r.getStatus(),Toast.LENGTH_LONG).show();
                    }
                });

//                app.getUserRepository().
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