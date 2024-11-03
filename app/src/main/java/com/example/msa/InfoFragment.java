package com.example.msa;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import androidx.recyclerview.widget.RecyclerView;

public class InfoFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    String rideName = "";  // 놀이기구 이름 변수 (초기 값 비워둠)
    String rideTime = "";  // 예약 시간 변수 (초기 값 비워둠)
    String ridePeople = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        // View 찾기
        TextView rideNameTextView = view.findViewById(R.id.rideName);
        TextView rideTimeTextView = view.findViewById(R.id.rideTime);
        TextView rideStatusTextView = view.findViewById(R.id.rideStatus);
        TextView ridePeopleTextView = view.findViewById(R.id.ridePeople);
        TextView noReservationMessage = view.findViewById(R.id.no_reservation_message); // 예약 없는 경우 메시지
        Button goReservation = view.findViewById(R.id.btn_go_reservation);
        Button reservationCancel = view.findViewById(R.id.reservation_cancel);
        ImageView rideImageView = view.findViewById(R.id.attraction_image);

        // 예약 정보가 없는 경우
        if (rideName.isEmpty() || rideTime.isEmpty()) {
            // 텍스트 설정 및 반투명 효과 적용
            rideNameTextView.setText("예약 없음");
            rideTimeTextView.setText("예약 없음");
            ridePeopleTextView.setText("예약 없음");
            rideNameTextView.setAlpha(0.5f);
            rideTimeTextView.setAlpha(0.5f);
            ridePeopleTextView.setAlpha(0.5f);

            // 상태 텍스트 설정 및 반투명 처리
            rideStatusTextView.setText("예약되지 않음");
            rideStatusTextView.setTextColor(Color.RED);
            rideStatusTextView.setAlpha(0.5f);

            // 예약 취소 버튼 비활성화 및 반투명 처리
            reservationCancel.setEnabled(false);
            reservationCancel.setAlpha(0.5f);

            // 이미지 숨기고 "예약된 시설이 없습니다." 메시지 표시
            rideImageView.setVisibility(View.GONE);
            noReservationMessage.setVisibility(View.VISIBLE);
        } else {
            // 예약이 있는 경우 이미지 표시하고 메시지 숨기기
            rideImageView.setVisibility(View.VISIBLE);
            noReservationMessage.setVisibility(View.GONE);
        }

        // 예약 취소 버튼 클릭 이벤트 설정
        reservationCancel.setOnClickListener(view1 -> showCancelDialog());
        goReservation.setOnClickListener(this);

        return view;
    }




    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_go_reservation) {
            // ChildFragment로 전환
            InfoFragment2 childFragment = new InfoFragment2();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, childFragment)
                    .addToBackStack(null)  // 뒤로 가기 지원
                    .commit();
        }
    }

    // 예약 취소 대화 상자 표시
    private void showCancelDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("예약 취소");
        builder.setMessage("예약을 취소하시겠습니까?");

        builder.setPositiveButton("예", (dialog, which) ->
                Toast.makeText(getActivity(), "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show()
        );

        builder.setNegativeButton("아니오", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
