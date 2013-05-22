package org.kenward.planmyvacation;

import java.util.Calendar;
import java.util.List;

import org.kenward.planmyvacation.db.PMV_Datasource;
import org.kenward.planmyvacation.model.TripTable;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.TextView;

public class TripActivity extends Activity {
	
	private static final String PREFS_NAME = "PMVPREFS";
	private static final String LOGTAG = "PMV";
	Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH);
	int day = c.get(Calendar.DAY_OF_MONTH);

	EditText etTripName;
	EditText etStartDate;
	EditText etEndDate;
	TextView tvDays;
	TextView tvID;

	PMV_Datasource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trip);
		
		etTripName = (EditText) findViewById(R.id.tripname);
		etStartDate = (EditText) findViewById(R.id.startdate);
		etEndDate = (EditText) findViewById(R.id.enddate);
		tvDays = (TextView) findViewById(R.id.days);
		tvID = (TextView) findViewById(R.id.tripID);
		
Log.i(LOGTAG, "about to open datasource");
		datasource = new PMV_Datasource(this);
		datasource.open();

		List<TripTable> ltTables = datasource.findAllTrips();
		if (ltTables.size() == 0) {
			Log.i(LOGTAG, "Triptable is empty.");
			//TODO createData();
			// ltTables = datasource.findAllTrips();
		}
Log.i(LOGTAG, "about to get shared settings");

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_MULTI_PROCESS);
		etTripName.setText(settings.getString("TRIPNAME", "Time to plan my vacation!"));
		int m = month + 1;
		etStartDate.setText(settings.getString("STARTDATE", m + "/" + day + "/" + year));
		etEndDate.setText(settings.getString("ENDDATE", m + "/" + day + "/" + year));
		// TODO calc days = startdate - current date
		tvDays.setText(settings.getString("DAYS", "0"));
		tvID.setText(settings.getString("TRIPID", "0"));

		Button btStart = (Button) findViewById(R.id.btStart);
		Button btEnd = (Button) findViewById(R.id.btEnd);

		btStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.i(LOGTAG, "startdate button clicked");
				showDatePickerDialog(etStartDate);
			}

		});

		btEnd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(LOGTAG, "enddate button clicked");
				showDatePickerDialog(etEndDate);
			}

		});
	}
	
// TODO improve date checking	
	public void showDatePickerDialog(final EditText et) {
		// Use the current date as the default date in the picker
		DatePickerDialog datePicker = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int month,
							int day) {
						int m = month + 1;
						et.setText(m + "/" + day + "/" + year);
					}
				}, year, month, day);
		datePicker.show();
	}

	@Override
	protected void onStop() {
		super.onStop();

		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		 EditText etTripName = (EditText) findViewById(R.id.tripname);
		 EditText etStartDate = (EditText) findViewById(R.id.startdate);
		 EditText etEndDate = (EditText) findViewById(R.id.enddate);
		 TextView tvDays = (TextView) findViewById(R.id.days);
		 TextView tvID = (TextView) findViewById(R.id.tripID);

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putString("TRIPNAME", etTripName.getText().toString());
		editor.putString("STARTDATE", etStartDate.getText().toString());
		editor.putString("ENDDATE", etEndDate.getText().toString());
		editor.putString("DAYS", tvDays.getText().toString());
		editor.putString("TRIPID", tvID.getText().toString());

		// Commit the edits!
		editor.commit();
	}

	@Override
	protected void onPause() {
		super.onPause();
		datasource.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		datasource.open();
	}

//	private void createData() {
		// declare new table object: Trip trip = new Trip();
		// set fields of table object:
		// call create: trip = datasource.create(trip);
		// Log.i(LOGTAG, "trip created with id. " + trip.getID());
		// TODO  in dbopenhelper
		// import from xml file create PMVPullParser class
		// put xml file in res/raw
		// PMVPullParser parser = new PMVPullParser();
		// List<MyTables> tables = parser.parseXML(this);
		// for (MyTables table : tables) {
		// datasource.create(table);
//	}
	
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.trip, menu);
	// return true;
	// }

}
