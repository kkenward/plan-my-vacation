package org.kenward.planmyvacation;

import java.util.List;

import org.kenward.planmyvacation.model.TripTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<TripTable> {

	Context context;
	List<TripTable> values;

	public MyAdapter(Context context, int resource, int textViewResourceId,
			List<TripTable> objects) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.values = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.trip_row, parent, false);

		TripTable trip = values.get(position);

		TextView tvName = (TextView) row.findViewById(R.id.name);
		TextView tvLocation = (TextView) row.findViewById(R.id.location);

		tvName.setText(trip.getTripname());
		tvLocation.setText(trip.getLocation());

		return row;
	}

}
