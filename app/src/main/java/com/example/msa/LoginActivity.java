package com.example.msa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button btn_google_login, btn_kakao_login;
    private TextView logoText, logoText2;  // MSA 및 통합 예약 앱 TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TextView 요소 찾기
        logoText = findViewById(R.id.logoText);
        logoText2 = findViewById(R.id.logoText2);
        btn_google_login = findViewById(R.id.btn_google_login);
        btn_kakao_login = findViewById(R.id.btn_kakao_login);

        // 애니메이션 로드
        Animation scaleFadeAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_fade_animation);

        // TextView에 애니메이션 적용
        logoText.startAnimation(scaleFadeAnimation);
        logoText2.startAnimation(scaleFadeAnimation);
//        btn_google_login.startAnimation(scaleFadeAnimation);
//        btn_kakao_login.startAnimation(scaleFadeAnimation);


        btn_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // btn_google_login 클릭 시 activity_main으로 화면 이동
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
