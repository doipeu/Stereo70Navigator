package com.stereo70.navigator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1;

    private TextInputEditText editLocationName;
    private TextInputEditText editStereoX;
    private TextInputEditText editStereoY;
    private Button btnConvert;
    private Button btnNavigate;
    private Button btnAddFavorite;
    private Button btnImportFile;
    private Button btnViewFavorites;
    private MaterialCardView cardResult;
    private TextView txtLatitude;
    private TextView txtLongitude;

    private FavoritesManager favoritesManager;
    private double currentLat = 0;
    private double currentLon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favoritesManager = new FavoritesManager(this);

        initViews();
        setupListeners();
    }

    private void initViews() {
        editLocationName = findViewById(R.id.editLocationName);
        editStereoX = findViewById(R.id.editStereoX);
        editStereoY = findViewById(R.id.editStereoY);
        btnConvert = findViewById(R.id.btnConvert);
        btnNavigate = findViewById(R.id.btnNavigate);
        btnAddFavorite = findViewById(R.id.btnAddFavorite);
        btnImportFile = findViewById(R.id.btnImportFile);
        btnViewFavorites = findViewById(R.id.btnViewFavorites);
        cardResult = findViewById(R.id.cardResult);
        txtLatitude = findViewById(R.id.txtLatitude);
        txtLongitude = findViewById(R.id.txtLongitude);
    }

    private void setupListeners() {
        btnConvert.setOnClickListener(v -> convertCoordinates());
        btnNavigate.setOnClickListener(v -> navigateToLocation());
        btnAddFavorite.setOnClickListener(v -> addToFavorites());
        btnImportFile.setOnClickListener(v -> importFromFile());
        btnViewFavorites.setOnClickListener(v -> viewFavorites());
    }

    private void convertCoordinates() {
        // Reset state
        currentLat = 0;
        currentLon = 0;
        cardResult.setVisibility(View.GONE);

        String xStr = editStereoX.getText().toString().trim();
        String yStr = editStereoY.getText().toString().trim();

        if (xStr.isEmpty() || yStr.isEmpty()) {
            Toast.makeText(this, "Introduceți ambele coordonate!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double x = Double.parseDouble(xStr); // X = Nord
            double y = Double.parseDouble(yStr); // Y = Est

            if (!Stereo70Converter.isValidStereo70(x, y)) {
                Toast.makeText(this, getString(R.string.invalid_coordinates), Toast.LENGTH_LONG).show();
                return;
            }

            // Converterul universal așteaptă formula matematică (Est, Nord) -> deci (y, x)
            Stereo70Converter.GPSCoordinate gps = Stereo70Converter.stereo70ToGPS(y, x);
            currentLat = gps.latitude;
            currentLon = gps.longitude;

            displayResult(gps);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Format numeric invalid!", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayResult(Stereo70Converter.GPSCoordinate gps) {
        txtLatitude.setText(getString(R.string.latitude) + " " + String.format("%.6f", gps.latitude));
        txtLongitude.setText(getString(R.string.longitude) + " " + String.format("%.6f", gps.longitude));
        cardResult.setVisibility(View.VISIBLE);
    }

    private void navigateToLocation() {
        // Force conversion to use the latest inputs on screen
        convertCoordinates();
        if (currentLat == 0 && currentLon == 0) {
            return;
        }

        // Open Google Maps with a pin at exact location instead of auto-routing to nearest road
        String label = Uri.encode("Locație Stereo70");
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + currentLat + "," + currentLon + "(" + label + ")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Fallback to browser if Google Maps not installed
            Uri browserUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" +
                    currentLat + "," + currentLon);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
            startActivity(browserIntent);
        }
    }

    private void addToFavorites() {
        String xStr = editStereoX.getText().toString().trim();
        String yStr = editStereoY.getText().toString().trim();
        String name = editLocationName.getText().toString().trim();

        if (xStr.isEmpty() || yStr.isEmpty()) {
            Toast.makeText(this, "Introduceți coordonatele mai întâi!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (name.isEmpty()) {
            name = "Locație " + System.currentTimeMillis();
        }

        try {
            double x = Double.parseDouble(xStr);
            double y = Double.parseDouble(yStr);

            if (!Stereo70Converter.isValidStereo70(x, y)) {
                Toast.makeText(this, getString(R.string.invalid_coordinates), Toast.LENGTH_LONG).show();
                return;
            }

            Coordinate coordinate = new Coordinate(name, x, y);
            favoritesManager.saveFavorite(coordinate);

            Toast.makeText(this, "Adăugat la favorite!", Toast.LENGTH_SHORT).show();

            // Clear inputs
            editLocationName.setText("");
            editStereoX.setText("");
            editStereoY.setText("");
            cardResult.setVisibility(View.GONE);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Format numeric invalid!", Toast.LENGTH_SHORT).show();
        }
    }

    private void importFromFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        String[] mimeTypes = {"text/plain", "text/csv", "text/comma-separated-values"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                parseImportedFile(uri);
            }
        }
    }

    private void parseImportedFile(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int importedCount = 0;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Skip empty lines and comments
                }

                // Expected format: name,x,y OR x,y OR x;y OR x y
                String[] parts = line.split("[,;\\s]+");

                if (parts.length >= 2) {
                    try {
                        String name;
                        double x, y;

                        if (parts.length >= 3) {
                            // Format: name,x,y
                            name = parts[0];
                            x = Double.parseDouble(parts[1]);
                            y = Double.parseDouble(parts[2]);
                        } else {
                            // Format: x,y
                            x = Double.parseDouble(parts[0]);
                            y = Double.parseDouble(parts[1]);
                            name = "Import " + lineNumber;
                        }

                        if (Stereo70Converter.isValidStereo70(x, y)) {
                            Coordinate coordinate = new Coordinate(name, x, y);
                            favoritesManager.saveFavorite(coordinate);
                            importedCount++;
                        }

                    } catch (NumberFormatException e) {
                        // Skip invalid lines
                    }
                }
            }

            reader.close();
            inputStream.close();

            Toast.makeText(this, "Importate " + importedCount + " locații!", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, "Eroare la importul fișierului: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void viewFavorites() {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }
}
