package com.stereo70.navigator;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoritesManager {
    private static final String PREFS_NAME = "Stereo70Favorites";
    private static final String FAVORITES_KEY = "favorites_list";
    private SharedPreferences prefs;
    private Gson gson;

    public FavoritesManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public List<Coordinate> getFavorites() {
        String json = prefs.getString(FAVORITES_KEY, "");
        if (json.isEmpty()) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<Coordinate>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public void saveFavorite(Coordinate coordinate) {
        List<Coordinate> favorites = getFavorites();
        coordinate.setFavorite(true);
        coordinate.setId(System.currentTimeMillis());
        favorites.add(coordinate);
        saveFavorites(favorites);
    }

    public void removeFavorite(long id) {
        List<Coordinate> favorites = getFavorites();
        favorites.removeIf(coord -> coord.getId() == id);
        saveFavorites(favorites);
    }

    public void updateFavorite(Coordinate coordinate) {
        List<Coordinate> favorites = getFavorites();
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getId() == coordinate.getId()) {
                favorites.set(i, coordinate);
                break;
            }
        }
        saveFavorites(favorites);
    }

    private void saveFavorites(List<Coordinate> favorites) {
        String json = gson.toJson(favorites);
        prefs.edit().putString(FAVORITES_KEY, json).apply();
    }

    public boolean isFavorite(double x, double y) {
        List<Coordinate> favorites = getFavorites();
        for (Coordinate coord : favorites) {
            if (Math.abs(coord.getStereoX() - x) < 0.001 &&
                Math.abs(coord.getStereoY() - y) < 0.001) {
                return true;
            }
        }
        return false;
    }
}
