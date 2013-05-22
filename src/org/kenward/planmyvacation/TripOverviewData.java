package org.kenward.planmyvacation;

import java.util.List;

import org.kenward.planmyvacation.db.PMV_Datasource;
import org.kenward.planmyvacation.model.TripTable;

import android.widget.TextView;

public class TripOverviewData {
	public PMV_Datasource datasource;
	public MyAdapter adapter;
	public List<TripTable> values;
	public TextView empty;

	public TripOverviewData() {
	}
}