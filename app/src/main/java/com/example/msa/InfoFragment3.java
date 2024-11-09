package com.example.msa;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoFragment3 extends Fragment {
    private TextView markerNameTextView;
    private View choiceCompleteButton;
    private AppCompatButton selectedButton = null;  // 선택된 버튼을 저장할 변수
    private TextView choicedTimeTextView;  // 선택된 시간을 표시할 TextView
    private TextView ridePeopleTextView;  // 탑승 인원수를 표시할 TextView
    private AppCompatButton btnMinus, btnPlus;  // 플러스, 마이너스 버튼
    private ReservationViewModel reservationViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info3, container, false);

        reservationViewModel = new ViewModelProvider(requireActivity()).get(ReservationViewModel.class);

        markerNameTextView = view.findViewById(R.id.choiced_facility);
        choiceCompleteButton = view.findViewById(R.id.choice_complete);

        // 탑승 인원수 TextView와 플러스/마이너스 버튼 초기화
        ridePeopleTextView = view.findViewById(R.id.ridePeople);
        btnMinus = view.findViewById(R.id.ridePeople_minus_btn);
        btnPlus = view.findViewById(R.id.ridePeople_plus_btn);

        // 플러스, 마이너스 버튼 클릭 리스너 추가
        btnMinus.setOnClickListener(v -> updateRidePeopleCount(-1));
        btnPlus.setOnClickListener(v -> updateRidePeopleCount(1));

        GridLayout gridLayoutMorning = view.findViewById(R.id.gridLayoutMorning);
        GridLayout gridLayoutAfternoon = view.findViewById(R.id.gridLayoutAfternoon);

        choicedTimeTextView = view.findViewById(R.id.choiced_time);  // 선택된 시간 TextView 초기화

        String[] timesMorning = {"9:00", "9:15", "9:30", "9:45", "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45"};
        String[] timesAfternoon = {"12:00", "12:15", "12:30", "12:45", "13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30",
                "14:45", "15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45", "17:00", "17:15", "17:30", "17:45", "18:00", "18:15", "18:30", "18:45"};

        createButtonsForTime(gridLayoutMorning, timesMorning);
        createButtonsForTime(gridLayoutAfternoon, timesAfternoon);

        Bundle args = getArguments();
        if (args != null) {
            String markerName = args.getString("markerName");
            markerNameTextView.setText(markerName);

            // 버튼 클릭 리스너 설정: 예약 존재 확인 후 진행
            choiceCompleteButton.setOnClickListener(v -> {
                if (reservationViewModel.getRideName().getValue() != null && !reservationViewModel.getRideName().getValue().isEmpty()) {
                    Toast.makeText(getContext(), "이미 예약이 있습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    enqueueUser(markerName, "1");
                }
            });
        }
        return view;
    }

    private void updateRidePeopleCount(int change) {
        int currentCount = Integer.parseInt(ridePeopleTextView.getText().toString());
        int newCount = currentCount + change;

        // 탑승 인원수를 1~5 사이로 제한
        if (newCount >= 1 && newCount <= 5) {
            ridePeopleTextView.setText(String.valueOf(newCount));
        }
    }

    private void enqueueUser(String rideId, String userId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        QueueRequest queueRequest = new QueueRequest(rideId, userId);

        String rideName = markerNameTextView.getText().toString();
        String rideTime = choicedTimeTextView.getText().toString();
        String ridePeople = ridePeopleTextView.getText().toString();

        apiService.enqueueUser(queueRequest).enqueue(new Callback<QueueResponse>() {
            @Override
            public void onResponse(Call<QueueResponse> call, Response<QueueResponse> response) {
                String message = response.isSuccessful() ? "대기열 등록 성공: " + response.body().getMessage() : "대기열 등록 실패";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                // ViewModel에 예약 정보 업데이트
                updateReservationViewModel(rideName, rideTime, ridePeople);
            }

            @Override
            public void onFailure(Call<QueueResponse> call, Throwable t) {
                Toast.makeText(getContext(), "서버 통신 에러: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                // ViewModel에 예약 정보 업데이트 (통신 실패 시에도 적용)
                updateReservationViewModel(rideName, rideTime, ridePeople);
            }
        });
    }

    private void updateReservationViewModel(String rideName, String rideTime, String ridePeople) {
        ReservationViewModel reservationViewModel = new ViewModelProvider(requireActivity()).get(ReservationViewModel.class);
        reservationViewModel.setReservation(rideName, rideTime, ridePeople);
    }


    private void createButtonsForTime(GridLayout gridLayout, String[] times) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        for (String time : times) {
            String[] splitTime = time.split(":");
            int hour = Integer.parseInt(splitTime[0]);
            int minute = Integer.parseInt(splitTime[1]);

            AppCompatButton button = new AppCompatButton(getContext());
            button.setText(time);
            button.setTextSize(14);
            button.setTextColor(Color.BLACK);  // 텍스트는 검정으로 설정

            if (hour < currentHour || (hour == currentHour && minute < currentMinute)) {
                button.setEnabled(false);
                button.setBackgroundResource(R.drawable.disabled_time_btn);  // 비활성화된 버튼 배경
            } else {
                button.setBackgroundResource(R.drawable.time_btn);  // 선택 가능한 버튼 배경
                button.setOnClickListener(v -> handleButtonClick(button));  // 클릭 이벤트 처리
            }

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = (int) (38 * getResources().getDisplayMetrics().density + 0.5f);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(20, 12, 20, 12);
            button.setLayoutParams(params);

            gridLayout.addView(button);
        }
    }


    private void handleButtonClick(AppCompatButton button) {
        // 기존에 선택된 버튼이 있을 경우 기본 상태로 되돌림
        if (selectedButton != null) {
            selectedButton.setSelected(false);
            selectedButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));  // 흰색 배경
            selectedButton.setTextColor(Color.BLACK);  // 검정 글자
        }

        // 새로 선택된 버튼을 파란색으로 설정하고 글자색을 흰색으로 변경
        button.setSelected(true);
        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5274E6")));  // 파란색 배경
        button.setTextColor(Color.WHITE);  // 흰색 글자

        // 선택된 시간 텍스트를 TextView에 표시
        choicedTimeTextView.setText(button.getText().toString());

        // 현재 선택된 버튼 업데이트
        selectedButton = button;
    }
}
