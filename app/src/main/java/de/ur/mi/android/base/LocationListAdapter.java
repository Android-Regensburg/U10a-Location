package de.ur.mi.android.base;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class LocationListAdapter extends BaseAdapter {

    private ArrayList<Location> locations;
    private Context context;

    public LocationListAdapter(Context context, ArrayList<Location> locations) {
        this.context = context;
        this.locations = locations;
    }

    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public Object getItem(int position) {
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.location_list_item, null);

        }

        Location location = (Location) getItem(position);

        if (location != null) {
            TextView longitude = v.findViewById(R.id.longitude);
            TextView latitude = v.findViewById(R.id.latitude);
            TextView time = v.findViewById(R.id.time);

            longitude.setText("" + location.getLongitude());
            latitude.setText("" + location.getLatitude());
            time.setText("" + location.getTime());
        }

        return v;
    }

}
