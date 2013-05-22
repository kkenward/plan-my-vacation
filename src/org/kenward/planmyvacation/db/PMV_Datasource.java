package org.kenward.planmyvacation.db;

import java.util.ArrayList;
import java.util.List;

import org.kenward.planmyvacation.model.PackingListTable;
import org.kenward.planmyvacation.model.TodoListTable;
import org.kenward.planmyvacation.model.TripTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PMV_Datasource {

	SQLiteOpenHelper dbHelper;
	SQLiteDatabase database;

	private static final String LOGTAG = "PMV ds";

	public static final String[] tripColumns = { PMV_DBOpenHelper.COLUMN_ID,
			PMV_DBOpenHelper.COLUMN_TRIPNAME, PMV_DBOpenHelper.COLUMN_LOCATION,
			PMV_DBOpenHelper.COLUMN_STARTDATE, PMV_DBOpenHelper.COLUMN_ENDDATE,
			PMV_DBOpenHelper.COLUMN_PURPOSE };
	public static final String[] packColumns = { PMV_DBOpenHelper.COLUMN_ID,
			PMV_DBOpenHelper.COLUMN_CATEGORY, PMV_DBOpenHelper.COLUMN_ITEM };
	public static final String[] todoColumns = packColumns;

	public PMV_Datasource(Context context) {
		dbHelper = new PMV_DBOpenHelper(context);
	}

	public void open() {
		database = dbHelper.getWritableDatabase();
		Log.i(LOGTAG, "Database opened.");
	}

	public void close() {
		dbHelper.close();
		Log.i(LOGTAG, "Database closed.");
	}

	public TripTable createTrip(TripTable table) {
		ContentValues values = new ContentValues();
		values.put(PMV_DBOpenHelper.COLUMN_TRIPNAME, table.getTripname());
		values.put(PMV_DBOpenHelper.COLUMN_LOCATION, table.getLocation());
		values.put(PMV_DBOpenHelper.COLUMN_STARTDATE, table.getStartdate());
		values.put(PMV_DBOpenHelper.COLUMN_ENDDATE, table.getEnddate());
		values.put(PMV_DBOpenHelper.COLUMN_PURPOSE, table.getPurpose());
		Long insertID = database.insert(PMV_DBOpenHelper.TABLE_TRIP, null,
				values);
		table.setId(insertID);
		return table;
	}

	public PackingListTable createPack(PackingListTable table) {
		ContentValues values = new ContentValues();
		values.put(PMV_DBOpenHelper.COLUMN_CATEGORY, table.getCategory());
		values.put(PMV_DBOpenHelper.COLUMN_ITEM, table.getItem());
		Long insertID = database.insert(PMV_DBOpenHelper.TABLE_PACK, null,
				values);
		table.setId(insertID);
		return table;
	}

	public TodoListTable createTodo(TodoListTable table) {
		ContentValues values = new ContentValues();
		values.put(PMV_DBOpenHelper.COLUMN_CATEGORY, table.getCategory());
		values.put(PMV_DBOpenHelper.COLUMN_ITEM, table.getItem());
		Long insertID = database.insert(PMV_DBOpenHelper.TABLE_TODO, null, values);
		table.setId(insertID);
		return table;
	}

	public List<TripTable> findAllTrips() {
		Cursor c = database.query(PMV_DBOpenHelper.TABLE_TRIP, tripColumns,
				null, null, null, null, null);
		List<TripTable> tables = cursorToListTrip(c);
		return tables;
	}

	public List<PackingListTable> findAllPackItems() {
		Cursor c = database.query(PMV_DBOpenHelper.TABLE_PACK, packColumns,
				null, null, null, null, null);
		Log.i(LOGTAG, c.getCount() + " rows in findAllPackItems");
		List<PackingListTable> tables = cursorToListPack(c);
		return tables;
	}

	public List<TodoListTable> findAllTodoItems() {
		Cursor c = database.query(PMV_DBOpenHelper.TABLE_TODO, todoColumns,
				null, null, null, null, null);
		Log.i(LOGTAG, c.getCount() + " row in findAllTodoItems");
		List<TodoListTable> tables = cursorToListTodo(c);
		return tables;
	}

	public List<TripTable> findFilteredTrip(String selection, String orderBy) {
		Cursor c = database.query(PMV_DBOpenHelper.TABLE_TRIP, tripColumns,
				selection, null, null, null, orderBy);
		Log.i(LOGTAG, c.getCount() + " rows in findFilteredTrip");
		List<TripTable> tables = cursorToListTrip(c);
		return tables;
	}

	private List<TripTable> cursorToListTrip(Cursor c) {
		List<TripTable> alTables = new ArrayList<TripTable>();
		if (c.getCount() > 0) {
			while (c.moveToNext()) {
				TripTable table = new TripTable();
				table.setId(c.getLong(c
						.getColumnIndex(PMV_DBOpenHelper.COLUMN_ID)));
				table.setTripname(c.getString(c
						.getColumnIndex(PMV_DBOpenHelper.COLUMN_TRIPNAME)));
				table.setLocation(c.getString(c
						.getColumnIndex(PMV_DBOpenHelper.COLUMN_LOCATION)));
				table.setStartdate(c.getString(c
						.getColumnIndex(PMV_DBOpenHelper.COLUMN_STARTDATE)));
				table.setEnddate(c.getString(c
						.getColumnIndex(PMV_DBOpenHelper.COLUMN_ENDDATE)));
				table.setPurpose(c.getString(c
						.getColumnIndex(PMV_DBOpenHelper.COLUMN_PURPOSE)));
				alTables.add(table);
			}
		}
		return alTables;
	}
	
//	private List<String> cursorToStringListTrip(Cursor c) {
//		
//		if (c.getCount() > 0) {
//			while (c.moveToNext())	{
//				
//			}
//		}
//		// TODO Auto-generated method stub
//		return null;
//	}

	private List<PackingListTable> cursorToListPack(Cursor c) {
		List<PackingListTable> alTables = new ArrayList<PackingListTable>();
		if (c.getCount() > 0) {
			while (c.moveToNext()) {
				PackingListTable table = new PackingListTable();
				table.setId(c.getLong(c
						.getColumnIndex(PMV_DBOpenHelper.COLUMN_ID)));
				table.setCategory(c.getString(c
						.getColumnIndex(PMV_DBOpenHelper.COLUMN_CATEGORY)));
				table.setCategory(c.getString(c
						.getColumnIndex(PMV_DBOpenHelper.COLUMN_ITEM)));
				alTables.add(table);
			}
		}
		return alTables;
	}

	private List<TodoListTable> cursorToListTodo(Cursor c) {
		List<TodoListTable> alTables = new ArrayList<TodoListTable>();
		if (c.getCount() > 0) {
			while (c.moveToNext()) {
				TodoListTable table = new TodoListTable();
				table.setId(c.getLong(c
						.getColumnIndex(PMV_DBOpenHelper.COLUMN_ID)));
				table.setCategory(c.getString(c
						.getColumnIndex(PMV_DBOpenHelper.COLUMN_CATEGORY)));
				table.setCategory(c.getString(c
						.getColumnIndex(PMV_DBOpenHelper.COLUMN_ITEM)));
				alTables.add(table);
			}
		}
		return alTables;
	}

	public boolean updateTrip(TripTable trip) {
		ContentValues values = new ContentValues();
		values.put(PMV_DBOpenHelper.COLUMN_TRIPNAME, trip.getTripname());
		values.put(PMV_DBOpenHelper.COLUMN_LOCATION, trip.getLocation());
		values.put(PMV_DBOpenHelper.COLUMN_STARTDATE, trip.getStartdate());
		values.put(PMV_DBOpenHelper.COLUMN_ENDDATE, trip.getEnddate());
		values.put(PMV_DBOpenHelper.COLUMN_PURPOSE, trip.getPurpose());
 	
		String where = PMV_DBOpenHelper.COLUMN_ID + "=" + trip.getId();
		int result = database.update(PMV_DBOpenHelper.TABLE_TRIP, values, where, null );
		return (result==1);
	}

	public boolean removeTrip(TripTable trip) {
		String where = PMV_DBOpenHelper.COLUMN_ID + "=" + trip.getId();
		int result = database.delete(PMV_DBOpenHelper.TABLE_TRIP, where, null);
		return (result == 1);
	}

}
