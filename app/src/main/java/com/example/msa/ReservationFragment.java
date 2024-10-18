package com.example.msa;

import static com.google.android.gms.common.api.internal.LifecycleCallback.getFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ReservationFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        // 뷰 초기화
        //imageViewQRCode = view.findViewById(R.id.ImageViewQRCode); // QR 코드 이미지뷰 초기화
        //textView = view.findViewById(R.id.textView); // 텍스트뷰 초기화

        Button ticket = view.findViewById(R.id.btn_ticket_reservation);

        // 버튼 클릭 이벤트 리스너
        ticket.setOnClickListener(this);
        return view;
    }

    // 버튼 클릭 이벤트 리스너
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_ticket_reservation) {

            // TicketSelectFragment로 전환
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new TicketSelectFragment());
            transaction.addToBackStack(null); // 백 스택에 추가
            transaction.commit();

        }
    }

    public void onResume() {
        super.onResume();
        // 화면이 보여질 때 FLAG_SECURE 플래그 제거
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }

    public void onPause() {
        super.onPause();
        // 화면이 사라질 때 FLAG_SECURE 플래그 추가
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
    }
}
