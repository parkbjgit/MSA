package com.example.msa;

import android.content.Intent;
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

import org.w3c.dom.Text;


public class ReservationFragment extends Fragment implements View.OnClickListener {

    private ImageView imageViewQRCode;
    private TextView textView;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button ticket = view.findViewById(R.id.btn_ticket_reservation);
        //Button attraction = view.findViewById(R.id.btn_attraction_reservation);

        // 버튼 클릭 이벤트 리스너
        ticket.setOnClickListener(this);
        //attraction.setOnClickListener(this);


        imageViewQRCode = view.findViewById(R.id.imageViewQRCode);
        textView = view.findViewById(R.id.textView);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getImageVisibility().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isVisible) {
                if (isVisible) {
                    // QR 코드 이미지를 불러와서 표시
                    Bitmap qrCodeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qr);
                    imageViewQRCode.setImageBitmap(qrCodeBitmap);
                    imageViewQRCode.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                }
            }
        });


        return view;
    }

    // 버튼 클릭 이벤트 리스너
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getActivity(), TicketSelectActivity.class);
        startActivity(intent);

        /*
        if (view.getId() == R.id.btn_ticket_reservation) {
            //버튼 클릭 이벤트
            DialogFragment dialogFragment = new DatePickerFragment();
            dialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
        } else if (view.getId() == R.id.btn_attraction_reservation) {
            //버튼 클릭 이벤트
            Intent intent = new Intent(getActivity(), FacilitySelectActivity.class);
            startActivity(intent);
        }
        */
    }
}