package org.kenward.planmyvacation;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class Splash extends Activity {

	MediaPlayer mpSplash;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);
		
		mpSplash = MediaPlayer.create(this, R.raw.logojet);
		mpSplash.start();
		
		Thread logoTimer = new Thread() {
			public void run() {
				try{
					int logoTimer = 0;
					while (logoTimer < 3500) {
						sleep(100);
						logoTimer = logoTimer + 100;
					}
					startActivity(new Intent("org.kenward.planmyvacation.TRIP2"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				finally{
					finish();
				}
			}
		};
		logoTimer.start();
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mpSplash.release();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mpSplash.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mpSplash.start();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

}
