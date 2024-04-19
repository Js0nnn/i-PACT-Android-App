package com.ipact.ipact_23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AllPapers extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    LoadingDialog loadingDialog;
    RecyclerView recyclerView;
    Button favButton;
    TextView toastTxt;
    ImageView favBtnImg;
    MyAdapter2 myAdapter2;
    ArrayList<User> list;
    EditText searchEditText;
    String paperTitle, paperID, author, date, status, mode, sessionTitle, venue, time;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String userName="", userEmail="";
    Toolbar toolbar;
    Handler handler = new Handler();

    private static final String txtSaved ="Paper already available in Favourites.";
    private static final String txtSave ="Paper saved to Favourites.";
    DatabaseReference database;
    private static final String FILE_NAME = "user_favourite_papers.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_papers);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        drawerLayout = findViewById(R.id.drawer_layout_papers);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view_papers);

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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(AllPapers.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_allPapers);

        TextView textView = findViewById(R.id.toolbarText);
        textView.setText("Papers");

        loadingDialog = new LoadingDialog(this);
        loadingDialog.showDialog();

        recyclerView = findViewById(R.id.paperList);
        database = FirebaseDatabase.getInstance().getReference("GoogleSheets");

        searchEditText = findViewById(R.id.searchEditText);
        toastTxt = findViewById(R.id.toastMsg);
        favButton = findViewById(R.id.favBtn);
        favBtnImg = findViewById(R.id.favBtnIcon);

        favButton.setVisibility(View.GONE);
        favBtnImg.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter2 = new MyAdapter2(this, list);
        recyclerView.setAdapter(myAdapter2);
        recyclerView.setVisibility(View.GONE);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed in this case
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterList(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This method is required to be implemented
            }
        });


        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (paperTitle != null) {
                    // Assuming you want to store the data when the button is pressed
                    String data = "title:" + paperTitle + "%%" + "id:" + paperID + "%%" + "auth:" + author +
                            "%%" + "date:" + date + "%%" + "stat:" + status + "%%" + "mode:" + mode + "%%" + "sesTitle:" + sessionTitle +
                            "%%" + "ven:" + venue + "%%" + "time:" + time + "##";

                    try {
                        // Open the file in append mode
                        FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND);
                        FileInputStream fis = openFileInput(FILE_NAME);
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader br = new BufferedReader(isr);
                        String line = br.readLine();

                        if (line != null) {
                            if (line.contains(data)) {
                                toastTxt.setText(txtSaved);
                            } else {
                                toastTxt.setText(txtSave);
                                // Write the data to the file
                                fos.write(data.getBytes());
                                // Close the file
                                fos.close();
                            }
                        } else {
                            toastTxt.setText(txtSave);
                            // Write the data to the file
                            fos.write(data.getBytes());
                            // Close the file
                            fos.close();
                        }

                        // Display a toast message indicating successful file write

                        searchEditText.setText("");
                        favButton.setVisibility(View.GONE);
                        favBtnImg.setVisibility(View.GONE);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loadingDialog.hideDialog();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    list.add(user);
                }
                myAdapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle potential errors here
            }
        });
    }


    private void filterList(String query) {
        ArrayList<User> filteredList = new ArrayList<>();

        for (User user : list) {
            // Use equalsIgnoreCase for case-insensitive comparison
            if (user.getPaperID().equalsIgnoreCase(query)) {

                recyclerView.setVisibility(View.VISIBLE);
                favButton.setVisibility(View.VISIBLE);
                favBtnImg.setVisibility(View.VISIBLE);

                filteredList.add(user);
                paperTitle = user.getPaperTitle();
                author = user.getAuthor();
                date = user.getDate();
                status = user.getStatus();
                paperID = user.getPaperID();
                mode = user.getMode();
                sessionTitle = user.getSessionTitle();
                venue = user.getVenue();
                time = user.getTime();

                break; // Break the loop if a match is found to show only one result
            }
            else {
                recyclerView.setVisibility(View.GONE);
                favButton.setVisibility(View.GONE);
                favBtnImg.setVisibility(View.GONE);
            }
        }

        // Update the RecyclerView with the filtered list using MyAdapter2's filterList method
        ((MyAdapter2) recyclerView.getAdapter()).filterList(filteredList);
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
                    Intent intent = new Intent(AllPapers.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_about) {
                    Intent intent = new Intent(AllPapers.this, About.class);
                    startActivity(intent);
                    finish();

                }
                else if (itemId == R.id.nav_sponsor) {
                    Intent intent = new Intent(AllPapers.this, Sponsors.class);
                    startActivity(intent);
                    finish();

                }
                else if (itemId == R.id.nav_speakers) {
                    Intent intent = new Intent(AllPapers.this, Speakers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_schedule) {
                    Intent intent = new Intent(AllPapers.this, Schedule.class);
                    startActivity(intent);
                    finish();

                }
                else if (itemId == R.id.nav_favourites) {
                    Intent intent = new Intent(AllPapers.this, FavouritePapers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_committee) {
                    Intent intent = new Intent(AllPapers.this, Committee.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_contact) {
                    Intent intent = new Intent(AllPapers.this, Contact.class);
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
            Intent intent = new Intent(AllPapers.this,Dashboard.class);
            startActivity(intent);
            finish();
        }
    }

}