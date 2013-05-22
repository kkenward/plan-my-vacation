package org.kenward.planmyvacation.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class PackingListTable implements Parcelable {
	private static final String LOGTAG = "PMV";
	private long id;
	private String category;
	private String item;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	@Override
	public String toString() {
		return category + "-" + item;
	}
    public PackingListTable() {
    }

    public PackingListTable(Parcel in) {
         Log.i(LOGTAG, "Parcel constructor");
        
         id = in.readLong();
         category = in.readString();
         item = in.readString();
   }

    @Override
    public int describeContents() {
         return 0;
    }
   
    @Override
    public void writeToParcel(Parcel dest, int flags) {
         Log.i(LOGTAG, "writeToParcel");
        
         dest.writeLong(id);
         dest.writeString(category);
         dest.writeString(item);
    }

    public static final Parcelable.Creator<PackingListTable> CREATOR =
              new Parcelable.Creator<PackingListTable>() {

         @Override
         public PackingListTable createFromParcel(Parcel source) {
              Log.i(LOGTAG, "createFromParcel");
              return new PackingListTable(source);
         }

         @Override
         public PackingListTable[] newArray(int size) {
              Log.i(LOGTAG, "newArray");
              return new PackingListTable[size];
         }

    };

}
