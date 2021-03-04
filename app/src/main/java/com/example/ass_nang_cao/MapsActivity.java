package com.example.ass_nang_cao;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ass_nang_cao.Models.GetLocation;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    FloatingActionButton btn_get_curren_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn_get_curren_location = findViewById(R.id.floatingActionButton_map);
        GetLocation getLocation = get_vi_tri_hien_tai();
        btn_get_curren_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng haNoi = null;
                try {
                    haNoi = new LatLng(getLocation.getLatitude(), getLocation.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(haNoi).title("Vị trí hiện tại "));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(haNoi));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));

                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }catch (Exception ex){
                    Log.e("--------------------" , ex.toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                    builder.setTitle("Thông Báo").setMessage("Bạn cần bật 3G/4G, Wifi hoặc GPS. \n\nĐể lấy vị trí");

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        LatLng haNoi = new LatLng(21.038381414157442, 105.74673969294757);

        mMap.addMarker(new MarkerOptions().position(haNoi).title("Trường Cao đẳng FPT Polytechnic"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(haNoi));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));

        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    public GetLocation get_vi_tri_hien_tai(){
        Double latitude;
        Double longitude;
        GetLocation getLocation = new GetLocation();
        // xin quyen
        if (ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    99);
        }else {
            // lay du lieu cho vao text
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            NetworkInfo mobile_3g = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobile_3g.isConnected() || wifi.isConnected()){
                //            lay gps tu vi tri cuoi cung luu tren thiet bi
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if (location != null){
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    getLocation.setLatitude(latitude);
                    getLocation.setLongitude(longitude);

                    Toast.makeText(MapsActivity.this , "Get location Data network",Toast.LENGTH_SHORT).show();
                }
            }
//            lay gps tu internet hoac song gps cua thiet bi
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,
                    1000, 1,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                            getLocation.setLatitude(location.getLatitude());
                            getLocation.setLongitude(location.getLongitude());

                            Toast.makeText(MapsActivity.this , "Get location GPS",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });
        }
        return getLocation;
    }
}