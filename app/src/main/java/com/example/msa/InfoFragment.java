package com.example.msa;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class InfoFragment extends Fragment implements View.OnClickListener {

    private ReservationViewModel reservationViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        // ReservationViewModel 인스턴스 가져오기
        reservationViewModel = new ViewModelProvider(requireActivity()).get(ReservationViewModel.class);

        // View 찾기
        TextView rideNameTextView = view.findViewById(R.id.rideName);
        TextView rideTimeTextView = view.findViewById(R.id.rideTime);
        TextView rideStatusTextView = view.findViewById(R.id.rideStatus);
        TextView ridePeopleTextView = view.findViewById(R.id.ridePeople);
        TextView noReservationMessage = view.findViewById(R.id.no_reservation_message);
        Button goReservation = view.findViewById(R.id.btn_go_reservation);
        Button reservationCancel = view.findViewById(R.id.reservation_cancel);
        ImageView rideImageView = view.findViewById(R.id.attraction_image);

        // 예약 상태 관찰 및 UI 업데이트
        reservationViewModel.getRideName().observe(getViewLifecycleOwner(), name -> {
            if (name.isEmpty()) {
                showNoReservation(rideNameTextView, rideTimeTextView, ridePeopleTextView, rideStatusTextView, reservationCancel, rideImageView, noReservationMessage);
            } else {
                showReservation(name, reservationViewModel.getRideTime().getValue(), reservationViewModel.getRidePeople().getValue(), rideNameTextView, rideTimeTextView, ridePeopleTextView, rideStatusTextView, reservationCancel, rideImageView, noReservationMessage);
            }
        });

        // 예약 취소 버튼 클릭 이벤트 설정
        reservationCancel.setOnClickListener(view1 -> {
            reservationViewModel.clearReservation();
            Toast.makeText(getActivity(), "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show();
        });

        // 예약 버튼 클릭 이벤트 설정
        goReservation.setOnClickListener(this);

        return view;
    }

    private void showNoReservation(TextView rideNameTextView, TextView rideTimeTextView, TextView ridePeopleTextView,
                                   TextView rideStatusTextView, Button reservationCancel, ImageView rideImageView, TextView noReservationMessage) {
        rideNameTextView.setText("예약 없음");
        rideTimeTextView.setText("없음");
        ridePeopleTextView.setText("없음");
        rideStatusTextView.setText("예약되지 않음");
        rideStatusTextView.setTextColor(Color.RED);

        // Alpha 값 설정
        rideNameTextView.setAlpha(0.5f);
        rideTimeTextView.setAlpha(0.5f);
        ridePeopleTextView.setAlpha(0.5f);
        rideStatusTextView.setAlpha(0.5f);

        reservationCancel.setEnabled(false);
        reservationCancel.setAlpha(0.5f);
        rideImageView.setVisibility(View.GONE); // 이미지 숨기기
        noReservationMessage.setVisibility(View.VISIBLE); // 예약 메시지 표시
    }


    private void showReservation(String rideName, String rideTime, String ridePeople, TextView rideNameTextView,
                                 TextView rideTimeTextView, TextView ridePeopleTextView, TextView rideStatusTextView,
                                 Button reservationCancel, ImageView rideImageView, TextView noReservationMessage) {
        rideNameTextView.setText(rideName);
        rideTimeTextView.setText(rideTime);
        ridePeopleTextView.setText(ridePeople);
        rideStatusTextView.setText("예약됨");
        rideStatusTextView.setTextColor(Color.GREEN);
        reservationCancel.setEnabled(true);
        reservationCancel.setAlpha(1.0f);
        noReservationMessage.setVisibility(View.GONE);

        // 놀이기구 이름에 따라 이미지 설정
        switch (rideName) {
            case "후렌치레볼루션":
                rideImageView.setImageResource(R.drawable.frenchrevolution);
                break;
            case "회전목마":
                rideImageView.setImageResource(R.drawable.horse);
                break;
            case "스페인해적선":
                rideImageView.setImageResource(R.drawable.spainpirates);
                break;
            case "후룸라이드":
                rideImageView.setImageResource(R.drawable.froomride);
                break;
            case "번지드롭":
                rideImageView.setImageResource(R.drawable.bungedrop);
                break;
            case "아틀란티스":
                rideImageView.setImageResource(R.drawable.atlantis);
                break;
            case "자이로스윙":
                rideImageView.setImageResource(R.drawable.ziroswing);
                break;
            case "자이로드롭":
                rideImageView.setImageResource(R.drawable.zirodrop);
                break;
            default:
                rideImageView.setVisibility(View.GONE); // 이름이 일치하지 않을 때 이미지 숨기기
                return;
        }

        rideImageView.setVisibility(View.VISIBLE); // 이미지가 설정된 경우 표시
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_go_reservation) {
            InfoFragment2 childFragment = new InfoFragment2();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, childFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}

