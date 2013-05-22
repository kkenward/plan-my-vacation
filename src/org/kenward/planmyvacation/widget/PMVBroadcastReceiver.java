package org.kenward.planmyvacation.widget;

import java.util.ArrayList;

import org.kenward.planmyvacation.widget.PMVWdgProvider;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * A BroadcastReceiver that listens for updates for the AppWidgetProvider.  This
 * BroadcastReceiver starts off disabled, and we only enable it when there is a widget
 * instance created, in order to only receive notifications when we need them.
 */

public class PMVBroadcastReceiver extends BroadcastReceiver {

	private static final String LOGCAT = "PMV";

	@Override
	public void onReceive(Context context, Intent intent) {
        // Listen for date_changed broadcasts to update widget(s)
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_DATE_CHANGED)) {
            AppWidgetManager gm = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = gm.getAppWidgetIds(new ComponentName("org.kenward", ".planmyvacation.PMVBroadcastReceiver"));
//            ArrayList<String> alDays = new ArrayList<String>();
//            ArrayList<String> alTripnames = new ArrayList<String>();

//           PMVWdgConfigure.loadAllPrefs(context, appWidgetIds, alDays, alTripnames);

            final int N = appWidgetIds.length;
            for (int i=0; i<N; i++) {
                PMVWdgProvider.updateAppWidget(context, gm, appWidgetIds[i]);
            }
            Log.i(LOGCAT, "Broadcast Received: Widget Updated.");
        }
	}

}
