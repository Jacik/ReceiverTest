package com.jacik.apps.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.AbstractCursor;
import android.net.http.AndroidHttpClient;
import android.util.Log;

public class TwitterSearchCursor extends AbstractCursor {

	 

	static class Tweet {

		String created_at = "";
	
		String id_str = "";
	
		String text = "";

	}


	ArrayList<Tweet> tweets = new ArrayList<Tweet>();


	@Override

	public String[] getColumnNames() {

		// TODO Auto-generated method stub
	
		return new String[] {"_id", "created_at","id_str","text"};

	}

	 

	@Override

	public int getCount() {

		if(tweets.size() == 0) {
	
			loadData();
	
		}


		Log.d("AAAA", "cnt="+tweets.size());
	
		return tweets.size();

	}

	 

	@Override

	public double getDouble(int arg0) {

		// TODO Auto-generated method stub
	
		return 0;

	}

	 

	@Override

	public float getFloat(int arg0) {

		// TODO Auto-generated method stub
	
		return 0;

	}

	 

	@Override

	public int getInt(int index) {

		return index;

	}

	 

	@Override

	public long getLong(int index) {

		return index;

	}

	 

	@Override

	public short getShort(int index) {

		return (short)index;

	}

	 

	@Override

	public String getString(int index) {

		Tweet t = tweets.get(mPos);
	
	
		switch(index) {
	
			case 1:
		
			return ""+t.created_at;
		
			case 2:
		
			return ""+t.id_str;
		
			case 3:
		
			return ""+t.text;
	
		}
	
		return null;

	}

	 

	@Override

	public boolean isNull(int arg0) {

		// TODO Auto-generated method stub
	
		return false;

	}

	private void loadData() {

		HttpClient client = AndroidHttpClient.newInstance("TwiAndroid");
        HttpGet get = new HttpGet("http://search.twitter.com/search.json?q=mlb");

        try {

			HttpResponse resp = client.execute(get);
	
			BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
			String line = null;
			StringBuilder r = new StringBuilder();
			while((line = reader.readLine())!=null) {
	
				Log.d("AAAA", line);
				r.append(line);

			}


			JSONObject obj = new JSONObject(r.toString());
			JSONArray ary = obj.getJSONArray("results");
	
	
			for(int i=0;i<ary.length();i++) {
	
				Tweet t = new Tweet();
		
				t.created_at = ary.getJSONObject(i).getString("created_at");
				t.id_str = ary.getJSONObject(i).getString("id_str");
				t.text = ary.getJSONObject(i).getString("text");			 
		
				tweets.add(t);

			}

		} catch (ClientProtocolException e) {

			// TODO Auto-generated catch block
	
			e.printStackTrace();

		} catch (IOException e) {

			// TODO Auto-generated catch block
	
			e.printStackTrace();

		} catch (JSONException e) {

			// TODO Auto-generated catch block
	
			e.printStackTrace();

		}

	}
}	
