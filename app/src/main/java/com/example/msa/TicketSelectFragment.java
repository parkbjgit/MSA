package com.example.msa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

public class TicketSelectFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment에서 사용할 레이아웃 inflate
        View view = inflater.inflate(R.layout.activity_ticket_select, container, false);

        // ViewModel 초기화
        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // ViewPager 설정
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyPagerAdapter(sharedViewModel)); // 어댑터 설정
        viewPager.setPageTransformer(true, new CustomZoomOutSlideTransformer()); // 페이지 변환 효과 적용

        // 양옆에 보이는 페이지 설정 (1은 기본값이므로 더 많은 페이지를 로드하도록 설정 가능)
        viewPager.setOffscreenPageLimit(3);

        // 페이지 간의 여백을 설정하여 옆 페이지가 작게 보이도록
        viewPager.setClipToPadding(false);
        viewPager.setPadding(100, 0, 100, 0); // 좌우 패딩 설정
        viewPager.setPageMargin(20); // 페이지 간의 간격 설정

        return view;
    }
}
