package com.example.msa;

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
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    FrameLayout fragment_container;
    BottomNavigationView bottomNavigationView;
    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(); // 객체 정의
        SettingListener(); // 리스너 등록

        if (savedInstanceState != null) {
            activeFragment = getSupportFragmentManager().getFragment(savedInstanceState, "activeFragment");
        } else {
            bottomNavigationView.setSelectedItemId(R.id.tab_home);
            switchFragment(new ReservationFragment());
        }

        getHashKey();
    }

    private void getHashKey() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            Log.e("KeyHash", "Unable to get MessageDigest.", e);
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

        // Replace fragment instead of adding and hiding previous fragments
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        activeFragment = fragment;
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
