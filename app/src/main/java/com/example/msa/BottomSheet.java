package com.example.msa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BottomSheet extends BottomSheetDialogFragment {
    private TextView markerNameTextView;
    private TextView phoneNumberTextView;
    private TextView openingHoursTextView;
    private TextView ratingTextView;
    private TextView priceLevelTextView;

    private Place place; // Place 객체

    // 생성자 추가
    public BottomSheet(Place place) {
        this.place = place;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        markerNameTextView = view.findViewById(R.id.facility_name);
        phoneNumberTextView = view.findViewById(R.id.phone_number);
        openingHoursTextView = view.findViewById(R.id.opening_hours);
        ratingTextView = view.findViewById(R.id.rating);
        priceLevelTextView = view.findViewById(R.id.price_level);

        // Place 정보를 사용하여 텍스트 설정
        if (place != null) {
            markerNameTextView.setText(place.getName());
            phoneNumberTextView.setText(place.getPhoneNumber() != null ? place.getPhoneNumber() : "전화번호 정보 없음");

            if (place.getOpeningHours() != null && place.getOpeningHours().getWeekdayText() != null) {
                List<String> hours = place.getOpeningHours().getWeekdayText();
                StringBuilder hoursBuilder = new StringBuilder();
                for (String hour : hours) {
                    hoursBuilder.append(hour).append("\n");
                }
                openingHoursTextView.setText(hoursBuilder.toString());
            } else {
                openingHoursTextView.setText("영업시간 정보 없음");
            }

            ratingTextView.setText(place.getRating() != null ? String.valueOf(place.getRating()) : "평점 정보 없음");
            priceLevelTextView.setText(place.getPriceLevel() != null ? getPriceLevelString(place.getPriceLevel()) : "가격 수준 정보 없음");
        }

        view.findViewById(R.id.btn_close).setOnClickListener(v -> dismiss());
    }

    private String getPriceLevelString(int priceLevel) {
        switch (priceLevel) {
            case 0:
                return "무료";
            case 1:
                return "저렴";
            case 2:
                return "보통";
            case 3:
                return "비쌈";
            case 4:
                return "매우 비쌈";
            default:
                return "가격 수준 정보 없음";
        }
    }
}
