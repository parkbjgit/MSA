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

        imageView = view.findViewById(R.id.qrCodeImageView);
        ticketTextView = view.findViewById(R.id.ticket_name);
        dateTextView = view.findViewById(R.id.ticket_date);
        timeTextView = view.findViewById(R.id.ticket_time);
        seatTextView = view.findViewById(R.id.ticket_seat);
        ticketidTextView = view.findViewById(R.id.ticket_unique_id);

        sharedViewModel.getSelectedTicket().observe(getViewLifecycleOwner(), ticket -> {
            if (ticket != null) {
                ticketTextView.setText(ticket);
            }
        });

        sharedViewModel.getSelectedDate().observe(getViewLifecycleOwner(), date -> {
            if (date != null) {
                dateTextView.setText(date);
                dateTextView.setVisibility(View.VISIBLE);
            }
        });

        sharedViewModel.getPaymentStatus().observe(getViewLifecycleOwner(), isCompleted -> {
            if (isCompleted) {  // 결제 완료 시 이미지 표시
                imageView.setVisibility(View.VISIBLE);
                timeTextView.setVisibility(View.VISIBLE);
                seatTextView.setText("사용가능");
                ticketidTextView.setVisibility(View.VISIBLE);
                ticketTextView.setVisibility(View.VISIBLE);
                dateTextView.setVisibility(View.VISIBLE);
            } else {    // 결제 완료되지 않았을 때 이미지 숨김
                imageView.setVisibility(View.GONE);
                ticketTextView.setVisibility(View.GONE);
                dateTextView.setVisibility(View.GONE);
                timeTextView.setVisibility(View.GONE);
                ticketidTextView.setVisibility(View.GONE);
            }
        });

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
