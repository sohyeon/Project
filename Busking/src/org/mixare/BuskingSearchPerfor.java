package org.mixare;

import java.io.StringReader;
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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

public class BuskingSearchPerfor extends Activity {
	
	private ListView mListView = null;
	private ArrayList<String> mReceivedData = null;
	private ArrayAdapter<String> mArrayAdapter = null;
	
	RadioButton rbName, rbDate;
	EditText etSearch;
	Button btnSearch;
	private String BroadURL = "https://lively-ace-618.appspot.com/api";
	
	String name = "", date = "", time = "", pr = "", lat = "", lon = "";
	String tag = "TEMP";
	String fin_lon = "";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busking_search_perfor);
        
        rbName = (RadioButton)findViewById(R.id.radio_name);
        rbDate = (RadioButton)findViewById(R.id.radio_date);
        
        etSearch = (EditText)findViewById(R.id.et_search);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(mClick);
        
        mListView = (ListView) findViewById(R.id.perforList);
        mReceivedData = new ArrayList<String>();
		mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mReceivedData);
		mListView.setAdapter(mArrayAdapter);
		
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //단클릭
    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    		{
    			//Log.d("test", position+"");
				//Log.d("test", m_data.get(position).get_name());
				Intent i = new Intent(BuskingSearchPerfor.this, BuskingDetailPerfor.class);
				i.putExtra("name", name);
				i.putExtra("dateandtime", date+" "+time);
				i.putExtra("pr", pr);
				i.putExtra("lat", lat);
				i.putExtra("lon", fin_lon);
				startActivity(i);
    		}
		});
	}
	
	Button.OnClickListener mClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_search:
				//mArrayAdapter.clear();
				
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
				HttpClient client = new DefaultHttpClient();
				String getURL = params[0].toString();
				
				List<NameValuePair> myparam = new ArrayList<NameValuePair>();
				
				
				if(rbName.isChecked())
				{
					myparam.add(new BasicNameValuePair("kind", "n"));
				}
				else if(rbDate.isChecked())
				{
					myparam.add(new BasicNameValuePair("kind", "d"));
				}
				
				myparam.add(new BasicNameValuePair("search", etSearch.getText().toString()));
				
				String paramString = URLEncodedUtils.format(myparam, "utf-8");
				
				HttpGet get = new HttpGet(getURL+"?"+paramString);
				//Log.e("www", get.getURI().toURL().toString());
				
				HttpResponse responseGet = client.execute(get);
				HttpEntity resEntityGet = responseGet.getEntity();
				if (resEntityGet != null) {
					mDataRespone = EntityUtils.toString(resEntityGet);
					Log.d("www" , mDataRespone);
				}
				
				// xml parsing
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();

				xpp.setInput(new StringReader(mDataRespone));
				int eventType = xpp.getEventType();
				while (eventType != XmlPullParser.END_DOCUMENT) {
					if (eventType == XmlPullParser.START_DOCUMENT) {
						System.out.println("Start document");
					} else if (eventType == XmlPullParser.START_TAG) {
						System.out.println("Start tag " + xpp.getName());
						tag = xpp.getName();
					} else if (eventType == XmlPullParser.END_TAG) {
						String _tag = xpp.getName();
						System.out.println("End tag " + _tag);
						if (_tag.equalsIgnoreCase("document"))
						{
							mReceivedData.add(name + " / " + date);
							Log.d("test", "여기");
							fin_lon = lon;
						}

					} else if (eventType == XmlPullParser.TEXT) {
						System.out.println("Text " + xpp.getText());

						if (tag.equalsIgnoreCase("name"))
							name = new String(xpp.getText().getBytes("UTF-8"));
						else if (tag.equalsIgnoreCase("date"))
							date = new String(xpp.getText().getBytes("UTF-8"));
						else if (tag.equalsIgnoreCase("time"))
							time = new String(xpp.getText().getBytes("UTF-8"));
						else if (tag.equalsIgnoreCase("pr"))
							pr = new String(xpp.getText().getBytes("UTF-8"));
						else if (tag.equalsIgnoreCase("lat"))
						{
							lat = new String(xpp.getText().getBytes("UTF-8"));
							Log.d("long", "lat : "+lat);
						}
						else if (tag.equalsIgnoreCase("lon")){
							lon = new String(xpp.getText().getBytes("UTF-8"));
							Log.d("long", "lon : "+lon);
						}
					} 
					eventType = xpp.next();
				}
			}
			catch(Exception e) {
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			mArrayAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}
	}
}