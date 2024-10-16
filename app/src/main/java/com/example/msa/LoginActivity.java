package com.example.msa;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button btn_google_login, btn_kakao_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("LoginActivity", "onCreate() 호출됨");
        mAuth = FirebaseAuth.getInstance();


        btn_google_login = findViewById(R.id.btn_google_login);
        btn_kakao_login = findViewById(R.id.btn_kakao_login);


        //자동 로그인이 되어있든 아니든 로그인 버튼을 눌러 mainactivity로 이동하도록
        btn_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GoogleLogin.class);
                startActivity(intent);
                finish();
            }
        });

//        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
//
//            btn_google_login.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), GoogleLogin.class);
//                    startActivity(intent);
//                    finish();
//                }
//            });
//        } else {
//            btn_google_login.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), GoogleLogin.class);
//                    startActivity(intent);
//                    finish();
//                }
//            });
//        }

        // 카카오 로그인 버튼 리스너는 기존대로 유지하거나 필요시 추가 구현
        btn_kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 카카오 로그인 구현 코드 필요
                // 예: Intent intent = new Intent(LoginActivity.this, KakaoLoginActivity.class);
                // startActivity(intent);
            }
        });
    }

}
