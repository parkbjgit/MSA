package com.example.msa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class PaymentFragment extends Fragment {

    private TextView parkNameTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment에서 사용할 레이아웃 inflate
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        // View 초기화
        parkNameTextView = view.findViewById(R.id.park_name_text_view); // 데이터가 표시될 TextView

        // ViewModel 초기화
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // ViewModel의 데이터를 관찰하여 TextView에 표시
        sharedViewModel.getSelectedParkName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String parkName) {
                parkNameTextView.setText(parkName); // TextView에 parkName 설정
            }
        });

        return view;
    }
}
