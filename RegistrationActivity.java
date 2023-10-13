package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.MinMaxPriorityQueue;
import com.google.common.collect.Range;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userName, userAge, userEmail, userPassword, userConfPassword;
    private EditText memName1, memName2, memPhone1, memPhone2;
    private Button UserRegister;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    String name, email, age, password, confPassword, memname1, memphone1, memname2, memphone2;
    UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Registration Form");
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        UserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    //Upload data to database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();
                    String user_conf_password = userConfPassword.getText().toString().trim();
                    String user_age = userAge.getText().toString().trim();

                    int userAGE = Integer.parseInt(user_age);

                    if (!user_conf_password.equals(user_password)) {
                        Toast.makeText(RegistrationActivity.this, "Password do not match! ", Toast.LENGTH_SHORT).show();
                        return;
//                    } else if (!PASSWORD_PATTERN.matcher(user_password).matches()) {
//                        Toast.makeText(RegistrationActivity.this, "Password too weak. Atleast 6 character with 1 uppercase, 1 lowercase, 1 digit and 1 special character ", Toast.LENGTH_SHORT).show();
//                        return;
//                    } else if (!EMAIL_PATTERN.matcher(user_email).matches()) {
//                        Toast.makeText(RegistrationActivity.this, "Invalid Email !!", Toast.LENGTH_SHORT).show();
//                        return;
                    } else if (userAGE < 18) {
                        Toast.makeText(RegistrationActivity.this, "Enter Valid Age!!", Toast.LENGTH_SHORT).show();
                        return;
//                    } else if (mem_name.isEmpty() || mem_phone.isEmpty() || mem_relation.isEmpty()) {
//                        Toast.makeText(RegistrationActivity.this, "Please Enter Emergency Details", Toast.LENGTH_SHORT).show();
//                    }
                    }
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(RegistrationActivity.this, "Registeration Successful", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                                sendEmailVerification();
                                progressDialog.dismiss();
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Registeration Failed", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });

    }

    private void setupUIViews() {
        userName = (EditText) findViewById(R.id.etName);
        userEmail = (EditText) findViewById(R.id.etUserEmail);
        userAge = (EditText) findViewById(R.id.etUserAge);
        userPassword = (EditText) findViewById(R.id.etUserPassword);
        userConfPassword = (EditText) findViewById(R.id.etUserConfPassword);
        UserRegister = (Button) findViewById(R.id.btn_Register);
        userLogin = (TextView) findViewById(R.id.tvLogin);
        memName1 = (EditText) findViewById(R.id.et_MemName1);
        memPhone1 = (EditText) findViewById(R.id.et_MemPhone1);
        memName2 = (EditText) findViewById(R.id.et_MemName2);
        memPhone2 = (EditText) findViewById(R.id.et_MemPhone2);

    }

    private Boolean validate() {
        Boolean result = false;

        name = userName.getText().toString();
        email = userEmail.getText().toString();
        age = userAge.getText().toString();
        password = userPassword.getText().toString();
        confPassword = userConfPassword.getText().toString();
        memname1 = memName1.getText().toString();
        memname2 = memName2.getText().toString();
        memphone1 = memPhone1.getText().toString();
        memphone2 = memPhone2.getText().toString();


        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confPassword.isEmpty() || memname1.isEmpty() || memname2.isEmpty() || memphone1.isEmpty() || memphone2.isEmpty()) {
            Toast.makeText(this, "Please enter all the details  !! ", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }


    private void sendEmailVerification() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendUserData();
                        Toast.makeText(RegistrationActivity.this, "Successfully Registered , Verification email sent! ", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Verification mail has'nt been sent! ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();
        UserProfile userProfile = new UserProfile(age, email, name);
        UserProfile userProfile1 = new UserProfile(memname1, memphone1, memname2, memphone2);
        myRef.child("Users").child(firebaseAuth.getUid()).setValue(userProfile);
        myRef.child("Users").child(firebaseAuth.getUid()).child("Members").setValue(userProfile1);
    }
}



