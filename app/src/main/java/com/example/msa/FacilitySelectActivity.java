package com.example.msa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FacilitySelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_select);

        Button fac1 = (Button) findViewById(R.id.btn_facility1);
        //Button fac2 = (Button) findViewById(R.id.btn_facility2);
        //Button fac3 = (Button) findViewById(R.id.btn_facility3);
        //Button fac4 = (Button) findViewById(R.id.btn_facility4);

        fac1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), FacilityTimeSelectActivity.class);
                startActivity(intent);
            }
        });

        //버튼 클릭 이벤트 추가 예정 (fac2,3,4)
    }
}