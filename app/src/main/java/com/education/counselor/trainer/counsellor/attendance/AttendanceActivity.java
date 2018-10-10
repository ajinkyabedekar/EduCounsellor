package com.education.counselor.trainer.counsellor.attendance;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class AttendanceActivity extends AppCompatActivity {
    double lat, lng;
    TextView latitude_tv, longitude_tv;
    Button attend;
    DatabaseReference db;
    LocationParameter myLoc;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance2);
        db = FirebaseDatabase.getInstance().getReference("counsellor");
        latitude_tv = findViewById(R.id.latText);
        longitude_tv = findViewById(R.id.lngText);
        attend = findViewById(R.id.attend);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(AttendanceActivity.this,
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(AttendanceActivity.this, new OnSuccessListener<Location>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    latitude_tv.setText(lat + "");
                    longitude_tv.setText(lng + "");
                    myLoc = new LocationParameter(lat, lng);
                }
            }
        });
        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (myLoc != null) {
                    db.child("location").setValue(myLoc).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(view, "Location added", Snackbar.LENGTH_SHORT).show();
                        }
                    });

                } else
                    Toast.makeText(AttendanceActivity.this, "Location is not set", Toast.LENGTH_SHORT).show();
            }
        });
    }
}