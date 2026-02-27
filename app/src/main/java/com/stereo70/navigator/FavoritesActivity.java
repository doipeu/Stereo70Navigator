package com.stereo70.navigator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements FavoritesAdapter.OnFavoriteActionListener {

    private RecyclerView recyclerViewFavorites;
    private TextView txtNoFavorites;
    private FavoritesManager favoritesManager;
    private FavoritesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        favoritesManager = new FavoritesManager(this);

        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites);
        txtNoFavorites = findViewById(R.id.txtNoFavorites);

        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));

        loadFavorites();
    }

    private void loadFavorites() {
        List<Coordinate> favorites = favoritesManager.getFavorites();

        if (favorites.isEmpty()) {
            recyclerViewFavorites.setVisibility(View.GONE);
            txtNoFavorites.setVisibility(View.VISIBLE);
        } else {
            recyclerViewFavorites.setVisibility(View.VISIBLE);
            txtNoFavorites.setVisibility(View.GONE);

            adapter = new FavoritesAdapter(this, favorites, this);
            recyclerViewFavorites.setAdapter(adapter);
        }
    }

    @Override
    public void onDeleteFavorite(Coordinate coordinate) {
        new AlertDialog.Builder(this)
                .setTitle("Șterge favorit")
                .setMessage("Sigur doriți să ștergeți \"" + coordinate.getName() + "\"?")
                .setPositiveButton("Da", (dialog, which) -> {
                    favoritesManager.removeFavorite(coordinate.getId());
                    loadFavorites();
                    Toast.makeText(this, "Favorit șters!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Nu", null)
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
