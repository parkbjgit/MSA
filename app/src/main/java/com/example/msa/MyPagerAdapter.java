package com.example.msa;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;

public class MyPagerAdapter extends PagerAdapter {

    private int[] layouts = {R.layout.test_activity, R.layout.test_activity2, R.layout.test_activity3}; // 각 페이지의 레이아웃
    private SharedViewModel sharedViewModel;

    public MyPagerAdapter(SharedViewModel sharedViewModel) {
        this.sharedViewModel = sharedViewModel;
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = inflater.inflate(layouts[position], container, false); // 각 레이아웃을 인플레이트
        container.addView(view); // ViewPager에 추가

        // TextView에 클릭 리스너 추가
        TextView parkNameTextView = view.findViewById(R.id.park_name);
        if (parkNameTextView != null) {
            parkNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭한 텍스트를 ViewModel에 전달
                    String parkName = parkNameTextView.getText().toString();
                    sharedViewModel.setSelectedParkName(parkName);

                    // PaymentFragment로 전환
                    PaymentFragment paymentFragment = new PaymentFragment();
                    ((FragmentActivity) container.getContext()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, paymentFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object); // 페이지가 사라질 때 뷰 제거
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
