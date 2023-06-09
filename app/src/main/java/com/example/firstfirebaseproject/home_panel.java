package com.example.firstfirebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class home_panel extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private MenuItem selectedMenuItem;
    AnimatedBottomBar bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_panel);
        setUpDrawer();
        bottomNavigationSetUp();
        selectMenuItem(selectedMenuItem);
        cardIntent();
    }

    private void cardIntent() {
        CardView cardView=findViewById(R.id.card_view);
        CardView cardView2=findViewById(R.id.card_view2);
        CardView cardView3=findViewById(R.id.card_view3);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the activity here
                Intent intent = new Intent(home_panel.this, admin_panel.class);
                startActivity(intent);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the activity here
                Intent intent = new Intent(home_panel.this, reservation_panel.class);
                startActivity(intent);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the activity here
                Intent intent = new Intent(home_panel.this, payment_panel.class);
                startActivity(intent);
            }
        });
    }

    private void bottomNavigationSetUp() {
        bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setBadgeTextColor(ContextCompat.getColor(this, R.color.white));
        bottomBar.setIndicatorColor(ContextCompat.getColor(this, R.color.white));
        bottomBar.setTabColor(ContextCompat.getColor(this, R.color.white));
        bottomBar.setTabColorSelected(ContextCompat.getColor(this, R.color.white));
        bottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {
                Log.d("bottom_bar", "Selected index: " + newIndex + ", title: " + newTab.getTitle());

                switch (newTab.getId()) {
                    case R.id.item_1:
                        // Handle selection of tab item 1
//                        Intent intent1 = new Intent(home_panel.this, home_panel.class);
//                        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
//                        startActivity(intent1);
                        break;
                    case R.id.item_2:
                        // Handle selection of tab item 2
                        Intent intent2 = new Intent(home_panel.this, admin_panel.class);
                        startActivity(intent2);
                        break;
                    case R.id.item_3:
                        // Handle selection of tab item 3
                        Intent intent3 = new Intent(home_panel.this, FragmentActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(intent3);
                        break;
                    case R.id.item_4:
                        // Handle selection of tab item 3
                        Intent intent4 = new Intent(home_panel.this, payment_panel.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(intent4);
                        break;
                    case R.id.item_5:
                        // Handle selection of tab item 3
                        Intent intent5 = new Intent(home_panel.this, reservation_panel.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(intent5);
                        break;
                    // Add more cases for additional tab items as needed
                }
            }

            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {
                Log.d("bottom_bar", "Reselected index: " + index + ", title: " + tab.getTitle());
            }
        });
    }
    private void setUpDrawer(){
        navigationView = findViewById(R.id.navigation_menu);
        Menu menu = navigationView.getMenu();
        selectedMenuItem = menu.findItem(R.id.home_menu);
        toolbar = findViewById(R.id.toobar);
        drawerLayout = findViewById(R.id.drawableLayout);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item clicks here
                int id = item.getItemId();

                if (id == R.id.home_menu) {
                    // Perform action for item1
                    // Example: Load fragment or start activity
                    selectMenuItem(item);
//                    Intent i = new Intent(home_panel.this, home_panel.class);
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    startActivity(i);
                } else if (id == R.id.payment_menu) {
                    // Perform action for item2
                    // Example: Load fragment or start activity
                    selectMenuItem(item);
                    Intent i = new Intent(home_panel.this, payment_panel.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(i);
                } else if (id == R.id.reservation_menu) {
                    // Perform action for item3
                    selectMenuItem(item);
                    Intent i = new Intent(home_panel.this, reservation_panel.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(i);
                } else if (id == R.id.logout_menu) {
                    Intent intent = new Intent(home_panel.this, FragmentActivity.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(intent);
                }
                else if (id == R.id.room_menu) {
                    Intent intent = new Intent(home_panel.this, admin_panel.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(intent);
                }
                // Close the navigation drawer
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    private void selectMenuItem(MenuItem item) {
        if (selectedMenuItem != null) {
            selectedMenuItem.setChecked(false);
        }
        selectedMenuItem = item;
        selectedMenuItem.setChecked(true);
    }
}