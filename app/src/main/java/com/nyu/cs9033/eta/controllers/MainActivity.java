package com.nyu.cs9033.eta.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nyu.cs9033.eta.R;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity"; // use in login

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.i(TAG, "MainActivity is in OnCreate()");
	}

	/**
	 * This method should start the
	 * Activity responsible for creating
	 * a Trip.
	 */

	public void startCreateTripActivity(View view) {
		Intent intent = new Intent(this, CreateTripActivity.class);
		startActivity(intent);

	}

	/**
	 * This method should start the
	 * Activity responsible for viewing
	 * Trips.
	 */
	public void startTripHistoryActivity(View view) {
		Intent intent = new Intent(this, TripHistoryActivity.class);
		startActivity(intent);

	}

}
