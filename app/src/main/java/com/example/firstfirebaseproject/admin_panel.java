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
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private ArrayList<UserModel> userList; // List to hold the Firestore data
    private ArrayList<UserModel> filteredList; // List to hold the filtered data
    private RecyclerUserAdapter searchadapter; // Adapter for the RecyclerView
    private EditText searchEditText; // EditText for the search query

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        recyclerViewuser = findViewById(R.id.recyleruser);
        recyclerViewuser.setLayoutManager(new LinearLayoutManager(this));
        addbtn = findViewById(R.id.roomaddbtn);
        delete_icon = findViewById(R.id.deleteIcon);
        bottomBar = findViewById(R.id.bottom_bar);
        userList = new ArrayList<>();
        filteredList = new ArrayList<>();
        searchEditText = findViewById(R.id.searchEditText);
        List<DocumentSnapshot> documents = null;
        searchadapter = new RecyclerUserAdapter(admin_panel.this, filteredList, documents);
        fetchRoomData();
        setUpDrawer();
        bottomNavigationSetUp();
        selectMenuItem(selectedMenuItem);
        searchdata();
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
                    String pr = document.getString("price");
                    String r_type = document.getString("room_type");
                    newEntries.add(new UserModel("Room No: "+room_no,"Guests: "+uid,"Occupied: "+status,pr,r_type));
                }
                userList = newEntries;
                filterData(searchEditText.getText().toString());
                searchadapter = new RecyclerUserAdapter(admin_panel.this, filteredList, documents);
                recyclerViewuser.setAdapter(searchadapter);
                // Create an instance of your RecyclerView adapter and pass the paymentList
                RecyclerUserAdapter adapter = new RecyclerUserAdapter(admin_panel.this, newEntries, documents);

                // Set the adapter to your RecyclerView
                recyclerViewuser.setAdapter(adapter);
                adapter.updateData(newEntries);
            }
        });
    }

    private void searchdata() {

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used in this case
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter the data based on the search query
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private void filterData(String query) {
        filteredList.clear();
        // Filter the data based on the search query
        for (UserModel userModel : userList) {
            if (userModel.room_no.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(userModel);
            }
        }
        // Notify the adapter that the data has changed
        searchadapter.notifyDataSetChanged();
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
                        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
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
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(intent3);
                        break;
                    case R.id.item_4:
                        // Handle selection of tab item 3
                        Intent intent4 = new Intent(admin_panel.this, payment_panel.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(intent4);
                        break;
                    case R.id.item_5:
                        // Handle selection of tab item 3
                        Intent intent5 = new Intent(admin_panel.this, reservation_panel.class);
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
                    Intent i = new Intent(admin_panel.this, home_panel.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(i);
                } else if (id == R.id.payment_menu) {
                    // Perform action for item2
                    // Example: Load fragment or start activity
                    selectMenuItem(item);
                    Intent i = new Intent(admin_panel.this, payment_panel.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(i);
                } else if (id == R.id.reservation_menu) {
                    // Perform action for item3
                    selectMenuItem(item);
                    Intent i = new Intent(admin_panel.this, reservation_panel.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(i);
                } else if (id == R.id.logout_menu) {
                    Intent intent = new Intent(admin_panel.this, FragmentActivity.class);
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
        EditText room_t = dialog.findViewById(R.id.room_type_popup);
        EditText pri = dialog.findViewById(R.id.price_popup);
        Button btnSubmit = dialog.findViewById(R.id.roomaddbtn_popup);
        // Set click listener for the submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform form submission logic here
                String room_number = r_number.getText().toString();
                String guest_number = g_number.getText().toString();
                String occupy_value = occupy.getText().toString();
                String price_value = pri.getText().toString();
                String room_t_value = room_t.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference RoomsCollection = db.collection("Rooms");
                Rooms rooms = new Rooms();
                payment payment = new payment();
                if (TextUtils.isEmpty(room_number) || TextUtils.isEmpty(guest_number) || TextUtils.isEmpty(occupy_value)) {
                    Toast.makeText(admin_panel.this, "Fill the required fields", Toast.LENGTH_SHORT).show();
                } else {
                    rooms.setRoom_no_Rooms(room_number);
                    rooms.setStatus_Rooms(occupy_value);
                    rooms.setGuests_Rooms(guest_number);
                    rooms.setRoom_type(room_t_value);
                    rooms.setPrice(price_value);
                    if (occupy_value.equalsIgnoreCase("Yes")) {
                        CollectionReference PaymentsCollection = db.collection("payment");
                        String p_id;
                        p_id = rooms.getRoom_no_Rooms() + "_" + PaymentsCollection.document().getId();
                        p_id = p_id.replaceAll("\\s", "");
                        // Add the payment pending for the room
                        String paymentId = p_id; // Generate a new document ID for the payment
                        String paymentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()); ; // Set the payment date as desired
                        String paymentRoomNumber = room_number; // Use the room number for payment
                        String paymentStatus = "Pending"; // Set the initial payment status
                        String pricePayment = price_value;
                        // Create a new PaymentModel object for the pending payment
                        payment.setPaymentId(p_id);
                        payment.setPaymentDate(paymentDate);
                        payment.setPricePayment(pricePayment);
                        payment.setPaymentRoomNumber(paymentRoomNumber);
                        payment.setPaymentStatus(paymentStatus);
//                        PaymentModel pendingPayment = new PaymentModel(paymentId, paymentDate, paymentRoomNumber, paymentStatus, pricePayment);

                        // Add the pending payment to the Payments collection in Firestore
                        PaymentsCollection.document(paymentId).set(payment)
                                .addOnCompleteListener(paymentTask -> {
                                    if (paymentTask.isSuccessful()) {
                                        // Payment added successfully
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
                                        if (occupy_value.equals("Yes") || occupy_value.equals("no") || occupy_value.equals("yes") || occupy_value.equals("No") || room_t_value.equals("Single") || room_t_value.equals("Double")) {
                                            RoomsCollection.document(id).set(rooms)
                                                    .addOnCompleteListener(task -> {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(admin_panel.this, "Room added Successfully!", Toast.LENGTH_SHORT).show();
                                                            r_number.setText(" ");
                                                            g_number.setText("");
                                                            occupy.setText(" ");
                                                            room_t.setText(" ");
                                                            pri.setText(" ");
                                                        } else {
                                                            Toast.makeText(admin_panel.this, "Failed to Add Room, Connection Error!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }else {
                                            Toast.makeText(admin_panel.this, "Only Yes or No accepted in Occupied & Single or Double accepted in Room Type", Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.dismiss();
                                    }else {
                                        // Failed to add payment
                                        Toast.makeText(admin_panel.this, "Failed to add payment. Connection error!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        // Occupancy value is neither "Yes" nor "No"
                        Toast.makeText(admin_panel.this, "Invalid occupancy value. Only 'Yes' or 'No' is accepted.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialog.show();
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
                        String pr = document.getString("price");
                        String r_type = document.getString("room_type");
                        arrUsers.add(new UserModel("Room No: "+room_no,"Guests: "+uid,"Occupied: "+status,pr,r_type));
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