package com.nyu.cs9033.eta;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nyu.cs9033.eta.models.Trip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Bing on 2016/4/2.
 */

public class TripHistoryListAdapter extends ArrayAdapter<String>{

    private static final String TAG = "TripHistoryListAdapter";

    private final Activity context;
    private final ArrayList<Trip> trips;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\nHH:mm");

    public TripHistoryListAdapter(Activity context, ArrayList<Trip> trips) {

        super(context, R.layout.activity_trip_history, new String[trips.size()]);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.trips = trips;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.trip_history_item, null, true);
        processView(rowView, position);

        return rowView;
    }


    private View processView(View rowView, int position){

        Trip trip = trips.get(position);

        TextView trip_name_view = (TextView) rowView.findViewById(R.id.trip_name);
        TextView trip_location_view = (TextView) rowView.findViewById(R.id.trip_destination);
        TextView trip_date_view = (TextView) rowView.findViewById(R.id.trip_date);


        Date date = trip.getStartDate();
        String startDateStr = dateFormat.format(date);

        trip_name_view.setText(trip.getName());
        trip_location_view.setText(trip.getDestination());
        trip_date_view.setText(startDateStr);

        return rowView;
    }


}
