package com.ipact.ipact_23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Speakers extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button s1,s2,s3,s4;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String userName="", userEmail="";
    Toolbar toolbar;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speakers);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        drawerLayout = findViewById(R.id.drawer_layout_speaker);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view_speaker);

        View navHeaderLayout = navigationView.getHeaderView(0);
        TextView user_email = navHeaderLayout.findViewById(R.id.nav_email);
        TextView user_name = navHeaderLayout.findViewById(R.id.nav_username);

        userName = firebaseUser.getDisplayName();
        userEmail = firebaseUser.getEmail();

        if (firebaseUser == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            if(userName!=null){
                user_name.setText(userName);
            }

            if (userEmail != null) {
                user_email.setText(userEmail);
            }
        }

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Speakers.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_speakers);

        TextView textView = findViewById(R.id.toolbarText);
        textView.setText("Speakers");

        s1 = findViewById(R.id.aznanBtn);
        s2 = findViewById(R.id.nasrudinBtn);
        s3 = findViewById(R.id.in_hoBtn);
        s4 = findViewById(R.id.farragBtn);

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://vit.ac.in/ipact/#speakers"));
                // Start the activity (open the browser or other app that can handle the URL)
                startActivity(intent);
            }
        });

        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://vit.ac.in/ipact/#speakers"));
                // Start the activity (open the browser or other app that can handle the URL)
                startActivity(intent);
            }
        });

        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://vit.ac.in/ipact/#speakers"));
                // Start the activity (open the browser or other app that can handle the URL)
                startActivity(intent);
            }
        });

        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://vit.ac.in/ipact/#speakers"));
                // Start the activity (open the browser or other app that can handle the URL)
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int itemId = menuItem.getItemId();

        drawerLayout.closeDrawer(GravityCompat.START);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Code to be delayed
                // For example, you can put your existing onCreate code here

                if (itemId == R.id.nav_home) {
                    Intent intent = new Intent(Speakers.this, About.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_about) {
                    Intent intent = new Intent(Speakers.this, About.class);
                    startActivity(intent);
                    finish();

                }
                else if (itemId == R.id.nav_sponsor) {
                    Intent intent = new Intent(Speakers.this, Sponsors.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_schedule) {
                    Intent intent = new Intent(Speakers.this, Schedule.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_allPapers) {
                    Intent intent = new Intent(Speakers.this, AllPapers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_favourites) {
                    Intent intent = new Intent(Speakers.this, FavouritePapers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_committee) {
                    Intent intent = new Intent(Speakers.this, Committee.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_contact) {
                    Intent intent = new Intent(Speakers.this, Contact.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_logout) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 250);

        return true;

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent intent = new Intent(Speakers.this,Dashboard.class);
            startActivity(intent);
            finish();
        }
    }
}