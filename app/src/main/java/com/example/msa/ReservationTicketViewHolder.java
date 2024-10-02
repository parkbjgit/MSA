package com.example.msa;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ReservationTicketViewHolder extends RecyclerView.ViewHolder {

    TextView tkName;

    public ReservationTicketViewHolder(View a_itemView, final ReservationTicketAdapter.OnItemClickEventListener a_itemClickListener) {
        super(a_itemView);

        tkName = itemView.findViewById(R.id.ticket_name);

        a_itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View a_view) {
                final int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    a_itemClickListener.onItemClick(a_view, position);
                }
            }
        });
    }
}
