package com.example.kendus.art_improvement_app;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends Activity {

    private DatabaseReference dB;
    private static final String TAG = "EmailPassword";
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private EditText nameEdit;
    private EditText ageEdit;
    private EditText genderEdit;
    private EditText passwordEdit;
    private EditText emailEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        dB = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth=FirebaseAuth.getInstance();
        nameEdit = findViewById(R.id.userName);
        ageEdit = findViewById(R.id.userAge);
        genderEdit = findViewById(R.id.userGender);
        passwordEdit = findViewById(R.id.userPassword);
        emailEdit = findViewById(R.id.userEmail);
        //setContentView(R.layout.activity_sign_up);
    }

    public void signUp (View view){
        String name = nameEdit.getText().toString();
        String age = ageEdit.getText().toString();
        String gender = genderEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String email = emailEdit.getText().toString();

        createAccount(password, email);
       // sendEmailVerification();
      //  newUser(age, name, gender);


    }
    public class User {
        public String age;
        public String username;
        public String sex;
        public int likes;


        public User (String age, String username, String sex){
            this.age = age;
            this.username = username;
            this.sex = sex;
            likes = 0;
        }
    }

    public void newUser(String age, String username, String sex)  {
        String mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User newUser = new User(age, username, sex);
        dB.child("Users").child(mUid).setValue(newUser);
    }

    public void createAccount(String email, String password) {



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");



                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Exception e = task.getException();
                            Toast.makeText(SignUp.this, e.toString(),
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    private void sendEmailVerification() {

        // Disable button




        // Send verification email

        // [START send_email_verification]

        final FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification()

                .addOnCompleteListener(this, new OnCompleteListener<Void>() {

                    @Override

                    public void onComplete(@NonNull Task<Void> task) {

                        // [START_EXCLUDE]

                        // Re-enable button





                        if (task.isSuccessful()) {

                            Toast.makeText(SignUp.this,

                                    "Verification email sent to " + user.getEmail(),

                                    Toast.LENGTH_SHORT).show();

                        } else {

                            Log.e(TAG, "sendEmailVerification", task.getException());

                            Toast.makeText(SignUp.this,

                                    "Failed to send verification email.",

                                    Toast.LENGTH_SHORT).show();

                        }

                        // [END_EXCLUDE]

                    }

                });

        // [END send_email_verification]

    }

}
