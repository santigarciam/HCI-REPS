package com.example.reps;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RoutineCardAdapter extends RecyclerView.Adapter<RoutineCardAdapter.ViewHolder>{

    private List<RoutineCard> routines;
    private LayoutInflater mInflater;
    private Context context;

    public RoutineCardAdapter(List<RoutineCard> routines, Context context) {
        this.routines = routines;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public RoutineCardAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.routine_card, null,false);
        return new RoutineCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RoutineCardAdapter.ViewHolder holder, int position) {
        holder.bindData(routines.get(position));
    }

    @Override
    public int getItemCount() {
        return routines.size();
    }

    public void setroutines(List<RoutineCard> rut) { routines = rut; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, owner; // AGREGAR LAS VARIABLES DEL ROUTINE CARD QUE FALTAN



        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rutine_card_title);
            owner = itemView.findViewById(R.id.rutine_card_user);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                Toast.makeText(view.getContext(),"Element " + position + " clicked", Toast.LENGTH_LONG).show();
            });

            itemView.findViewById(R.id.rutine_card_fav).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    // Si NO esta fav
                    ((ImageButton)itemView.findViewById(R.id.rutine_card_fav)).setImageResource(R.drawable.baseline_favorite_black_24dp_pressed);
                    Toast.makeText(view.getContext(),"Rutina " + position + " agregada a favoritos", Toast.LENGTH_LONG).show();
                    // FALTA agregar ID a arreglo de FAVORITOS

                    // Si ESTA fav
                    //((ImageButton)itemView.findViewById(R.id.rutine_card_fav)).setImageResource(R.drawable.baseline_favorite_black_24dp);
                    //Toast.makeText(view.getContext(),"Rutina " + position + " borrada de favoritos", Toast.LENGTH_LONG).show();
                    // FALTA borrar ID de arreglo de FAVORITOS
                }
            });

            itemView.findViewById(R.id.rutine_card_share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    Uri myUri = Uri.parse("http://stackoverflow.com"); //cambiar al link de la rutina
                    ClipData clip = ClipData.newRawUri("Rutine Link", myUri);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(view.getContext(),"Link de rutina " + position + " copiado al portapapeles", Toast.LENGTH_LONG).show();

                }
            });
        }

        void bindData(final RoutineCard item){
            name.setText(item.getName());
            owner.setText(item.getOwner());
        }
    }
}
