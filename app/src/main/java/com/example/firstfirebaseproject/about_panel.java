package com.example.firstfirebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class about_panel extends AppCompatActivity {
    private Button myButton;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private MenuItem selectedMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_panel);
        myButton = findViewById(R.id.button_contact);
        myButton.setOnClickListener(view -> {
            startActivity(new Intent(new Intent(about_panel.this, contact_us.class)));
        });
        setUpDrawer();
        selectMenuItem(selectedMenuItem);
    }

    //    private void setUpDrawer(){
//        toolbar = findViewById(R.id.toobar);
//        drawerLayout = findViewById(R.id.drawableLayout);
//        navigationView = findViewById(R.id.navigation_menu);
//        setSupportActionBar(toolbar);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar,R.string.navigation_open,R.string.navigation_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        selectedMenuItem.setChecked(true);
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                // Handle navigation item clicks here
//                int id = item.getItemId();
//
//                if (id == R.id.home_menu) {
//                    // Perform action for item1
//                    // Example: Load fragment or start activity
//                    selectedMenuItem = item;
//                    Intent i = new Intent(about_panel.this, admin_panel.class);
//                    startActivity(i);
//                } else if (id == R.id.about_menu) {
//                    // Perform action for item2
//                    // Example: Load fragment or start activity
//                    selectedMenuItem = item;
//                } else if (id == R.id.contact_us_menu) {
//                    // Perform action for item3
//                    // Example: Load fragment or start activity
//                    //loadFragment(new Item3Fragment());
//                    selectedMenuItem = item;
//                    Intent i = new Intent(about_panel.this, user_panel.class);
//                    startActivity(i);
//                } else if (id == R.id.logout_menu) {
//                    Intent intent = new Intent(about_panel.this, FragmentActivity.class);
//                    startActivity(intent);
//                }
//                // Close the navigation drawer
//                drawerLayout.closeDrawer(GravityCompat.START);
//                return true;
//            }
//        });
//    }
//}
    private void setUpDrawer() {
        navigationView = findViewById(R.id.navigation_menu);
        Menu menu = navigationView.getMenu();
        selectedMenuItem = menu.findItem(R.id.payment_menu);
        toolbar = findViewById(R.id.toobar);
        drawerLayout = findViewById(R.id.drawableLayout);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
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
                    Intent i = new Intent(about_panel.this, admin_panel.class);
                    startActivity(i);
                    selectMenuItem(item);
                } else if (id == R.id.payment_menu) {
                    // Perform action for item2
                    // Example: Load fragment or start activity
                    selectMenuItem(item);
                } else if (id == R.id.reservation_menu) {
                    // Perform action for item3
                    // Example: Load fragment or start activity
                    //loadFragment(new Item3Fragment());
                    selectMenuItem(item);
                    Intent i = new Intent(about_panel.this, user_panel.class);
                    startActivity(i);
                } else if (id == R.id.logout_menu) {
                    Intent intent = new Intent(about_panel.this, FragmentActivity.class);
                    startActivity(intent);
                }
                // Close the navigation drawer
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
        private void selectMenuItem (MenuItem item){
            if (selectedMenuItem != null) {
                selectedMenuItem.setChecked(false);
            }
            selectedMenuItem = item;
            selectedMenuItem.setChecked(true);
        }
}