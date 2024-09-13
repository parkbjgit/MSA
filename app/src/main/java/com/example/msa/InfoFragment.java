package com.example.msa;

import android.content.Intent;
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

        //예약하러 가기 버튼 선언
        Button go_reservation = view.findViewById(R.id.btn_go_reservation);

        recyclerView = view.findViewById(R.id.recyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager((getActivity()));
        recyclerView.setLayoutManager(layoutManager);

        s1 = getResources().getStringArray(R.array.reservation_lists);
        s2 = getResources().getStringArray(R.array.reservation_times);

        Reservation_Adapter reservationAdapter = new Reservation_Adapter(getActivity(), s1, s2);
        recyclerView.setAdapter(reservationAdapter);

        // 버튼 클릭 이벤트 리스너
        go_reservation.setOnClickListener(this);

        return view;
    }

    // 버튼 클릭 이벤트 리스너
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_go_reservation) {
            // ChildFragment 전환
            InfoFragment2 childFragment = new InfoFragment2();
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, childFragment)
                    .addToBackStack(null) // 뒤로 가기 시 이전 프래그먼트로 돌아가기 가능
                    .commit();
        }
    }

}