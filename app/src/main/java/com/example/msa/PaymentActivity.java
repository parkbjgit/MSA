package com.example.msa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class PaymentActivity extends AppCompatActivity {

    private TextView priceTextView;

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
        String ticketName = dateintent.getStringExtra("ticket_name");
        TextView nameTextView = findViewById(R.id.name_title);
        nameTextView.setText("시설명: " + ticketName);

        Button ok = (Button) findViewById(R.id.btn_payment_ok);
        Button cancel = (Button) findViewById(R.id.btn_payment_cancel);


        // 가격 정보 추가
        //priceTextView = findViewById(R.id.price_display); // 가격 텍스트 뷰 초기화
        //String ticketPrice = dateintent.getStringExtra("ticket_price"); // Intent에서 가격 정보 받기
        //priceTextView.setText(ticketPrice); // 가격 정보 설정


        //결제 버튼 클릭시 - 결제 진행 화면 이동 추가
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.putExtra("show_image", true);

                Toast toast = Toast.makeText(getApplicationContext(), "입장권이 정상적으로 구매되었습니다.", Toast.LENGTH_SHORT);
                toast.show();

                startActivity(intent);
            }
        });

        //취소 버튼 클릭시 메인 화면 이동
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                Toast toast = Toast.makeText(getApplicationContext(), "입장권 구매를 취소합니다.", Toast.LENGTH_SHORT);
                toast.show();

                startActivity(intent);
            }
        });

    }

}