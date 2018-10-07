package com.education.counselor.trainer.counsellor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MyAccountActivity extends AppCompatActivity {
    EditText name, mail, registrations, amount, collection_month, collection_months, absence, rating;
    Button submit;
    DatabaseReference reference;
    private String n = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Intent i = getIntent();
        if (i.hasExtra("name")) {
            n = i.getStringExtra("name");
        }
        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        registrations = findViewById(R.id.registrations);
        amount = findViewById(R.id.amount);
        collection_month = findViewById(R.id.collection_month);
        collection_months = findViewById(R.id.collection_months);
        absence = findViewById(R.id.absence);
        rating = findViewById(R.id.rating);
        submit = findViewById(R.id.submit);
        reference = FirebaseDatabase.getInstance().getReference("education_counsellor");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("name").getValue(String.class), n)) {
                        name.setText(n);
                        mail.setText(ds.child("mail").getValue(String.class));
                        registrations.setText(ds.child("net_registrations").getValue(String.class));
                        amount.setText(ds.child("net_amount").getValue(String.class));
                        collection_month.setText(ds.child("last_month_collection").getValue(String.class));
                        collection_months.setText(ds.child("collection_by_month_for_last_12_months").getValue(String.class));
                        absence.setText(ds.child("total_absence_month_wise").getValue(String.class));
                        rating.setText(ds.child("average_rating").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText e[] = new EditText[]{name, mail, registrations, amount, collection_month, collection_months, absence, rating};
                if (check(e)) {
                    Toast.makeText(MyAccountActivity.this, "Error", Toast.LENGTH_SHORT).show();
                } else
                    update();
            }
        });
    }

    private void update() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("education_counsellor").push();
        myRef.child("name").setValue(name.getText().toString());
        myRef.child("mail").setValue(mail.getText().toString());
        myRef.child("net_registrations").setValue(registrations.getText().toString());
        myRef.child("net_amount").setValue(amount.getText().toString());
        myRef.child("last_month_collection").setValue(collection_month.getText().toString());
        myRef.child("collection_by_month_for_last_12_months").setValue(collection_months.getText().toString());
        myRef.child("total_absence_month_wise").setValue(absence.getText().toString());
        myRef.child("average_rating").setValue(rating.getText().toString());
        Toast.makeText(getBaseContext(), "Education Counsellor Details Updated Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getBaseContext(), CounsellorDashboardActivity.class));
    }

    private boolean check(EditText[] e) {
        for (EditText ed : e) {
            if (TextUtils.isEmpty(ed.getText().toString())) {
                ed.requestFocus();
                ed.setError("This Is A Required Field");
                return true;
            }
        }
        return false;
    }
}