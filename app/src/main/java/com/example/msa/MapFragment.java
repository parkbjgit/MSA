package com.example.msa;

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
        marker.setMap(naverMap); // 지도에 마커 표시
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
        setMoveLocation(37.51103128734522, 127.09836284873701, distance); // 초기 반경 설정

        marker.setMap(naverMap); // 지도에 마커 표시
    }
}