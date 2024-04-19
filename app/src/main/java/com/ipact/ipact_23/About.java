package com.ipact.ipact_23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Handler;
import android.view.MenuItem;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class About extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout umLayout, vitLayout, ipactLayout;
    Button  umReadButton, vitReadButton, ipactReadButton;

    ImageButton umButton, vitButton, ipactButton;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String userName="", userEmail="";
    Toolbar toolbar;
    Handler handler = new Handler();
    boolean b1=false,b2=false,b3=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        umLayout = findViewById(R.id.umLayout);
        vitLayout = findViewById(R.id.vitLayout);
        ipactLayout = findViewById(R.id.ipactLayout);

        umLayout.setVisibility(View.GONE);
        vitLayout.setVisibility(View.GONE);
        ipactLayout.setVisibility(View.GONE);

        umButton = findViewById(R.id.umBtn);
        vitButton = findViewById(R.id.vitBtn);
        ipactButton = findViewById(R.id.ipactBtn);

        umReadButton = findViewById(R.id.umReadBtn);
        vitReadButton = findViewById(R.id.vitReadBtn);
        ipactReadButton = findViewById(R.id.ipactReadBtn);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        drawerLayout = findViewById(R.id.drawer_layout_about);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view_about);

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

        TextView textView = findViewById(R.id.toolbarText);
        textView.setText("About Us");

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(About.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_about);


        umButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!b1) {

                    umLayout.setVisibility(View.VISIBLE);
                    vitLayout.setVisibility(View.GONE);
                    ipactLayout.setVisibility(View.GONE);
                    b1 = true;
                }
                else{
                    umLayout.setVisibility(View.GONE);
                    vitLayout.setVisibility(View.GONE);
                    ipactLayout.setVisibility(View.GONE);
                    b1 = false;
                }

            }
        });

        vitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!b2) {

                    umLayout.setVisibility(View.GONE);
                    vitLayout.setVisibility(View.VISIBLE);
                    ipactLayout.setVisibility(View.GONE);
                    b2 = true;
                }
                else{
                    umLayout.setVisibility(View.GONE);
                    vitLayout.setVisibility(View.GONE);
                    ipactLayout.setVisibility(View.GONE);
                    b2 = false;
                }
            }
        });

        ipactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!b3) {

                    umLayout.setVisibility(View.GONE);
                    vitLayout.setVisibility(View.GONE);
                    ipactLayout.setVisibility(View.VISIBLE);
                    b3 = true;
                }
                else{
                    umLayout.setVisibility(View.GONE);
                    vitLayout.setVisibility(View.GONE);
                    ipactLayout.setVisibility(View.GONE);
                    b3 = false;
                }
            }
        });

        umReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.um.edu.my/"));
                // Start the activity (open the browser or other app that can handle the URL)
                startActivity(intent);
            }
        });

        vitReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://vit.ac.in/"));
                // Start the activity (open the browser or other app that can handle the URL)
                startActivity(intent);
            }
        });

        ipactReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://vit.ac.in/ipact/"));
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
                    Intent intent = new Intent(About.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_sponsor) {
                    Intent intent = new Intent(About.this, Sponsors.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_speakers) {
                    Intent intent = new Intent(About.this, Speakers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_schedule) {
                    Intent intent = new Intent(About.this, Schedule.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_allPapers) {
                    Intent intent = new Intent(About.this, AllPapers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_favourites) {
                    Intent intent = new Intent(About.this, FavouritePapers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_committee) {
                    Intent intent = new Intent(About.this, Committee.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_contact) {
                    Intent intent = new Intent(About.this, Contact.class);
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
            Intent intent = new Intent(About.this,Dashboard.class);
            startActivity(intent);
            finish();
        }
    }
}

