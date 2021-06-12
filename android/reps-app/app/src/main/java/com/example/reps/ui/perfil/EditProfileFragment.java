package com.example.reps.ui.perfil;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.reps.LoginFragmentDirections;
import com.example.reps.R;
import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.UserInformation;
import com.example.reps.retrofit.repository.Status;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tvDate;
    DatePickerDialog.OnDateSetListener setListener;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        //final TextView textView = binding.textPerfil;
//        perfilViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

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
                String date = (dayOfMonth<10?"0"+dayOfMonth:dayOfMonth) + "/" + (month<10?"0"+month:month) + "/" + year;
                tvDate.setText(date);
            }
        };

        //////////////////////////////////////////////////////////////////////
        // Boton "Guardar" setup

        root.findViewById(R.id.editProfile_guardar_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App app;
                app = (App) requireActivity().getApplication();
                if(app == null){
                    Log.d("EditProfileFragment", "onViewCreated: Error app");
                }

                String newName = ((TextView)root.findViewById(R.id.editProfile_nombre_input)).getText().toString();
                String newLastName = ((TextView)root.findViewById(R.id.editProfile_apellido_input)).getText().toString();
                String newGenero = ((Spinner)root.findViewById(R.id.editProfile_spinner_generos)).getSelectedItem().toString()=="Masculino"?"male":"female";
                String auxDate = ((TextView)root.findViewById(R.id.editProfile_fecha_input)).getText().toString();
                Date newBirthdate = new Date(Integer.parseInt(auxDate.substring(6,7)),Integer.parseInt(auxDate.substring(3,4)),Integer.parseInt(auxDate.substring(0,1)));
                String newAvatarURL = ((TextView)root.findViewById(R.id.editProfile_avatar_url_input)).getText().toString();
                app.getUserRepository().modify(new UserInformation(newName,newLastName,newGenero,newBirthdate,"11112222",newAvatarURL)).observe(requireActivity(), r->{
                    if (r.getStatus() == Status.SUCCESS) {
                        Log.d("EditPRofileFragment", "Se modifico el usuario");
                        Navigation.findNavController(view).navigate(EditProfileFragmentDirections.actionEditProfileFragmentToNavigationPerfil());
                        //app.getPreferences().setAuthToken(r.getData().getToken());
                        //progressBar.setVisibility(View.INVISIBLE);
                        //Navigation.findNavController(view).navigate(LoginFragmentDirections.actionLoginFragmentToLogedActivity());
                    }else if(r.getStatus() == Status.ERROR){
                        Log.d("EditPRofileFragment", "Se NO se modifico el usuario");
                        Toast.makeText(getContext(), r.getError().getDescription(), Toast.LENGTH_SHORT);
                        Log.d("EditPRofileFragment", r.getError().getDescription());
                        //progressBar.setVisibility(View.INVISIBLE);
//                        if (r.getError().getDetails().contains("Password does not match")){
//                            passwordField.setError("Contraseña incorrecta");
//                        }else{
//                            usernameField.setError("Usuario incorrecto");
//                        }
                    } else {
                       // progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(view.getContext(), "-------", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return root;
    }
}