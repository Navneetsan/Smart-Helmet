package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SpeedActivity extends AppCompatActivity {

    private TextView mem1phone, mem2phone;

    public String mmem1phone, mmem2phone;

    private DatabaseReference reff;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    private ArrayList<String> al1 = new ArrayList<>();

    private ListView l1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);

        mem1phone = (TextView) findViewById(R.id.tv_memphone1Speed);
        mem2phone = (TextView) findViewById(R.id.tv_memphone2Speed);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseAuth.getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid()).child("Members");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mmem1phone = dataSnapshot.child("MemberPhone1").getValue().toString();
                mmem2phone = dataSnapshot.child("MemberPhone2").getValue().toString();
                mem1phone.setText("Member 1 Phone no: " + mmem1phone);
                mem2phone.setText("Member 2 Phone no: " + mmem2phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SpeedActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });

        reff = FirebaseDatabase.getInstance().getReference().child("IOT").child("Speed");

        l1 = findViewById(R.id.list1);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al1);

        l1.setAdapter(arrayAdapter);

        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.child("Speed Time").getValue(String.class);
                boolean p = al1.add("Over-Speeding Detected On  " + value);
                al1.add("");
                arrayAdapter.notifyDataSetChanged();

                if(p) {
                    String phoneNumber1 = mmem1phone;
                    String phoneNumber2 = mmem2phone;
                    String message = "Rider is crossing the speed limit !!";
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber1, null, message, null, null);
                    smsManager.sendTextMessage(phoneNumber2, null, message, null, null);
                } else {
                    Toast.makeText(SpeedActivity.this, "Error sending sms", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}