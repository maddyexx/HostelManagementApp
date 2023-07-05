//package com.example.firstfirebaseproject;
//
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.content.res.ColorStateList;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QuerySnapshot;
//
//public class payment_user extends AppCompatActivity {
//    private static final String TAG = "payment_panel";
//    private TextView PaymentAmount, PaymentDate, PaymentStatus, PaymentRoom;
//    private Button payBtn;
//    private FirebaseFirestore db;
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_payment_user);
//        initComponents();
//
//        String userId = "user_id";
//        db.collection("user").document(userId).get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                DocumentSnapshot document = task.getResult();
//                if (document.exists()) {
//                    String paymentAmount = document.getString("paymentAmount");
//                    String paymentDate = document.getString("paymentDate");
//                    String paymentStatus = document.getString("paymentStatus");
//                    String paymentRoom = document.getString("paymentRoomNumber");
//                    PaymentAmount.setText("Amount: $" + paymentAmount);
//                    PaymentDate.setText("Date: " + paymentDate);
//                    PaymentStatus.setText("Status: " + paymentStatus);
//                    PaymentRoom.setText("Room Number: " + paymentRoom);
//                }
//            }
//        });
//
//        payBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(payment_user.this);
//                LayoutInflater inflater = getLayoutInflater();
//                View dialogView = inflater.inflate(R.layout.activity_payment_form, null);
//                builder.setView(dialogView);
//
//                // Retrieve the payment form inputs
//                EditText editCardNumber = dialogView.findViewById(R.id.editCardNumber);
//                EditText editExpiryDate = dialogView.findViewById(R.id.editExpiryDate);
//                EditText editCvc = dialogView.findViewById(R.id.editCvc);
//                EditText editAmount = dialogView.findViewById(R.id.editAmount);
//                Button buttonSubmit = dialogView.findViewById(R.id.payformbtn);
//                AlertDialog alertDialog = builder.create();
//                buttonSubmit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // Get the entered card details
//                        String cardNumber = editCardNumber.getText().toString();
//                        String expiryDate = editExpiryDate.getText().toString();
//                        String cvc = editCvc.getText().toString();
//                        String amount = editAmount.getText().toString();
//                        // Use the card details to process the payment via Stripe
//                        new CreatePaymentIntentTask().execute(amount, "USD");
//                        // Close the dialog after payment processing is complete
//                        alertDialog.dismiss();
//                    }
//                });
//                alertDialog.show();
//                // Update payment status to "Paid" in Firestore
//                String userId = "paymentId"; // Replace with the actual user ID
//                db.collection("payment")
//                        .whereEqualTo("paymentId", userId)
//                        .get()
//                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                            @Override
//                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                if (!queryDocumentSnapshots.isEmpty()) {
//                                    DocumentSnapshot paymentDoc = queryDocumentSnapshots.getDocuments().get(0);
//                                    String paymentId = paymentDoc.getId();
//                                    DocumentReference paymentRef = db.collection("payment").document(paymentId);
//                                    paymentRef.update("paymentStatus", "Paid")
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//                                                    // Update UI or perform any other operations upon successful update
//
//                                                    Toast.makeText(payment_user.this, "Payment paid successfully", Toast.LENGTH_SHORT).show();
//                                                }
//                                            })
//                                            .addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    PaymentStatus.setText("Status: Paid");
//                                                    //Toast.makeText(payment_user.this, "Failed to update payment status", Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//                                } else {
//                                        PaymentStatus.setText("Status: Paid");
//                                        PaymentStatus.setTextColor(ColorStateList.valueOf(Color.GREEN));
////                                    Toast.makeText(payment_user.this, "Payment not found for the user", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(payment_user.this, "Failed to retrieve payment information", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
//        });
//    }
//    private void initComponents() {
//        payBtn = findViewById(R.id.buttonPay);
//        PaymentAmount = findViewById(R.id.paymentAmount);
//        PaymentDate = findViewById(R.id.paymentDate);
//        PaymentRoom = findViewById(R.id.paymentRoom);
//        PaymentStatus = findViewById(R.id.paymentStatus);
//        db = FirebaseFirestore.getInstance();
//    }
//}
