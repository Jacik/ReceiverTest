package com.jacik.apps.receivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* 
 * This receiver is introduced to see  
 * how the main thread schedules broadcast receivers 
 *  
 * it helps answer such questions as  
 * 1. Do they get invoked in the order they are specified? 
 * 2. Do they get invoked one after the other? or do they get invoked parallel 
 *  
 * The time delay here shows that the main thread  
 * gets halted for those many secs. You can see this 
 * in the Log.d output  
 */ 

public class ReceiverTest extends BroadcastReceiver {

	private static final String tag = "ReceiverTest"; 
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(tag, "ReceiverTest Intent received"); 
		Utils.logThreadSignature(tag); 
        Log.d(tag, "intent=" + intent); 
        Log.d(tag, "going to sleep for 2 secs"); 
        Utils.sleepForInSecs(2); 
        Log.d(tag, "wake up"); 
        String message = intent.getStringExtra("message"); 
        Log.d(tag, message);

	}

}
