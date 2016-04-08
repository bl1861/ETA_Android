package com.nyu.cs9033.eta.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nyu.cs9033.eta.R;
import com.nyu.cs9033.eta.TripDatabaseHelper;
import com.nyu.cs9033.eta.TripHistoryListAdapter;
import com.nyu.cs9033.eta.models.Trip;

import java.util.ArrayList;

public class TripHistoryActivity extends Activity {

    private static final  String TAG = "TripHistoryActivity";
    private ArrayList<Trip> trips;
    private ListView trip_list_view;
    private TripHistoryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);


        TripDatabaseHelper tripDatabaseHelper = new TripDatabaseHelper(this);
        trips = tripDatabaseHelper.getAllTrips();

        adapter = new TripHistoryListAdapter(this, trips);
        trip_list_view = (ListView)findViewById(R.id.trip_history_list);
        trip_list_view.setAdapter(adapter);
        setListItemListener();
        setListItemLongListener();

    }


    private void setListItemListener(){

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

            // when clicking item, new an intent and jump to ViewTripActivity
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Trip trip = trips.get(position);
                Intent intent = new Intent(getApplicationContext(), ViewTripActivity.class);
                intent.putExtra("trip_id", trip.getId());

                startActivity(intent);
            }

        };

        trip_list_view.setOnItemClickListener(onItemClickListener);
    }

    private void setListItemLongListener(){
        AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TripHistoryActivity.this);

                // set title
                alertDialogBuilder.setTitle("Do you want to delete?");

                // set dialog message
                alertDialogBuilder
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, remove the trip
                                TripDatabaseHelper tripDatabaseHelper = new TripDatabaseHelper(TripHistoryActivity.this);
                                tripDatabaseHelper.deleteTrip(trips.get(position));

                                ArrayList<Trip> tripBuffer = new ArrayList<Trip>();
                                for(int i = 0; i < trips.size(); i++){
                                    if(i != position){
                                        tripBuffer.add(trips.get(i));
                                    }
                                }

                                trips = tripBuffer;
                                adapter = new TripHistoryListAdapter(TripHistoryActivity.this, trips);
                                trip_list_view.setAdapter(adapter);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

                return true;
            }
        };

        trip_list_view.setOnItemLongClickListener(itemLongClickListener);

    }

}
