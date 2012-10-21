package com.jacik.apps.receivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LongRunningReceiverTest extends BroadcastReceiver{

	private static final String tag = "LongRunningReceiverTest"; 
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
    	Log.d(tag,"Receiver started");
    	//create wake lock
    	startService(context,intent);
    	Log.d(tag,"Receiver finished");
	}
    private void startService(Context context, Intent intent)
    {
    	Intent serviceIntent = new Intent(context,LongRunningBroadcastServiceTest.class);
    	serviceIntent.putExtra("original_intent", intent);
    	context.startService(serviceIntent);
    }
}
