package com.ipact.ipact_23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String userName="", userEmail="";
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Handler handler = new Handler();


    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //loadingDialog = new LoadingDialog(this);

        //hooks
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);

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



        Button regButton = findViewById(R.id.register_btn);

        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }

        TextView textView = findViewById(R.id.toolbarText);
        textView.setText("Home");

        if (isNetworkAvailable()) {

            navigationView.bringToFront();
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Dashboard.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setCheckedItem(R.id.nav_home);

            regButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://vit.ac.in/ipact/#registration"));
                    // Start the activity (open the browser or other app that can handle the URL)
                    startActivity(intent);
                }
            });






        } else {
            setContentView(R.layout.offline);
            ImageButton restartButton = findViewById(R.id.toolbarRestartButton);
            Toast.makeText(getBaseContext(), "No internet connection", Toast.LENGTH_SHORT).show();

            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            System.exit(0);
            }
            else {
                Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            pressedTime = System.currentTimeMillis();
        }
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

                if (itemId == R.id.nav_about) {
                    Intent intent = new Intent(Dashboard.this, About.class);
                    startActivity(intent);
                    finish();

                }
                else if (itemId == R.id.nav_sponsor) {
                    Intent intent = new Intent(Dashboard.this, Sponsors.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_speakers) {
                    Intent intent = new Intent(Dashboard.this, Speakers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_schedule) {
                    Intent intent = new Intent(Dashboard.this, Schedule.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_allPapers) {
                    Intent intent = new Intent(Dashboard.this, AllPapers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_favourites) {
                    Intent intent = new Intent(Dashboard.this, FavouritePapers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_committee) {
                    Intent intent = new Intent(Dashboard.this, Committee.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_contact) {
                    Intent intent = new Intent(Dashboard.this, Contact.class);
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}
