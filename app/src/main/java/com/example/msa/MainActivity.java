package com.example.msa;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FrameLayout fragment_container;
    BottomNavigationView bottomNavigationView;
    private Fragment activeFragment;

    private FirebaseAuth mAuth;

    private TicketViewModel ticketViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ticketViewModel = new ViewModelProvider(this).get(TicketViewModel.class);

        //Log.d("엑티비티 메인 레이아웃", "onCreate() 호출됨");
        //앱 실행시 로그인 되지 않은 경우
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            //Log.d("메인엑티비티 oncreate 호출", "로그인 되지 않음");
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        } else {    //로그인 된 경우
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            //Log.d("메인엑티비티 로그인 된경우","로그인이  됨");
        }

        init(); // 객체 정의
        SettingListener(); // 리스너 등록

        if (savedInstanceState != null) {
            activeFragment = getSupportFragmentManager().getFragment(savedInstanceState, "activeFragment");
        } else {
            bottomNavigationView.setSelectedItemId(R.id.tab_home);
            switchFragment(new ReservationFragment());
        }
    }

    private void init() {
        fragment_container = findViewById(R.id.fragment_container);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void SettingListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new TabSelectedListener());
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace fragment
        transaction.replace(R.id.fragment_container, fragment);

        // Do not add root fragments to the back stack
        transaction.commit();
        activeFragment = fragment;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            showCustomExitDialog();
        } else {
            // 백 스택에 프래그먼트가 있으면 기본 동작 수행
            super.onBackPressed();
        }
    }

    public void showCustomExitDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_exit, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        AppCompatButton cancelButton = dialogView.findViewById(R.id.cancel_btn);
        AppCompatButton exitButton = dialogView.findViewById(R.id.exit_btn);

        cancelButton.setOnClickListener( v-> dialog.dismiss());
        exitButton.setOnClickListener( v-> {
                    dialog.dismiss();
                    finishAffinity();   //그냥 finish는 activity만 종료
                });

        // 다이얼로그 스타일 설정 및 표시
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  // radius 때문에 겉에 투명색으로 설정
        }

        dialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (activeFragment != null) {
            getSupportFragmentManager().putFragment(outState, "activeFragment", activeFragment);
        }
    }

    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int itemId = menuItem.getItemId();

            // Clear the back stack when switching tabs
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            if (itemId == R.id.tab_home) {
                switchFragment(new ReservationFragment());
                return true;
            } else if (itemId == R.id.tab_reservation) {
                switchFragment(new InfoFragment());
                return true;
            } else if (itemId == R.id.tab_congestion) {
                switchFragment(new MapFragment());
                return true;
            } else if (itemId == R.id.tab_mypage) {
                switchFragment(new MypageFragment());
                return true;
            }
            return false;
        }
    }
}
