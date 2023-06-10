package com.example.firstfirebaseproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class user_panel extends AppCompatActivity {
    Toolbar toobar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    AnimatedBottomBar bottomBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_panel);
        toobar = findViewById(R.id.toobar);
        drawerLayout = findViewById(R.id.drawableLayout);
        navigationView = findViewById(R.id.navigation_menu);
        bottomBar = findViewById(R.id.bottom_bar);
        setSupportActionBar(toobar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout, toobar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new home()).commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item clicks here
                int id = item.getItemId();

                if (id == R.id.home_menu) {
                    loadFragment(new home());
                } else if (id == R.id.about_menu_user) {
                    loadFragment(new about());
                }else if (id == R.id.contact_us_menu_user) {
                    loadFragment(new contact_us());
                }else if (id == R.id.room_menu_user) {
                    loadFragment(new nav_room());
                }
                else if (id == R.id.logout_menu_user) {
                    startActivity(new Intent(user_panel.this, FragmentActivity.class));
                }

                // Close the navigation drawer
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
        private void bottomNavigationSetUp() {
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
                            loadFragment(new home());
                            break;
                        case R.id.item_2:
                            loadFragment(new nav_room());
                            break;
                        case R.id.item_3:
                            // Handle selection of tab item 3
                            startActivity(new Intent(user_panel.this, FragmentActivity.class));
                            break;
                        case R.id.item_4:
                            // Handle selection of tab item 3

                            break;
                        case R.id.item_5:
                            // Handle selection of tab item 3
                            loadFragment(new about());
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
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}

