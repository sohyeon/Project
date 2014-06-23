package org.mixare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BuskingDetailPerfor extends Activity{
	
	TextView tvName, tvPR, tvDate;
	String latitude;
	String longitude;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busking_detail_perfor);
		
		String name = getIntent().getStringExtra("name");
		String dateandtime = getIntent().getStringExtra("dateandtime");
		String pr = getIntent().getStringExtra("pr");
		
		latitude = getIntent().getStringExtra("lat");
		longitude = getIntent().getStringExtra("lon");
		Log.i("long","lat"+latitude+"////");
		Log.i("long","long"+longitude+"////");
		Log.i("long","end");
		tvName = (TextView)findViewById(R.id.detail_artist_name);
		tvPR = (TextView)findViewById(R.id.detail_perfor_PR);
		tvDate = (TextView)findViewById(R.id.detail_artist_date);
		
		tvName.setText(name);
		tvPR.setText(pr);
		tvDate.setText(dateandtime);
		
		((Button) findViewById(R.id.detail_perfor_place)).setOnClickListener(mClick);
	}
	
	Button.OnClickListener mClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.detail_perfor_place:
				
				double lati = Double.parseDouble(latitude);
				double longi = Double.parseDouble(longitude);
				Log.d("test", longi+"ttttt");
				
				
				Intent i = new Intent(BuskingDetailPerfor.this, BuskingDetailPerforPlace.class);
				
				//double lati = 0;
				//lati = Double.parseDouble(latitude);
				//double longi = 0;
				//longi = Double.parseDouble(longitude);
				i.putExtra("lat", lati);
				i.putExtra("lon", longi);
				//i.putExtra("lon", longi);
				startActivity(i);
				
				break;
			}
		}
	};

}
