package com.example.firstfirebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class admin_panel extends AppCompatActivity {
    Toolbar toobar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerViewuser, roomreservation, paymentlist;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        toobar = findViewById(R.id.toobar);
        drawerLayout = findViewById(R.id.drawableLayout);
        navigationView = findViewById(R.id.navigation_menu);
        recyclerViewuser = findViewById(R.id.recyleruser);
        roomreservation = findViewById(R.id.roomRequestsRecyclerView);
        roomreservation.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewuser.setLayoutManager(new LinearLayoutManager(this));
        paymentlist = findViewById(R.id.paymentRecyclerView);
        paymentlist.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(toobar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout, toobar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        fetchRoomData();
        fetchReservationData();
        fetchPaymentData();
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
                        String uid = document.getString("user_id");
                        String status = document.getString("status");
                        arrPayment.add(new PaymentModel("3", "10-20-24", "3","pending"));
                    }
                    RecyclePaymentAdapter adapter1 = new RecyclePaymentAdapter(admin_panel.this, arrPayment);
                    paymentlist.setAdapter(adapter1);
                } else {
                    // Failed to fetch data
                    Toast.makeText(admin_panel.this, "Connection Error", Toast.LENGTH_SHORT).show();
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
                        String uid = document.getString("user_id");
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
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Extract data from the document and create a PaymentModel object
                        String id = document.getId();
                        String status = document.getString("status");
                        String room_no = document.getString("room_no");
                        String uid = document.getString("user_id");
                        arrUsers.add(new UserModel(room_no, uid, status));
                    }

                    // Create an instance of your RecyclerView adapter and pass the paymentList
                    RecyclerUserAdapter adapter = new RecyclerUserAdapter(admin_panel.this, arrUsers);

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