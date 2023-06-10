package com.example.firstfirebaseproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class RecyclerUserAdapter extends RecyclerView.Adapter<RecyclerUserAdapter.ViewHolder> {
    Context context;
    ArrayList<UserModel> arrUsers;
    List<UserModel> entries;
    private FirebaseFirestore db;

    private List<DocumentSnapshot> documents;

    public void updateData(ArrayList<UserModel> newEntries) {
        // Replace the existing data set with the new entries
        this.entries = newEntries;
        notifyDataSetChanged();
    }
    RecyclerUserAdapter(Context context, ArrayList<UserModel> arrUsers, List<DocumentSnapshot> documents){
        this.context = context;
        this.arrUsers = arrUsers;
        this.documents = documents;
        db = FirebaseFirestore.getInstance();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_user_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.r_no.setText(String.valueOf(arrUsers.get(position).room_no));
        holder.u_no.setText(String.valueOf(arrUsers.get(position).user_no));
        holder.sta.setText(arrUsers.get(position).status);
        DocumentSnapshot document = documents.get(position);
        String documentId = document.getId();
        holder.setDocumentId(documentId);
    }

    @Override
    public int getItemCount() {
        return arrUsers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView r_no, u_no, sta;
        private String documentId;
        ImageView delete_btn, edit_btn;

        public ViewHolder(View itemView) {
            super(itemView);
            r_no = itemView.findViewById(R.id.roomno);
            delete_btn = itemView.findViewById(R.id.deleteIcon);
            edit_btn = itemView.findViewById(R.id.editIcon);
            u_no = itemView.findViewById(R.id.roomuser);
            sta = itemView.findViewById(R.id.room_status);
            delete_btn.setOnClickListener(view -> {
                deleteRoom(documentId);
            });
            edit_btn.setOnClickListener(view -> {
                editRoom(documentId);
            });
        }
            private void deleteRoom(String documentId) {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext(), R.style.AlertDialogCustom);
            builder.setTitle("Confirm Deletion");
            builder.setMessage("Are you sure you want to delete this room?");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // User clicked the Delete button, proceed with deletion
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Rooms")
                            .document(documentId)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Document successfully deleted
                                    Toast.makeText(itemView.getContext(), "Room deleted successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error occurred while deleting the document
                                    Toast.makeText(itemView.getContext(), "Failed to delete Room: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // User clicked the Cancel button, do nothing
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        private void editRoom(String documentId) {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext(), R.style.AlertDialogCustom);
            builder.setTitle("Edit Room");
            builder.setMessage("Are you sure you want to edit this room?"); // Update the message accordingly

            builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showPopupForm(documentId);
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // User clicked the Cancel button, do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        private void showPopupForm(String documentId) {
            Dialog dialog = new Dialog(itemView.getContext());
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
                    String updatedRoomNumber = r_number.getText().toString();
                    String updatedGuestNumber = g_number.getText().toString();
                    String updatedOccupancyStatus = occupy.getText().toString();

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference roomRef = db.collection("Rooms").document(documentId);

                    if (TextUtils.isEmpty(updatedRoomNumber) || TextUtils.isEmpty(updatedGuestNumber) || TextUtils.isEmpty(updatedOccupancyStatus)) {
                        Toast.makeText(itemView.getContext(), "Fill the required fields", Toast.LENGTH_SHORT).show();
                    } else {
                        // Update the document with the new values
                        roomRef.update(
                                "room_no_Rooms", updatedRoomNumber,
                                "guests_Rooms", updatedGuestNumber,
                                "status_Rooms", updatedOccupancyStatus
                        ).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(itemView.getContext(), "Room updated successfully", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(itemView.getContext(), "Failed to update room: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

            dialog.show();
        }


        public void setDocumentId(String documentId1) {
            this.documentId = documentId1;
        }
    }
}
