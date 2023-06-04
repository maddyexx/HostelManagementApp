package com.example.firstfirebaseproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerUserAdapter extends RecyclerView.Adapter<RecyclerUserAdapter.ViewHolder> {
    Context context;
    ArrayList<UserModel> arrUsers;
    RecyclerUserAdapter(admin_panel context, ArrayList<UserModel> arrUsers){
        this.context = context;
        this.arrUsers = arrUsers;
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
    }

    @Override
    public int getItemCount() {
        return arrUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView r_no, u_no, sta;
        public ViewHolder(View itemView){
            super(itemView)  ;
            r_no = itemView.findViewById(R.id.roomno);
            u_no = itemView.findViewById(R.id.roomuser);
            sta = itemView.findViewById(R.id.room_status);
        }
    }
}
