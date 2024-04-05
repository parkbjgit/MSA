package com.example.msa;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


public class ReservationFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        Button ticket = view.findViewById(R.id.btn_ticket_reservation);
        Button attraction = view.findViewById(R.id.btn_attraction_reservation);

        // 버튼 클릭 이벤트 리스너
        ticket.setOnClickListener(this);
        attraction.setOnClickListener(this);

        return view;
    }

    // 버튼 클릭 이벤트 리스너
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_ticket_reservation) {
            //버튼 클릭 이벤트
            DialogFragment dialogFragment = new DatePickerFragment();
            dialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
        } else if (view.getId() == R.id.btn_attraction_reservation) {
            //버튼 클릭 이벤트
            Intent intent = new Intent(getActivity(), FacilitySelectActivity.class);
            startActivity(intent);
        }
    }

}