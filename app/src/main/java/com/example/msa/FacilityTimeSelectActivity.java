package com.example.msa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FacilityTimeSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facility_time_select);

        Button time1 = (Button) findViewById(R.id.btn_select_time1);
        Button time2 = (Button) findViewById(R.id.btn_select_time2);
        Button time3 = (Button) findViewById(R.id.btn_select_time3);
        Button time4 = (Button) findViewById(R.id.btn_select_time4);
        Button time5 = (Button) findViewById(R.id.btn_select_time5);
        Button time6 = (Button) findViewById(R.id.btn_select_time6);

        //버튼 비활성 - 조건 추가 예정
        time1.setEnabled(false);

        //버튼 활성
        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), ((TextView)view).getText().toString() + " 시간은 현재 이용할 수 없습니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}