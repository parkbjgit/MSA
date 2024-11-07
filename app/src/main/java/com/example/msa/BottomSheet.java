package com.example.msa;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BottomSheet extends BottomSheetDialogFragment {
    private TextView markerNameTextView;
    private TextView phoneNumberTextView;
    private TextView openingHoursTextView;
    private RatingBar ratingBar; // RatingBar로 변경

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
        ratingBar = view.findViewById(R.id.rating_bar); // RatingBar 참조

        // Place 정보를 사용하여 텍스트 설정
        if (place != null) {
            markerNameTextView.setText(place.getName());
            phoneNumberTextView.setText(place.getPhoneNumber() != null ? place.getPhoneNumber() : "전화번호 정보 없음");

            if (place.getOpeningHours() != null && place.getOpeningHours().getWeekdayText() != null) {
                List<String> hours = place.getOpeningHours().getWeekdayText();
                StringBuilder hoursBuilder = new StringBuilder();
                for (String hour : hours) {
                    hoursBuilder.append(hour).append(" ");
                }
                openingHoursTextView.setText(hoursBuilder.toString());
            } else {
                openingHoursTextView.setText("영업시간 정보 없음");
            }

            // 평점 설정
            if (place.getRating() != null) {
                ratingBar.setRating(place.getRating().floatValue());
            } else {
                ratingBar.setRating(0f);
            }

        }

        view.findViewById(R.id.btn_close).setOnClickListener(v -> dismiss());
    }
}
