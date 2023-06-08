package com.example.firstfirebaseproject;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclePaymentAdapter extends RecyclerView.Adapter<RecyclePaymentAdapter.ViewHolder> {
    Context context;
    ArrayList<PaymentModel> arrPayment;
    RecyclePaymentAdapter(admin_panel context, ArrayList<PaymentModel> arrPayment){
        this.context = (Context) context;
        this.arrPayment = arrPayment;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.payment_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.r_no_p.setText(String.valueOf(arrPayment.get(position).room_no_payment));
        holder.u_no_p.setText(String.valueOf(arrPayment.get(position).user_id_payment));
        holder.sta_p.setText(arrPayment.get(position).date_of_res_payment);
        holder.pay_btn.setText(arrPayment.get(position).payment_status);
        if (arrPayment.get(position).payment_status.equals("pending")||arrPayment.get(position).payment_status.equals("Pending")) {
            int color = Color.RED;
            holder.pay_btn.setBackgroundTintList(ColorStateList.valueOf(color));
        } else {
            int color = Color.GREEN;
            holder.pay_btn.setBackgroundTintList(ColorStateList.valueOf(color));
            holder.pay_btn.setTextColor(Color.DKGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return arrPayment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView r_no_p, u_no_p, sta_p;
        Button pay_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            r_no_p = itemView.findViewById(R.id.roomno);
            u_no_p = itemView.findViewById(R.id.userid);
            sta_p = itemView.findViewById(R.id.date);
            pay_btn = itemView.findViewById(R.id.payment_btn);
        }
    }
}
