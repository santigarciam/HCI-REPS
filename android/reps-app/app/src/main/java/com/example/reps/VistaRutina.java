package com.example.reps;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.model.Routine;
import com.example.reps.retrofit.api.repository.Status;

import org.jetbrains.annotations.NotNull;


public class VistaRutina extends Fragment {
    TextView name,owner,descr;

    public VistaRutina() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        App app = (App) requireActivity().getApplication();
        name = view.findViewById(R.id.nombreRut);
        owner = view.findViewById(R.id.autorRut);
        descr = view.findViewById(R.id.descripcionRut);
       // Routine rut;
        VistaRutinaArgs args = VistaRutinaArgs.fromBundle(getArguments());
        if (getArguments() != null) {
            Integer id = args.getIDRutina();
            app.getRoutineRepository().getRoutine(id).observe(requireActivity(),r->{
                // TODO: Cambiar esto de imprimir por el manejo de la API
                if(r.getStatus() == Status.SUCCESS) {

                   name.setText(r.getData().getName());
                    owner.setText(r.getData().getUser().getUsername());
                   descr.setText(r.getData().getDetail());


                    ImageButton share_btn = view.findViewById(R.id.shareRut);

                    share_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = args.getIDRutina();
                            ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                            Uri myUri = Uri.parse("http://stackoverflow.com"); //cambiar al link de la rutina
                            ClipData clip = ClipData.newRawUri("Rutine Link", myUri);
                            clipboard.setPrimaryClip(clip);

                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_STREAM, myUri);
                            getContext().startActivity(Intent.createChooser(intent, "Compartir"));
                            //Toast.makeText(view.getContext(),"Link de rutina " + position + " copiado al portapapeles", Toast.LENGTH_LONG).show();
                        }

                    });

                }});

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vista_rutina, container, false);
    }
}