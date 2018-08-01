package com.example.kendus.art_improvement_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// import com.example.kendus.Art_Improvement_App.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private EditText textEmail;
    private EditText textPassword;
    private DatabaseReference mDatabase;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        textEmail =  findViewById(R.id.email);
        textPassword =  findViewById(R.id.password);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    public void newActivity (View view) {
        startActivity(new Intent(MainActivity.this,SignUp.class));
    }




    public void signIn (View view) {

        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Authentication successful.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }






    public class User {
        int age;
        String username;
        boolean sex;
        int likes;


        public User (int age, String username, boolean sex){
            this.age = age;
            this.username = username;
            this.sex = sex;
            likes = 0;
        }
    }

    public void newUser(int age, String username, boolean sex)  {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        User newUser = new User(age, username, sex);
        mDatabase.child("Users").child(uid).setValue(newUser);
    }




    private void updateUI(FirebaseUser user) {


        //       if (user != null) {

//            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,

        //                 user.getEmail(), user.isEmailVerified()));

//            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));



//            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);

        //           findViewById(R.id.email_password_fields).setVisibility(View.GONE);

        //           findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);



//            findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());

//        } else {

        //           mStatusTextView.setText(R.string.signed_out);

        //           mDetailTextView.setText(null);



        //           findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);

        //           findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);

        //           findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);

        //      }

    }
}

