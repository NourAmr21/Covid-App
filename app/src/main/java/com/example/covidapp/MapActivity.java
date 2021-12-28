package com.example.covidapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    // OnMyLocationButtonClickListener
    //  OnMyLocationClickListener,
    //  ActivityCompat.OnRequestPermissionsResultCallback {
  //  private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
  //  private boolean permissionDenied = false;
  //  private GoogleMap map;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
   // double new_lat;
    //double new_lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //new_lang=0;
        //new_lat=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }

    private void fetchLastLocation() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MapActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();}}
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
               // Location location = locationManager.getLastKnownLocation(bestProvider);
                if(location!=null) {
                    currentLocation = location;

                    //  Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(MapActivity.this::onMapReady);
                } else{


                    }
                }
            }
        );
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
      //  if(currentLocation!=null) {

//            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//            MarkerOptions markerOptions = new MarkerOptions().position(latLng);
//            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
//            googleMap.addMarker(markerOptions);
            //System.out.println(getIntent().getStringExtra("lat"));
            double lat=(double)(Integer.parseInt(getIntent().getStringExtra("lat")));
                //System.out.println(lat);
            double lang=(double)(Integer.parseInt(getIntent().getStringExtra("lang")));
            LatLng location=new LatLng(lat,lang);
            googleMap.addMarker(new MarkerOptions()
                   .position(location));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
       // }

    }


}