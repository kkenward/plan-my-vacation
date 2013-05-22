package org.kenward.planmyvacation.widget;

import java.util.ArrayList;

import org.kenward.planmyvacation.R;
import org.kenward.planmyvacation.R.id;
import org.kenward.planmyvacation.R.layout;
import org.kenward.planmyvacation.R.string;
import org.kenward.planmyvacation.db.PMV_DBOpenHelper;
import org.kenward.planmyvacation.model.TripTable;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PMVWdgConfigure extends ListActivity {
// TODO get keys from resource strings
	private static final String LOGCAT = "PMV";

	private static final String PREF_DAYS_KEY = "DAYS";
    private static final String PREF_TRIPNAME_KEY = "TRIPNAME";
    private static final String PREF_STARTDATE_KEY = "STARTDATE";
    private static final String PREF_ENDDATE_KEY = "ENDDATE";
    private static final String PREF_TRIPID_KEY = "TRIPID";
    
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    
    // private Cursor cursor;
    private SimpleCursorAdapter adapter;

    TextView etDays;
    TextView etTripname;
    TextView etStartdate;
    TextView etEnddate;
    TextView etTripID;
    

    public PMVWdgConfigure() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);
// TODO
        // Set the view layout resource to use.
        setContentView(R.layout.pmv_appwidg_config);

        // Find the EditTexts  Listview
//        etDays = (TextView)findViewById(R.id.wdg_cfg_Days);
//        etTripname = (TextView)findViewById(R.id.wdg_cfg_Tripname);
//        etStartdate = (TextView)findViewById(R.id.wdg_cfg_Startdate);
//        etEnddate = (TextView)findViewById(R.id.wdg_cfg_Enddate);
//        etTripID = (TextView)findViewById(R.id.wdg_cfg_TripID);

        // Bind the action for the save button.
//        findViewById(R.id.btSave).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent. 
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
        
        fillData();

        //String[] trip = loadPrefs(PMVWdgConfigure.this, mAppWidgetId);
//        etDays.setText(trip[0]);
//        etTripname.setText(trip[1]);
//        etStartdate.setText(trip[2]);
//        etEnddate.setText(trip[3]);
//        etTripID.setText(trip[4]);
        //listview
        Log.i(LOGCAT, "Widget config loaded.");
        
    }
    
    // Saves prefs if an entry is clicked
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
      super.onListItemClick(l, v, position, id);
      final Context context = PMVWdgConfigure.this;

      // When the button is clicked, save the string in our prefs and return that they
      // clicked OK.
      String[] trip = { "", "", "", "", "" };
      trip[0]= etDays.getText().toString();
      trip[1]= etTripname.getText().toString();
      trip[2]= etStartdate.getText().toString();
      trip[3]= etEnddate.getText().toString();
      trip[4]= etTripID.getText().toString();
      
      savePrefs(context, mAppWidgetId, trip);
      
      // Push widget update to surface with newly set prefix
      AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
      PMVWdgProvider.updateAppWidget(context, appWidgetManager, mAppWidgetId);
      Log.i(LOGCAT, "Widget config selected and updated.");
      
      // Make sure we pass back the original appWidgetId
      Intent resultValue = new Intent();
      resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
      setResult(RESULT_OK, resultValue);
      finish();
    }

    private void fillData() {
        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { 
        		PMV_DBOpenHelper.COLUMN_ID,
        		PMV_DBOpenHelper.COLUMN_TRIPNAME,
        		PMV_DBOpenHelper.COLUMN_LOCATION,
        		PMV_DBOpenHelper.COLUMN_STARTDATE,
        		PMV_DBOpenHelper.COLUMN_ENDDATE};
        
        // Fields on the UI to which we map
        int[] to = new int[] { 
        		R.id.etID, 
        		R.id.tvTripname, 
        		R.id.tvLocation, 
        		R.id.etStartdate, 
        		R.id.etEnddate };

//        getLoaderManager().initLoader(0, null, this);
//        adapter = new SimpleCursorAdapter(this, R.layout.pmv_appwidg_row, null, from, to, 0);
//
//        setListAdapter(adapter);
		
	}

//	View.OnClickListener mOnClickListener = new View.OnClickListener() {
//        public void onClick(View v) {
//            final Context context = PMVWdgConfigure.this;
//
//            // When the button is clicked, save the string in our prefs and return that they
//            // clicked OK.
//            String[] trip = { "", "", "", "", "" };
//            trip[0]= etDays.getText().toString();
//            trip[1]= etTripname.getText().toString();
//            trip[2]= etStartdate.getText().toString();
//            trip[3]= etEnddate.getText().toString();
//            trip[4]= etTripID.getText().toString();
//            
//            savePrefs(context, mAppWidgetId, trip);
//
//            // Push widget update to surface with newly set prefix
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            PMVWdgProvider.updateAppWidget(context, appWidgetManager, mAppWidgetId);
//            Log.i(LOGCAT, "Widget config selected and updated.");
//            
//            // Make sure we pass back the original appWidgetId
//            Intent resultValue = new Intent();
//            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
//            setResult(RESULT_OK, resultValue);
//            finish();
//        }
//    };

    // Write to the SharedPreferences object for this widget
    static void savePrefs(Context context, int appWidgetId, String[] text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(context.getString(R.string.prefs_filename), 0).edit();
        prefs.putString(PREF_DAYS_KEY, text[0]);
        prefs.putString(PREF_TRIPNAME_KEY, text[1]);
        prefs.putString(PREF_STARTDATE_KEY, text[2]);
        prefs.putString(PREF_ENDDATE_KEY, text[3]);
        prefs.putString(PREF_TRIPID_KEY, text[4]);
        
        prefs.commit();
        Log.i(LOGCAT, "Save shared prefs.");
    }

    // Read from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String[] loadPrefs(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.prefs_filename), 0);
        String days = prefs.getString(PREF_DAYS_KEY, null);
        String tripname = prefs.getString(PREF_TRIPNAME_KEY, null);
        String startdate = prefs.getString(PREF_STARTDATE_KEY, null);
        String enddate = prefs.getString(PREF_ENDDATE_KEY, null);
        String tripID = prefs.getString(PREF_TRIPID_KEY, null);
        
        if ((days != null) && (tripname != null)) {
        	String[] trip = { days, tripname, startdate, enddate, tripID };
        	Log.i(LOGCAT, "Retrieve saved prefs.");
        	return trip;
        } else {
        	days = context.getString(R.string.wdg_days_default);
        	tripname = context.getString(R.string.wdg_tripname_default);
        	String[] trip = { days, tripname, startdate, enddate, tripID };
        	Log.i(LOGCAT, "Use default prefs.");
            return trip;
        }
    }

    static void deletePrefs(Context context, int appWidgetId) {
    	// do not want to remove prefs yet
    }

    // Creates a new loader after the initLoader () call
//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//      String[] projection = {
//    		  PMV_DBOpenHelper.COLUMN_ID,
//    		  PMV_DBOpenHelper.COLUMN_TRIPNAME,
//    		  PMV_DBOpenHelper.COLUMN_LOCATION,
//    		  PMV_DBOpenHelper.COLUMN_STARTDATE,
//    		  PMV_DBOpenHelper.COLUMN_ENDDATE };
//      CursorLoader cursorLoader = new CursorLoader(this, PMV_ContentProvider.CONTENT_URI, projection, null, null, null);
//      return cursorLoader;
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//      adapter.swapCursor(data);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//      // data is not available anymore, delete reference
//      adapter.swapCursor(null);
//    }

//    static void loadAllPrefs(Context context, ArrayList<Integer> appWidgetIds,
//    		ArrayList<String> alDays, ArrayList<String> alTripnames) {
//    		// assuming only 1 appwidget
//    		String[] trip = loadPrefs(context, appWidgetIds.get(0));
//    		alDays.set(0, trip[0]);
//    		alTripnames.set(0, trip[1]);
//    		
//    	// TODO
//    }

}
