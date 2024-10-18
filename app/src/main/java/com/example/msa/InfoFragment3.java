package com.example.msa;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoFragment3 extends Fragment {
    private TextView markerNameTextView;
    private View choiceCompleteButton;

    // 리스트로 버튼들을 관리
    private List<AppCompatButton> morningButtons = new ArrayList<>();
    private List<AppCompatButton> afternoonButtons = new ArrayList<>();

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
        String[] timesMorning = {"9:00", "9:15", "9:30", "9:45", "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45"};
        String[] timesAfternoon = {"12:00","12:15","12:30","12:45","13:00","13:15","13:30","13:45","14:00","14:15","14:30","14:45","15:00","15:15","15:30","15:45","16:00","16:15","16:30","16:45","17:00","17:15","17:30","17:45","18:00","18:15","18:30","18:45","19:00","19:15","19:30","19:45","20:00","20:15","20:30","20:45"};

        // 오전 버튼 생성
        createButtonsForTime(gridLayoutMorning, timesMorning, morningButtons, afternoonButtons);

        // 오후 버튼 생성
        createButtonsForTime(gridLayoutAfternoon, timesAfternoon, afternoonButtons, morningButtons);

        Bundle args = getArguments();
        if (args != null) {
            String markerName = args.getString("markerName");
            markerNameTextView.setText(markerName);

            // 'choice_complete' 버튼을 눌렀을 때 대기열 등록
            choiceCompleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enqueueUser(markerName, "1");  // 임의 사용자 ID:1
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

            //queueresponse는 데이터타입 명시되어있음
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

    // 버튼 생성 및 클릭 시 다른 그룹 버튼 초기화 로직 추가
    private void createButtonsForTime(GridLayout gridLayout, String[] times, List<AppCompatButton> buttonList, List<AppCompatButton> otherGroupButtons) {
        // 현재 시간 가져오기
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY); // 24시간 형식의 현재 시간
        int currentMinute = calendar.get(Calendar.MINUTE); // 현재 분
        Log.d(currentHour + currentMinute + "", "현재 시간");       //오전은 2, 1 이렇게 출력

        for (String time : times) {
            // 시간 문자열을 "HH:mm" 형식으로 파싱
            String[] splitTime = time.split(":");
            int hour = Integer.parseInt(splitTime[0]);
            int minute = Integer.parseInt(splitTime[1]);

            // 현재 시간 이후의 버튼만 생성
            if (hour > currentHour || (hour == currentHour && minute >= currentMinute)) {
                AppCompatButton button = new AppCompatButton(getContext());
                button.setText(time);
                button.setTextSize(14);
                button.setTextColor(Color.WHITE);
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4A4A4A")));

                // 버튼의 레이아웃 파라미터 설정
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0; // 0dp로 설정하면 GridLayout의 열 weight가 적용됨
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // 동일한 열 너비를 설정
                params.setMargins(5, 4, 5, 4);
                button.setLayoutParams(params);

                // 버튼 클릭 이벤트 설정
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 다른 그룹의 버튼 초기화
                        for (AppCompatButton btn : otherGroupButtons) {
                            btn.setSelected(false);
                            btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4A4A4A")));
                        }

                        // 현재 그룹의 버튼 초기화
                        for (AppCompatButton btn : buttonList) {
                            btn.setSelected(false);
                            btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4A4A4A")));
                        }

                        // 클릭된 버튼의 상태 변경
                        button.setSelected(true);
                        button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5274E6")));
                    }
                });

                // 버튼을 리스트와 GridLayout에 추가
                buttonList.add(button);
                gridLayout.addView(button);
            }
        }
    }

}
