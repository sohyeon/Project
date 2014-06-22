package org.mixare;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class BuskingMainActivity extends FragmentActivity implements OnMyLocationChangeListener {

	GoogleMap mGoogleMap;
	LatLng loc = new LatLng(37.565893, 126.976941);
	CameraPosition cp = new CameraPosition.Builder().target(loc).zoom(8).build();
	MarkerOptions marker;
	Marker m;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busking_main);
		
		((Button) findViewById(R.id.load_mixare)).setOnClickListener(mClick);
		((Button) findViewById(R.id.search_perfor)).setOnClickListener(mClick);
		((Button) findViewById(R.id.add_perfor)).setOnClickListener(mClick);

		mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapview)).getMap();
		mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));

		// Enabling MyLocation Layer of Google Map
		mGoogleMap.setMyLocationEnabled(true);
		// Setting event handler for location change
		mGoogleMap.setOnMyLocationChangeListener(this); //자신의 위치가 변경될 때 마다 OnMyLocationChange를 콜하는 부분
		
		//서버에 등록된 공연 정보를 불러와서 마커를 띄워줌.
	}

	@Override
	public void onMyLocationChange(Location location) //내 위치가 변경될 때마다 콜되는 함수
	{
		// TODO Auto-generated method stub
		double latitude = location.getLatitude(); //현재 위도
		double longitude = location.getLongitude(); //현재 경도

		loc = new LatLng(latitude, longitude); //lat, Lng에 현재 위도, 경도 저장

		// 마커,타이틀, 스니핏 표시
		if (m != null)
		{
			m.remove(); // 기존마커지우기
		}
		
		cp = new CameraPosition.Builder().target((loc)).zoom(18).build();
		mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
		marker = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin)).position(loc).title("현재위치");
		m = mGoogleMap.addMarker(marker);
	}

	Button.OnClickListener mClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i;

			switch (v.getId()) {
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