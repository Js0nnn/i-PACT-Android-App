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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Contact extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button e1,e2,e3,e4,e5,e6,emailButtonVIT,emailButtonUM;
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
        setContentView(R.layout.activity_contact);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        drawerLayout = findViewById(R.id.drawer_layout_contact);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view_contact);

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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Contact.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_contact);

        TextView textView = findViewById(R.id.toolbarText);
        textView.setText("Contact Us");

        emailButtonVIT = findViewById(R.id.ipactEmailVIT);
        emailButtonUM = findViewById(R.id.ipactEmailUM);
        e1 = findViewById(R.id.b1);
        e2 = findViewById(R.id.b2);
        e3 = findViewById(R.id.b3);
        e4 = findViewById(R.id.b4);
        e5 = findViewById(R.id.b5);
        e6 = findViewById(R.id.b6);


        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail("jacobraglend.i@vit.ac.in", "i-PACT Conference query");
            }
        });

        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail("hazli@um.edu.my", "i-PACT Conference query");
            }
        });

        e3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail("jbelwinedward@vit.ac.in", "i-PACT Conference query");
            }
        });

        e4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail("mohamadariff@um.edu.my", "i-PACT Conference query");
            }
        });

        e5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail("thirumalai.r@vit.ac.in", "i-PACT Conference query");
            }
        });

        e6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail("tengkufaiz@um.edu.my", "i-PACT Conference query");
            }
        });

        emailButtonVIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail("ipactconference@vit.ac.in", "i-PACT Conference query");
            }
        });

        emailButtonUM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail("ipact@um.edu.my", "i-PACT Conference query");
            }
        });

    }

    private void composeEmail(String emailAddress, String subject) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailAddress, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, subject);
        startActivity(Intent.createChooser(emailIntent, subject));
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
                    Intent intent = new Intent(Contact.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_about) {
                    Intent intent = new Intent(Contact.this, About.class);
                    startActivity(intent);
                    finish();

                }
                else if (itemId == R.id.nav_sponsor) {
                    Intent intent = new Intent(Contact.this, Sponsors.class);
                    startActivity(intent);
                    finish();

                }
                else if (itemId == R.id.nav_speakers) {
                    Intent intent = new Intent(Contact.this, Speakers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_schedule) {
                    Intent intent = new Intent(Contact.this, Schedule.class);
                    startActivity(intent);
                    finish();

                }
                else if (itemId == R.id.nav_favourites) {
                    Intent intent = new Intent(Contact.this, FavouritePapers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_allPapers) {
                    Intent intent = new Intent(Contact.this, AllPapers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_committee) {
                    Intent intent = new Intent(Contact.this, Committee.class);
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
            Intent intent = new Intent(Contact.this,Dashboard.class);
            startActivity(intent);
            finish();
        }
    }
}