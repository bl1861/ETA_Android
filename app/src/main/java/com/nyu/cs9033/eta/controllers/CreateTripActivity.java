package com.nyu.cs9033.eta.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.nyu.cs9033.eta.R;
import com.nyu.cs9033.eta.TripDatabaseHelper;
import com.nyu.cs9033.eta.models.Location;
import com.nyu.cs9033.eta.models.Person;
import com.nyu.cs9033.eta.models.Trip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateTripActivity extends Activity {

    private static final String TAG = "CreateTripActivity";
    private static final int SEARCH_LOCATION_REQUEST_CODE = 0;
    private static final int SEARCH_CONTACT_REQUEST_CODE = 1;

    private final boolean isStart = true;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final String[] invalidInputMessages = {"Please enter name", "Please enter destination", "Invalid Dates and Times"};


    private int iYear, iMonth, iDay, iHour, iMinute;
    private EditText name_text, destination_text, destination_type_text;
    private TextView people_text, location_text;
    private Button start_date_button, start_time_button, end_date_button, end_time_button, location_button;
    private String startDateText, startTimeText, endDateText, endTimeText;
    private Date startDate, endDate;
    private boolean[] validInputs = new boolean[3];

    private String name, destination;
    private Location location = new Location();
    private Map<String, Boolean> peopleMap = new HashMap();
    private ArrayList<String> peopleNames = new ArrayList<String>();
    private ArrayList<Person> peopleArray = new ArrayList<Person>();

    private TripDatabaseHelper tripDatabaseHelper = new TripDatabaseHelper(this);


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        processView();

        /** This method will show the current time on the date and time picker
         *  It is easier for user to pick time
         */
        setDefaultDateAndTime();


        // TODO - fill in here
    }


    @Override
    public void onPause(){
        super.onPause();

    }


    /**
     *  processView() help app get the required view from layout.
     */
    private void processView() {
        name_text = (EditText) findViewById(R.id.create_trip_name_edit);
        destination_text = (EditText) findViewById(R.id.create_trip_destination_edit);
        destination_type_text = (EditText) findViewById(R.id.create_trip_destination_type_edit);
        location_text = (TextView) findViewById(R.id.create_trip_location_text);
        people_text = (TextView) findViewById(R.id.create_trip_people_members);
        start_date_button = (Button) findViewById(R.id.create_start_date);
        start_time_button = (Button) findViewById(R.id.create_start_time);
        end_date_button = (Button) findViewById(R.id.create_end_date);
        end_time_button = (Button) findViewById(R.id.create_end_time);
        location_button = (Button) findViewById(R.id.create_trip_location_button);


    }

    public void createAndSaveTrip(View view) {
        boolean saveSuccess = isSaveTrip(createTrip());
        if (saveSuccess) {
            finish();
        } 
        else {
            invalidInputWindow();
        }
    }

    /**
     * This method should be used to
     * instantiate a Trip model object.
     *
     * @return The Trip as represented
     * by the View.
     */
    public Trip createTrip() {
        // TODO - fill in here

        name = name_text.getText().toString();
        destination = destination_text.getText().toString();

        validInputs[0] = validString(name);
        validInputs[1] = validString(destination);

        startDate = strToDate(startDateText, startTimeText);
        endDate = strToDate(endDateText, endTimeText);

        if(startDate == null || endDate == null){
            return null;
        }

        validInputs[2] = startDate.compareTo(endDate) <= 0;

        if (!(validInputs[0] && validInputs[1] && validInputs[2])) {
            return null;
        }

        return new Trip(1L, name, destination, startDate, endDate);

    }


    /**
     *  In hw2, I put the trip to the intent in this function.
     *  In hw3, I change it to save the trip to databasae
     */

    public boolean isSaveTrip(Trip trip) {

        if (trip == null) {
            return false;
        }

        long trip_id = tripDatabaseHelper.insertTrip(trip);
        tripDatabaseHelper.insertLocation(trip_id, location);
        for(Person person: peopleArray) {
            tripDatabaseHelper.insertPerson(trip_id, person);
        }

        // TODO - fill in here

        return true;
    }


    public void cancelTrip(View view) {
        setResult(RESULT_CANCELED);
        finish();
        // TODO - fill in here
    }

    /**
     *  If input is invalid, pop alert window.
     */
    private void invalidInputWindow(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(CreateTripActivity.this);
        dialog.setTitle("Invalid Input");
        StringBuilder stringBuilder = new StringBuilder("");

        boolean firstLine = true;

        for(int i = 0; i < 3; i++){
            if(!validInputs[i]){
                if(!firstLine){
                    stringBuilder.append("\n");
                }
                stringBuilder.append(invalidInputMessages[i]);
                firstLine = false;
            }
        }

        dialog.setMessage(stringBuilder.toString());
        dialog.show();
    }


    private void getCurrentTime(){
        final Calendar c = Calendar.getInstance();
        iYear = c.get(Calendar.YEAR);
        iMonth = c.get(Calendar.MONTH);
        iDay = c.get(Calendar.DAY_OF_MONTH);
        iHour = c.get(Calendar.HOUR_OF_DAY);
        iMinute = c.get(Calendar.MINUTE);
    }


    /**
     *  This method will get current time and set the time to the
     *  text of date and time's buttons.
     */

    private void setDefaultDateAndTime() {

        getCurrentTime();
        String defaultDate = String.format("%d-%02d-%02d", iYear, iMonth + 1, iDay);
        String defaultTime = String.format("%02d:%02d", iHour, iMinute);

        startDateText = defaultDate;
        endDateText = defaultDate;
        startTimeText = defaultTime;
        endTimeText = defaultTime;

        start_date_button.setText(defaultDate);
        start_time_button.setText(defaultTime);
        end_date_button.setText(defaultDate);
        end_time_button.setText(defaultTime);

        startDate = strToDate(defaultDate, defaultTime);
        endDate = strToDate(defaultDate, defaultTime);

    }

    public void setStartDate(View view) {
        showDatePickerDialog(isStart);
    }

    public void setStartTime(View view) {
        showTimePickerDialog(isStart);
    }

    public void setEndDate(View view) {
        showDatePickerDialog(!isStart);
    }

    public void setEndTime(View view) {
        showTimePickerDialog(!isStart);
    }


    /**
     *  pop a date picker dialog
     */

    public void showDatePickerDialog(final boolean isStart) {

        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (isStart) {
                            startDateText = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                            start_date_button.setText(startDateText);
                        } else{
                            endDateText = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                            end_date_button.setText(endDateText);
                        }
                        iYear = year;
                        iMonth = monthOfYear;
                        iDay = dayOfMonth;
                    }
                }, iYear, iMonth, iDay);
        dpd.show();
    }


    /**
     *  pop a time picker dialog
     */
    public void showTimePickerDialog(final boolean isStart) {

        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (isStart) {
                            startTimeText = String.format("%02d:%02d", hourOfDay, minute);
                            start_time_button.setText(startTimeText);
                        } else{
                            endTimeText = String.format("%02d:%02d", hourOfDay, minute);
                            end_time_button.setText(endTimeText);
                        }
                        iHour = hourOfDay;
                        iMinute = minute;
                    }
                }, iHour, iMinute, false);

        tpd.show();
    }

    /**
     *  check if the string is not only containing space or newline
     */
    private boolean validString(String str) {
        return (str != null && !str.equals("") && !str.trim().equals(""));
    }


    /**
     *  combine the strings of date and time, then convert the string into date type.
     */
    private Date strToDate(String strDate, String strTime) {
        Date date;
        try {
            date = simpleDateFormat.parse(String.format("%s %s", strDate, strTime));
        } catch (java.text.ParseException e) {
            Log.i(TAG, "parse fail");
            return null;
        }
        return date;
    }


    /**
     *  This will create an intent to use HW3API
     */
    public void searchLocation(View view){
        String destination = destination_text.getText().toString();
        String typeOfDestination = destination_type_text.getText().toString();
        if(!validString(typeOfDestination)){
            typeOfDestination = "food";
        }

        Uri uri = Uri.parse("location://com.example.nyu.hw3api");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.putExtra("searchVal", destination + "::" + typeOfDestination);

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            startActivityForResult(intent, SEARCH_LOCATION_REQUEST_CODE);
        }

    }

    /**
     *  searchPhoneBook will create an intent to use contact book
     */

    public void searchPhoneBook(View view){
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(pickContactIntent, SEARCH_CONTACT_REQUEST_CODE);
    }

    // remove the people on the screen
    public void removePeople(View view){
        for(String name: peopleNames){
            peopleMap.put(name, false);
        }
        peopleNames.clear();
        peopleArray.clear();
        people_text.setText("");

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == SEARCH_LOCATION_REQUEST_CODE) {

            // create an object of Location
            if (resultCode == Activity.RESULT_FIRST_USER) {
                ArrayList<String> locationList;

                locationList = data.getStringArrayListExtra("retVal");
                location.setName(locationList.get(0));
                location.setAddress(locationList.get(1));
                location.setLatitude(Double.parseDouble(locationList.get(2)));
                location.setLongitude(Double.parseDouble(locationList.get(3)));
                location_text.setText(locationList.get(0));

            }
        }

        else if (requestCode == SEARCH_CONTACT_REQUEST_CODE && resultCode == Activity.RESULT_OK){

            // gets Uri from the Intent Object
            Uri contactUri = data.getData();

            Cursor c = this.getContentResolver().query(contactUri, null, null, null, null);

            // check to make sure you got the results
            if(c.getCount() == 0){
                c.close();
                return;
            }

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                String personName = c.getString(0);

                // if the name has already on the list, return.
                if(peopleMap.containsKey(personName) && peopleMap.get(personName)) {
                    return;
                }

                Person person = new Person();
                person.setName(personName);

                // renew the name on the screen
                setPeopleView(personName);

                // search the information about the person
                String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                String indexNum = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if("1".equalsIgnoreCase(indexNum)){

                    Cursor contactCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);

                    while (contactCursor.moveToNext()) {
                        int type = contactCursor.getInt(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        if (type == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
                            int colIdx = contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            String phoneNumbers = contactCursor.getString(colIdx);
                            person.setPhoneNumber(phoneNumbers);
                            break;
                        }
                    }
                }

                peopleArray.add(person);

            }
        }
    }


    // set the people's name on the screen
    public void setPeopleView(String personName){
        peopleMap.put(personName, true);
        peopleNames.add(personName);
        String peopleInView = "";
        boolean first = true;
        for(String str: peopleNames){
            if(!first){
                peopleInView += "\n";
            }
            peopleInView += str;
            first = false;
        }

        people_text.setText(peopleInView);

        Log.i(TAG, personName);
    }


}
