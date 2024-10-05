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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoFragment3 extends Fragment {
    private TextView markerNameTextView;
    private View choiceCompleteButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info3, container, false);

        markerNameTextView = view.findViewById(R.id.choiced_facility);
        choiceCompleteButton = view.findViewById(R.id.choice_complete);

        // GridLayout 찾기
        GridLayout gridLayoutMorning = view.findViewById(R.id.gridLayoutMorning);
        GridLayout gridLayoutAfternoon = view.findViewById(R.id.gridLayoutAfternoon);

        // 오전, 오후 시간 배열 정의
        String[] timesMorning = {"10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45"};
        String[] timesAfternoon = {"12:00", "12:15", "12:30", "12:45", "1:00", "1:15", "1:30", "1:45", "2:00", "2:15", "2:30", "2:45", "3:00", "3:15", "3:30", "3:45", "4:00", "4:15", "4:30", "4:45"};

        // 오전 버튼 생성
        createButtonsForTime(gridLayoutMorning, timesMorning);

        // 오후 버튼 생성
        createButtonsForTime(gridLayoutAfternoon, timesAfternoon);

        Bundle args = getArguments();
        if (args != null) {
            String markerName = args.getString("markerName");
            markerNameTextView.setText(markerName);

            // 'choice_complete' 버튼을 눌렀을 때 대기열 등록
            choiceCompleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enqueueUser(markerName, "1");  // 사용자 ID는 적절히 수정하세요
                }
            });
        }

        return view;
    }

    private void enqueueUser(String rideId, String userId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        QueueRequest queueRequest = new QueueRequest(rideId, userId);

        Call<QueueResponse> call = apiService.enqueueUser(queueRequest);
        call.enqueue(new Callback<QueueResponse>() {
            @Override
            public void onResponse(Call<QueueResponse> call, Response<QueueResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "대기열 등록 성공: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "대기열 등록 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QueueResponse> call, Throwable t) {
                Toast.makeText(getContext(), "서버 통신 에러: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createButtonsForTime(GridLayout gridLayout, String[] times) {
        for (String time : times) {
            AppCompatButton button = new AppCompatButton(getContext());
            button.setText(time);
            button.setTextSize(12);
            button.setTextColor(Color.WHITE);
            button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4A4A4A")));

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0; // GridLayout에서 0dp로 설정하면 열의 weight를 적용할 수 있음
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // 1f로 설정하면 각 버튼이 동일한 너비를 가짐
            params.setMargins(4, 4, 4, 4);
            button.setLayoutParams(params);

            // 버튼 클릭 시 클릭 상태 유지 및 색상 변경
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button.setSelected(!button.isSelected());
                    button.setBackgroundTintList(button.isSelected() ?
                            ColorStateList.valueOf(Color.GREEN) : ColorStateList.valueOf(Color.parseColor("#4A4A4A")));
                }
            });

            gridLayout.addView(button);
        }
    }
}
