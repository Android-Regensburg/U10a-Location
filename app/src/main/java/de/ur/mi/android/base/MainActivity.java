package de.ur.mi.android.base;

import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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


    }

    private void init(){
        initNavigation();
        initUI();
    }

    private void initNavigation() {
        navigationController = new NavigationController(this);
    }


    private void initUI() {
        startAndStop = findViewById(R.id.button_save_current_position);




        ListView location_list = findViewById(R.id.list_saved_positions);



    }

    private void modifyButtonLayout(String caption, int colorId) {
        // der button background und text wird mit den übergebenen werten angepasst

    }


    private void saveCurrentPosition() {

    }

}
