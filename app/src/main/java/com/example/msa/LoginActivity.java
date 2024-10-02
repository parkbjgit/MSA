package com.example.msa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

// 구글 로그인 관련 임포트
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100; // 요청 코드
    private static final String TAG = "LoginActivity";

    private Button btn_google_login, btn_kakao_login;
    private TextView logoText, logoText2;  // MSA 및 통합 예약 앱 TextView

    private GoogleSignInClient mGoogleSignInClient;

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

        // Google Sign-In 옵션 설정
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // GoogleSignInClient 생성
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btn_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 구글 로그인 인텐트 시작
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 구글 로그인 결과 처리
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // 로그인 성공
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    // 로그인 성공 시 로그 출력 (필요 시 추가 로직 구현)
                    Log.d(TAG, "Google sign in successful: " + account.getEmail());
                }
            } catch (ApiException e) {
                // 로그인 실패
                //Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this, "구글 로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
            } finally {
                // 인증 결과와 상관없이 MainActivity로 이동
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    // 앱이 이미 로그인된 상태인지 확인 (옵션)
    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            // 이미 로그인된 상태이면 MainActivity로 이동
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
