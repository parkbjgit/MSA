package com.example.msa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //DataPickerFragment에서 전달된 데이터 받기
        Intent dateintent = getIntent();
        if (dateintent != null) {
            int year = dateintent.getIntExtra("year", 0);
            int month = dateintent.getIntExtra("month", 0);
            int day = dateintent.getIntExtra("day", 0);

            TextView dateTextView = findViewById(R.id.date_title);
            dateTextView.setText("예약 날짜: " + year + "년 " + (month + 1) + "월 " + day + "일");
        }

        //선택된 시설 이름 출력 추가 예정
        TextView nameTextView = findViewById(R.id.name_title);
        nameTextView.setText("시설명: " + "ABC");



        Button ok = (Button) findViewById(R.id.btn_payment_ok);
        Button cancel = (Button) findViewById(R.id.btn_payment_cancel);

        //결제 버튼 클릭시 - 결제 진행 화면 이동 추가
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "결제 화면 추가 예정", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //취소 버튼 클릭시 메인 화면 이동
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

}