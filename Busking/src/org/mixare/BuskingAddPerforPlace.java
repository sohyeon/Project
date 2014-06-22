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
	
	double lat; //위도
	double lon; //현재 경도
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busking_add_perfor_place);
		
		((Button) findViewById(R.id.select_place_ok)).setOnClickListener(mClick);
		
		mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.addPlace_mapview)).getMap();
		mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
		
		//공연 위치 설정(맵 터치하면 마커 표시)
		mGoogleMap.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public void onMapClick(LatLng latLng) {
				
				lat = latLng.latitude;
				lon = latLng.longitude;
				
				// 마커,타이틀,스니핏 표시
				if (m != null)
				{
					m.remove(); // 기존마커지우기
				}
				
				marker = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin)).position(latLng).title("공연 장소");
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
				finish(); //종료
				break;
			}
		}
	};
}