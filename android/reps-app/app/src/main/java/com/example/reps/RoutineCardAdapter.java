package com.example.reps;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reps.retrofit.App;
import com.example.reps.retrofit.api.repository.Status;
import com.example.reps.ui.home.HomeFragmentDirections;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoutineCardAdapter extends RecyclerView.Adapter<RoutineCardAdapter.ViewHolder> {

    public List<RoutineCard> routines;
    private LayoutInflater mInflater;
    private Context context;
    // almacna el estado original y no cambia en toda la busqueda
    private List<RoutineCard> originalRoutines;
    private App app;
    private Activity activity;

    public RoutineCardAdapter(List<RoutineCard> routines, Context context, App app, Activity activity) {
        this.routines = routines;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.app = app;
        this.activity = activity;
        originalRoutines = new ArrayList<>();
        originalRoutines.addAll(routines);
    }

    @NonNull
    @NotNull
    @Override
    public RoutineCardAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.routine_card, null, false);
        return new RoutineCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RoutineCardAdapter.ViewHolder holder, int position) {
        holder.bindData(routines.get(position));
        Log.d("ROUTINE_CARD_ADAPTER", "onBindViewHolder: " + routines.get(position).getId() + " " + routines.get(position).isFavourite());
    }

    @Override
    public int getItemCount() {
        return routines.size();
    }

    public void filter(String strSearch) {
        if (strSearch.length() == 0) {
            routines.clear();
            routines.addAll(originalRoutines);
        } else {
            List<RoutineCard> searched = routines.stream().filter(r -> r.getName().toLowerCase().contains(strSearch))
                    .collect(Collectors.toList());
            routines.clear();
            routines.addAll(searched);
        }
        notifyDataSetChanged();
    }

    public void setroutines(List<RoutineCard> rut) {
        routines = rut;
    }

    public void auxNotify(int position){

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, owner, description; // AGREGAR LAS VARIABLES DEL ROUTINE CARD QUE FALTAN
        RatingBar ratingBar;
        ImageButton favButton;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.rutine_card_title);
            owner = itemView.findViewById(R.id.rutine_card_user);
            description = itemView.findViewById(R.id.descripcionRut);
            ratingBar = itemView.findViewById(R.id.rutine_card_rating);
            favButton = itemView.findViewById(R.id.vista_rutina_fav_button);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                View parent = (View) itemView.getParentForAccessibility().getParentForAccessibility();
                Log.d("COMPARACION", "ID parent " + parent.getId() + " ID fragment" + R.id.fragment_home);
                // Todo: Ver este problema, cuando se toca la rutina y queremos abrirla depende
                // en que fragmento este es el navigator a usa
                // if(parent.getId() == R.id.fragment_home) {

                Navigation.findNavController(view).navigate(
                        HomeFragmentDirections.actionNavigationHomeToVistaRutina(routines.get(position).getId(), routines.get(position).isFavourite()));

                // }
                // }else if (itemView.getId() == R.id.fragment_descubrir){
                // Navigation.findNavController(view).navigate(DescubrirFragmentDirections.actionNavigationDescubrirToVistaRutina(position));
                // }

            });

            itemView.findViewById(R.id.vista_rutina_fav_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    int routineID = routines.get(position).getId();

                    if(app == null){
                        Log.d("ROUTINE_CARD_ADAPTER", "onClick: Error app");
                    }

                    if (!routines.get(position).isFavourite()){
                        app.getFavouriteRepository().addFavourite(routineID).observe((LifecycleOwner) activity, r ->{
                            if (r.getStatus() == Status.SUCCESS) {
                                routines.get(position).setFavourite(true);
                                ((ImageButton) itemView.findViewById(R.id.vista_rutina_fav_button))
                                        .setImageResource(R.drawable.baseline_favorite_black_24dp_pressed);
                                Toast.makeText(view.getContext(), "Rutina \"" + routines.get(position).getName() + "\" agregada a favoritos", Toast.LENGTH_LONG)
                                        .show();
                            }else{

                            }
                        });
                    }else{
                        app.getFavouriteRepository().deleteFavourite(routineID).observe((LifecycleOwner) activity, r ->{
                            if (r.getStatus() == Status.SUCCESS) {
                                routines.get(position).setFavourite(false);
                                ((ImageButton) itemView.findViewById(R.id.vista_rutina_fav_button))
                                        .setImageResource(R.drawable.baseline_favorite_black_24dp);
                                Toast.makeText(view.getContext(), "Rutina \"" + routines.get(position).getName() + "\" eliminada de favoritos", Toast.LENGTH_LONG)
                                        .show();
                                auxNotify(position);
                            }else{

                            }
                        });
                    }

                }
            });

            itemView.findViewById(R.id.rutine_card_share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    Uri myUri = Uri.parse("http://stackoverflow.com"); // cambiar al link de la rutina
                    ClipData clip = ClipData.newRawUri("Rutine Link", myUri);
                    clipboard.setPrimaryClip(clip);

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, myUri.toString());
                    context.startActivity(Intent.createChooser(intent, "Compartir"));
                    // Toast.makeText(view.getContext(),"Link de rutina " + position + " copiado al
                    // portapapeles", Toast.LENGTH_LONG).show();
                }
            });
        }

        void bindData(final RoutineCard item) {
            if (item.isFavourite()){
                Log.d("ROUTINE_CARD_ADAPTER", "rut id fav: " + item.getId());
                favButton.setImageResource(R.drawable.baseline_favorite_black_24dp_pressed);
            }
            name.setText(item.getName());
            owner.setText(item.getOwner());
            description.setText(item.getDescription());
            ratingBar.setRating(item.getRating());
        }

    }
}
