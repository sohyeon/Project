package org.mixare;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class BuskingDetailPerforPlace extends FragmentActivity{
	
	GoogleMap mGoogleMap;
	LatLng loc = new LatLng(37.565893, 126.976941);
	CameraPosition cp;
	MarkerOptions marker;
	Marker m;
	
	double lat; //위도
	double lon; //현재 경도
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busking_detail_place);
		
		Double lat = getIntent().getDoubleExtra("lat", 0.0);
		Double lon = getIntent().getDoubleExtra("lon", 0.0);
		
		LatLng latLng = new LatLng(lat, lon);
		
		cp = new CameraPosition.Builder().target(latLng).zoom(13).build();
		
		mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.detail_place_mapview)).getMap();
		mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
		
		marker = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin)).position(latLng).title("공연 장소");
		m = mGoogleMap.addMarker(marker);
	}
}