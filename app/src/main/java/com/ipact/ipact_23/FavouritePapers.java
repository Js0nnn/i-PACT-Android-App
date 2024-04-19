package com.ipact.ipact_23;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FavouritePapers extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton clearDataButton;
    RecyclerView recyclerView;
    TextView toastTxt;
    MyAdapter myAdapter;
    ArrayList<User> list;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String userName="", userEmail="";
    Toolbar toolbar;
    Handler handler = new Handler();
    private static final String FILE_NAME = "user_favourite_papers.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_papers);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        drawerLayout = findViewById(R.id.drawer_layout_fav);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view_fav);

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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(FavouritePapers.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_speakers);

        recyclerView = findViewById(R.id.paperList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list here
        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        clearDataButton = findViewById(R.id.clearBtn);
        toastTxt = findViewById(R.id.favToastTxt);


        // Read data from the file
        ArrayList<String[]> storedData = readDataFromFile();

        if (storedData.isEmpty() || storedData.get(0) == null) {
            toastTxt.setVisibility(View.VISIBLE);
            toastTxt.setText("No data saved to favourites.");
        } else {
            toastTxt.setVisibility(View.INVISIBLE);
        }


        for (String[] dataSets : storedData) {
            String paperTitle="", paperID="", author="", date="", status="", mode="", sessionTitle="", venue="", time="";

            for (String value : dataSets) {
                if(value.contains("title:")){
                    paperTitle = value.substring(6);
                } else if (value.contains("id:")) {
                    paperID = value.substring(3);
                }else if (value.contains("auth:")) {
                    author = value.substring(5);
                }else if (value.contains("date:")) {
                    date = value.substring(5);
                }else if (value.contains("stat:")) {
                    status = value.substring(5);
                }else if (value.contains("mode:")) {
                    mode = value.substring(5);
                }else if (value.contains("sesTitle:")) {
                    sessionTitle = value.substring(9);
                }else if (value.contains("ven:")) {
                    venue = value.substring(4);
                }else if (value.contains("time:")) {
                    time = value.substring(5);
                }

            }
            User user = new User(paperTitle, paperID, author, date, status, mode, sessionTitle, venue, time);
            list.add(user);
        }
        myAdapter.notifyDataSetChanged();







        clearDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear all data in the file
                clearDataInFile();
                recyclerView.setVisibility(View.GONE);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(FavouritePapers.this, FavouritePapers.class);
                        startActivity(intent);
                        finish();
                    }},1000);
            }
        });

    }

    // Method to read data from the file
    // Method to read data from the file and separate sets of data after "##"
    private ArrayList<String[]> readDataFromFile() {
        ArrayList<String[]> dataList = new ArrayList<>();

        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;




            while ((line = br.readLine()) != null) {
                // Separate sets of data after "##" and add them to the list
                String[] dataSets = line.split("##");
                for (String dataSet : dataSets) {
                    String[] values = dataSet.split("%%");
                    dataList.add(values);
                }
            }

            br.close();
            isr.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }


    private void clearDataInFile() {
        try {
            // Open the file in write mode
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            // Write an empty string to the file
            fos.write("".getBytes());
            // Close the file
            fos.close();

            // Display a toast message indicating successful data clearance
            toastTxt.setVisibility(View.VISIBLE);
            toastTxt.setText("Data cleared");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

                if (itemId == R.id.nav_home) {
                    Intent intent = new Intent(FavouritePapers.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_about) {
                    Intent intent = new Intent(FavouritePapers.this, About.class);
                    startActivity(intent);
                    finish();

                }
                else if (itemId == R.id.nav_sponsor) {
                    Intent intent = new Intent(FavouritePapers.this, Sponsors.class);
                    startActivity(intent);
                    finish();

                }
                else if (itemId == R.id.nav_speakers) {
                    Intent intent = new Intent(FavouritePapers.this, Speakers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_schedule) {
                    Intent intent = new Intent(FavouritePapers.this, Schedule.class);
                    startActivity(intent);
                    finish();

                }
                else if (itemId == R.id.nav_allPapers) {
                    Intent intent = new Intent(FavouritePapers.this, AllPapers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_committee) {
                    Intent intent = new Intent(FavouritePapers.this, Committee.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_contact) {
                    Intent intent = new Intent(FavouritePapers.this, Contact.class);
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
            Intent intent = new Intent(FavouritePapers.this,Dashboard.class);
            startActivity(intent);
            finish();
        }
    }
}
