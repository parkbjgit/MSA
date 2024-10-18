package com.example.msa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
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
    private AppCompatButton btnPurchaseTicket;

    private final int ADULT_PRICE = 59000;
    private final int TEEN_PRICE = 52000;
    private final int CHILD_PRICE = 46000;

    private final int ADULT_PRICE2 = 47000;
    private final int TEEN_PRICE2 = 41000;
    private final int CHILD_PRICE2 = 35000;

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

        btnPurchaseTicket = view.findViewById(R.id.btn_purchase_ticket);
        updateTotalPrice();

        // 선택된 티켓 이름을 TextView에 표시
        Bundle arguments = getArguments();
        if (arguments != null) {
            String selectedTicket = arguments.getString("selectedTicket");
            parkNameTextView.setText(selectedTicket);
        }

        // CalendarView에서 날짜가 선택될 때 TextView에 표시
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = year + " - " + (month + 1) + " - " + dayOfMonth ;
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
                    updateTotalPrice();
                }
            }
        });

        view.findViewById(R.id.adult_plus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adultCount++;
                adultCountTextView.setText(String.valueOf(adultCount));
                updateTotalPrice();
            }
        });

        // 종합이용권 청소년 인원수 증가 및 감소 버튼
        view.findViewById(R.id.teen_minus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (teenCount > 0) {
                    teenCount--;
                    teenCountTextView.setText(String.valueOf(teenCount));
                    updateTotalPrice();
                }
            }
        });

        view.findViewById(R.id.teen_plus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teenCount++;
                teenCountTextView.setText(String.valueOf(teenCount));
                updateTotalPrice();
            }
        });

        // 종합이용권 어린이 인원수 증가 및 감소 버튼
        view.findViewById(R.id.child_minus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childCount > 0) {
                    childCount--;
                    childCountTextView.setText(String.valueOf(childCount));
                    updateTotalPrice();
                }
            }
        });

        view.findViewById(R.id.child_plus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childCount++;
                childCountTextView.setText(String.valueOf(childCount));
                updateTotalPrice();
            }
        });

        // 파크이용권 성인 인원수 증가 및 감소 버튼
        view.findViewById(R.id.adult_minus_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adultCount2 > 0) {
                    adultCount2--;
                    adultCountTextView2.setText(String.valueOf(adultCount2));
                    updateTotalPrice();
                }
            }
        });

        view.findViewById(R.id.adult_plus_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adultCount2++;
                adultCountTextView2.setText(String.valueOf(adultCount2));
                updateTotalPrice();
            }
        });

        // 파크이용권 청소년 인원수 증가 및 감소 버튼
        view.findViewById(R.id.teen_minus_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (teenCount2 > 0) {
                    teenCount2--;
                    teenCountTextView2.setText(String.valueOf(teenCount2));
                    updateTotalPrice();
                }
            }
        });

        view.findViewById(R.id.teen_plus_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teenCount2++;
                teenCountTextView2.setText(String.valueOf(teenCount2));
                updateTotalPrice();
            }
        });

        // 파크이용권 어린이 인원수 증가 및 감소 버튼
        view.findViewById(R.id.child_minus_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childCount2 > 0) {
                    childCount2--;
                    childCountTextView2.setText(String.valueOf(childCount2));
                    updateTotalPrice();
                }
            }
        });

        view.findViewById(R.id.child_plus_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childCount2++;
                childCountTextView2.setText(String.valueOf(childCount2));
                updateTotalPrice();
            }
        });

        return view;
    }


    // 총 가격을 계산하고 결제하기 버튼의 텍스트를 업데이트하는 메서드
    private void updateTotalPrice() {
        int totalPrice = (adultCount * ADULT_PRICE)
                + (teenCount * TEEN_PRICE)
                + (childCount * CHILD_PRICE)
                + (adultCount2 * ADULT_PRICE2)
                + (teenCount2 * TEEN_PRICE2)
                + (childCount2 * CHILD_PRICE2);

        btnPurchaseTicket.setText(totalPrice + "원 결제하기");
    }
}
