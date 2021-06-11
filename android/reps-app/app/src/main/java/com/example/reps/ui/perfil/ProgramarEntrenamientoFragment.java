package com.example.reps.ui.perfil;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.reps.R;
import com.example.reps.ReminderBroadcast;

import java.sql.Time;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgramarEntrenamientoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgramarEntrenamientoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tvDate;
    DatePickerDialog.OnDateSetListener setDateListener;

    TextView tvTime;
    TimePickerDialog.OnTimeSetListener setTimeListener;

    public ProgramarEntrenamientoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgramarEntrenamientoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgramarEntrenamientoFragment newInstance(String param1, String param2) {
        ProgramarEntrenamientoFragment fragment = new ProgramarEntrenamientoFragment();
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

        createNotificationChannel();
        View root = inflater.inflate(R.layout.fragment_programar_entrenamiento, container, false);

        //////////////////////////////////////////////////////////////////////
        // DatePicker para fecha de notificacion

        tvDate = root.findViewById(R.id.programarEntrenamiento_fecha_input);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int yearSet, int monthSet, int daySet) {
                        tvDate.setText((daySet<10?"0"+daySet:daySet) + "/" + (monthSet<10?"0"+monthSet:monthSet) + "/" + yearSet);
                    }
                }, year, month, day);
                datePickerDialog.show();
                //DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setDateListener, year, month, day);
                //datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //datePickerDialog.show();
            }
        });

        setDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" + month + "/" + year;
                tvDate.setText(date);
            }
        };

        //////////////////////////////////////////////////////////////////////
        // TimePicker para hora de notificacion

        tvTime = root.findViewById(R.id.programarEntrenamiento_hora_input);
        final int hora = calendar.get(Calendar.HOUR_OF_DAY);
        final int minutos = calendar.get(Calendar.MINUTE);

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hora, int minuto) {
                        tvTime.setText(hora + ":" + (minuto<10?"0"+minuto:minuto));
                    }
                }, hora, minutos, true);
                timePickerDialog.show();
            }
        });

        root.findViewById(R.id.programarEntrenamiento_cancelar_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                //ver por que no anda con pop

                Navigation.findNavController(view).navigate(ProgramarEntrenamientoFragmentDirections.actionProgramarEntrenamientoFragmentToNavigationPerfil());
            }
        });

        //////////////////////////////////////////////////////
        // SetUp boton "Guardar"

        root.findViewById(R.id.programarEntrenamiento_guardar_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Se seteo el recordatorio correctamente.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);

                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

                ///AUX

                long timeAtButtonClick = System.currentTimeMillis();
                long tenSecondsInMillis = 1000 * 10;

                ///

                alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick+tenSecondsInMillis, pendingIntent);
            }
        });



        // Inflate the layout for this fragment
        return root;
    }

    /////////////////////////////////////////////////////////
    // Para la notificacion

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "programarEntrenamientoChannel";
            String description = "Canal para programar un entrenamiento";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("programarEntrenamiento", name, importance);
            channel.setDescription(description);

            NotificationManager notificationChannel = getActivity().getSystemService(NotificationManager.class);
            notificationChannel.createNotificationChannel(channel);
        }
    }
}