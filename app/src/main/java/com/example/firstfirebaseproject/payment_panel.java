package com.example.firstfirebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class payment_panel extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    RecyclerView recyclerViewuser, paymentlist;
    Button addbtn;
    private MenuItem selectedMenuItem;
    AnimatedBottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_panel);
        paymentlist = findViewById(R.id.paymentRecyclerView);
        paymentlist.setLayoutManager(new LinearLayoutManager(this));
        paymentlist = findViewById(R.id.paymentRecyclerView);
        bottomBar = findViewById(R.id.bottom_bar);
        setUpDrawer();
        fetchPaymentData();
        bottomNavigationSetUp();
    }

    private void setUpDrawer(){
        navigationView = findViewById(R.id.navigation_menu);
        Menu menu = navigationView.getMenu();
        selectedMenuItem = menu.findItem(R.id.payment_menu);
        toolbar = findViewById(R.id.toobar);
        drawerLayout = findViewById(R.id.drawableLayout);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        bottomNavigationSetUp();
        selectMenuItem(selectedMenuItem);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item clicks here
                int id = item.getItemId();

                if (id == R.id.home_menu) {
                    // Perform action for item1
                    selectMenuItem(item);
                    Intent i = new Intent(payment_panel.this, home_panel.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(i);
                } else if (id == R.id.payment_menu) {
                    // Perform action for item2
                    selectMenuItem(item);
//                    Intent i = new Intent(payment_panel.this, payment_panel.class);
//                    startActivity(i);
                } else if (id == R.id.room_menu) {
                    // Perform action for item3
                    selectMenuItem(item);
                    Intent i = new Intent(payment_panel.this, admin_panel.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(i);
                } else if (id == R.id.logout_menu) {
                    Intent intent = new Intent(payment_panel.this, FragmentActivity.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(intent);
                }
                else if (id == R.id.reservation_menu) {
                    // Perform action for item3
                    selectMenuItem(item);
                    Intent i = new Intent(payment_panel.this, reservation_panel.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(i);
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
                        Intent intent1 = new Intent(payment_panel.this, home_panel.class);
                        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                        startActivity(intent1);
                        break;
                    case R.id.item_2:
                        // Handle selection of tab item 2
                        Intent intent2 = new Intent(payment_panel.this, admin_panel.class);
                        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                        startActivity(intent2);
                        break;
                    case R.id.item_3:
                        // Handle selection of tab item 3
                        Intent intent3 = new Intent(payment_panel.this, FragmentActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(intent3);
                        break;
                    case R.id.item_4:
                        // Handle selection of tab item 4
//                        Intent intent4 = new Intent(payment_panel.this, payment_panel.class);
//                        startActivity(intent4);
                            selectMenuItem(selectedMenuItem);
                        break;
                    case R.id.item_5:
                        // Handle selection of tab item 5
                        Intent intent5 = new Intent(payment_panel.this, reservation_panel.class);
                        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
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
    private void fetchPaymentData() {
        CollectionReference paymentCollectionRef = FirebaseFirestore.getInstance().collection("payment");

        Task<QuerySnapshot> querySnapshotTask = paymentCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Data successfully fetched
                    ArrayList<PaymentModel> arrPayment = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Extract data from the document and create a PaymentModel object
                        String id = document.getId();
                        String date = document.getString("paymentDate");
                        String room_no = document.getString("paymentRoomNumber");
                        String uid = document.getString("paymentId");
                        String status = document.getString("paymentStatus");
                        String room_p = document.getString("pricePayment");
                        arrPayment.add(new PaymentModel("Payment Id: "+uid,"Due Date: "+ date,"Room No: "+ room_no,status,"Amount: "+ room_p));
                    }
                    RecyclePaymentAdapter adapter1 = new RecyclePaymentAdapter(payment_panel.this, arrPayment);
                    paymentlist.setAdapter(adapter1);
                } else {
                    // Failed to fetch data
                }
            }
        });
    }
}
