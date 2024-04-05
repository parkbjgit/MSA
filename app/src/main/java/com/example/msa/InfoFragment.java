package com.example.msa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class InfoFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        //버튼 선언
        Button today_info = view.findViewById(R.id.btn_today_info);
        Button b1_info = view.findViewById(R.id.btn_b1_info);
        Button b2_info = view.findViewById(R.id.btn_b2_info);
        Button b3_info = view.findViewById(R.id.btn_b3_info);
        Button b4_info = view.findViewById(R.id.btn_b4_info);
        Button b5_info = view.findViewById(R.id.btn_b5_info);
        Button b6_info = view.findViewById(R.id.btn_b6_info);
        Button b7_info = view.findViewById(R.id.btn_b7_info);

        //버튼 비활성
        b2_info.setVisibility(View.GONE);
        b3_info.setVisibility(View.GONE);
        b4_info.setVisibility(View.GONE);
        b5_info.setVisibility(View.GONE);
        b6_info.setVisibility(View.GONE);

        // 버튼 클릭 이벤트 리스너
        today_info.setOnClickListener(this);
        b1_info.setOnClickListener(this);
        b2_info.setOnClickListener(this);
        b3_info.setOnClickListener(this);
        b4_info.setOnClickListener(this);
        b5_info.setOnClickListener(this);
        b6_info.setOnClickListener(this);
        b7_info.setOnClickListener(this);

        return view;
    }

    // 버튼 클릭 이벤트 리스너
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_today_info) {
            //버튼 클릭 이벤트 - 팝업창 추가
            Toast.makeText(getActivity(), "오늘 예약 내역입니다.", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btn_b1_info) {
            Toast.makeText(getActivity(), "1일 전 예약 내역입니다.", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btn_b7_info) {
            Toast.makeText(getActivity(), "7일 전 예약 내역입니다.", Toast.LENGTH_SHORT).show();
        }
    }
}