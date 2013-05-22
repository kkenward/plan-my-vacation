package org.kenward.planmyvacation;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.kenward.planmyvacation.db.PMV_DBOpenHelper;
import org.kenward.planmyvacation.db.PMV_Datasource;
import org.kenward.planmyvacation.model.TripTable;
import org.kenward.planmyvacation.DateHelper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
 * TripDetail allows user to enter a new trip 
 * or to edit an existing trip
 */

public class TripDetail extends Activity {

	private static final String LOGTAG = "PMV dtl";
	private static final String PREFS_NAME = "PMVPREFS";
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;
	private static int MODE = ACTIVITY_EDIT;

	private EditText mTripname;
	private EditText mLocation;
	private TextView mStartDate;
	private TextView mEndDate;
	private TextView mDays;
	private EditText mPurpose;

	GregorianCalendar calStart = new GregorianCalendar();
	Calendar c = Calendar.getInstance();
	int curYear = c.get(Calendar.YEAR);
	int curMonth = c.get(Calendar.MONTH);
	int curDay = c.get(Calendar.DAY_OF_MONTH);
	DateHelper dhCurrent = new DateHelper(curYear, curMonth, curDay);
	DateHelper dhStart = new DateHelper(curYear, curMonth, curDay);
	DateHelper dhEnd = new DateHelper(curYear, curMonth, curDay);
	
	TripTable trip;
	private long tripID = -1;
	PMV_Datasource datasource;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.trip_edit);

		datasource = new PMV_Datasource(this);
		datasource.open();

		mTripname = (EditText) findViewById(R.id.trip_edit_tripname);
		mLocation = (EditText) findViewById(R.id.trip_edit_location);
		mStartDate = (TextView) findViewById(R.id.trip_edit_start);
		mEndDate = (TextView) findViewById(R.id.trip_edit_end);
		mDays = (TextView) findViewById(R.id.trip_edit_days);
		mPurpose = (EditText) findViewById(R.id.trip_edit_purpose);

		Button startButton = (Button) findViewById(R.id.btStart);
		Button endButton = (Button) findViewById(R.id.btEnd);
		Button confirmButton = (Button) findViewById(R.id.trip_edit_button);

		// coming from 3 possibilities:
		// TripOverview.creatNewTrip
		// TripOverview.editTrip
		// or savedInstance
		Bundle extras = getIntent().getExtras();
		trip = extras.getParcelable(".model.TripTable");
		MODE = extras.getInt("MODE");
		tripID = trip.getId();
		if (MODE == ACTIVITY_EDIT) { // get data from trip otherwise leave UI blank
			fillDataDetail(tripID);
		}

		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (TextUtils.isEmpty(mTripname.getText().toString())) {
					makeToast();
				} else {
					setResult(RESULT_OK);
					finish();
				}
			}
		});

		startButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showStartDatePickerDialog();
			}
		});

		endButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showEndDatePickerDialog();
			}
		});
	}

	protected void showStartDatePickerDialog() {
		int year = dhStart.getDhYear();
		int month = dhStart.getDhMonth();
		int day = dhStart.getDhDay();
		DatePickerDialog datePicker = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int month, int day) {
						setStartDate(year, month, day);
					}
				}, year, month, day);
		datePicker.setTitle("Select Start Date");
		datePicker.show();
	}

	protected void showEndDatePickerDialog() {
		int year = dhEnd.getDhYear();
		int month = dhEnd.getDhMonth();
		int day = dhEnd.getDhDay();
		DatePickerDialog datePicker = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int month, int day) {
						setEndDate(year, month, day);
					}
				}, year, month, day);
		datePicker.setTitle("Select End Date");
		datePicker.show();		
	}
	
	private void setStartDate(int year, int month, int day) {
		dhStart.setDhYear(year);
		dhStart.setDhMonth(month);
		dhStart.setDhDay(day);
		String days = dhStart.howManyDays() + " Days";
		mDays.setText(days);
		mStartDate.setText(dhStart.toString());
		dhEnd = dhStart;
	}

	private void setEndDate(int year, int month, int day) {
		dhEnd.setDhYear(year);
		dhEnd.setDhMonth(month);
		dhEnd.setDhDay(day);
		mEndDate.setText(dhEnd.toString());
	}

	private void fillDataDetail(long tripID) {
		mTripname.setText(trip.getTripname());
		mLocation.setText(trip.getLocation());
		dhStart = new DateHelper(trip.getStartdate());
		mStartDate.setText(dhStart.toString());
		dhEnd = new DateHelper(trip.getEnddate());
		mEndDate.setText(dhEnd.toString());
		mPurpose.setText(trip.getPurpose());
		String days = dhStart.howManyDays() + " Days";
		mDays.setText(days);
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
	}

	private void saveState() {
		TripTable svtrip = new TripTable();
		saveText(svtrip);
		datasource.open();
		if (tripID == -1) {
			// New trip
			svtrip = datasource.createTrip(svtrip);
			Log.i(LOGTAG, "trip created with id. " + svtrip.getId());
		} else {
			// Update trip
			svtrip.setId(tripID);
			boolean success = datasource.updateTrip(svtrip);
			if (success) {
				Log.i(LOGTAG, "trip updated. " + svtrip.getId());
			} else {
				Log.i(LOGTAG, "trip failed to update. " + svtrip.getId());
			}
		}
		savePrefs(svtrip);
	}

	private void saveText(TripTable svtrip) {
		svtrip.setTripname(mTripname.getText().toString());
		svtrip.setLocation(mLocation.getText().toString());
		svtrip.setStartdate(mStartDate.getText().toString());
		svtrip.setEnddate(mEndDate.getText().toString());
		svtrip.setPurpose(mPurpose.getText().toString());
	}

	private void savePrefs(TripTable svtrip) {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_MULTI_PROCESS);
		SharedPreferences.Editor editor = settings.edit();

		editor.putString("TRIPNAME", mTripname.getText().toString());
		editor.putString("DAYS", mDays.getText().toString());
		editor.putLong("TRIPID", svtrip.getId());

		editor.commit();
	}

	private void makeToast() {
		Toast.makeText(TripDetail.this, "Please enter a name for this trip.",
				Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
		datasource.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		datasource.open();
	}

}
