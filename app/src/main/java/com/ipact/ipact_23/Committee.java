package com.ipact.ipact_23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anthonyfdev.dropdownview.DropDownView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Committee extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
        setContentView(R.layout.activity_committee);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        drawerLayout = findViewById(R.id.drawer_layout_com);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view_com);

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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Committee.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_committee);

        TextView textView = findViewById(R.id.toolbarText);
        textView.setText("Committee");

        DropDownView dropDownView = (DropDownView) findViewById(R.id.chief_patrons);
        View collapsedView = LayoutInflater.from(this).inflate(R.layout.h_chief_patrons, null, false);
        View expandedView = LayoutInflater.from(this).inflate(R.layout.f_chief_patrons, null, false);

        dropDownView.setHeaderView(collapsedView);
        dropDownView.setExpandedView(expandedView);

        collapsedView.setOnClickListener(v -> {
            // on click the drop down will open or close
            if (dropDownView.isExpanded()) {
                dropDownView.collapseDropDown();
            } else {
                dropDownView.expandDropDown();
            }
        });




        DropDownView dropDownView2 = (DropDownView) findViewById(R.id.patrons);
        View collapsedView2 = LayoutInflater.from(this).inflate(R.layout.h_patrons, null, false);
        View expandedView2 = LayoutInflater.from(this).inflate(R.layout.f_patrons, null, false);
        dropDownView2.setHeaderView(collapsedView2);
        dropDownView2.setExpandedView(expandedView2);

        collapsedView2.setOnClickListener(v -> {
            // on click the drop down will open or close
            if (dropDownView2.isExpanded()) {
                dropDownView2.collapseDropDown();
            } else {
                dropDownView2.expandDropDown();
            }
        });


        DropDownView dropDownView3 = (DropDownView) findViewById(R.id.general_chairs);
        View collapsedView3 = LayoutInflater.from(this).inflate(R.layout.h_generl_chairs, null, false);
        View expandedView3 = LayoutInflater.from(this).inflate(R.layout.f_general_chairs, null, false);
        dropDownView3.setHeaderView(collapsedView3);
        dropDownView3.setExpandedView(expandedView3);

        collapsedView3.setOnClickListener(v -> {
            // on click the drop down will open or close
            if (dropDownView3.isExpanded()) {
                dropDownView3.collapseDropDown();
            } else {
                dropDownView3.expandDropDown();
            }
        });

        DropDownView dropDownView4 = (DropDownView) findViewById(R.id.apex_committee);
        View collapsedView4 = LayoutInflater.from(this).inflate(R.layout.h_apex, null, false);
        View expandedView4 = LayoutInflater.from(this).inflate(R.layout.f_apex, null, false);
        dropDownView4.setHeaderView(collapsedView4);
        dropDownView4.setExpandedView(expandedView4);

        collapsedView4.setOnClickListener(v -> {
            // on click the drop down will open or close
            if (dropDownView4.isExpanded()) {
                dropDownView4.collapseDropDown();
            } else {
                dropDownView4.expandDropDown();
            }
        });

        DropDownView dropDownView5 = (DropDownView) findViewById(R.id.conference_chairs);
        View collapsedView5 = LayoutInflater.from(this).inflate(R.layout.h_confchair, null, false);
        View expandedView5= LayoutInflater.from(this).inflate(R.layout.f_confchair, null, false);
        dropDownView5.setHeaderView(collapsedView5);
        dropDownView5.setExpandedView(expandedView5);

        collapsedView5.setOnClickListener(v -> {
            // on click the drop down will open or close
            if (dropDownView5.isExpanded()) {
                dropDownView5.collapseDropDown();
            } else {
                dropDownView5.expandDropDown();
            }
        });

        DropDownView dropDownView6 = (DropDownView) findViewById(R.id.conference_sec);
        View collapsedView6 = LayoutInflater.from(this).inflate(R.layout.h_confsec, null, false);
        View expandedView6= LayoutInflater.from(this).inflate(R.layout.f_confsec, null, false);
        dropDownView6.setHeaderView(collapsedView6);
        dropDownView6.setExpandedView(expandedView6);

        collapsedView6.setOnClickListener(v -> {
            // on click the drop down will open or close
            if (dropDownView6.isExpanded()) {
                dropDownView6.collapseDropDown();
            } else {
                dropDownView6.expandDropDown();
            }
        });

        DropDownView dropDownView7 = (DropDownView) findViewById(R.id.fin_chair);
        View collapsedView7 = LayoutInflater.from(this).inflate(R.layout.h_finchair, null, false);
        View expandedView7 = LayoutInflater.from(this).inflate(R.layout.f_finchair, null, false);
        dropDownView7.setHeaderView(collapsedView7);
        dropDownView7.setExpandedView(expandedView7);

        collapsedView7.setOnClickListener(v -> {
            // on click the drop down will open or close
            if (dropDownView7.isExpanded()) {
                dropDownView7.collapseDropDown();
            } else {
                dropDownView7.expandDropDown();
            }
        });

        DropDownView dropDownView8 = (DropDownView) findViewById(R.id.pub_chair);
        View collapsedView8 = LayoutInflater.from(this).inflate(R.layout.h_pubchair, null, false);
        View expandedView8= LayoutInflater.from(this).inflate(R.layout.f_pubchair, null, false);
        dropDownView8.setHeaderView(collapsedView8);
        dropDownView8.setExpandedView(expandedView8);

        collapsedView8.setOnClickListener(v -> {
            // on click the drop down will open or close
            if (dropDownView8.isExpanded()) {
                dropDownView8.collapseDropDown();
            } else {
                dropDownView8.expandDropDown();
            }
        });

        DropDownView dropDownView9 = (DropDownView) findViewById(R.id.publicity_chai);
        View collapsedView9 = LayoutInflater.from(this).inflate(R.layout.h_publicitychair, null, false);
        View expandedView9 = LayoutInflater.from(this).inflate(R.layout.f_publicitychair, null, false);
        dropDownView9.setHeaderView(collapsedView9);
        dropDownView9.setExpandedView(expandedView9);

        collapsedView9.setOnClickListener(v -> {
            // on click the drop down will open or close
            if (dropDownView9.isExpanded()) {
                dropDownView9.collapseDropDown();
            } else {
                dropDownView9.expandDropDown();
            }
        });


        DropDownView dropDownView10 = (DropDownView) findViewById(R.id.sponsor_chair);
        View collapsedView10 = LayoutInflater.from(this).inflate(R.layout.h_sponchair, null, false);
        View expandedView10 = LayoutInflater.from(this).inflate(R.layout.f_sponchair, null, false);
        dropDownView10.setHeaderView(collapsedView10);
        dropDownView10.setExpandedView(expandedView10);

        collapsedView10.setOnClickListener(v -> {
            // on click the drop down will open or close
            if (dropDownView10.isExpanded()) {
                dropDownView10.collapseDropDown();
            } else {
                dropDownView10.expandDropDown();
            }
        });

        DropDownView dropDownView11 = (DropDownView) findViewById(R.id.tech_chair);
        View collapsedView11 = LayoutInflater.from(this).inflate(R.layout.h_techair, null, false);
        View expandedView11 = LayoutInflater.from(this).inflate(R.layout.f_techair, null, false);
        dropDownView11.setHeaderView(collapsedView11);
        dropDownView11.setExpandedView(expandedView11);

        collapsedView11.setOnClickListener(v -> {
            // on click the drop down will open or close
            if (dropDownView11.isExpanded()) {
                dropDownView11.collapseDropDown();
            } else {
                dropDownView11.expandDropDown();
            }
        });

        DropDownView dropDownView12 = (DropDownView) findViewById(R.id.relation_chair);
        View collapsedView12 = LayoutInflater.from(this).inflate(R.layout.h_relchair, null, false);
        View expandedView12 = LayoutInflater.from(this).inflate(R.layout.f_relchair, null, false);
        dropDownView12.setHeaderView(collapsedView12);
        dropDownView12.setExpandedView(expandedView12);

        collapsedView12.setOnClickListener(v -> {
            // on click the drop down will open or close
            if (dropDownView12.isExpanded()) {
                dropDownView12.collapseDropDown();
            } else {
                dropDownView12.expandDropDown();
            }
        });


        DropDownView dropDownView13 = (DropDownView) findViewById(R.id.reg_chair);
        View collapsedView13 = LayoutInflater.from(this).inflate(R.layout.h_regchair, null, false);
        View expandedView13 = LayoutInflater.from(this).inflate(R.layout.f_regchair, null, false);
        dropDownView13.setHeaderView(collapsedView13);
        dropDownView13.setExpandedView(expandedView13);

        collapsedView13.setOnClickListener(v -> {
            // on click the drop down will open or close
            if (dropDownView13.isExpanded()) {
                dropDownView13.collapseDropDown();
            } else {
                dropDownView13.expandDropDown();
            }
        });


        DropDownView dropDownView14 = (DropDownView) findViewById(R.id.hospitality_chair);
        View collapsedView14 = LayoutInflater.from(this).inflate(R.layout.h_hosp, null, false);
        View expandedView14 = LayoutInflater.from(this).inflate(R.layout.f_hosp, null, false);
        dropDownView14.setHeaderView(collapsedView14);
        dropDownView14.setExpandedView(expandedView14);

        collapsedView14.setOnClickListener(v -> {
            // on click the drop down will open or close
            if (dropDownView14.isExpanded()) {
                dropDownView14.collapseDropDown();
            } else {
                dropDownView14.expandDropDown();
            }
        });

        DropDownView dropDownView15 = (DropDownView) findViewById(R.id.arrangement_chair);
        View collapsedView15 = LayoutInflater.from(this).inflate(R.layout.h_arrange, null, false);
        View expandedView15 = LayoutInflater.from(this).inflate(R.layout.f_arrange, null, false);
        dropDownView15.setHeaderView(collapsedView15);
        dropDownView15.setExpandedView(expandedView15);

        collapsedView15.setOnClickListener(v -> {
            // on click the drop down will open or close
            if (dropDownView15.isExpanded()) {
                dropDownView15.collapseDropDown();
            } else {
                dropDownView15.expandDropDown();
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
                    Intent intent = new Intent(Committee.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_about) {
                    Intent intent = new Intent(Committee.this, About.class);
                    startActivity(intent);
                    finish();

                }
                else if (itemId == R.id.nav_sponsor) {
                    Intent intent = new Intent(Committee.this, Sponsors.class);
                    startActivity(intent);
                    finish();

                }
                else if (itemId == R.id.nav_speakers) {
                    Intent intent = new Intent(Committee.this, Speakers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_schedule) {
                    Intent intent = new Intent(Committee.this, Schedule.class);
                    startActivity(intent);
                    finish();

                }
                else if (itemId == R.id.nav_favourites) {
                    Intent intent = new Intent(Committee.this, FavouritePapers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_allPapers) {
                    Intent intent = new Intent(Committee.this, AllPapers.class);
                    startActivity(intent);
                    finish();
                }
                else if (itemId == R.id.nav_contact) {
                    Intent intent = new Intent(Committee.this, Contact.class);
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
            Intent intent = new Intent(Committee.this,Dashboard.class);
            startActivity(intent);
            finish();
        }
    }
}