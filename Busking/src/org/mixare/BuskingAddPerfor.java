package org.mixare;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BuskingAddPerfor extends Activity {
	EditText etName, etPR, etDate;
	String latitude = null, longitude = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busking_add_perfor);
		
		etName = (EditText)findViewById(R.id.artist_name);
		etPR = (EditText)findViewById(R.id.perfor_PR);
		etDate = (EditText)findViewById(R.id.perfor_date);
		
		((Button) findViewById(R.id.btn_add_ok)).setOnClickListener(mClick);
		((Button) findViewById(R.id.perfor_place)).setOnClickListener(mClick);
	}
	
	Button.OnClickListener mClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.btn_add_ok:
				PerforAsync ca = new PerforAsync();
            	String name = etName.getText().toString();
            	String pr = etPR.getText().toString();
            	String date = etDate.getText().toString().split(" ")[0];
            	String time = etDate.getText().toString().split(" ")[1];
            	String lat = latitude;
            	String lon = longitude;
            	
            	ca.execute(name, pr, date, time, lat, lon);
            	
				break;
				
			case R.id.perfor_place:
				Intent i = new Intent(BuskingAddPerfor.this, BuskingAddPerforPlace.class);
				startActivityForResult(i, 1);
				
				break;
			}
		}
	};
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	super.onActivityResult(requestCode, resultCode, data);
        
    	if(resultCode == RESULT_OK) //��Ƽ��Ƽ�� ���������� ����Ǿ��� ���
        {
    		if(requestCode == 1) //BuskingAddPerfor���� ȣ���� ��쿡�� ó���մϴ�.
    		{
    			//������ �޾ƿ���
    			latitude = data.getStringExtra("data_lat");
    			longitude = data.getStringExtra("data_lon");
            }
        }
    }
	
	private class PerforAsync extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... strs)
		{
			String response = "";
			
			try{
				URL url = new URL("https://lively-ace-618.appspot.com/buskingserver");
				
				String param = "name=" + URLEncoder.encode(strs[0], "UTF-8") + "&" +
				"pr=" + URLEncoder.encode(strs[1], "UTF-8") + "&" +
				"date=" + URLEncoder.encode(strs[2], "UTF-8") + "&" +
				"time=" + URLEncoder.encode(strs[3], "UTF-8") + "&" +
				"latitude=" + URLEncoder.encode(strs[4], "UTF-8") + "&" +
				"longitude=" + URLEncoder.encode(strs[5], "UTF-8");
				
				HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
				
				con.setDoOutput(true);
				
				con.setRequestMethod("POST");
				
				con.setFixedLengthStreamingMode(param.getBytes().length);
				
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				
				PrintWriter out = new PrintWriter(con.getOutputStream());
				out.print(param);
				out.close();
				
				Scanner inStream = new Scanner(con.getInputStream());
				while(inStream.hasNextLine())
					response += (inStream.nextLine());
			}catch(MalformedURLException e1){
				e1.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
			
			return response;
		}
		
		protected void onPostExecute(String res){
			
			//�ʱ�ȭ
			etName.setText("");
			etPR.setText("");
			etDate.setText("");
			latitude = "";
			longitude = "";
			
			Toast.makeText(getApplicationContext(), "������ ��ϵǾ����ϴ�.", Toast.LENGTH_SHORT).show();
		}
	}
}