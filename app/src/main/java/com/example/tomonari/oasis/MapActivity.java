package com.example.tomonari.oasis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        user = (User) getIntent().getSerializableExtra("USER");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.map_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, HomeActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                MapActivity.this.finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        createReportPins(googleMap);
    }

    public void createReportPins(final GoogleMap googleMap) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("source_reports");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot templateSnapshot : dataSnapshot.getChildren()){
                    for(DataSnapshot snap : templateSnapshot.getChildren()){
                        WaterSourceReport wsReport = (WaterSourceReport) snap.getValue(WaterSourceReport.class);
                        double latitude = Double.parseDouble(wsReport.getLocation().split(",")[0]);
                        double longitude = Double.parseDouble(wsReport.getLocation().split(",")[1]);
                        LatLng location = new LatLng(latitude, longitude);
                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(latitude, longitude))
                                .title("Water Source Report " + wsReport.getReportNumber())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Query query2 = reference.child("purity_reports");
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot templateSnapshot : dataSnapshot.getChildren()){
                    for(DataSnapshot snap : templateSnapshot.getChildren()){
                        WaterPurityReport wpReport = (WaterPurityReport) snap.getValue(WaterPurityReport.class);
                        double latitude = Double.parseDouble(wpReport.getLocation().split(",")[0]);
                        double longitude = Double.parseDouble(wpReport.getLocation().split(",")[1]);
                        LatLng location = new LatLng(latitude, longitude);
                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(latitude, longitude))
                                .title("Water Purity Report " + wpReport.getReportNumber())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}