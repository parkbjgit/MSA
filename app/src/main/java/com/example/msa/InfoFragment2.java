package com.example.msa;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InfoFragment2 extends Fragment implements OnMapReadyCallback {

    private GoogleMap gMap;
    private SupportMapFragment mapFragment;
    private LatLng myLocation;

    // 놀이기구 정보를 담을 클래스
    private class Ride {
        LatLng position;
        String name;
        List<LatLng> routePoints; 

        public Ride(LatLng position, String name, List<LatLng> routePoints) {
            this.position = position;
            this.name = name;
            this.routePoints = routePoints;
        }
    }

    private List<Ride> rides = new ArrayList<>();

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
        if (mapFragment != null) {
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        // 지도 기본 설정
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.getUiSettings().setMapToolbarEnabled(false);
        gMap.setBuildingsEnabled(false);
        gMap.setTrafficEnabled(false);
        gMap.setIndoorEnabled(false);
        gMap.getUiSettings().setCompassEnabled(false);
        gMap.getUiSettings().setZoomControlsEnabled(false);

        // 지도 테마 설정
        gMap.setPadding(0, 0, 0, 1500);  // 하단 패딩 설정
        boolean success = gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style));

        // 지도 중심
        LatLng lotteWorld = new LatLng(37.512670758789156, 127.09878915785984);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lotteWorld, 17));

        // 내 위치
        myLocation = new LatLng(37.51073357397042, 127.09882053642566);
        gMap.addMarker(new MarkerOptions()
                .position(myLocation)
                .title("내 위치")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        // 마커 클릭 리스너 설정
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

        // 놀이기구 마커 및 경로 추가
        initializeRides();

        for (Ride ride : rides) {
            addMapMarker(ride.position, ride.name);

            // 경로가 있는 놀이기구에 대해서만 경로 그리기
            if (ride.routePoints != null && !ride.routePoints.isEmpty()) {
                drawRoute(ride.routePoints);
            }
        }
    }

    // 놀이기구 정보 및 경로 초기화
    private void initializeRides() {

        rides.add(new Ride(
                new LatLng(37.511034520520695, 127.09717806527742),
                "후렌치레볼루션",
                Arrays.asList(
                        myLocation,
                        new LatLng(37.510800, 127.098700),
                        new LatLng(37.510900, 127.098500),
                        new LatLng(37.510950, 127.098300),
                        new LatLng(37.511000, 127.098100),
                        new LatLng(37.511034520520695, 127.09717806527742)
                )
        ));

        rides.add(new Ride(
                new LatLng(37.51120620917864, 127.09922739837569),
                "후룸라이드",
                Arrays.asList(
                        myLocation,
                        new LatLng(37.510800, 127.099000),
                        new LatLng(37.511000, 127.099100),
                        new LatLng(37.51120620917864, 127.09922739837569)
                )
        ));

        rides.add(new Ride(
                new LatLng(37.511701300660036, 127.09928543185079),
                "스페인해적선",
                Arrays.asList(
                        myLocation,
                        new LatLng(37.510800, 127.099100),
                        new LatLng(37.511000, 127.099150),
                        new LatLng(37.511300, 127.099200),
                        new LatLng(37.511500, 127.099250),
                        new LatLng(37.511701300660036, 127.09928543185079)
                )
        ));

        rides.add(new Ride(
                new LatLng(37.51051008661316, 127.09790593088849),
                "회전목마",
                Arrays.asList(
                        myLocation,
                        new LatLng(37.510600, 127.098600),
                        new LatLng(37.510550, 127.098300),
                        new LatLng(37.510530, 127.098100),
                        new LatLng(37.51051008661316, 127.09790593088849)
                )
        ));

        rides.add(new Ride(
                new LatLng(37.50877477183853, 127.10051625967026),
                "자이로드롭",
                null  // 경로 없음
        ));

        rides.add(new Ride(
                new LatLng(37.50883221943, 127.09914028644562),
                "아틀란티스",
                null  // 경로 없음
        ));

        rides.add(new Ride(
                new LatLng(37.50827786219699, 127.09969707129508),
                "자이로스윙",
                null  // 경로 없음
        ));

        rides.add(new Ride(
                new LatLng(37.50927477717089, 127.10009783506393),
                "번지드롭",
                null  // 경로 없음
        ));
    }

    // 경로를 지도에 그리는 메서드
    private void drawRoute(List<LatLng> routePoints) {
        PolylineOptions lineOptions = new PolylineOptions()
                .addAll(routePoints)
                .width(8)
                .color(Color.RED);

        gMap.addPolyline(lineOptions);
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
