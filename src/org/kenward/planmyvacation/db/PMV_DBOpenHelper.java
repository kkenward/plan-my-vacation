package org.kenward.planmyvacation.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PMV_DBOpenHelper extends SQLiteOpenHelper {
	private static final String LOGTAG = "PMV";

	// Database
	public static final String DBNAME = "planmyvacation";
	public static final int DBVERSION = 1;

	// Database tables
	public static final String TABLE_TRIP = "triptable";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TRIPNAME = "tripname";
	public static final String COLUMN_LOCATION = "location";
	public static final String COLUMN_STARTDATE = "begindate";
	public static final String COLUMN_ENDDATE = "enddate";
	public static final String COLUMN_PURPOSE = "purpose";
	public static final String TABLE_PACK = "packlisttable";
	public static final String TABLE_TODO = "todolisttable";
	public static final String COLUMN_CATEGORY = "category";
	public static final String COLUMN_ITEM = "item";

	// Database creation SQL statement
	private static final String TABLE_CREATE_TRIP = "create table "
			+ TABLE_TRIP + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_TRIPNAME
			+ " text not null, " + COLUMN_LOCATION + " text, "
			+ COLUMN_STARTDATE + " text, " + COLUMN_ENDDATE + " text, "
			+ COLUMN_PURPOSE + " text" + ");";
	private static final String TABLE_CREATE_PACK = "create table "
			+ TABLE_PACK + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_CATEGORY
			+ " text not null, " + COLUMN_ITEM + " text not null" + ");";
	private static final String TABLE_CREATE_TODO = "create table "
			+ TABLE_TODO + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_CATEGORY
			+ " text not null, " + COLUMN_ITEM + " text not null" + ");";

	public PMV_DBOpenHelper(Context context) {
		super(context, DBNAME, null, DBVERSION);
	}

	// My code never calls onCreate or OnUpgrade directly outside of this class
	@Override
	public void onCreate(SQLiteDatabase db) {
		// only called by androidSDK if db does not exist
		db.execSQL(TABLE_CREATE_TRIP);
		db.execSQL(TABLE_CREATE_PACK);
		db.execSQL(TABLE_CREATE_TODO);
		// TODO code to populate with data
		Log.i(LOGTAG, "Table created " + TABLE_CREATE_TRIP);
		Log.i(LOGTAG, "Table created " + TABLE_CREATE_PACK);
		Log.i(LOGTAG, "Table created " + TABLE_CREATE_TODO);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// only called by androidSDK if VERSION has changed
		Log.w(DBNAME, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PACK);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
		Log.i(LOGTAG, "Tables dropped");
		onCreate(db);
	}
}
