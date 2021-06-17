package com.example.reps;

import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.navigation.Navigation;

import android.widget.Button;
import android.widget.Toast;

import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.CredentialRegister;
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
            password = args.getPassword();
            username = args.getUsername();
        }

        app = (App)requireActivity().getApplication();

        Button confirmBtn = requireActivity().findViewById(R.id.confirmCode);
        TextView codeField = requireActivity().findViewById(R.id.verificationCode);
        ProgressBar progressBar = view.findViewById(R.id.progressBarCode);
        progressBar.setVisibility(View.INVISIBLE);


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String code = codeField.getText().toString();
                Log.d("OkHttpClient", "registers: "+code);
                app.getUserRepository().verifyCode(new VerificationCodeModel(mail,code)).observe(requireActivity(),r->{
                    if(r.getStatus() == Status.SUCCESS){
                        Log.d("OkHttpClient", "registers: "+username+password);
                        Credentials credentials = new Credentials(username,password);
                        app.getUserRepository().login(credentials).observe(requireActivity(),l->{
                            if(l.getStatus() == Status.SUCCESS){
                                progressBar.setVisibility(View.INVISIBLE);
                                app.getPreferences().setAuthToken(l.getData().getToken());
                                Navigation.findNavController(view).navigate(VerificationCodeDirections.actionVerificationCodeToLogedActivity());
                                requireActivity().finish();
                            }else if (l.getStatus() == Status.ERROR){
                                progressBar.setVisibility(View.INVISIBLE);
                                String errorMesagge = getResources().getString(R.string.error_mesagge_verificacion);
                                Toast.makeText(getContext(),errorMesagge,Toast.LENGTH_LONG).show();
                            }
                        });
                    }else if(r.getStatus() == Status.ERROR){
                        progressBar.setVisibility(View.INVISIBLE);
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
                    progressBar.setVisibility(View.VISIBLE);
                    if(r.getStatus() == Status.SUCCESS){
                        progressBar.setVisibility(View.INVISIBLE);
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