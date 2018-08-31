package com.education.counselor.trainer;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentLoginActivity extends AppCompatActivity {
EditText name,pass;
Button login,register;
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        name=findViewById(R.id.user);
        pass=findViewById(R.id.pass);
        login = findViewById(R.id.login);
        register=findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              login();

            }
        });
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                register();
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String Email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
            Toast.makeText(getBaseContext(), name +"\n"+ Email +"\n"+ photoUrl +"\n"+ emailVerified +"\n"+ uid, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
     }
     public void register()
     {
         String email=name.getText().toString();
         String password=pass.getText().toString();

         mAuth.createUserWithEmailAndPassword(email,password)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             // Sign in success, update UI with the signed-in user's information
                             FirebaseUser user = mAuth.getCurrentUser();
                         } else {
                             // If sign in fails, display a message to the user.
                             Toast.makeText(getBaseContext(), "Authentication failed.",
                                     Toast.LENGTH_SHORT).show();
                         }

                         // ...
                     }
                 });

     }
     public void login()
     {
         String email=name.getText().toString();
         String password=pass.getText().toString();

         mAuth.signInWithEmailAndPassword(email, password)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             // Sign in success, update UI with the signed-in user's information
                             FirebaseUser user = mAuth.getCurrentUser();
                             Toast.makeText(StudentLoginActivity.this, "Welcome "+user.getEmail(), Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(getBaseContext(),AdminLoginActivity.class));
                         } else {
                             // If sign in fails, display a message to the user.
                             Toast.makeText(getBaseContext(), "Authentication failed.",
                                     Toast.LENGTH_SHORT).show();
                         }

                         // ...
                     }
                 });

     }
}
