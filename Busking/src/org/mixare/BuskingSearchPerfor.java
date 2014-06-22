package org.mixare;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class BuskingSearchPerfor extends Activity {
	EditText etSearch;
	Button btnSearch;
	private String BroadURL = "https://lively-ace-618.appspot.com/api";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busking_search_perfor);
        
        etSearch = (EditText)findViewById(R.id.et_search);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(mClick);
	}
	
	Button.OnClickListener mClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_search:
				MyAsyncTask myAsyncTask = new MyAsyncTask();
				myAsyncTask.execute(BroadURL);
				break;
			}
		}
	};
	
	private class MyAsyncTask extends AsyncTask<String, Void, String> {

		private String mDataRespone = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			try {
				Log.e("www" , "qweqweqwe");
				
				HttpClient client = new DefaultHttpClient();
				String getURL = params[0].toString();
				
				List<NameValuePair> myparam = new ArrayList<NameValuePair>();
				myparam.add(new BasicNameValuePair("search", "test"));
				String paramString = URLEncodedUtils.format(myparam, "utf-8");
				
				HttpGet get = new HttpGet(getURL+"?"+paramString);
				Log.e("www", get.getURI().toURL().toString());
				
				HttpResponse responseGet = client.execute(get);
				HttpEntity resEntityGet = responseGet.getEntity();
				if (resEntityGet != null) {
					mDataRespone = EntityUtils.toString(resEntityGet);
					Log.e("www" , mDataRespone);
				}
			}
			catch(Exception e) {
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}
	}
}