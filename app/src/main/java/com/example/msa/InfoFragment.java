package com.example.msa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InfoFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    String s1[], s2[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((getActivity()));
        recyclerView.setLayoutManager(layoutManager);

        s1 = getResources().getStringArray(R.array.reservation_lists);
        s2 = getResources().getStringArray(R.array.reservation_times);

        Reservation_Adapter reservationAdapter = new Reservation_Adapter(getActivity(), s1, s2);
        recyclerView.setAdapter(reservationAdapter);

        //버튼 선언

        Button go_reservation = view.findViewById((R.id.btn_go_reservation));


        // 버튼 클릭 이벤트 리스너

        go_reservation.setOnClickListener(this);

        return view;
    }

    // 버튼 클릭 이벤트 리스너
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_go_reservation) {
            //버튼 클릭 이벤트 - 팝업창 추가
            Toast.makeText(getActivity(), "기구 시간 선택으로 넘어갑니다.", Toast.LENGTH_SHORT).show();}
//        } else if (view.getId() == R.id.btn_b1_info) {
//            Toast.makeText(getActivity(), "1일 전 예약 내역입니다.", Toast.LENGTH_SHORT).show();
//        } else if (view.getId() == R.id.btn_b7_info) {
//            Toast.makeText(getActivity(), "7일 전 예약 내역입니다.", Toast.LENGTH_SHORT).show();
//        }

    }
}