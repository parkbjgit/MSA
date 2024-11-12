package com.example.msa;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        reservationViewModel.getRideName().observe(getViewLifecycleOwner(), name -> {
            if (name.isEmpty()) {
                showNoReservation(rideNameTextView, rideTimeTextView, ridePeopleTextView, rideStatusTextView, reservationCancel, rideImageView, noReservationMessage);
            } else {
                String rideTime = convertToSimpleTime(reservationViewModel.getRideTime().getValue());
                String ridePeople = reservationViewModel.getRidePeople().getValue();
                showReservation(name, rideTime, ridePeople, rideNameTextView, rideTimeTextView, ridePeopleTextView, rideStatusTextView, reservationCancel, rideImageView, noReservationMessage);
            }
        });

        // 예약 취소 버튼 클릭 이벤트 설정
        reservationCancel.setOnClickListener(view1 -> showCancelDialog());

        // 예약 버튼 클릭 이벤트 설정
        goReservation.setOnClickListener(this);

        return view;
    }

    private String convertToSimpleTime(String isoTime) {
        if (isoTime == null || isoTime.isEmpty()) return "없음";
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = isoFormat.parse(isoTime);

            SimpleDateFormat simpleFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return simpleFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "없음";
        }
    }
    private void showCancelDialog() {
        // 다이얼로그에 사용할 레이아웃을 생성
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_cancel_reservation, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        // 다이얼로그의 배경을 투명하게 설정
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        // 다이얼로그의 '예'와 '아니요' 버튼 설정
        Button yesButton = dialogView.findViewById(R.id.yes_btn);
        Button noButton = dialogView.findViewById(R.id.cancel_btn);

        yesButton.setOnClickListener(v -> {
            // 예약을 취소하고 다이얼로그 닫기
//            reservationViewModel.clearReservation();
//            Toast.makeText(getActivity(), "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show();
//            dialog.dismiss();
            cancelReservation(dialog);
        });

        noButton.setOnClickListener(v -> {
            // 다이얼로그 닫기
            dialog.dismiss();
        });

        dialog.show();
    }

    private void cancelReservation(AlertDialog dialog) {
        ReservationsApiService apiService = ReservationsRetrofitClient.getRetrofitInstance().create(ReservationsApiService.class);
        String rideId = "6732bc18b91092cb1d44504b";
        String userId = "1";
        CancelRequest cancelRequest = new CancelRequest("User requested cancellation");

        apiService.cancelRide(rideId, userId, cancelRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    reservationViewModel.clearReservation();
                    Toast.makeText(getActivity(), "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "예약 취소에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "서버와의 통신에 실패했습니다.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
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
            case "프렌치레볼루션":
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

