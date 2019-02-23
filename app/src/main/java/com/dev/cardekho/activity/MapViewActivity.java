package com.dev.cardekho.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.dev.cardekho.R;
import com.dev.cardekho.utils.Constants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapViewActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<String> cityString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(getString(R.string.map_view));
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        if (intent != null) {
            cityString = intent.getStringArrayListExtra(Constants.KEY_CITY_DATA);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setupDataOnUi(cityString);
    }

    void setupDataOnUi(List<String> cityString) {
        for (String city : cityString) {
            List<Marker> markers = new ArrayList<>();
            if (Geocoder.isPresent()) {
                try {
                    Geocoder gc = new Geocoder(this);
                    List<Address> addresses = null;
                    addresses = gc.getFromLocationName(city, 5);
                    List<LatLng> ll = new ArrayList<LatLng>(addresses.size());
                    for (Address a : addresses) {
                        if (a.hasLatitude() && a.hasLongitude()) {
                            ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                            LatLng latLng = new LatLng(a.getLatitude(), a.getLongitude());
                            markers.add(mMap.addMarker(new MarkerOptions().position(latLng)));
                        }
                    }
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (Marker markerList : markers) {
                        builder.include(markerList.getPosition());
                    }
                    LatLngBounds bounds = builder.build();
                    int height = getResources().getDisplayMetrics().heightPixels;
                    int padding = (int) (height * 0.20);
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    mMap.animateCamera(cu);
                } catch (IOException e) {
                    // handle the exception
                    pAppToast.showToast(pContext, getString(R.string.something_went_wrong));
                }
            }
        }
    }
}
