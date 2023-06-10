package com.example.firstfirebaseproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    Context context;
    ArrayList<UserModel> arrUsers;
    List<UserModel> entries;
    private FirebaseFirestore db;

    RoomAdapter(Context context, ArrayList<UserModel> arrUsers) {
        this.context = context;
        this.arrUsers = arrUsers;
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_room, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.ViewHolder holder, int position) {
//        holder.r_no.setText(String.valueOf(arrUsers.get(position).room_no));
//        holder.u_no.setText(String.valueOf(arrUsers.get(position).user_no));
//        holder.sta.setText(arrUsers.get(position).status);
//        holder.pr_nav_room.setText(arrUsers.get(position).price);
        if (arrUsers != null) {
            if (arrUsers.get(position).room_no != null) {
                holder.r_no.setText(String.valueOf(arrUsers.get(position).room_no));
            } else {
                holder.r_no.setText("Room No: null");
            }

            if (arrUsers.get(position).user_no != null) {
                holder.u_no.setText(arrUsers.get(position).user_no);
            } else {
                holder.u_no.setText("Guests: null");
            }
            if (arrUsers.get(position).price != null) {
                holder.pr_nav_room.setText(arrUsers.get(position).price);
            } else {
                holder.pr_nav_room.setText("Amount: 0");
            }
            if (arrUsers.get(position).status != null) {
                holder.sta.setText(arrUsers.get(position).status);
            } else {
                holder.sta.setText("Occupy: null");
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrUsers.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ArrayList<UserModel> newEntries) {
        // Replace the existing data set with the new entries
        this.entries = newEntries;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView r_no, u_no, sta, pr_nav_room;

        public ViewHolder(View itemView) {
            super(itemView);
            r_no = itemView.findViewById(R.id.roomno_navroom);
            u_no = itemView.findViewById(R.id.roomuser_navroom);
            pr_nav_room = itemView.findViewById(R.id.priceroom_navroom);
        }
    }
}
