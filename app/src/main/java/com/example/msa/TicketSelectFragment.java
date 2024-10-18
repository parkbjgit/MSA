package com.example.msa;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

public class TicketSelectFragment extends Fragment implements MyPagerAdapter.OnPageClickListener {

    private TextView ticketNameTextView;
    private SharedViewModel sharedViewModel; // ViewModel을 전역 변수로 선언

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment에서 사용할 레이아웃 inflate
        View view = inflater.inflate(R.layout.fragment_ticket_select, container, false);

        // ViewModel 초기화
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // ViewPager 설정
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyPagerAdapter(sharedViewModel, this)); // 어댑터 설정
        viewPager.setPageTransformer(true, new CustomZoomOutSlideTransformer()); // 페이지 변환 효과 적용

        // 양옆에 보이는 페이지 설정
        viewPager.setOffscreenPageLimit(3);

        // 페이지 간의 여백을 설정
        viewPager.setClipToPadding(false);
        viewPager.setPadding(100, 0, 100, 0); // 좌우 패딩 설정
        viewPager.setPageMargin(20); // 페이지 간의 간격 설정

        // TextView 초기화
        ticketNameTextView = view.findViewById(R.id.park_name);

        // ViewModel의 데이터를 관찰하여 TextView 업데이트
        sharedViewModel.getSelectedTicketName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // 애니메이션 효과 추가
                ObjectAnimator blink = ObjectAnimator.ofFloat(ticketNameTextView, "alpha", 1f, 0f, 1f);
                blink.setDuration(500);
                blink.setRepeatCount(1);
                blink.start();

                ticketNameTextView.setText(s);
            }
        });

        // 페이지 변경 리스너 추가
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 필요에 따라 처리
            }

            @Override
            public void onPageSelected(int position) {
                updateTicketName(position); // 페이지가 변경되면 ticketName 업데이트
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 필요에 따라 처리
            }
        });

        return view;
    }

    private void updateTicketName(int position) {
        switch (position) {
            case 0:
                sharedViewModel.setSelectedTicketName("롯데월드");
                break;
            case 1:
                sharedViewModel.setSelectedTicketName("에버랜드");
                break;
            case 2:
                sharedViewModel.setSelectedTicketName("경주월드");
                break;
            default:
                sharedViewModel.setSelectedTicketName("");
        }
    }

    //놀이공원 클릭 시 이벤트 처리
    @Override
    public void onPageClick(int position) {

        PaymentFragment paymentFragment = new PaymentFragment();

        //번들에 담아서 sharedViewModel에 저장->PaymentFragment로 전달
        Bundle bundle = new Bundle();
        bundle.putString("selectedTicket", sharedViewModel.getSelectedTicketName().getValue());
        paymentFragment.setArguments(bundle);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, paymentFragment);
        transaction.addToBackStack(null); // 백 스택에 추가
        transaction.commit();
    }
}


