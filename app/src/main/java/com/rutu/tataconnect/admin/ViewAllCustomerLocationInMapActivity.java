package com.rutu.tataconnect.admin;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.rutu.tataconnect.R;
import com.rutu.tataconnect.databinding.ActivityViewAllCustomerLocationInMapBinding;

import java.io.IOException;
import java.util.List;

public class ViewAllCustomerLocationInMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityViewAllCustomerLocationInMapBinding binding;

    double latitude,longitude;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewAllCustomerLocationInMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (ActivityCompat.checkSelfPermission(ViewAllCustomerLocationInMapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(ViewAllCustomerLocationInMapActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(ViewAllCustomerLocationInMapActivity.this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },101);
        }
        else
        {
            getMyCurrentLocation();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    private void getMyCurrentLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(ViewAllCustomerLocationInMapActivity.this);

        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
                new CancellationToken() {
                    @NonNull
                    @Override
                    public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                        return null;
                    }

                    @Override
                    public boolean isCancellationRequested() {
                        return false;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Geocoder geocoder = new Geocoder(ViewAllCustomerLocationInMapActivity.this);
                try {
                    List<Address> addressList = geocoder.getFromLocation(latitude,longitude,1);
                    address = addressList.get(0).getAddressLine(0);

                    LatLng currentlocation = new LatLng(latitude,longitude);
                    mMap.addMarker(new MarkerOptions().position(currentlocation).title(address));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlocation,16),
                            5000,null);
                    Toast.makeText(ViewAllCustomerLocationInMapActivity.this, "", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ViewAllCustomerLocationInMapActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

    }
}