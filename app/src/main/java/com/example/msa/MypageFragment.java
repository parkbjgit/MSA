package com.example.msa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

public class MypageFragment extends Fragment {

    public MypageFragment() {
        // Required empty public constructor
    }

    public static MypageFragment newInstance(String param1, String param2) {
        MypageFragment fragment = new MypageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment first
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        // Initialize the progress bar after inflating the layout
        RoundCornerProgressBar progressBar = view.findViewById(R.id.progress_bar);

        // Set the dynamic percentage based on the current reservation status
        int currentReservation = 30;  // 현재 예약된 인원
        int maxReservation = 100;     // 최대 예약 가능 인원
        float percentage = (currentReservation / (float) maxReservation) * 100;
        progressBar.setProgress(percentage);

        return view;
    }
}
