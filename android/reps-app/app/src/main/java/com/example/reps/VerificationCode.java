package com.example.reps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.Navigation;

import android.widget.Button;
import android.widget.Toast;

import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.Credentials;
import com.example.reps.retrofit.api.model.VerificationCodeModel;
import com.example.reps.retrofit.api.repository.Status;

import org.jetbrains.annotations.NotNull;

public class VerificationCode extends Fragment {
    private App app;
    private String mail,username,password;


    public VerificationCode() {
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
        return inflater.inflate(R.layout.fragment_verification_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VerificationCodeArgs args = VerificationCodeArgs.fromBundle(getArguments());
        if(getArguments()!=null){
            mail = args.getMailToConfirm();
            //password = args.getPassword();
            //username = args.getUsername();
        }

        app = (App)requireActivity().getApplication();

        Button confirmBtn = requireActivity().findViewById(R.id.confirmCode);
        TextView codeField = requireActivity().findViewById(R.id.verificationCode);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = codeField.getText().toString();
                Log.d("OkHttpClient", "registers: "+code);
                app.getUserRepository().verifyCode(new VerificationCodeModel(mail,code)).observe(requireActivity(),r->{
                    if(r.getStatus() == Status.SUCCESS){
                        app.getUserRepository().login(new Credentials(username,password)).observe(requireActivity(),l->{
                            if(l.getStatus() == Status.SUCCESS){
                                app.getPreferences().setAuthToken(l.getData().getToken());
                                Navigation.findNavController(view).navigate(VerificationCodeDirections.actionVerificationCodeToLogedActivity());
                            }else if (l.getStatus() == Status.ERROR){
                                String errorMesagge = getResources().getString(R.string.error_mesagge_verificacion);
                                Toast.makeText(getContext(),errorMesagge,Toast.LENGTH_LONG).show();
                            }
                        });
                    }else if(r.getStatus() == Status.ERROR){
                        String error = getString(R.string.codigo_invalido);
                        codeField.setError(error);
                    }
                });
            }
        });

        Button resendBtn = requireActivity().findViewById(R.id.reenviarCode);


        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = codeField.getText().toString();
                app.getUserRepository().resendCode(new VerificationCodeModel(mail,code)).observe(requireActivity(),r->{
                    if(r.getStatus() == Status.SUCCESS){
                        Toast.makeText(getContext(),getString(R.string.reenviar_codigo),Toast.LENGTH_LONG).show();
                    }else if(r.getStatus() == Status.ERROR){
                        String errorMesagge = getResources().getString(R.string.error_mesagge_verificacion);
                        Toast.makeText(getContext(),errorMesagge,Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

    }
}