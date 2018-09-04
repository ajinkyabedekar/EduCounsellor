package com.education.counselor.trainer;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Timer;
import java.util.TimerTask;
public class CounsellorLoginActivity extends AppCompatActivity {
    EditText username, password;
    Button login, reset;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_login);
        username = findViewById(R.id.user);
        password = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        reset = findViewById(R.id.reset);
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("")) {
                    username.requestFocus();
                    username.setError("This Is A Required Field");
                } else if (password.getText().toString().equals("")) {
                    password.requestFocus();
                    password.setError("This Is A Required Field");
                } else {
                    login();
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog= new AlertDialog.Builder(view.getContext());
                dialog.setCancelable(false);
                dialog.setMessage("RESET YOUR PASSWORD");
                LayoutInflater inflater = getLayoutInflater();
                @SuppressLint("InflateParams") final View v= inflater.inflate(R.layout.alert, null);
                dialog.setView(v).setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText email= v.findViewById(R.id.email);
                        if (email.getText().toString().equals("")) {
                            email.requestFocus();
                            email.setError("This Is A Required Field");
                        } else {
                            reset(email.getText().toString());
                            reset.setEnabled(false);
                            Timer disable = new Timer();
                            disable.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            reset.setEnabled(true);
                                        }
                                    });
                                }
                            }, 60000);
                        }
                    }
                });
                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String Email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            boolean emailVerified = user.isEmailVerified();
//             The user's ID, unique to the Firebase project. Do NOT use this value to
//             authenticate with your backend server, if you have one. Use
//             FirebaseUser.getToken() instead.
            String uid = user.getUid();
            Toast.makeText(getBaseContext(), name + "\n" + Email + "\n" + photoUrl + "\n" + emailVerified + "\n" + uid, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    public void login()
    {
        String email = username.getText().toString();
        String pass = password.getText().toString();
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
//                    Sign in success, update UI with the signed-in user's information
//                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(getBaseContext(), "Authentication succeeded.", Toast.LENGTH_SHORT).show();
                } else {
//                    If sign in fails, display a message to the user.
                    Toast.makeText(getBaseContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void reset(String email)
    {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "Password reset Link sent to your email", Toast.LENGTH_SHORT).show();
//                     Sign in success, update UI with the signed-in user's information
//                     FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(getBaseContext(), "Check Your Email", Toast.LENGTH_SHORT).show();
                } else {
//                     If sign in fails, display a message to the user.
                    Toast.makeText(getBaseContext(), "Reset Password Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}