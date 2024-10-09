package com.example.msa;

import androidx.viewpager.widget.ViewPager;
import android.view.View;

public class CustomZoomOutSlideTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // 화면의 왼쪽으로 완전히 넘어간 페이지
            view.setScaleX(MIN_SCALE); // 페이지를 축소
            view.setScaleY(MIN_SCALE);
            view.setAlpha(1f); // 투명도는 그대로 유지하여 페이지가 보이도록 설정
        } else if (position <= 1) { // 현재 화면에 보이는 페이지 및 그 다음 페이지
            // 기본 슬라이드 전환을 수정하여 페이지를 축소
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;

            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }

            // 페이지 크기 조정
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // 페이지를 크기에 따라 페이드 효과 적용, 하지만 투명도는 최소화하여 사라지지 않도록 설정
            view.setAlpha(1f);
        } else { // 화면의 오른쪽으로 완전히 넘어간 페이지
            view.setScaleX(MIN_SCALE); // 페이지를 축소
            view.setScaleY(MIN_SCALE);
            view.setAlpha(1f); // 투명도는 그대로 유지하여 페이지가 보이도록 설정
        }
    }
}
