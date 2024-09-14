package com.example.msa;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class MapFragment extends Fragment implements OnMapReadyCallback {

    Marker marker = new Marker(); // 지도 상 마커를 나타내는 Marker 객체 선언
    private static NaverMap naverMap; // 지도를 나타내는 NaverMap 객체 선언
    private MapView map_fragment; // 지도를 나타내는 MapView 객체 선언
    private static int distance = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        NaverMapSdk.getInstance(requireContext()).setClient(new NaverMapSdk.NaverCloudPlatformClient("lubhho3zva"));
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        map_fragment = view.findViewById(R.id.map_fragment);
        map_fragment.onCreate(savedInstanceState);
        map_fragment.getMapAsync(this);

        return view;
    }

    public void setMapOptions(NaverMap naverMap) {
        naverMap.setIndoorEnabled(true); // 실내지도 사용 여부 설정
        naverMap.setNightModeEnabled(true); // 야간 모드 사용 여부 설정
        naverMap.setMapType(NaverMap.MapType.Navi); // 지도 타입 설정
    }

    public void setMoveLocation(double x, double y, int move) {
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(x, y)); // 해당 좌표로 카메라 이동
        naverMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        setMapOptions(naverMap); // 지도 옵션 설정
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(37.51103128734522, 127.09836284873701), // 초기 좌표 설정
                16 // 줌 레벨 설정
        );
        naverMap.setCameraPosition(cameraPosition); // 카메라 위치 설정

        marker.setPosition(new LatLng(37.51103128734522, 127.09836284873701)); // 초기 마커 위치 설정
        marker.setIconTintColor(Color.RED);
        marker.setCaptionText("롯데월드");
        marker.setMap(naverMap);

        Marker marker2 = new Marker();
        marker2.setPosition(new LatLng( 37.511034520520695, 127.09717806527742 )); // 후렌치레볼루션
        marker2.setWidth(80);
        marker2.setHeight(80);
        marker2.setCaptionText("후렌치레볼루션");
        marker2.setMap(naverMap);

        Marker marker3 = new Marker();
        marker3.setPosition(new LatLng( 37.51120620917864 ,127.09922739837569)); //후룸라이드
        marker3.setWidth(80);
        marker3.setHeight(80);
        marker3.setCaptionText("후룸라이드");
        marker3.setMap(naverMap);

        Marker marker4 = new Marker();
        marker4.setPosition(new LatLng(37.50877477183853, 127.10051625967026)); //자이로드롭
        marker4.setWidth(80);
        marker4.setHeight(80);
        marker4.setCaptionText("자이로드롭");
        marker4.setMap(naverMap);

        Marker marker5 = new Marker();
        marker5.setPosition(new LatLng(37.50883221943, 127.09914028644562)); //아틀란티스
        marker5.setWidth(80);
        marker5.setHeight(80);
        marker5.setCaptionText("아틀란티스");
        marker5.setMap(naverMap);

        Marker marker6 = new Marker();
        marker6.setPosition(new LatLng(37.50827786219699, 127.09969707129508));//자이로스윙
        marker6.setWidth(80);
        marker6.setHeight(80);
        marker6.setCaptionText("자이로스윙");
        marker6.setMap(naverMap);

        setMoveLocation(37.51103128734522, 127.09836284873701, distance); // 초기 반경 설정
    }
}