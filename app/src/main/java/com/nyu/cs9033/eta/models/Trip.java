package com.nyu.cs9033.eta.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class Trip implements Parcelable {

    private static final String TAG = "Trip class";

	/**
	 * 	 id is used to record the key in the database
	 */
    private long id;

	private String name;
    private String destination;
	private Date startDate;
	private Date endDate;

	/**
	 * Parcelable creator. Do not modify this function.
	 */
	public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {
		public Trip createFromParcel(Parcel p) {
			return new Trip(p);
		}

		public Trip[] newArray(int size) {
			return new Trip[size];
		}
	};

	/**
	 * Create a Trip model object from a Parcel. This
	 * function is called via the Parcelable creator.
	 *
	 * @param p The Parcel used to populate the
	 * Model fields.
	 */
	public Trip(Parcel p) {
        id = p.readLong();
		name = p.readString();
        destination = p.readString();
		startDate = new Date(p.readLong());
		endDate = new Date(p.readLong());
		// TODO - fill in here
	}

	/**
	 * Create a Trip model object from arguments
	 *
	 * @param name  Add arbitrary number of arguments to
	 * instantiate Trip class based on member variables.
	 */

	public Trip(Long id, String name, String destination, Date startDate, Date endDate) {
        this.id = id;
		this.name = name;
        this.destination = destination;
		this.startDate = startDate;
		this.endDate = endDate;
		// TODO - fill in here, please note you must have more arguments here
	}

    public Trip(){
    }

	/**
	 * Serialize Trip object by using writeToParcel. 
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
		dest.writeString(name);
        dest.writeString(destination);
		dest.writeLong(startDate.getTime());
		dest.writeLong(endDate.getTime());
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

    public Long getId(){
        return id;
    }

	public String getName(){
		return name;
	}

    public String getDestination(){
        return destination;
    }

	public Date getStartDate(){
		return startDate;
	}

	public Date getEndDate(){
		return endDate;
	}

	public void setId(Long id){
		this.id = id;
	}

	public void setName(String name){
		this.name = name;
	}

    public void setDestination(String destination) {
        this.destination = destination;
    }

	public void setStartDate(Date date){
		this.startDate = date;
	}

	public void setEndDate(Date date){
		this.endDate = date;
	}


}
