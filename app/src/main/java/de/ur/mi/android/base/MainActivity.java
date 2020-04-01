package de.ur.mi.android.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationListener {

    private ArrayList<Location> saved_locations;
    private LocationListAdapter location_adapter;
    private NavigationController navigationController;

    private Button startAndStop;
    private boolean isRunning = false;

    public static final int PERMISSIONS_REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Vor Start der App: Permissions werden abgefragt, die App wird nur dann richtig gestartet (init()), wenn Permissions erteilt wurden
        // Wurden die Permissions noch nicht erteilt, dann wird eine Anfrage gesendet
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);
        } else {
            init();
        }
    }

    private void init(){
        initNavigation();
        initUI();
    }

    private void initNavigation() {
        navigationController = new NavigationController(this,this);
    }



    private void initUI() {
        startAndStop = findViewById(R.id.button_save_current_position);
        startAndStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isRunning){
                    modifyButtonLayout(getString(R.string.start), Color.GREEN);
                    navigationController.stop();
                }else{
                    modifyButtonLayout(getString(R.string.stop), Color.RED);
                    navigationController.start();
                }

                isRunning = !isRunning;
            }
        });

        ListView location_list = findViewById(R.id.list_saved_positions);

        saved_locations = new ArrayList<>();

        location_adapter = new LocationListAdapter(this, saved_locations);
        location_list.setAdapter(location_adapter);
    }

    private void modifyButtonLayout(String caption, int colorId) {
        startAndStop.getBackground().setColorFilter(colorId, PorterDuff.Mode.MULTIPLY);
        startAndStop.setText(caption);
    }


    // neues Object vom Typ Location wird erstellt. Dieses kann über die Methode getLastKnownLocation vom navigationController geholt werden
    // das Object wird der Liste hinzugefügt
    private void saveCurrentPosition() {
        Location currentLocation = navigationController.getLastKnownLocation();
        if (currentLocation == null) {
            return;
        }
        saved_locations.add(currentLocation);
        location_adapter.notifyDataSetChanged();
    }

    // die Methode wird aufgerufen, wenn der User auf die Permission Anfrage reagiert hat
    // über den requestCode lässt sich ermitteln, ob die Anfrage und die Antwort richtig übergeben wurde
    // in grantResults stecken die Ergebnisse der Abfrage. In dem Fall überprüfen wir, ob grantResults leer ist und ob die erste Stelle akzeptiert wurde,
    // da wir nur eine Permission abfragen
    // Nun starten wir nachträglich die App (init())
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
                init();
            } else {
                Toast.makeText(this, "Permissions not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    // überschriebene Methode des Interfaces, wird vom NavigationController aufgerufen, wenn die Location geupdatet wurde
    @Override
    public void onNewNavigationInformation() {
        saveCurrentPosition();
    }
}