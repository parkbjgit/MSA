package com.example.msa;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MypageFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        Button facilityadd = view.findViewById(R.id.btn_facility_add);
        Button logout = view.findViewById(R.id.btnLogout);
        //버튼 클릭 리스터 등록
        facilityadd.setOnClickListener(this);
        logout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_facility_add) {
            Toast.makeText(getActivity(), "관리자 승인 계정만 이용할 수 있습니다.", Toast.LENGTH_SHORT).show();
        }

        else if (view.getId() == R.id.btnLogout) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

}