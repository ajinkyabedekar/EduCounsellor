/*
*************************************
*      Author:Yogesh Sharma         *
*************************************
******************************************************************
*              Loction wise attendance                           *
******************************************************************
*/


//package
package com.education.counselor.trainer.student.attendance;
//importing all the required Classes
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.student.StudentDashboardActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
//firebse 
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Objects;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
//attendance class
public class AttendanceActivity extends AppCompatActivity {
    double lat, lng;
    TextView latitude_tv, longitude_tv;
    Button attend, exit;
    DatabaseReference db;
    LocationParameter myLoc;
    String key, email;
    FirebaseUser user;
    Date date = new Date();
    Long t1, t2;
    Long hours, minutes, secs, mils;
    @Override
    protected void onStart() {
        super.onStart();
    }
    //overridding the function oncreate for creating a user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        db = FirebaseDatabase.getInstance().getReference("student");
        latitude_tv = findViewById(R.id.latText);
        longitude_tv = findViewById(R.id.lngText);
        attend = findViewById(R.id.attend);
        exit = findViewById(R.id.exit);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        }
        db.addValueEventListener(new ValueEventListener() {
            //overridding datas when updated the data
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("mail").getValue(String.class), email)) {
                        key = ds.getKey();
                    }
                }
            }
            //overridding when something wents error
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if (ActivityCompat.checkSelfPermission(AttendanceActivity.this,
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(AttendanceActivity.this, new OnSuccessListener<Location>() {
            @SuppressLint("SetTextI18n")
            //writing on success 
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
            //overidding new entry
            @Override
            public void onClick(final View view) {
                attend.setVisibility(View.GONE);
                attend.setEnabled(false);
                exit.setVisibility(View.VISIBLE);
                exit.setEnabled(true);
                if (myLoc != null) {
                    db.child(key).child("attendance").child("batch_number").child(String.valueOf(date)).child("location_entry").setValue(myLoc).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AttendanceActivity.this, "Location Added", Toast.LENGTH_SHORT).show();
                            t1 = System.currentTimeMillis();
                        }
                    });
                    db.child(key).child("attendance").child("batch_number").child(String.valueOf(date)).child("class_status").setValue("Attending");
                } else
                    Toast.makeText(AttendanceActivity.this, "Location is not set", Toast.LENGTH_SHORT).show();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            //final view
            public void onClick(final View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setCancelable(false);
                dialog.setMessage("ARE YOU SURE YOU WANT TO EXIT CLASS ?");
                dialog.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (myLoc != null) {
                            db.child(key).child("attendance").child("batch_number").child(String.valueOf(date)).child("location_exit").setValue(myLoc).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(AttendanceActivity.this, "Location Added", Toast.LENGTH_SHORT).show();
                                    t2 = System.currentTimeMillis();
                                    mils = t2 - t1;
                                    secs = mils / 1000;
                                    mils %= 1000;
                                    minutes = secs / 60;
                                    secs %= 60;
                                    hours = minutes / 60;
                                    minutes %= 60;
                                    db.child(key).child("attendance").child("batch_number").child(String.valueOf(date)).child("class_attended_time").setValue(hours + " hours, " + minutes + " minutes, " + secs + " seconds, " + mils + " milliseconds");
                                }
                            });
                            db.child(key).child("attendance").child("batch_number").child(String.valueOf(date)).child("class_status").setValue("Attended");
                            startActivity(new Intent(getBaseContext(), StudentDashboardActivity.class));
                        } else
                            Toast.makeText(AttendanceActivity.this, "Location is not set", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    //error cotrol message
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
            }
        });
    }
}
