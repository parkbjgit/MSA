package com.example.msa;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import kr.co.bootpay.android.Bootpay;
import kr.co.bootpay.android.events.BootpayEventListener;
import kr.co.bootpay.android.models.BootItem;
import kr.co.bootpay.android.models.Payload;


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

    private TicketViewModel ticketViewModel;
    private ApiService ticketApiService;

    private SharedViewModel sharedViewModel;

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
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth ;
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

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        //결제하기 버튼 클릭 이벤트
        Button buyTicketButton = view.findViewById(R.id.btn_purchase_ticket);


        //ticketViewModel = new ViewModelProvider(requireActivity()).get(TicketViewModel.class);
        //ticketApiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        buyTicketButton.setOnClickListener(v -> {
            double totalPrice = (adultCount * ADULT_PRICE)
                    + (teenCount * TEEN_PRICE)
                    + (childCount * CHILD_PRICE)
                    + (adultCount2 * ADULT_PRICE2)
                    + (teenCount2 * TEEN_PRICE2)
                    + (childCount2 * CHILD_PRICE2);

            paymentTest(view, totalPrice);

            //TicketRequest request = new TicketRequest("user123", "park456", "standard", Arrays.asList("facility1", "facility2"));
            //createTicket(request);

        });


        return view;
    }

    private void paymentTest(View v, double totalPrice) {

        String selectedTicket = parkNameTextView.getText().toString();
        String selectedDate = selectedDateTextView.getText().toString();

        List<BootItem> items = new ArrayList<>();
        BootItem item1 = new BootItem().setName("롯데월드").setId("ITEM_CODE_LOTTEWORLD").setQty(1).setPrice(500d);
        BootItem item2 = new BootItem().setName("에버랜드").setId("ITEM_CODE_EVERLAND").setQty(1).setPrice(500d);
        BootItem item3 = new BootItem().setName("경주월드").setId("ITEM_CODE_GYEONGJUWORLD").setQty(1).setPrice(500d);
        items.add(item1);
        items.add(item2);
        items.add(item3);

        Payload payload = new Payload();
        payload.setApplicationId("67300b68a3175898bd6e4f12")
                .setOrderName(selectedTicket + " / " + selectedDate)
                .setPg("네이버페이")
                .setMethod("네이버페이")
                .setOrderId("1111")
                .setPrice(totalPrice);

        // 프래그먼트에서 Bootpay 결제 요청
        Bootpay.init(getChildFragmentManager())
                .setPayload(payload)
                .setEventListener(new BootpayEventListener() {
                    @Override
                    public void onCancel(String data) {
                        Log.d("bootpay", "cancel: " + data);
                    }

                    @Override
                    public void onError(String data) {
                        Log.d("bootpay", "error: " + data);
                    }

                    @Override
                    public void onClose() {
                        Log.d("bootpay", "close: ");
                        Bootpay.removePaymentWindow();
                    }

                    @Override
                    public void onIssued(String data) {
                        Log.d("bootpay", "issued: " + data);
                    }

                    @Override
                    public boolean onConfirm(String data) {
                        Log.d("bootpay", "confirm: " + data);
                        return true;
                    }

                    @Override
                    public void onDone(String data) {
                        Log.d("done", data);

                        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
                        sharedViewModel.setSelectedTicket(selectedTicket);
                        sharedViewModel.setSelectedDate(selectedDate);
                        sharedViewModel.setPaymentCompleted(true);

                        sharedViewModel.setAdultCount(adultCount);
                        sharedViewModel.setAdultCount2(adultCount2);
                        sharedViewModel.setTeenCount(teenCount);
                        sharedViewModel.setTeenCount2(teenCount2);
                        sharedViewModel.setChildCount(childCount);
                        sharedViewModel.setChildCount2(childCount2);

                        ReservationFragment imageFragment = new ReservationFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("selectedTicket", selectedTicket);
                        bundle.putString("selectedDate", selectedDate);
                        //bundle.putBoolean("paymentCompleted", true); // 결제 완료 정보 전달
                        imageFragment.setArguments(bundle);

                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, imageFragment); // fragment_container는 프래그먼트를 담는 레이아웃 ID
                        transaction.addToBackStack(null); // 뒤로가기 시 이전 프래그먼트로 돌아가기 위해 백스택에 추가
                        transaction.commit();

                        Toast.makeText(getActivity(), "결제가 완료되었습니다.", Toast.LENGTH_SHORT).show();

                        Bootpay.removePaymentWindow();
                    }
                }).requestPayment();

    }

    private void createTicket(TicketRequest request) {
        Call<TicketResponse> call = ticketApiService.createTicket(request);

        call.enqueue(new Callback<TicketResponse>() {
            @Override
            public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {
                Log.d("Retrofit", "Request sent");
                if (response.isSuccessful() && response.body() != null) {
                    ticketViewModel.setTicket(response.body());
                    Toast.makeText(getContext(), "티켓이 성공적으로 발급되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Retrofit", "Response unsuccessful: " + response.errorBody());
                    Toast.makeText(getContext(), "티켓 발급에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TicketResponse> call, Throwable t) {
                Log.e("Retrofit", "Request failed: " + t.getMessage());
                Toast.makeText(getContext(), "서버 요청에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
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
