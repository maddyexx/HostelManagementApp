package com.example.firstfirebaseproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class nav_room extends Fragment {
    Toolbar toolbar;
    NavigationView navigationView;
    private RecyclerView roomRecyclerView;
    private ArrayList<UserModel> userList; // List to hold the Firestore data

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_room, container, false);

        roomRecyclerView = view.findViewById(R.id.roomRecyclerView);
        Button reservationButton = view.findViewById(R.id.reservationButton);
        fetchRoomData();
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
                // Create an instance of your RecyclerView adapter and pass the paymentList
                RoomAdapter adapter = new RoomAdapter(requireContext(), newEntries);

                // Set the adapter to your RecyclerView
                roomRecyclerView.setAdapter(adapter);
                adapter.updateData(newEntries);
            }
        });

        // Handle click on "Make Reservation" button
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Reservation activity
                Intent intent = new Intent(getActivity(), reservationByUser.class);
                startActivity(intent);
            }
        });

        return view;
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
                    RoomAdapter adapter = new RoomAdapter(requireContext(), arrUsers);

                    // Set the adapter to your RecyclerView
                    roomRecyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(requireContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
