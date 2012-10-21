package com.jacik.apps.receivertest;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.util.Log;

public class LongRunningBroadcastServiceTest extends IntentService {

	public static String tag = "LongRunningBroadcastServiceTest";

	public LongRunningBroadcastServiceTest() {
		super(tag);
	}
	
	public LongRunningBroadcastServiceTest(String name) {
		super(name);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		//create wake lock
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStart(intent, startId);
		//mark this as non sticky
		//Means: Don't restart the service if there are no
		//pending intents.
		return Service.START_NOT_STICKY;
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		 Utils.logThreadSignature(tag); 
		 Intent broadcastIntent	= intent.getParcelableExtra("original_intent");
		/*
		 * Note that this method call runs
		 * in a secondary thread setup by the IntentService.
		 * 
		 * Retrieve the original broadcast intent.
		 * finally tell the ligthed room that you are leaving.
		 * if this is the last visitor then the lock 
		 * will be released.
		 */
		Utils.logThreadSignature(tag);
		Log.d(tag,"Sleeping for 30 secs");
		Utils.sleepForInSecs(30);
		String message = 
			broadcastIntent.getStringExtra("message");
		Log.d(tag,"Job completed");
		Log.d(tag,message);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//release wake lock
	}
}
