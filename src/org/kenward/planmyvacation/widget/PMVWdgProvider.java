package org.kenward.planmyvacation.widget;

import org.kenward.planmyvacation.R;
import org.kenward.planmyvacation.TripActivity;
import org.kenward.planmyvacation.R.id;
import org.kenward.planmyvacation.R.layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

public class PMVWdgProvider extends AppWidgetProvider {

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        // TODO ? doesn't this delete all prefs? Assume 1 appwidget and keep prefs
//        final int N = appWidgetIds.length;
//        for (int i=0; i<N; i++) {
//            PMVAppWidgetConfigure.deletePrefs(context, appWidgetIds[i]);
//            Log.d("PMV", "onDeleted " + i);
//        }
	}

	@Override
	public void onDisabled(Context context) {
		// TODO ? is this last widget deleted? seems same as onEnabled ?  changed to state_disabled
        // When the first widget is created, stop listening for the DATE_CHANGED broadcasts.
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName("org.kenward", ".planmyvacation.PMVBroadcastReceiver"),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                0);
	}

	@Override
	public void onEnabled(Context context) {
        // When the first widget is created, register for the DATE_CHANGED
        // broadcasts.  We don't want to be listening for these if nobody has our widget active.
        // This setting is sticky across reboots, but that doesn't matter, because this will
        // be called after boot if there is a widget instance for this provider.
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName("org.kenward", ".planmyvacation.PMVBroadcastReceiver"),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // For each widget that needs an update, get the text that we should display:
        //   - Create a RemoteViews object for it
        //   - Set the text in the RemoteViews object
        //   - Tell the AppWidgetManager to show that views object for the widget.
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetId);
       }
	}
	
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
    	// TODO get the date and tripname from prefs file
    	// calc number of days = startdate - currentdate
    	// uihelper class?
    	int mdays = 125;
    	String pfTripname = "Disney";
    	
    	String textDays = mdays + " Days until:";
    	String textTripname = pfTripname + "!";

        // Construct the RemoteViews object.  It takes the package name (in our case, it's our
        // package, but it needs this because on the other side it's the widget host inflating
        // the layout from our package).
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pmv_appwidget);
        views.setTextViewText(R.id.wdg_days, textDays);
        views.setTextViewText(R.id.wdg_tripname, textTripname);
        // Create an Intent to launch TripActivity and attach to click event
	    Intent intent = new Intent(context, TripActivity.class);
	    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.wdg_days, pendingIntent);
        views.setOnClickPendingIntent(R.id.wdg_tripname, pendingIntent);

        // Tell the widget manager
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}
