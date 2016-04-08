package com.nyu.cs9033.eta.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.nyu.cs9033.eta.R;
import com.nyu.cs9033.eta.TripDatabaseHelper;
import com.nyu.cs9033.eta.models.Location;
import com.nyu.cs9033.eta.models.Person;
import com.nyu.cs9033.eta.models.Trip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewTripActivity extends Activity {

	private static final String TAG = "ViewTripActivity";
	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private final TripDatabaseHelper tripDatabaseHelper = new TripDatabaseHelper(this);


	private TextView trip_name_view, trip_destination_view, trip_start_view, trip_end_view;
	private TextView trip_people_view, trip_address_view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_trip);

		Intent intent = getIntent();

		long trip_id = intent.getLongExtra("trip_id", 0L);

		processViews();

		TripDatabaseHelper tripDatabaseHelper = new TripDatabaseHelper(this);
		Trip trip = tripDatabaseHelper.getTrip(trip_id);
		Date date = trip.getStartDate();

		Log.i(TAG, String.format("trip_id = %d\n", trip.getId()));
		Log.i(TAG, date.toString());

		viewTrip(trip);

	}



	private void processViews(){
		trip_name_view = (TextView)findViewById(R.id.view_trip_name_content);
		trip_destination_view = (TextView)findViewById(R.id.view_trip_location_content);
		trip_start_view = (TextView)findViewById(R.id.view_trip_start_content);
		trip_end_view = (TextView)findViewById(R.id.view_trip_end_content);
		trip_people_view = (TextView)findViewById(R.id.view_trip_people_names);
        trip_address_view = (TextView)findViewById(R.id.view_trip_address_content);
	}


	/**
	 * Populate the View using a Trip model.
	 *
	 * @param trip The Trip model used to
	 * populate the View.
	 */
	private void viewTrip(Trip trip) {

		if(trip == null){
			Log.e(TAG, "no trip available");
			return;
		}

		trip_name_view.setText(trip.getName());
		trip_destination_view.setText(trip.getDestination());

		Date startDate = trip.getStartDate();
		Date endDate = trip.getEndDate();

		String startDateStr = dateFormat.format(startDate);
		String endDateStr = dateFormat.format(endDate);

		trip_start_view.setText(startDateStr);
		trip_end_view.setText(endDateStr);

		setPeopleNames(trip);
        setAddress(trip);

		// TODO - fill in here
	}

	/**
	 *   Set the names of People on the TexView
	 */
	private void setPeopleNames(Trip trip){
		ArrayList<Person> personArray = tripDatabaseHelper.getPeopleFromTrip(trip);

		String str = "";
		for(Person person: personArray){
			str += person.getName() + " ";
            if(person.getPhoneNumber() != null) {
                str += person.getPhoneNumber() + "\n";
            }else{
                str += "\n";
            }
		}
		trip_people_view.setText(str);
	}

	/**
	 *  Set the address of the location on the TextView
	 */
	private void setAddress(Trip trip){
		Location location = tripDatabaseHelper.getLocation(trip.getId());
        trip_address_view.setText(location.getAddress());
	}

}
