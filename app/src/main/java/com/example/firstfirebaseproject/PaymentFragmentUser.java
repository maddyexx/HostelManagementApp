package com.example.firstfirebaseproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
@SuppressLint("MissingInflatedId")
public class PaymentFragmentUser extends Fragment {
    private static final String TAG = "payment_panel";
    private TextView PaymentAmount, PaymentDate, PaymentStatus, PaymentRoom;
    private Button payBtn;
    private FirebaseFirestore db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_user, container, false);
        payBtn = view.findViewById(R.id.buttonPay);
        PaymentAmount = view.findViewById(R.id.paymentAmount);
        PaymentDate = view.findViewById(R.id.paymentDate);
        PaymentRoom = view.findViewById(R.id.paymentRoom);
        PaymentStatus = view.findViewById(R.id.paymentStatus);
        db = FirebaseFirestore.getInstance();
        String userId = "user_id";
        db.collection("user").document(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String paymentAmount = document.getString("paymentAmount");
                    String paymentDate = document.getString("paymentDate");
                    String paymentStatus = document.getString("paymentStatus");
                    String paymentRoom = document.getString("paymentRoomNumber");
                    PaymentAmount.setText("Amount: $" + paymentAmount);
                    PaymentDate.setText("Date: " + paymentDate);
                    PaymentStatus.setText("Status: " + paymentStatus);
                    PaymentRoom.setText("Room Number: " + paymentRoom);
                }
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialogView = new Dialog(getActivity());
                dialogView.setContentView(R.layout.activity_payment_form);
                dialogView.setCancelable(true);
                // Retrieve the payment form inputs
                EditText editCardNumber = dialogView.findViewById(R.id.editCardNumber);
                EditText editExpiryDate = dialogView.findViewById(R.id.editExpiryDate);
                EditText editCvc = dialogView.findViewById(R.id.editCvc);
                EditText editAmount = dialogView.findViewById(R.id.editAmount);
                Button buttonSubmit = dialogView.findViewById(R.id.payformbtn);
                buttonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Get the entered card details
                        String cardNumber = editCardNumber.getText().toString();
                        String expiryDate = editExpiryDate.getText().toString();
                        String cvc = editCvc.getText().toString();
                        String amount = editAmount.getText().toString();
                        // Use the card details to process the payment via Stripe
                        new CreatePaymentIntentTask().execute(amount, "USD");
                        // Close the dialog after payment processing is complete
                        dialogView.dismiss();
                    }
                });
                dialogView.show();
                // Update payment status to "Paid" in Firestore
                String userId = "paymentId"; // Replace with the actual user ID
                db.collection("payment")
                        .whereEqualTo("paymentId", userId)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    DocumentSnapshot paymentDoc = queryDocumentSnapshots.getDocuments().get(0);
                                    String paymentId = paymentDoc.getId();
                                    DocumentReference paymentRef = db.collection("payment").document(paymentId);
                                    paymentRef.update("paymentStatus", "Paid")
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // Update UI or perform any other operations upon successful update

                                                    Toast.makeText(getActivity(), "Payment paid successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    PaymentStatus.setText("Status: Paid");
                                                    //Toast.makeText(payment_user.this, "Failed to update payment status", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    PaymentStatus.setText("Status: Paid");
                                    PaymentStatus.setTextColor(ColorStateList.valueOf(Color.GREEN));
//                                    Toast.makeText(payment_user.this, "Payment not found for the user", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to retrieve payment information", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        return view;
    }
}