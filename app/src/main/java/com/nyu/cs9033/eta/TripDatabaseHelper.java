package com.nyu.cs9033.eta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nyu.cs9033.eta.models.Location;
import com.nyu.cs9033.eta.models.Person;
import com.nyu.cs9033.eta.models.Trip;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Bing on 2016/3/31.
 */

public class TripDatabaseHelper extends SQLiteOpenHelper{

    private static final String TAG = "TripDatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "trips";

    private static final String TABLE_TRIP = "trip";
    private static final String COLUMN_TRIP_ID = "_id";
    private static final String COLUMN_TRIP_NAME = "name";
    private static final String COLUMN_TRIP_DESTINATION = "destination";
    private static final String COLUMN_TRIP_START_DATE = "start_date";
    private static final String COLUMN_TRIP_END_DATE = "end_date";

    private static final String TABLE_LOCATION = "location";
    private static final String COLUMN_LOC_TRIP_ID = "trip_id";
    private static final String COLUMN_LOC_NAME = "name";
    private static final String COLUMN_LOC_ADDRESS = "address";
    private static final String COLUMN_LOC_LAT = "latititude";
    private static final String COLUMN_LOC_LONG = "longitude";

    private static final String TABLE_PERSON = "person";
    private static final String COLUMN_PER_ID = "p_id";
    private static final String COLUMN_PER_TRIP_ID = "trip_id";
    private static final String COLUMN_PER_NAME = "name";
    private static final String COLUMN_PER_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_PER_EMAIL_ADDRESS = "email_address";
    private static final String COLUMN_PER_CURRENT_LOCATION = "current_location";
    private static final String COLUMN_PER_FACEBOOK_ACCOUNT = "facebook_account";


    public TripDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        // create trip table
        db.execSQL("create table " + TABLE_TRIP + "("
                + COLUMN_TRIP_ID + " integer primary key autoincrement, "
                + COLUMN_TRIP_NAME + " text, "
                + COLUMN_TRIP_DESTINATION + " text, "
                + COLUMN_TRIP_START_DATE + " integer, "
                + COLUMN_TRIP_END_DATE + " integer)"
        );

        db.execSQL("create table " + TABLE_LOCATION + "("
                + COLUMN_LOC_TRIP_ID + " integer references trip(_id), "
                + COLUMN_LOC_NAME + " test, "
                + COLUMN_LOC_ADDRESS + " text, "
                + COLUMN_LOC_LAT + " real, "
                + COLUMN_LOC_LONG + " real)"
        );

        db.execSQL("create table " + TABLE_PERSON + "("
                        + COLUMN_PER_ID + " integer primary key autoincrement, "
                        + COLUMN_PER_TRIP_ID + " integer, "
                        + COLUMN_PER_NAME + " text, "
                        + COLUMN_PER_PHONE_NUMBER + " text, "
                        + COLUMN_PER_EMAIL_ADDRESS + " text, "
                        + COLUMN_PER_CURRENT_LOCATION + " text, "
                        + COLUMN_PER_FACEBOOK_ACCOUNT + " text)"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);

        // create table again
        onCreate(db);
    }

    public long insertTrip(Trip trip){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TRIP_NAME, trip.getName());
        cv.put(COLUMN_TRIP_DESTINATION, trip.getDestination());
        cv.put(COLUMN_TRIP_START_DATE, trip.getStartDate().getTime());
        cv.put(COLUMN_TRIP_END_DATE, trip.getEndDate().getTime());

        Log.d(TAG, "inserting trip goes well.");

        // return id of new location
        return getWritableDatabase().insert(TABLE_TRIP, null, cv);
    }

    public long insertLocation(long tripId, Location location){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LOC_TRIP_ID, tripId);
        cv.put(COLUMN_LOC_NAME, location.getName());
        cv.put(COLUMN_LOC_ADDRESS, location.getAddress());
        cv.put(COLUMN_LOC_LAT, location.getLatitude());
        cv.put(COLUMN_LOC_LONG, location.getLongitude());

        Log.d(TAG, "inserting location goes well.");

        // reuturn id of new location
        return getWritableDatabase().insert(TABLE_LOCATION, null, cv);
    }

    public long insertPerson(long tripId, Person person){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PER_TRIP_ID, tripId);
        cv.put(COLUMN_PER_NAME, person.getName());
        cv.put(COLUMN_PER_PHONE_NUMBER, person.getPhoneNumber());
        cv.put(COLUMN_PER_EMAIL_ADDRESS, person.getEmailAddress());
        cv.put(COLUMN_PER_CURRENT_LOCATION, person.getCurrentLocation());
        cv.put(COLUMN_PER_FACEBOOK_ACCOUNT, person.getFacebookAccount());

        Log.d(TAG, "inserting person goes well.");

        // return id of person
        return getWritableDatabase().insert(TABLE_PERSON, null, cv);
    }

    public ArrayList<Trip> getAllTrips(){
        ArrayList<Trip> tripList = new ArrayList<Trip>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_TRIP, null);

        // loop through all query results
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Trip trip = new Trip();
            trip.setId(cursor.getLong(0));
            trip.setName(cursor.getString(1));
            trip.setDestination(cursor.getString(2));
            trip.setStartDate(new Date(cursor.getLong(3)));
            trip.setEndDate(new Date(cursor.getLong(3)));
            tripList.add(trip);
        }

        Log.d(TAG, "getAllTrip goes well");

        return tripList;
    }

    public Trip getTrip(long trip_id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_TRIP + " WHERE "
                + COLUMN_TRIP_ID + " = " + String.format("%d", trip_id), null);

        Trip trip = new Trip();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            trip.setId(cursor.getLong(0));
            trip.setName(cursor.getString(1));
            trip.setDestination(cursor.getString(2));
            trip.setStartDate(new Date(cursor.getLong(3)));
            trip.setEndDate(new Date(cursor.getLong(3)));
        }

        Log.d(TAG, "getTrip goes well");

        return trip;
    }

    public Location getLocation(long trip_id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_LOCATION + " WHERE "
                + COLUMN_LOC_TRIP_ID + " = " + trip_id, null);
        Location location = new Location();

        for(cursor.moveToNext(); !cursor.isAfterLast(); cursor.moveToNext()){
            location.setName(cursor.getString(1));
            location.setAddress(cursor.getString(2));
            location.setLatitude(cursor.getDouble(3));
            location.setLongitude(cursor.getDouble(4));
        }

        Log.d(TAG, "getLocation goes well");

        return location;

    }

    public ArrayList<Person> getPeopleFromTrip(Trip trip){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_PERSON + " WHERE "
                + COLUMN_PER_TRIP_ID + " = " + String.format("%d", trip.getId()), null);

        ArrayList<Person> personList = new ArrayList<Person>();

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            Person person = new Person();
            person.setId(cursor.getLong(0));
            person.setTripId(cursor.getLong(1));
            person.setName(cursor.getString(2));
            person.setPhoneNumber(cursor.getString(3));
            person.setCurrentLocation(cursor.getString(4));
            person.setFacebookAccount(cursor.getString(5));

            personList.add(person);
        }

        return personList;
    }


    public boolean deleteTrip(Trip trip){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isDeletePeople = deletePeoepleInTrip(trip);
        if(isDeletePeople){
            Log.i(TAG, "delete people successfully");
        }else{
            Log.e(TAG, "fail to delete people");
        }

        boolean isDeleteLocation = deleteLocationInTrip(trip);
        if(isDeleteLocation){
            Log.i(TAG, "delete location successfully");
        }else{
            Log.e(TAG, "fail to delete location");
        }

        return getWritableDatabase().delete(TABLE_TRIP, COLUMN_TRIP_ID + "=" + trip.getId(), null) > 0;
    }

    private boolean deleteLocationInTrip(Trip trip){
        return getWritableDatabase().delete(TABLE_LOCATION, COLUMN_LOC_TRIP_ID + "=" + trip.getId(), null) > 0;
    }

    private boolean deletePeoepleInTrip(Trip trip){
        return getWritableDatabase().delete(TABLE_PERSON, COLUMN_PER_TRIP_ID + "=" + trip.getId(), null) > 0;
    }


}
