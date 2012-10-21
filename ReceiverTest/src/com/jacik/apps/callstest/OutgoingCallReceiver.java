package com.jacik.apps.callstest;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class OutgoingCallReceiver extends BroadcastReceiver {

	public static final String ABORT_PHONE_NUMBER = "123123123";
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
			String phoneNumber = extras.getString(Intent.EXTRA_PHONE_NUMBER);
			if ((phoneNumber != null) && (phoneNumber.equals(OutgoingCallReceiver.ABORT_PHONE_NUMBER))){
				
		        debugOut("arg0: " + context.toString());
		        debugOut("arg1: " + context.toString());
		        debugOut("isOrderedBroadcast = " + isOrderedBroadcast());
				
				Toast.makeText(context, 
								"Przechwycono zdarzenie NEW_OUTGOING_CALL z numerem" 
								+ OutgoingCallReceiver.ABORT_PHONE_NUMBER 
								+ " - przerywanie po³¹czenia",
								Toast.LENGTH_LONG).show();
				this.setResultData(null); //this work - call is not started
				this.abortBroadcast(); //this doesn't work
			}
		}
		else if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED )){
			String state = extras.getString(TelephonyManager.EXTRA_STATE);
			debugOut("state: " + state);
			if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
				String incommingNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
				debugOut("number: " + incommingNumber);
				
				debugOut("Call Killer service started");
		    	Intent serviceIntent = new Intent(context,CallKillerService.class);
		    	serviceIntent.putExtra("original_intent", intent);
		    	context.startService(serviceIntent);
			}

		}
		
	}
	
    private static void debugOut(String str) {
        Log.i("OutgoingCallReceiver", str);
    }
}
