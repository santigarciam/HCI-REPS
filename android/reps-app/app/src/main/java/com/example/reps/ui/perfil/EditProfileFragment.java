package com.example.reps.ui.perfil;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reps.LoadingDialog;
import com.example.reps.R;
import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.User;
import com.example.reps.retrofit.api.model.UserInformation;
import com.example.reps.retrofit.api.repository.Status;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class EditProfileFragment extends Fragment{


   private TextView tvDate;
   private DatePickerDialog.OnDateSetListener setListener;
   private User user;
   private App app;
   private long fechaCumpleanosMillis;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        //////////////////////////////////////////////////////////////////////
        // Busqueda de informacion a la API

        app = (App) requireActivity().getApplication();
        app.getUserRepository().getCurrentUser().observe(requireActivity(),r->{

            if(r.getStatus() == Status.SUCCESS){
                user = r.getData();
                Log.d("EditarPerfil IN ", "onCreateView: "+user);
                TextView nameField = root.findViewById(R.id.editProfile_nombre_input);
                nameField.setText(user.getFirstName());

                TextView lastNameField = root.findViewById(R.id.editProfile_apellido_input);
                lastNameField.setText(user.getLastName());

                Spinner generoField = root.findViewById(R.id.editProfile_spinner_generos);
                generoField.setSelection(user.getGender().equals("male")?0:1);


                TextView dateField = root.findViewById(R.id.editProfile_fecha_input);
                Calendar c = Calendar.getInstance(TimeZone.getDefault());
                fechaCumpleanosMillis = user.getBirthdate().getTime();
                c.setTimeInMillis(fechaCumpleanosMillis);
                DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
                dateField.setText(df.format(c.getTime()));

                TextView avatarField = root.findViewById(R.id.editProfile_avatar_url_input);
                avatarField.setText(user.getAvatarUrl());
            }

        });


        //////////////////////////////////////////////////////////////////////
        // onClick boton "Cancelar" dentro del fragmento

        root.findViewById(R.id.editProfile_cancelar_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(EditProfileFragmentDirections.actionEditProfileFragmentToNavigationPerfil());
                //getActivity().getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                //ver por que no anda con pop
            }
        });

        //////////////////////////////////////////////////////////////////////
        // Para crear el spinner que muestra los distintos generos disponibles

        Spinner spinner = root.findViewById(R.id.editProfile_spinner_generos);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.generos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //////////////////////////////////////////////////////////////////////
        // DatePicker para fecha de nacimiento

        tvDate = root.findViewById(R.id.editProfile_fecha_input);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                  datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                  datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String dateS = (dayOfMonth<10?"0"+dayOfMonth:dayOfMonth) + "/" + (month<10?"0"+month:month) + "/" + year;

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dateF = new Date(fechaCumpleanosMillis);
                try {
                  dateF = dateFormat.parse(dateS);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                fechaCumpleanosMillis = dateF.getTime();
                tvDate.setText(dateS);
                //Log.d("DATE", "onDateSet: " + dateF);
                //Log.d("DATE", "onDateSet: " + fechaCumpleanosMillis);
            }
        };


        //////////////////////////////////////////////////////////////////////
        // Boton "Guardar" setup

        root.findViewById(R.id.editProfile_guardar_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(app == null){
                    Log.d("EditProfileFragment", "onViewCreated: Error app");
                }
                LoadingDialog loadingDialog = new LoadingDialog(getActivity());
                loadingDialog.startLoadingDialog();

                String newName = ((TextView)root.findViewById(R.id.editProfile_nombre_input)).getText().toString();
                String newLastName = ((TextView)root.findViewById(R.id.editProfile_apellido_input)).getText().toString();
                String newGenero = ((Spinner)root.findViewById(R.id.editProfile_spinner_generos)).getSelectedItem().toString().equals("Masculino")?"male":"female";
                long newDate = fechaCumpleanosMillis;
                String newAvatarURL = ((TextView)root.findViewById(R.id.editProfile_avatar_url_input)).getText().toString();
                app.getUserRepository().modify(new UserInformation(newName,newLastName,newGenero, newDate,"11112222",newAvatarURL)).observe(requireActivity(), r->{
                    if (r.getStatus() == Status.SUCCESS) {
                        loadingDialog.dismissDialog();
                        Navigation.findNavController(view).navigate(EditProfileFragmentDirections.actionEditProfileFragmentToNavigationPerfil());
                    }else if(r.getStatus() == Status.ERROR){
                        Toast.makeText(getContext(), "Ocurrio un error, intentelo nuevamente mas tarde.", Toast.LENGTH_SHORT);
                        Navigation.findNavController(view).navigate(EditProfileFragmentDirections.actionEditProfileFragmentToNavigationPerfil());
                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return root;
    }
}