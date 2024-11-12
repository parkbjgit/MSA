package com.example.msa;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class ReservationFragment extends Fragment implements View.OnClickListener {

    private ImageView imageView;
    private TextView ticketTextView, dateTextView, timeTextView, seatTextView;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // 모든 뷰를 초기화합니다.
        imageView = view.findViewById(R.id.qrCodeImageView);
        if (imageView == null) {
            Log.e("ReservationFragment", "imageView is null. Check if qrCodeImageView ID is correctly set in XML.");
        }
        //qrCodeTextView = view.findViewById(R.id.qr_code_text_view);
        ticketTextView = view.findViewById(R.id.ticket_name);
        dateTextView = view.findViewById(R.id.ticket_date);
        timeTextView = view.findViewById(R.id.ticket_time);
        seatTextView = view.findViewById(R.id.ticket_seat);

        setInitialState();

        sharedViewModel.getQrCode().observe(getViewLifecycleOwner(), qrCode -> {
            if (qrCode != null) {
                //qrCodeTextView.setText(qrCode);
            } else {
                //qrCodeTextView.setText("QR 코드가 없습니다.");
            }
        });

        // QR 코드 이미지 관찰하여 설정
        sharedViewModel.getQrCodeBitmap().observe(getViewLifecycleOwner(), bitmap -> {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Log.e("ReservationFragment", "QR 코드 이미지가 없습니다.");
            }
        });

        // 기타 LiveData 관찰 설정
        sharedViewModel.getSelectedTicket().observe(getViewLifecycleOwner(), ticket -> {
            if (ticket != null) {
                ticketTextView.setText(ticket);
            }
        });

        sharedViewModel.getSelectedDate().observe(getViewLifecycleOwner(), date -> {
            if (date != null) {
                dateTextView.setText(date);
            }
        });

        sharedViewModel.getPaymentStatus().observe(getViewLifecycleOwner(), isCompleted -> {
            if (isCompleted) {  // 결제 완료 시
                //imageView.setImageResource(R.drawable.qr); // 실제 QR 코드 이미지로 변경
                ticketTextView.setText(sharedViewModel.getSelectedTicket().getValue());
                dateTextView.setText(sharedViewModel.getSelectedDate().getValue());
                seatTextView.setText("사용가능");

                // 인원 수 가져오기
                int adultCount = sharedViewModel.getAdultCount().getValue() != null ? sharedViewModel.getAdultCount().getValue() : 0;
                int adultCount2 = sharedViewModel.getAdultCount2().getValue() != null ? sharedViewModel.getAdultCount2().getValue() : 0;
                int teenCount = sharedViewModel.getTeenCount().getValue() != null ? sharedViewModel.getTeenCount().getValue() : 0;
                int teenCount2 = sharedViewModel.getTeenCount2().getValue() != null ? sharedViewModel.getTeenCount2().getValue() : 0;
                int childCount = sharedViewModel.getChildCount().getValue() != null ? sharedViewModel.getChildCount().getValue() : 0;
                int childCount2 = sharedViewModel.getChildCount2().getValue() != null ? sharedViewModel.getChildCount2().getValue() : 0;

                int totalCount = adultCount + adultCount2 + teenCount + teenCount2 + childCount + childCount2;

                // 총합을 timeTextView에 설정
                timeTextView.setText(totalCount + "명");

                // 뷰를 표시
                imageView.setVisibility(View.VISIBLE);
                ticketTextView.setVisibility(View.VISIBLE);
                dateTextView.setVisibility(View.VISIBLE);
                timeTextView.setVisibility(View.VISIBLE);
                seatTextView.setVisibility(View.VISIBLE);
            } else {    // 결제 미완료 시
                imageView.setImageResource(R.color.gray);

                // 텍스트뷰들을 "-"로 설정
                ticketTextView.setText("-");
                dateTextView.setText("-");
                timeTextView.setText("-");
                seatTextView.setText("-");

                // 뷰를 표시
                imageView.setVisibility(View.VISIBLE);
                ticketTextView.setVisibility(View.VISIBLE);
                dateTextView.setVisibility(View.VISIBLE);
                timeTextView.setVisibility(View.VISIBLE);
                seatTextView.setVisibility(View.VISIBLE);
            }
        });

        Button ticket = view.findViewById(R.id.btn_ticket_reservation);
        ticket.setOnClickListener(this);
        return view;
    }

    private void setInitialState() {
        if (imageView != null) {
            imageView.setImageResource(R.color.gray); // 회색으로 채움
        } else {
            Log.e("ReservationFragment", "imageView is null in setInitialState()");
        }

        if (ticketTextView != null) ticketTextView.setText("-");
        if (dateTextView != null) dateTextView.setText("-");
        if (timeTextView != null) timeTextView.setText("-");
        if (seatTextView != null) seatTextView.setText("-");

        // 뷰를 표시
        if (imageView != null) imageView.setVisibility(View.VISIBLE);
        if (ticketTextView != null) ticketTextView.setVisibility(View.VISIBLE);
        if (dateTextView != null) dateTextView.setVisibility(View.VISIBLE);
        if (timeTextView != null) timeTextView.setVisibility(View.VISIBLE);
        if (seatTextView != null) seatTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_ticket_reservation) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new TicketSelectFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // QR 코드 이미지 설정 (ViewModel에서 가져와 설정)
        if (sharedViewModel.getQrCodeBitmap().getValue() != null) {
            imageView.setImageBitmap(sharedViewModel.getQrCodeBitmap().getValue());
        } else {
            Log.e("ReservationFragment", "QR 코드 이미지가 없습니다.");
        }
    }
}

