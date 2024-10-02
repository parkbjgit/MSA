package com.example.msa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReservationTicketAdapter extends RecyclerView.Adapter<ReservationTicketViewHolder> {

    public interface OnItemClickEventListener {
        void onItemClick(View a_view, int a_position);
    }

    private List<ReservationTicketRecyclerItem> mItemList;

    private OnItemClickEventListener mItemClickListener;

    public ReservationTicketAdapter (List<ReservationTicketRecyclerItem> a_list) {
        mItemList = a_list;
    }


    @Override
    public ReservationTicketViewHolder onCreateViewHolder(ViewGroup a_viewGroup, int a_viewType) {
        View view = LayoutInflater.from(a_viewGroup.getContext()).inflate(R.layout.reservation_ticket_list, a_viewGroup, false);
        return new ReservationTicketViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(ReservationTicketViewHolder a_viewHolder, int a_position) {
        final ReservationTicketRecyclerItem item = mItemList.get(a_position);

        a_viewHolder.tkName.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void setOnItemClickListener(OnItemClickEventListener a_listener) {
        mItemClickListener = a_listener;
    }

}
