package com.education.counselor.trainer.employee.trainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.education.counselor.trainer.R;
import com.education.counselor.trainer.launcher.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    FirebaseUser user;
    String email, key;
    TextView id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account2);
        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        registrations = findViewById(R.id.registrations);
        amount = findViewById(R.id.amount);
        collection_month = findViewById(R.id.collection_month);
        collection_months = findViewById(R.id.collection_months);
        absence = findViewById(R.id.absence);
        rating = findViewById(R.id.rating);
        submit = findViewById(R.id.submit);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();
        }
        reference = FirebaseDatabase.getInstance().getReference("trainer");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (Objects.equals(ds.child("mail").getValue(String.class), email)) {
                        id.setText(ds.getKey());
                        name.setText(ds.child("name").getValue(String.class));
                        mail.setText(email);
                        registrations.setText(ds.child("net_registrations").getValue(String.class));
                        amount.setText(ds.child("net_amount").getValue(String.class));
                        collection_month.setText(ds.child("last_month_collection").getValue(String.class));
                        collection_months.setText(ds.child("collection_by_month_for_last_12_months").getValue(String.class));
                        absence.setText(ds.child("total_absence_month_wise").getValue(String.class));
                        rating.setText(ds.child("rating").getValue(String.class));
                        key = ds.getKey();
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
        DatabaseReference myRef = database.getReference("trainer").child(key);
        myRef.child("name").setValue(name.getText().toString());
        myRef.child("mail").setValue(mail.getText().toString());
        myRef.child("net_registrations").setValue(registrations.getText().toString());
        myRef.child("net_amount").setValue(amount.getText().toString());
        myRef.child("last_month_collection").setValue(collection_month.getText().toString());
        myRef.child("collection_by_month_for_last_12_months").setValue(collection_months.getText().toString());
        myRef.child("total_absence_month_wise").setValue(absence.getText().toString());
        myRef.child("rating").setValue(rating.getText().toString());
        Toast.makeText(getBaseContext(), "Trainer Details Updated Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getBaseContext(), TrainerDashboardActivity.class));
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

    String access;
    boolean flag = false;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            checkUser(user);
        }
    }

    private void checkUser(final FirebaseUser user) {
        email = user.getEmail();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : ds.getChildren()) {
                        if (Objects.equals(dataSnapshot1.child("mail").getValue(String.class), email)) {
                            access = ds.getKey();
                            flag = true;
                            break;
                        }
                    }
                    if (flag)
                        break;
                }
                if (access != null) {
                    switch (access) {
                        case "trainer":
                            return;
                        default:
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getBaseContext(), LoginActivity.class));
                            finishAffinity();
                            Toast.makeText(getBaseContext(), "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Please Check Your Network Connection and Login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), TrainerDashboardActivity.class));
        finishAffinity();
    }
}