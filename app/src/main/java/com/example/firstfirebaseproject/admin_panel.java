package com.example.firstfirebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class admin_panel extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerViewuser, roomreservation, paymentlist;
    Button addbtn;
    ImageView delete_icon;
    private MenuItem selectedMenuItem;
    AnimatedBottomBar bottomBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        recyclerViewuser = findViewById(R.id.recyleruser);
        roomreservation = findViewById(R.id.roomRequestsRecyclerView);
//        roomreservation.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewuser.setLayoutManager(new LinearLayoutManager(this));
        paymentlist = findViewById(R.id.paymentRecyclerView);
        addbtn = findViewById(R.id.roomaddbtn);
        delete_icon = findViewById(R.id.deleteIcon);
        bottomBar = findViewById(R.id.bottom_bar);
//        paymentlist.setLayoutManager(new LinearLayoutManager(this));
        fetchRoomData();
//        fetchReservationData();
//        fetchPaymentData();
        setUpDrawer();
        bottomNavigationSetUp();
        selectMenuItem(selectedMenuItem);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupForm();
            }
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference entriesRef = db.collection("Rooms");
        ListenerRegistration listenerRegistration = entriesRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // Handle any errors
                    return;
                }
                // Create a new list to hold the updated entries
                ArrayList<UserModel> newEntries = new ArrayList<>();
                List<DocumentSnapshot> documents = value.getDocuments();
                for (QueryDocumentSnapshot document : value) {
                    String id = document.getId();
                    String status = document.getString("status_Rooms");
                    String room_no = document.getString("room_no_Rooms");
                    String uid = document.getString("guests_Rooms");
                    newEntries.add(new UserModel("Room No: "+room_no,"Guests: "+uid,"Occupied: "+status));
                }

                // Create an instance of your RecyclerView adapter and pass the paymentList
                RecyclerUserAdapter adapter = new RecyclerUserAdapter(admin_panel.this, newEntries, documents);

                // Set the adapter to your RecyclerView
                recyclerViewuser.setAdapter(adapter);
                adapter.updateData(newEntries);
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
                        Intent intent1 = new Intent(admin_panel.this, home_panel.class);
                        startActivity(intent1);
                        break;
                    case R.id.item_2:
                        // Handle selection of tab item 2
//                        Intent intent2 = new Intent(admin_panel.this, DestinationActivity2.class);
//                        startActivity(intent2);
                        break;
                    case R.id.item_3:
                        // Handle selection of tab item 3
                        Intent intent3 = new Intent(admin_panel.this, FragmentActivity.class);
                        startActivity(intent3);
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
        selectedMenuItem = menu.findItem(R.id.room_menu);
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
                } else if (id == R.id.about_menu) {
                    // Perform action for item2
                    // Example: Load fragment or start activity
                    selectMenuItem(item);
                    Intent i = new Intent(admin_panel.this, about_panel.class);
                    startActivity(i);
                } else if (id == R.id.contact_us_menu) {
                    // Perform action for item3
                    // Example: Load fragment or start activity
                    //loadFragment(new Item3Fragment());
                    selectMenuItem(item);
                    Intent i = new Intent(admin_panel.this, user_panel.class);
                    startActivity(i);
                } else if (id == R.id.logout_menu) {
                    Intent intent = new Intent(admin_panel.this, FragmentActivity.class);
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
//    private void loadFragment(Fragment fragment) {
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container, fragment)
//                .commit();
//    }
    private void showPopupForm() {
        Dialog dialog = new Dialog(admin_panel.this);
        dialog.setContentView(R.layout.popup_form);
        dialog.setCancelable(true);
        // Get references to the form elements
        EditText r_number = dialog.findViewById(R.id.roomno_popup);
        EditText g_number = dialog.findViewById(R.id.guests_popup);
        EditText occupy = dialog.findViewById(R.id.occupy_popup);
        Button btnSubmit = dialog.findViewById(R.id.roomaddbtn_popup);
        // Set click listener for the submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform form submission logic here
                String room_number = r_number.getText().toString();
                String guest_number = g_number.getText().toString();
                String occupy_value = occupy.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference RoomsCollection = db.collection("Rooms");
                Rooms rooms = new Rooms();
                if (TextUtils.isEmpty(room_number) || TextUtils.isEmpty(guest_number) || TextUtils.isEmpty(occupy_value)) {
                    Toast.makeText(admin_panel.this, "Fill the required fields", Toast.LENGTH_SHORT).show();
                } else {
                    rooms.setRoom_no_Rooms(room_number);
                    rooms.setStatus_Rooms(occupy_value);
                    rooms.setGuests_Rooms(guest_number);
                    // Generate a new document ID
                    String id;
                    try {
                        id = rooms.getRoom_no_Rooms() + "_" + RoomsCollection.document().getId();
                        id = id.replaceAll("\\s", "");
                        rooms.setRoom_id(id);
                    } catch (NumberFormatException e) {
                        // Handle the case when the phone value cannot be parsed
                        Toast.makeText(admin_panel.this, "Invalid ID", Toast.LENGTH_SHORT).show();
                        return; // Exit the method to prevent further execution
                    }
                    if (occupy_value.equals("Yes") || occupy_value.equals("no") || occupy_value.equals("yes") || occupy_value.equals("No")) {
                        RoomsCollection.document(id).set(rooms)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(admin_panel.this, "Room added Successfully!", Toast.LENGTH_SHORT).show();
                                        r_number.setText(" ");
                                        g_number.setText("");
                                        occupy.setText(" ");
                                    } else {
                                        Toast.makeText(admin_panel.this, "Failed to Add Room, Connection Error!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        dialog.dismiss();
                    } else {
                        Toast.makeText(admin_panel.this, "Only Yes or No accepted in Occupied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialog.show();
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
                        String date = document.getString("date");
                        String room_no = document.getString("room_no");
                        String uid = document.getString("id");
                        String status = document.getString("status");
                        arrPayment.add(new PaymentModel(uid, date, room_no,status));
                    }
                    RecyclePaymentAdapter adapter1 = new RecyclePaymentAdapter(admin_panel.this, arrPayment);
                    paymentlist.setAdapter(adapter1);
                } else {
                    // Failed to fetch data
                }
            }
        });
    }

    private void fetchReservationData() {
        CollectionReference paymentCollectionRef = FirebaseFirestore.getInstance().collection("Reservation_Request");

        Task<QuerySnapshot> querySnapshotTask = paymentCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Data successfully fetched
                    ArrayList<RoomReservationModel> arrReservation = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Extract data from the document and create a PaymentModel object
                        String id = document.getId();
                        String date = document.getString("date");
                        String room_no = document.getString("room_no");
                        String uid = document.getString("id");
                        arrReservation.add(new RoomReservationModel(uid, date, room_no));
                    }
                    RecycleReservationAdapter adapter1 = new RecycleReservationAdapter(admin_panel.this, arrReservation);
                    roomreservation.setAdapter(adapter1);
                } else {
                    // Failed to fetch data
                    Toast.makeText(admin_panel.this, "Connection Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchRoomData() {
        CollectionReference paymentCollectionRef = FirebaseFirestore.getInstance().collection("Rooms");

        Task<QuerySnapshot> querySnapshotTask = paymentCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Data successfully fetched
                    ArrayList<UserModel> arrUsers = new ArrayList<>();
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Extract data from the document and create a PaymentModel object
                        String id = document.getId();
                        String status = document.getString("status_Rooms");
                        String room_no = document.getString("room_no_Rooms");
                        String uid = document.getString("guests_Rooms");
                        arrUsers.add(new UserModel("Room No: "+room_no,"Guests: "+uid,"Occupied: "+status));
                    }

                    // Create an instance of your RecyclerView adapter and pass the paymentList
                    RecyclerUserAdapter adapter = new RecyclerUserAdapter(admin_panel.this, arrUsers, documents);

                    // Set the adapter to your RecyclerView
                    recyclerViewuser.setAdapter(adapter);
                } else {
                    // Failed to fetch data
                    Toast.makeText(admin_panel.this, "Connection Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}