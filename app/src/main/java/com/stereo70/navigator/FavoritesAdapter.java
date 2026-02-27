package com.stereo70.navigator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {

    private List<Coordinate> favorites;
    private Context context;
    private OnFavoriteActionListener listener;

    public interface OnFavoriteActionListener {
        void onDeleteFavorite(Coordinate coordinate);
    }

    public FavoritesAdapter(Context context, List<Coordinate> favorites, OnFavoriteActionListener listener) {
        this.context = context;
        this.favorites = favorites;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Coordinate coordinate = favorites.get(position);
        holder.bind(coordinate);
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public void updateFavorites(List<Coordinate> newFavorites) {
        this.favorites = newFavorites;
        notifyDataSetChanged();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView txtFavoriteName;
        TextView txtFavoriteCoords;
        Button btnNavigateToFavorite;
        Button btnDeleteFavorite;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFavoriteName = itemView.findViewById(R.id.txtFavoriteName);
            txtFavoriteCoords = itemView.findViewById(R.id.txtFavoriteCoords);
            btnNavigateToFavorite = itemView.findViewById(R.id.btnNavigateToFavorite);
            btnDeleteFavorite = itemView.findViewById(R.id.btnDeleteFavorite);
        }

        public void bind(Coordinate coordinate) {
            txtFavoriteName.setText(coordinate.getName());
            txtFavoriteCoords.setText(String.format("X: %.2f, Y: %.2f",
                    coordinate.getStereoX(), coordinate.getStereoY()));

            btnNavigateToFavorite.setOnClickListener(v -> {
                // Convert and navigate
                Stereo70Converter.GPSCoordinate gps = Stereo70Converter.stereo70ToGPS(
                        coordinate.getStereoX(), coordinate.getStereoY());

                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + gps.latitude + "," + gps.longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                } else {
                    // Fallback to browser
                    Uri browserUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=" +
                            gps.latitude + "," + gps.longitude);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
                    context.startActivity(browserIntent);
                }
            });

            btnDeleteFavorite.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteFavorite(coordinate);
                }
            });
        }
    }
}
