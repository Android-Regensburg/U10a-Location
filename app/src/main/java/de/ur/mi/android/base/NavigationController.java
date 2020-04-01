package de.ur.mi.android.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;


public class NavigationController {

    private static final long UPDATE_TIME = 100;
    private  static final float UPDATE_DISTANCE = 1;

    private LocationManager locationManger;
    private Location lastLocation;
    private String bestProvider;
    private Context context;

    private NavigationListener navigationListener;
    private LocationListener locationListener;

    public NavigationController(Context context,NavigationListener navigationListener) {
        this.context = context;
        this.navigationListener = navigationListener;
        init();
    }

    // neues LocationManager Object wird über den Context erstellt
    // Permission Check muss gemacht werden, für den Fall, dass die App während der Laufzeit die Permission entzogen wird
    // bestProvider wird ermittelt und schon mal die aktuelle Location abgerufen
    private void init() {
        locationManger = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        setBestProvider();
        lastLocation = locationManger.getLastKnownLocation(bestProvider);
    }

    // Listener wird initialisiert
    // bei Location updates wird die neue Location in die Variable eingetragen und der MainActivity wird Bescheid gegeben
    // auch hier muss ein Permission Check gemacht werden
    public void start(){
        setBestProvider();
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                lastLocation = locationManger.getLastKnownLocation(bestProvider);
                navigationListener.onNewNavigationInformation();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        // das Lauschen auf Location Updates wird gestartet, ein weiteres Mal müssen die Permissions vorher überprüft werden
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManger.requestLocationUpdates(bestProvider, UPDATE_TIME, UPDATE_DISTANCE, locationListener);
    }

    //der Listener lauscht nicht mehr auf Updates
    public void stop(){
        if(locationManger!= null){
            locationManger.removeUpdates(locationListener);
        }
    }

    //der Beste Provider wird ermittelt
    private void setBestProvider() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        criteria.setBearingRequired(true);
        bestProvider = locationManger.getBestProvider(criteria, true);
        if (bestProvider == null) {
            Log.e("setbestprovider", "no Provider set");
        }
    }


    public Location getLastKnownLocation() {
        return lastLocation;
    }


}

