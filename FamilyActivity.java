package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FamilyActivity extends AppCompatActivity {

    private TextView mem1name, mem1phone, mem2name, mem2phone;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family);

        mem1name = (TextView) findViewById(R.id.tv_memName1);
        mem1phone = (TextView) findViewById(R.id.tv_memPhone1);
        mem2name = (TextView) findViewById(R.id.tv_memName2);
        mem2phone = (TextView) findViewById(R.id.tv_memPhone2);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid()).child("Members");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String mmem1name = dataSnapshot.child("MemberName1").getValue().toString();
                String mmem1phone = dataSnapshot.child("MemberPhone1").getValue().toString();
                String mmem2name = dataSnapshot.child("MemberName2").getValue().toString();
                String mmem2phone = dataSnapshot.child("MemberPhone2").getValue().toString();
                mem1name.setText("Member1 Name: " + mmem1name);
                mem1phone.setText("Member1 Phone no: " + mmem1phone);
                mem2name.setText("Member2 Name: " + mmem2name);
                mem2phone.setText("Member2 Phone no: " + mmem2phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FamilyActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });

    }
}


