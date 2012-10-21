package com.jacik.apps.callstest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;
import com.jacik.apps.receivertest.Utils;

public class CallKillerService extends IntentService {

	public static String tag = "CallKillerService";
	
	public CallKillerService() {
		super(tag);
	}
	public CallKillerService(String name) {
		super(name);
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
		Intent broadcastIntent	= intent.getParcelableExtra("original_intent");
		Context context = getApplication();
		
		debugOut("Killing Cal in 5 seconds");
		Utils.sleepForInSecs(5);
		debugOut("Killing current Call");
		endCallAidl(getBaseContext());

	}
	
	private void endCallAidl(Context ctx){
		TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		Class c = null;
		Method mt = null;
		try {
			c = Class.forName(tm.getClass().getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			 mt = c.getDeclaredMethod("getITelephony");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		mt.setAccessible(true);
		ITelephony telSer = null;
		try {
			telSer = (ITelephony) mt.invoke(tm);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		//try {
		//	telSer.silenceRinger();
		//} catch (RemoteException e) {
		//	e.printStackTrace();
		//}
		try {
			telSer.endCall();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
    private static void debugOut(String str) {
        Log.i("CallKillerService", str);
    }
    
}
