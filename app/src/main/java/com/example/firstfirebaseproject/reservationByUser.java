package com.example.firstfirebaseproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class reservationByUser extends AppCompatActivity {
    private EditText arrivalDateEditText;
    private EditText numberOfGuestsEditText;
    private EditText contactInformationEditText;
    private EditText roomTypeEditText;
    private FirebaseFirestore db;
    Button reserveButton, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_by_user);
        initComponents();
        settingUpListeners();
    }

    private void initComponents() {
        db = FirebaseFirestore.getInstance();
        reserveButton = findViewById(R.id.reserveButtonbookroom);
        arrivalDateEditText = findViewById(R.id.arrivalDateEditText);
        numberOfGuestsEditText = findViewById(R.id.numberOfGuestsEditText);
        contactInformationEditText = findViewById(R.id.contactInformationEditText);
        roomTypeEditText = findViewById(R.id.roomTypeEditText);

    }

    private void settingUpListeners() {
        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arrivalDate = arrivalDateEditText.getText().toString();
                String numberOfGuests = numberOfGuestsEditText.getText().toString();
                String contactInformation = contactInformationEditText.getText().toString();
                String roomType = roomTypeEditText.getText().toString();

                Reservation reservation = new Reservation(arrivalDate, numberOfGuests, contactInformation, roomType);

                db.collection("reservationByUser").add(reservation)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Reservation saved successfully
                                Toast.makeText(reservationByUser.this, "Reservation successful", Toast.LENGTH_SHORT).show();
                                arrivalDateEditText.setText("");
                                numberOfGuestsEditText.setText("");
                                contactInformationEditText.setText("");
                                roomTypeEditText.setText("");
                                startActivity(new Intent(reservationByUser.this, user_panel.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to save reservation
                                Toast.makeText(reservationByUser.this, "Failed to make a reservation", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}