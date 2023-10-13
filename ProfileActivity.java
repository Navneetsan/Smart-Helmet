package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileName, profileAge, profileEmail;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileName = (TextView) findViewById(R.id.tvProfileName);
        profileAge = (TextView) findViewById(R.id.tvProfileAge);
        profileEmail = (TextView) findViewById(R.id.tvProfileEmail);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String pprofileName = dataSnapshot.child("Name").getValue().toString();
                profileName.setText("Name: " +pprofileName);
                String pprofileAge = dataSnapshot.child("Age").getValue().toString();
                profileAge.setText("Age: " +pprofileAge);
                String pprofileEmail = dataSnapshot.child("Email").getValue().toString();
                profileEmail.setText("Email: " +pprofileEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });

    }
}