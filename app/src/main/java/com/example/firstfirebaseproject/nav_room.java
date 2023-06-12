package com.example.firstfirebaseproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

public class nav_room extends Fragment {
    private EditText arrivalDateEditText;
    private EditText numberOfGuestsEditText;
    private EditText contactInformationEditText;
    private EditText roomTypeEditText;
    private RecyclerView roomRecyclerView;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_room, container, false);

        roomRecyclerView = view.findViewById(R.id.roomRecyclerView);
        Button reservationButton = view.findViewById(R.id.reservationButton);
        roomRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fetchRoomData();
        // Handle click on "Make Reservation" button
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Reservation activity
                showPopupForm();
            }
        });
        return view;
    }
private void fetchRoomData() {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference roomsRef = db.collection("Rooms");
    String targetStatus = "No";
    Query query = roomsRef.whereEqualTo("status_Rooms", targetStatus.toLowerCase());
    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                ArrayList<UserModel> arrUsers = new ArrayList<>();
                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                for (DocumentSnapshot document : documents) {
                    String id = document.getId();
                    String status = document.getString("status_Rooms");
                    String room_no = document.getString("room_no_Rooms");
                    String uid = document.getString("guests_Rooms");
                    String pr = document.getString("price");
                    String r_type = document.getString("room_type");
                    arrUsers.add(new UserModel("Room No: " + room_no, "Guests: " + uid, "Occupied: " + status, pr+"Rs", r_type));
                }
                Log.d("RoomAdapter", "arrUsers values: " + arrUsers.toString());
                RoomAdapter adapter = new RoomAdapter(getActivity(), arrUsers);
                roomRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(requireContext(), "Connection Error", Toast.LENGTH_SHORT).show();
            }
        }
    });
}
    private void showPopupForm() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.activity_reservation_by_user);
        dialog.setCancelable(true);
        // Get references to the form elements
        arrivalDateEditText = dialog.findViewById(R.id.arrivalDateEditText);
        numberOfGuestsEditText = dialog.findViewById(R.id.numberOfGuestsEditText);
        contactInformationEditText = dialog.findViewById(R.id.contactInformationEditText);
        roomTypeEditText = dialog.findViewById(R.id.roomTypeEditText);
        Button btnSubmit = dialog.findViewById(R.id.reserveButtonbookroom);
        // Set click listener for the submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arrivalDate = arrivalDateEditText.getText().toString();
                String numberOfGuests = numberOfGuestsEditText.getText().toString();
                String contactInformation = contactInformationEditText.getText().toString();
                String roomType = roomTypeEditText.getText().toString();

                Reservation reservation = new Reservation(arrivalDate, numberOfGuests, contactInformation, roomType);
                db = FirebaseFirestore.getInstance();
                db.collection("reservationByUser").add(reservation)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Reservation saved successfully
                                Toast.makeText(requireContext(), "Reservation successful", Toast.LENGTH_SHORT).show();
                                arrivalDateEditText.setText("");
                                numberOfGuestsEditText.setText("");
                                contactInformationEditText.setText("");
                                roomTypeEditText.setText("");
                                dialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to save reservation
                                Toast.makeText(requireContext(), "Failed to make a reservation", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
            }
        });
        dialog.show();
    }
}
