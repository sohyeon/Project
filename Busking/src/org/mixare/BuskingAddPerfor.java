package org.mixare;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BuskingAddPerfor extends Activity {
	EditText etName, etPR, etDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busking_add_perfor);
		
		etName = (EditText)findViewById(R.id.artist_name);
		etPR = (EditText)findViewById(R.id.artist_PR);
		etDate = (EditText)findViewById(R.id.perfor_date);
		
		Button btnOK = (Button)findViewById(R.id.btn_add_ok);
		btnOK.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View v) {
            	
            	TestAsync ca = new TestAsync();
            	String name = etName.getText().toString();
            	String pr = etPR.getText().toString();
            	String date = etDate.getText().toString();
            	String lat = "37";
            	String lon = "126";
            	
            	ca.execute(name, pr, date, lat, lon);
            }
        });
	}
	
	private class TestAsync extends AsyncTask<String, Void, String>
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
				"latitude=" + URLEncoder.encode(strs[3], "UTF-8") + "&" +
				"longitude=" + URLEncoder.encode(strs[4], "UTF-8");
				
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
			
			Toast.makeText(getApplicationContext(), "공연이 등록되었습니다.", Toast.LENGTH_SHORT).show();
		}
	}
}