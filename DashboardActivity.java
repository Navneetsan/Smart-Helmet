package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    Button btn_dashProfile, btn_dashFamily, btn_dashMaps, btn_dashSpeed, btn_dashAlcohol, btn_dashAccident, logout;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();

        logout = (Button)findViewById(R.id.btn_logout);
        btn_dashProfile = (Button)findViewById(R.id.btn_dashProfile);
        btn_dashFamily = (Button)findViewById(R.id.btn_dashFamily);
        btn_dashMaps = (Button)findViewById(R.id.btn_dashMaps);
        btn_dashSpeed = (Button)findViewById(R.id.btn_dashSpeed);
        btn_dashAlcohol = (Button)findViewById(R.id.btn_dashAlcohol);
        btn_dashAccident = (Button)findViewById(R.id.btn_dashAccident);


        btn_dashProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
            }
        });

        btn_dashFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, FamilyActivity.class));
            }
        });

        btn_dashMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, MapActivity.class));
            }
        });

        btn_dashSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, SpeedActivity.class));
            }
        });

        btn_dashAlcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, AlcoholActivity.class));
            }
        });

        btn_dashAccident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, AccLocation.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                firebaseAuth.signOut();
                finish();
            }
        });
    }
}
