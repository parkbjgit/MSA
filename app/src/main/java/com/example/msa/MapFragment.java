package com.example.msa;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.CircleOverlay;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    Marker marker = new Marker(); // 지도 상 마커를 나타내는 Marker 객체 선언
    private static NaverMap naverMap; // 지도를 나타내는 NaverMap 객체 선언
    private MapView map_fragment; // 지도를 나타내는 MapView 객체 선언
    private Button selectedbutton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        NaverMapSdk.getInstance(requireContext()).setClient(new NaverMapSdk.NaverCloudPlatformClient("lubhho3zva"));
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        map_fragment = view.findViewById(R.id.map_fragment);
        map_fragment.onCreate(savedInstanceState);
        map_fragment.getMapAsync(this);

        //필터버튼들 초기화 및 리스너 설정
        Button ridingFilter = view.findViewById(R.id.riding_filter);
        Button restaurantFilter = view.findViewById(R.id.restaurant_filter);
        Button cafeFilter = view.findViewById(R.id.cafe_filter);
        Button convenienceFilter = view.findViewById(R.id.convenience_filter);

        selectedbutton = ridingFilter;
        ridingFilter.setSelected(true);

        ridingFilter.setOnClickListener(this::onFilterClicked);
        restaurantFilter.setOnClickListener(this::onFilterClicked);
        cafeFilter.setOnClickListener(this::onFilterClicked);
        convenienceFilter.setOnClickListener(this::onFilterClicked);

        return view;
    }

    private void onFilterClicked(View view) {
        Button clickedButton = (Button) view;

        //이전에 선택된 버튼은 선택해제
        if(selectedbutton != null){
            selectedbutton.setSelected(false);
        }

        //새로 클릭된 버튼 선택
        clickedButton.setSelected(true);
        selectedbutton = clickedButton;
    }

    public void setMapOptions(NaverMap naverMap) {
        naverMap.setIndoorEnabled(true); // 실내지도 사용 여부 설정
        naverMap.setNightModeEnabled(true); // 야간 모드 사용 여부 설정
        naverMap.setMapType(NaverMap.MapType.Navi); // 지도 타입 설정
    }

    public void setMoveLocation(double x, double y) {
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(x, y)); // 해당 좌표로 카메라 이동
        naverMap.moveCamera(cameraUpdate);
    }

    private void addMarkerAndCircle(LatLng position, String caption, Integer color) {
        // 마커 설정
        Marker marker = new Marker();
        marker.setPosition(position);
        marker.setCaptionText(caption);
        marker.setWidth(80);
        marker.setHeight(80);
        marker.setMap(naverMap);

        //color가 null이 아닐때만 원을 그림
        if(color != null) {
            CircleOverlay circle = new CircleOverlay();
            circle.setCenter(position);
            circle.setRadius(100); // 원의 반경 설정 (단위: 미터)
            circle.setColor(Color.argb(80, Color.red(color), Color.green(color), Color.blue(color))); // 색상 설정 (50% 투명도)
            circle.setMap(naverMap);
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        setMapOptions(naverMap); // 지도 옵션 설정
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(37.51103128734522, 127.09836284873701), // 초기 카메라 위치 설정
                16 // 줌 레벨 설정
        );
        naverMap.setCameraPosition(cameraPosition); // 카메라 위치 설정

        // 마커와 원을 추가하는 메소드
        //addMarkerAndCircle(new LatLng(37.51103128734522, 127.09836284873701), "롯데월드", null); //색 선택 안해
        addMarkerAndCircle(new LatLng(37.511034520520695, 127.09717806527742), "후렌치레볼루션", Color.RED);
        addMarkerAndCircle(new LatLng(37.51120620917864 ,127.09922739837569), "후룸라이드", Color.GREEN);
        addMarkerAndCircle(new LatLng(37.50877477183853, 127.10051625967026), "자이로드롭", Color.YELLOW);
        addMarkerAndCircle(new LatLng(37.50883221943, 127.09914028644562), "아틀란티스", Color.GREEN);
        addMarkerAndCircle(new LatLng(37.50827786219699, 127.09969707129508), "자이로스윙", Color.RED);

        setMoveLocation(37.51103128734522, 127.09836284873701); // 초기 반경 설정
    }
}