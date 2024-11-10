package com.example.msa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class TicketFragment extends Fragment {

    private TicketViewModel ticketViewModel;
    private TextView ticketInfoTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ticketInfoTextView = view.findViewById(R.id.qrCodeImageView);
        ticketViewModel = new ViewModelProvider(requireActivity()).get(TicketViewModel.class);

        // ViewModel에서 티켓 정보 관찰
        ticketViewModel.getTicket().observe(getViewLifecycleOwner(), ticketResponse -> {
            if (ticketResponse != null) {
                String ticketInfo = "Ticket ID: " + ticketResponse.getTicket_id() + "\n" +
                        "Ticket Type: " + ticketResponse.getData().getTicket_type() + "\n" +
                        "Amount: " + ticketResponse.getData().getAmount();
                ticketInfoTextView.setText(ticketInfo);
            }
        });

        return view;
    }
}
