package com.example.msa;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            // 백 스택이 비어 있으면 앱 종료
            finish();
        } else {
            // 백 스택에 프래그먼트가 있으면 기본 동작 수행
            super.onBackPressed();
        }
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
