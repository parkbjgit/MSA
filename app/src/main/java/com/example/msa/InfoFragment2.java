package com.example.msa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment2 extends Fragment implements OnMapReadyCallback {

    private GoogleMap gMap;
    private SupportMapFragment mapFragment;


    public class MapMarker {
        Marker marker; // 지도 위의 마커

        // 생성자
        public MapMarker(Marker marker) {
            this.marker = marker;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info2, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_facility_choice);
        if ( mapFragment != null ) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    private void addMapMarker(LatLng position, String caption) {
        Bitmap customMarker = createCustomMarker(caption);

        // 마커 옵션 생성 및 설정
        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .title(caption) // 마커의 제목에 캡션 설정
                .icon(BitmapDescriptorFactory.fromBitmap(customMarker)); // 아이콘 설정

        // 마커를 지도에 추가
        Marker marker = gMap.addMarker(markerOptions);

        if (marker != null) {
            MapMarker mapMarker = new MapMarker(marker);
            marker.setTag(mapMarker);
        }
    }

    private Bitmap resizeMarkerIcon(int resId, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), resId);
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
    }


    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        // 모든 기본 아이콘 제거
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.getUiSettings().setMapToolbarEnabled(false);  // 지도 도구 제거
        gMap.setBuildingsEnabled(false);  // 3D 건물 표시 제거
        gMap.setTrafficEnabled(false);  // 교통 정보 제거
        gMap.setIndoorEnabled(false);  // 실내 맵 제거
        gMap.getUiSettings().setCompassEnabled(false);  // 나침반 제거
        gMap.getUiSettings().setZoomControlsEnabled(false);  // 줌 컨트롤 제거

        // 다크 테마 스타일 적용
        boolean success = gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style));

        // 마커 클릭 리스너 설정 (GoogleMap의 리스너 사용)
        gMap.setOnMarkerClickListener(clickedMarker -> {
            String caption = clickedMarker.getTitle();  // 마커 제목 가져오기

            // InfoFragment3로 이동하며 데이터 전달
            InfoFragment3 infoFragment3 = new InfoFragment3();
            Bundle args = new Bundle();
            args.putString("markerName", caption);
            infoFragment3.setArguments(args);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, infoFragment3)
                    .addToBackStack(null)
                    .commit();

            return true;  // 이벤트 소비
        });

        // 서울에 마커 추가
        LatLng LotteWorld = new LatLng(37.51103128734522, 127.09836284873701);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(LotteWorld)
                .title("롯데월드");


        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LotteWorld, 17));

        addMapMarker(new LatLng(37.511034520520695, 127.09717806527742), "후렌치레볼루션");
        addMapMarker(new LatLng(37.51120620917864, 127.09922739837569), "후룸라이드");
        addMapMarker(new LatLng(37.50877477183853, 127.10051625967026), "자이로드롭");
        addMapMarker(new LatLng(37.50883221943, 127.09914028644562), "아틀란티스");
        addMapMarker(new LatLng(37.50827786219699, 127.09969707129508), "자이로스윙");
        addMapMarker(new LatLng(37.50927477717089, 127.10009783506393), "번지드롭");
        addMapMarker(new LatLng(37.511701300660036, 127.09928543185079), "스페인해적선");
        addMapMarker(new LatLng(37.51051008661316, 127.09790593088849), "회전목마");

    }

    private Bitmap createCustomMarker(String caption) {
        View markerView = LayoutInflater.from(getContext()).inflate(R.layout.custom_marker, null);
        TextView markerCaption = markerView.findViewById(R.id.marker_caption);
        markerCaption.setText(caption);

        // 측정 후 비트맵으로 변환
        markerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        markerView.layout(0, 0, markerView.getMeasuredWidth(), markerView.getMeasuredHeight());

        Bitmap bitmap = Bitmap.createBitmap(markerView.getMeasuredWidth(), markerView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        markerView.draw(canvas);
        return bitmap;
    }

    // MapView 생명주기 메서드들 추가
    @Override
    public void onResume() {
        super.onResume();
        mapFragment.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapFragment.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFragment.onLowMemory();
    }
}
