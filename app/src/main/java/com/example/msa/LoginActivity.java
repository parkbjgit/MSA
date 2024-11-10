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

    private Button btn_google_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("LoginActivity", "onCreate() 호출됨");
        mAuth = FirebaseAuth.getInstance();


        btn_google_login = findViewById(R.id.btn_google_login);


        //자동 로그인이 되어있든 아니든 로그인 버튼을 눌러 mainactivity로 이동하도록
        btn_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GoogleLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
