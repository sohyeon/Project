package org.mixare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BuskingMainActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busking_main);
        
        ((Button)findViewById(R.id.load_mixare)).setOnClickListener(mClick);
        ((Button)findViewById(R.id.search_perfor)).setOnClickListener(mClick);
        ((Button)findViewById(R.id.add_perfor)).setOnClickListener(mClick);
	}
	
	Button.OnClickListener mClick = new OnClickListener() {
		@Override
		public void onClick(View v)
		{
			Intent i;
			
			switch(v.getId())
			{
				case R.id.load_mixare:
					i = new Intent(BuskingMainActivity.this, MixView.class);
					startActivity(i);
					break;
					
				case R.id.search_perfor:
					i = new Intent(BuskingMainActivity.this, BuskingSearchPerfor.class);
					startActivity(i);
					break;
					
				case R.id.add_perfor:
					i = new Intent(BuskingMainActivity.this, BuskingAddPerfor.class);
					startActivity(i);
					break;
			}
		}
	};
}