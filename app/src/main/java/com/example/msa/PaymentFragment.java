package com.example.msa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class PaymentFragment extends Fragment {

    // 종합이용권 섹션
    private TextView adultCountTextView, teenCountTextView, childCountTextView;
    private int adultCount = 0, teenCount = 0, childCount = 0;

    // 파크이용권 섹션
    private TextView adultCountTextView2, teenCountTextView2, childCountTextView2;
    private int adultCount2 = 0, teenCount2 = 0, childCount2 = 0;

    private TextView parkNameTextView;
    private TextView selectedDateTextView;
    private CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment에서 사용할 레이아웃 inflate
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        // View 초기화
        parkNameTextView = view.findViewById(R.id.park_name_text_view);
        selectedDateTextView = view.findViewById(R.id.selected_date_text_view);
        calendarView = view.findViewById(R.id.calendar_view);

        // 종합이용권 섹션 View 초기화
        adultCountTextView = view.findViewById(R.id.adult_count);
        teenCountTextView = view.findViewById(R.id.teen_count);
        childCountTextView = view.findViewById(R.id.child_count);

        // 파크이용권 섹션 View 초기화
        adultCountTextView2 = view.findViewById(R.id.adult_count2);
        teenCountTextView2 = view.findViewById(R.id.teen_count2);
        childCountTextView2 = view.findViewById(R.id.child_count2);

        // Get the selected ticket name from arguments (if available)
        Bundle arguments = getArguments();
        if (arguments != null) {
            String selectedTicket = arguments.getString("selectedTicket");
            parkNameTextView.setText(selectedTicket);
        }

        // CalendarView에서 날짜가 선택될 때 TextView에 표시
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = year + "년 " + (month + 1) + "월 " + dayOfMonth + "일";
                selectedDateTextView.setText(selectedDate);
            }
        });

        // 종합이용권 성인 인원수 증가 및 감소 버튼
        view.findViewById(R.id.adult_minus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adultCount > 0) {
                    adultCount--;
                    adultCountTextView.setText(String.valueOf(adultCount));
                }
            }
        });

        view.findViewById(R.id.adult_plus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adultCount++;
                adultCountTextView.setText(String.valueOf(adultCount));
            }
        });

        // 종합이용권 청소년 인원수 증가 및 감소 버튼
        view.findViewById(R.id.teen_minus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (teenCount > 0) {
                    teenCount--;
                    teenCountTextView.setText(String.valueOf(teenCount));
                }
            }
        });

        view.findViewById(R.id.teen_plus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teenCount++;
                teenCountTextView.setText(String.valueOf(teenCount));
            }
        });

        // 종합이용권 어린이 인원수 증가 및 감소 버튼
        view.findViewById(R.id.child_minus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childCount > 0) {
                    childCount--;
                    childCountTextView.setText(String.valueOf(childCount));
                }
            }
        });

        view.findViewById(R.id.child_plus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childCount++;
                childCountTextView.setText(String.valueOf(childCount));
            }
        });

        // 파크이용권 성인 인원수 증가 및 감소 버튼
        view.findViewById(R.id.adult_minus_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adultCount2 > 0) {
                    adultCount2--;
                    adultCountTextView2.setText(String.valueOf(adultCount2));
                }
            }
        });

        view.findViewById(R.id.adult_plus_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adultCount2++;
                adultCountTextView2.setText(String.valueOf(adultCount2));
            }
        });

        // 파크이용권 청소년 인원수 증가 및 감소 버튼
        view.findViewById(R.id.teen_minus_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (teenCount2 > 0) {
                    teenCount2--;
                    teenCountTextView2.setText(String.valueOf(teenCount2));
                }
            }
        });

        view.findViewById(R.id.teen_plus_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teenCount2++;
                teenCountTextView2.setText(String.valueOf(teenCount2));
            }
        });

        // 파크이용권 어린이 인원수 증가 및 감소 버튼
        view.findViewById(R.id.child_minus_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childCount2 > 0) {
                    childCount2--;
                    childCountTextView2.setText(String.valueOf(childCount2));
                }
            }
        });

        view.findViewById(R.id.child_plus_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childCount2++;
                childCountTextView2.setText(String.valueOf(childCount2));
            }
        });

        return view;
    }
}
