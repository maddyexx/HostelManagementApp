package com.example.firstfirebaseproject;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleReservationAdapter extends RecyclerView.Adapter<RecycleReservationAdapter.ViewHolder>{

    Context context;
    ArrayList<RoomReservationModel> arrReservation;
    RecycleReservationAdapter(Context context, ArrayList<RoomReservationModel> arrReservation){
        this.context = context;
        this.arrReservation = arrReservation;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.room_reservation, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.r_no.setText(String.valueOf(arrReservation.get(position).room_no));
        holder.u_no.setText(String.valueOf(arrReservation.get(position).user_id));
        holder.sta.setText(arrReservation.get(position).date_of_res);
    }

    @Override
    public int getItemCount() {
        return arrReservation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView r_no, u_no, sta;
        public ViewHolder(View itemView){
            super(itemView)  ;
            r_no = itemView.findViewById(R.id.roomno);
            u_no = itemView.findViewById(R.id.userid);
            sta = itemView.findViewById(R.id.date);
        }
    }
}
