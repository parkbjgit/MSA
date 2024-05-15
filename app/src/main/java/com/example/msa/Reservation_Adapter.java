package com.example.msa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
public class Reservation_Adapter extends RecyclerView.Adapter<Reservation_Adapter.MyViewHolder> {
    String data1[], data2[];
    Context context;

    public Reservation_Adapter(Context ct, String s1[], String s2[]){
        context = ct;
        data1 = s1;
        data2 = s2;
    }


    @Override
    public void onBindViewHolder(@NonNull Reservation_Adapter.MyViewHolder holder, int position) {
        holder.facilities_name.setText(data1[position]);
        holder.facilities_time.setText(data2[position]);
    }

    @Override
    public int getItemCount() {
        //Log.d("ReservationAdapter", "data1.length: " + data1.length);
        return data1.length;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reservation_cus_list, parent, false);
        return new MyViewHolder(view);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView facilities_name, facilities_time;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            facilities_name = itemView.findViewById(R.id.facility_name);
            facilities_time = itemView.findViewById(R.id.facility_time);
        }
    }
}