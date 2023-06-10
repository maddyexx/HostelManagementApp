package com.example.firstfirebaseproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class contact_us extends Fragment {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText messageEditText;

    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        db = FirebaseFirestore.getInstance();

        nameEditText = view.findViewById(R.id.nameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        messageEditText = view.findViewById(R.id.messageEditText);

        Button submitBtn = view.findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String message = messageEditText.getText().toString();

                // Create a Contact object
                Contact contact = new Contact(name, email, message);

                // Save the contact_us information to Firestore
                db.collection("contact_form").add(contact)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Contact information saved successfully
                                Toast.makeText(getActivity(), "Contact information submitted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to save contact_us information
                                Toast.makeText(getActivity(), "Failed to submit contact_us information", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return view;
    }
}