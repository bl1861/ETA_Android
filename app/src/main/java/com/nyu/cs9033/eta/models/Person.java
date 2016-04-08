package com.nyu.cs9033.eta.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    /**
     *  we add id and tripId in the Person.
     *  Each person in a trip should have their own id. we should store it.
     *  Each person is belonging to a trip. So we add store tripId
     */
	private long id;
	private long tripId;

	private String name;
	private String phoneNumber;
	private String emailAddress;
	private String currentLocation;
	private String facebookAccount;

	/**
	 * Parcelable creator. Do not modify this function.
	 */
	public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
		public Person createFromParcel(Parcel p) {
			return new Person(p);
		}

		public Person[] newArray(int size) {
			return new Person[size];
		}
	};

	/**
	 * Create a Person model object from a Parcel. This
	 * function is called via the Parcelable creator.
	 *
	 * @param p The Parcel used to populate the
	 * Model fields.
	 */
	public Person(Parcel p) {
		id = p.readInt();
		tripId = p.readInt();
		name = p.readString();
		phoneNumber = p.readString();
		emailAddress = p.readString();
		currentLocation = p.readString();
		facebookAccount = p.readString();

		// TODO - fill in here

	}

	/**
	 * Create a Person model object from arguments
	 *
	 * @param name Add arbitrary number of arguments to
	 * instantiate Person class based on member variables.
	 */


	public Person(long id, long tripId, String name, String phoneNumber, String emailAddress, String currentLocation, String facebookAccount) {
		this.id = id;
		this.tripId = tripId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.currentLocation = currentLocation;
		this.facebookAccount = facebookAccount;
		// TODO - fill in here, please note you must have more arguments here
	}

	public Person(){}

	/**
	 * Serialize Person object by using writeToParcel.  
	 * This function is automatically called by the
	 * system when the object is serialized.
	 *
	 * @param dest Parcel object that gets written on 
	 * serialization. Use functions to write out the
	 * object stored via your member variables. 
	 *
	 * @param flags Additional flags about how the object
	 * should be written. May be 0 or PARCELABLE_WRITE_RETURN_VALUE.
	 * In our case, you should be just passing 0.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeLong(tripId);
		dest.writeString(name);
		dest.writeString(phoneNumber);
		dest.writeString(emailAddress);
		dest.writeString(currentLocation);
		dest.writeString(facebookAccount);

		// TODO - fill in here 	
	}

	/**
	 * Feel free to add additional functions as necessary below.
	 */

	/**
	 * Do not implement
	 */
	@Override
	public int describeContents() {
		// Do not implement!
		return 0;
	}

	public long getId(){
		return id;
	}

	public long getTripId(){
		return tripId;
	}

	public String getName(){
		return name;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}
	public String getEmailAddress(){
		return emailAddress;
	}

	public String getCurrentLocation(){
		return currentLocation;
	}

	public String getFacebookAccount(){
		return facebookAccount;
	}

	public void setId(long id){
		this.id = id;
	}

	public void setTripId(long tripId){
		this.tripId = tripId;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public void setEmailAddress(String emailAddress){
		this.emailAddress = emailAddress;
	}

	public void setCurrentLocation(String currentLocation){
		this.currentLocation = currentLocation;
	}

	public void setFacebookAccount(String facebookAccount){
		this.facebookAccount = facebookAccount;
	}


}
