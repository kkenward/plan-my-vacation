package org.kenward.planmyvacation;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.kenward.planmyvacation.db.PMV_DBOpenHelper;
import org.kenward.planmyvacation.db.PMV_Datasource;
import org.kenward.planmyvacation.model.TripTable;

/*
 * TripOverview displays the existing trips
 * in a list
 * 
 * You can create new ones via the  "Insert" button
 * You can delete existing ones via a long press on the item
 */

public class TripOverview extends ListActivity {

	private static final String LOGTAG = "PMV ovr";

	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;
	private static final int DELETE_ID = Menu.FIRST + 1;

	TripOverviewData data = new TripOverviewData();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trip_list);
		this.getListView().setDividerHeight(2);
		registerForContextMenu(getListView());
		
		data.datasource = new PMV_Datasource(this);
		data.datasource.open();
		data.values = data.datasource.findAllTrips();
		if (data.values.size() == 0) {
			createNewTrip();
//			data.values = data.datasource.findAllTrips();
		}
		refreshDisplay();
	}

	// insert a new trip
	private void createNewTrip() {
		Intent i = new Intent(this, TripDetail.class);
		i.putExtra("MODE", ACTIVITY_CREATE);
		i.putExtra(".model.TripTable", new TripTable());
		startActivityForResult(i, ACTIVITY_CREATE);
	}

	private void editTrip(TripTable trip) {
		Intent i = new Intent(this, TripDetail.class);
		i.putExtra("MODE", ACTIVITY_EDIT);
		i.putExtra(".model.TripTable", trip);
		startActivityForResult(i, ACTIVITY_EDIT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		refreshDisplay();
	}

	public void refreshDisplay() {
		data.datasource.open();
		data.values = data.datasource.findAllTrips();
		data.adapter = new MyAdapter(this, R.layout.trip_row, R.id.name, data.values);
		setListAdapter(data.adapter);
	}

	// Opens the second activity if an entry is clicked
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		TripTable selected = data.adapter.getItem(position);
		//long myid = selected.getId();
		editTrip(selected);
		refreshDisplay();
	}

	// Reaction to the menu selection
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.insert:
			createNewTrip(); // insert a new trip
			refreshDisplay();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			TripTable selected = data.adapter.getItem(info.position);
			data.datasource.open();
			boolean success = data.datasource.removeTrip(selected);
			if (success) {
				Log.i(LOGTAG, "trip removed successfully. " + selected.getId());
			} else {
				Log.i(LOGTAG, "trip failed to be removed. " + selected.getId());
			}
			refreshDisplay();
			break;
		}
		return super.onContextItemSelected(item);
	}

	// Context menu comes up on long press to delete
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, "Delete Trip");
	}

	// Create the menu based on the XML definition
	// XML defines one menu item as INSERT
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.listmenu, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		data.datasource.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		data.datasource.open();
	}
}

// private class MyAdapter extends ArrayAdapter<TripTable> {
// private final Context context;
// private final List<TripTable> values;
//
// public MyAdapter(Context context, int resource, int textViewResourceId,
// List<TripTable> objects) {
// super(context, resource, textViewResourceId, objects);
// this.context = context;
// this.values = objects;
// }
//
// @Override
// public View getView(int position, View convertView, ViewGroup parent){
// LayoutInflater inflater = (LayoutInflater)
// getSystemService(Context.LAYOUT_INFLATER_SERVICE);
// View row = inflater.inflate(R.layout.trip_row, parent, false);
// List<TripTable> items = data.datasource.findAllTrips();
//
// TextView tvName = (TextView) row.findViewById(R.id.name);
// TextView tvLocation = (TextView) row.findViewById(R.id.location);
//
// tvName.setText(items.get(position).getTripname().toString());
// tvLocation.setText(items.get(position).getLocation().toString());
//
// return row;
// }
// }

// private void fillDataOverview() {
// data.values = data.datasource.findAllTrips();
// if (!data.values.isEmpty()) {
// Log.i(LOGTAG + "fill", data.values.toString());
//
// //data.adapter.notifyDataSetChanged();
// }
// }
