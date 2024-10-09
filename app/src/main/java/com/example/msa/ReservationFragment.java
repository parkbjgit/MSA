package com.example.msa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ReservationFragment extends Fragment implements View.OnClickListener {

    private ImageView imageViewQRCode;
    private TextView textView;
    private SharedViewModel sharedViewModel;

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
            TicketSelectFragment childFragment = new TicketSelectFragment(); // Fragment로 변경
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, childFragment) // R.id.fragment_container가 적절한지 확인
                    .addToBackStack(null) // 뒤로 가기 시 이전 프래그먼트로 돌아가기 가능
                    .commit();
        }
    }
}
