package com.example.msa;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    LinearLayout home_ly;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(); //객체 정의
        SettingListener(); //리스너 등록

        //맨 처음 시작할 탭 설정
        bottomNavigationView.setSelectedItemId(R.id.tab_home);
    }

    private void init() {
        home_ly = findViewById(R.id.home_ly);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void SettingListener() {
        //선택 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new TabSelectedListener());
    }

    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.tab_home) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_ly, new ReservationFragment())
                        .commit();
                return true;
            } else if (itemId == R.id.tab_reservation) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_ly, new InfoFragment())
                        .commit();
                return true;
            } else if (itemId == R.id.tab_congestion) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_ly, new MapFragment())
                        .commit();
                return true;
            } else if (itemId == R.id.tab_mypage) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_ly, new MypageFragment())
                        .commit();
                return true;
            }
            return false;
        }
    }


}