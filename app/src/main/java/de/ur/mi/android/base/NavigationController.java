package de.ur.mi.android.base;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;


public class NavigationController {

    public static final long UPDATE_TIME = 100;
    public static final float UPDATE_DISTANCE = 1;

    private LocationManager locationManger;
    private Location lastLocation;
    private String bestProvider;
    private Context context;

    private LocationListener locationListener;


    public NavigationController(Context context) {
        this.context = context;
        init();
    }

    private void init() {

    }

    public void start(){

    }

    public void stop(){


    }

    private void setBestProvider() {
        // ein neues object vom typ criteria wird erstellt  (Accuracy fine, power medium , bearing true)
        // criteria hilft den locationmanager den passenden provider auszuwählen , dieser soll dann in der variable abgelegt werden

        if (bestProvider == null) {
            Log.e("setbestprovider", "no Provider set");
        }
    }


    public Location getLastKnownLocation() {
        return null;
    }


}

