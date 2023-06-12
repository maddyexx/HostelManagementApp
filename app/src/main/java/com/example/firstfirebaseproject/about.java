package com.example.firstfirebaseproject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class about extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText messageEditText;

    // TODO: Rename and change types and number of parameters
    public static about newInstance(String param1, String param2) {
        about fragment = new about();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public about() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        Button btnContact = view.findViewById(R.id.button_contact);
        btnContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Replace the current fragment with the "Contact Us" fragment
                showPopupForm();
            }

        });
        return view;
    }
    private void showPopupForm() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_contact);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        dialog.setCancelable(true);
        // Get references to the form elements
        nameEditText = dialog.findViewById(R.id.nameEditText);
        emailEditText = dialog.findViewById(R.id.emailEditText);
        messageEditText = dialog.findViewById(R.id.messageEditText);
        Button submitBtn = dialog.findViewById(R.id.submitBtn);
        // Set click listener for the submit button
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
                                dialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to save contact_us information
                                Toast.makeText(getActivity(), "Failed to submit contact_us information", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
            }
        });
        dialog.show();
    }
}