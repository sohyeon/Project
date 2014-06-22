package org.mixare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class BuskingAddPerforPlace extends FragmentActivity{
	
	GoogleMap mGoogleMap;
	LatLng loc = new LatLng(37.565893, 126.976941);
	CameraPosition cp = new CameraPosition.Builder().target(loc).zoom(13).build();
	MarkerOptions marker;
	Marker m;
	
	double lat; //����
	double lon; //���� �浵
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busking_add_perfor_place);
		
		((Button) findViewById(R.id.select_place_ok)).setOnClickListener(mClick);
		
		mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.addPlace_mapview)).getMap();
		mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
		
		//���� ��ġ ����(�� ��ġ�ϸ� ��Ŀ ǥ��)
		mGoogleMap.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public void onMapClick(LatLng latLng) {
				
				lat = latLng.latitude;
				lon = latLng.longitude;
				
				// ��Ŀ,Ÿ��Ʋ,������ ǥ��
				if (m != null)
				{
					m.remove(); // ������Ŀ�����
				}
				
				marker = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin)).position(latLng).title("���� ���");
				m = mGoogleMap.addMarker(marker);
			}
		});
		mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	}
	
	Button.OnClickListener mClick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.select_place_ok:
				Intent intent = getIntent();
				intent.putExtra("data_lat", lat+"");
				intent.putExtra("data_lon", lon+"");
	            setResult(RESULT_OK, intent);
				finish(); //����
				break;
			}
		}
	};
}