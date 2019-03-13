package com.ajstechnosoft.republicframe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

import com.ajstechnosoft.republicframe.R;

import java.io.File;

public class SplashScreen extends Activity {

	private Runnable runnable1;
	private Handler handler1;
	long installed; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		/*try {
            PackageInfo info = getPackageManager().getPackageInfo("com.skillBrighter.hairMustacheStudio",PackageManager.GET_SIGNATURES);

			for (android.content.pm.Signature signature : info.signatures) {
            	
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
            
        } catch (NameNotFoundException e) {
        	e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
        }*/
		
		/**
		 * Get the intertial ads 
		 */
		//ProjectUtils.getInterstitialAds(SplashScreen.this);
		
		
		handler1 = new Handler();
		runnable1 = new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
			}
		};
		
		handler1.postDelayed(runnable1, 2000);
		
		try {
			PackageManager pm = getPackageManager();
			ApplicationInfo appInfo = pm.getApplicationInfo(getPackageName(), 0);
			String appFile = appInfo.sourceDir;
			installed = new File(appFile).lastModified(); //Epoch Time
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

	   /*Pref pref = new Pref(this);
       if(!pref.getNotificationCreated()) {
    	   pref.setNotificationCreated(true);
    	   showNotification();
       }*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
		
	/*
	 * Set alarm 
	 */
	/*public void showNotification() {
		
		  Intent myIntent = new Intent(this , NotifyService.class);     
		  AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		  PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);
		  Calendar calendar = Calendar.getInstance();
		  alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, installed, 1000*60*60*60*8, pendingIntent);
	 }*/
}
