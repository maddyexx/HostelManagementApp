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
    ArrayList<Reservation> arrReservation;
    RecycleReservationAdapter(Context context, ArrayList<Reservation> arrReservation){
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
        holder.date_r.setText(String.valueOf(arrReservation.get(position).getArrivalDate()));
        holder.contact_r.setText(String.valueOf(arrReservation.get(position).getContactInformation()));
        holder.guest_res.setText(arrReservation.get(position).getNumberOfGuests());
        holder.roomType_res.setText(arrReservation.get(position).getRoomType());
    }

    @Override
    public int getItemCount() {
        return arrReservation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView date_r, contact_r, guest_res, roomType_res;
        public ViewHolder(View itemView){
            super(itemView)  ;
            date_r = itemView.findViewById(R.id.date_res);
            contact_r = itemView.findViewById(R.id.contact_res);
            guest_res = itemView.findViewById(R.id.guests_no_res);
            roomType_res = itemView.findViewById(R.id.room_type_res);
        }
    }
}
