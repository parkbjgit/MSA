package com.example.msa;

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
import androidx.lifecycle.ViewModelProvider;

public class ReservationFragment extends Fragment implements View.OnClickListener {

    private ImageView imageView;
    private TextView ticketTextView, dateTextView, timeTextView, seatTextView, ticketidTextView;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // 뷰 초기화
        imageView = view.findViewById(R.id.qrCodeImageView);
        ticketTextView = view.findViewById(R.id.ticket_name);
        dateTextView = view.findViewById(R.id.ticket_date);
        timeTextView = view.findViewById(R.id.ticket_time);
        seatTextView = view.findViewById(R.id.ticket_seat);
        ticketidTextView = view.findViewById(R.id.ticket_unique_id);

        // 초기 상태 설정
        setInitialState();

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
                imageView.setImageResource(R.drawable.qr); // 실제 QR 코드 이미지로 변경
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
                ticketidTextView.setVisibility(View.VISIBLE);
            } else {    // 결제 미완료 시
                imageView.setImageResource(R.color.gray); // 회색으로 채움 (R.color.gray는 회색 리소스)

                // 텍스트뷰들을 "-"로 설정
                ticketTextView.setText("-");
                dateTextView.setText("-");
                timeTextView.setText("-");
                seatTextView.setText("-");
                ticketidTextView.setText("-");

                // 뷰를 표시
                imageView.setVisibility(View.VISIBLE);
                ticketTextView.setVisibility(View.VISIBLE);
                dateTextView.setVisibility(View.VISIBLE);
                timeTextView.setVisibility(View.VISIBLE);
                seatTextView.setVisibility(View.VISIBLE);
                ticketidTextView.setVisibility(View.VISIBLE);
            }
        });

        Button ticket = view.findViewById(R.id.btn_ticket_reservation);
        ticket.setOnClickListener(this);
        return view;
    }

    private void setInitialState() {
        // 초기에는 결제 미완료 상태로 설정
        imageView.setImageResource(R.color.gray); // 회색으로 채움
        ticketTextView.setText("-");
        dateTextView.setText("-");
        timeTextView.setText("-");
        seatTextView.setText("-");
        ticketidTextView.setText("-");

        // 뷰를 표시
        imageView.setVisibility(View.VISIBLE);
        ticketTextView.setVisibility(View.VISIBLE);
        dateTextView.setVisibility(View.VISIBLE);
        timeTextView.setVisibility(View.VISIBLE);
        seatTextView.setVisibility(View.VISIBLE);
        ticketidTextView.setVisibility(View.VISIBLE);
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
}

