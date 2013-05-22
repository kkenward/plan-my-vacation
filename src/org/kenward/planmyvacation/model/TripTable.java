package org.kenward.planmyvacation.model;

import org.kenward.planmyvacation.DateHelper;
import org.kenward.planmyvacation.model.TripTable;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;


public class TripTable implements Parcelable {
	private static final String LOGTAG = "PMV";
	private long id = -1;
	private String tripname;
	private String location;
	private String startdate;
	private String enddate;
	private long days;
	private String purpose;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTripname() {
		return tripname;
	}
	public void setTripname(String tripname) {
		this.tripname = tripname;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public long getDays() {
		return days;
	}
	public void setDays() {
		DateHelper dhStart = new DateHelper(this.startdate);
		this.days = dhStart.howManyDays(); 
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	@Override
	public String toString() {
		return tripname + " - " + location + "\n" + startdate + " - " + enddate;
	}
	
    public TripTable() {
    }

    public TripTable(Parcel in) {
         Log.i(LOGTAG, "Parcel constructor");
        
         id = in.readLong();
         tripname = in.readString();
         location = in.readString();
         startdate = in.readString();
         enddate = in.readString();
         days = in.readLong();
         purpose = in.readString();
   }

    @Override
    public int describeContents() {
         return 0;
    }
   
    @Override
    public void writeToParcel(Parcel dest, int flags) {
         Log.i(LOGTAG, "writeToParcel");
        
         dest.writeLong(id);
         dest.writeString(tripname);
         dest.writeString(location);
         dest.writeString(startdate);
         dest.writeString(enddate);
         dest.writeLong(days);
         dest.writeString(purpose);
    }

    public static final Parcelable.Creator<TripTable> CREATOR =
              new Parcelable.Creator<TripTable>() {

         @Override
         public TripTable createFromParcel(Parcel source) {
              Log.i(LOGTAG, "createFromParcel");
              return new TripTable(source);
         }

         @Override
         public TripTable[] newArray(int size) {
              Log.i(LOGTAG, "newArray");
              return new TripTable[size];
         }

    };

}
