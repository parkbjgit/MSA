package com.example.msa;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.maps.android.SphericalUtil;

public class InfoFragment2 extends Fragment implements OnMapReadyCallback {

    private GoogleMap gMap;
    private SupportMapFragment mapFragment;
    private LatLng myLocation;

    // 놀이기구 정보를 담을 클래스
    private class Ride {
        LatLng position;
        String name;
        List<LatLng> routePoints;
        int nearcount;

        public Ride(LatLng position, String name, List<LatLng> routePoints, int nearcount) {
            this.position = position;
            this.name = name;
            this.routePoints = routePoints;
            this.nearcount = nearcount;
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
        //initializeRides(view);

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

        boolean success = gMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style));

        // 지도 중심
        LatLng lotteWorld = new LatLng(37.51073357397042, 127.09882053642566);
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
        initializeRides(getView());

        for (Ride ride : rides) {
            addMapMarker(ride.position, ride.name);

            // 경로가 있는 놀이기구에 대해서만 경로 그리기
            if (ride.routePoints != null && !ride.routePoints.isEmpty()) {
                drawRoute(ride.routePoints);
            }
        }
    }

    // 놀이기구 정보 및 경로 초기화
    private void initializeRides(View view) {

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
                ),
                2
        ));

        rides.add(new Ride(
                new LatLng(37.51120620917864, 127.09922739837569),
                "후룸라이드",
                Arrays.asList(
                        myLocation,
                        new LatLng(37.510800, 127.099000),
                        new LatLng(37.511000, 127.099100),
                        new LatLng(37.51120620917864, 127.09922739837569)
                ),
                2
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
                ),
                2
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
                ),
                0
        ));

        // 아틀란티스 경로 추가
        rides.add(new Ride(
                new LatLng(37.50883221943, 127.09914028644562),
                "아틀란티스",
                Arrays.asList(
                        myLocation,
                        new LatLng(37.509000, 127.098800),
                        new LatLng(37.509100, 127.099000),
                        new LatLng(37.509200, 127.099100),
                        new LatLng(37.50883221943, 127.09914028644562) // 아틀란티스
                ),
                2
        ));

        // 번지드롭 경로 추가
        rides.add(new Ride(
                new LatLng(37.50927477717089, 127.10009783506393),
                "번지드롭",
                Arrays.asList(
                        myLocation,
                        new LatLng(37.510000, 127.099000),
                        new LatLng(37.509500, 127.099500),
                        new LatLng(37.509600, 127.099700),
                        new LatLng(37.50940785278853, 127.10029762046987),
                        new LatLng(37.50927477717089, 127.10009783506393) // 번지드롭
                ),
                1
        ));

        // 자이로스윙 경로 추가
        rides.add(new Ride(
                new LatLng(37.50827786219699, 127.09969707129508),
                "자이로스윙",
                Arrays.asList(
                        myLocation,
                        new LatLng(37.509200, 127.099200),
                        new LatLng(37.509000, 127.099300),
                        new LatLng(37.508800, 127.099400),
                        new LatLng(37.508500, 127.099500),
                        new LatLng(37.50827786219699, 127.09969707129508) // 자이로스윙
                ),
                0
        ));

        // 자이로드롭 경로 추가
        rides.add(new Ride(
                new LatLng(37.50877477183853, 127.10051625967026),
                "자이로드롭",
                Arrays.asList(
                        myLocation,
                        new LatLng(37.509000, 127.099800),
                        new LatLng(37.508800, 127.100000),
                        new LatLng(37.508700, 127.100200),
                        new LatLng(37.50877477183853, 127.10051625967026) // 자이로드롭
                ),
                1
        ));

        for (Ride ride : rides) {
            double distance = calculateRouteDistance(ride.routePoints);
            Log.d("Route Distance", "Route for " + ride.name + " is " + distance + " meters.");
        }

        rides.sort((ride1, ride2) -> Double.compare(
                calculateRouteDistance(ride1.routePoints),
                calculateRouteDistance(ride2.routePoints)
        ));

        LinearLayout rideListContainer = view.findViewById(R.id.ride_list_container);
        for (Ride ride : rides) {
            double distance = calculateRouteDistance(ride.routePoints);

            View rideItemView = createRideItemView(ride.name, distance, ride.nearcount);
            rideListContainer.addView(rideItemView);
        }
    }

    private View createRideItemView(String name, double distance, int nearcount) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View itemView = inflater.inflate(R.layout.ride_item, null);

        // 레이아웃 파라미터로 뷰 간격 설정
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 32); // 상단과 하단에 16dp 마진 추가
        itemView.setLayoutParams(layoutParams);

        // 이미지, 텍스트뷰 등 뷰 설정
        ImageView rideImageView = itemView.findViewById(R.id.ride_image);
        TextView nameTextView = itemView.findViewById(R.id.ride_name);
        TextView distanceTextView = itemView.findViewById(R.id.ride_distance);
        TextView congestionTextView = itemView.findViewById(R.id.congestion_text_view);

        // 놀이기구 이름과 거리 설정
        nameTextView.setText(name);
        distanceTextView.setText(String.format("%.0f m", distance));

        // 혼잡도 설정
        switch (nearcount) {
            case 2:
                congestionTextView.setText("혼잡");
                congestionTextView.setTextColor(Color.RED);
                break;
            case 1:
                congestionTextView.setText("약간 혼잡");
                congestionTextView.setTextColor(Color.YELLOW);
                break;
            case 0:
                congestionTextView.setText("여유");
                congestionTextView.setTextColor(Color.GREEN);
                break;
        }

        // 이미지 설정
        switch (name) {
            case "후룸라이드":
                rideImageView.setImageResource(R.drawable.froomride);
                break;
            case "회전목마":
                rideImageView.setImageResource(R.drawable.horse);
                break;
            case "스페인해적선":
                rideImageView.setImageResource(R.drawable.spainpirates);
                break;
            case "후렌치레볼루션":
                rideImageView.setImageResource(R.drawable.frenchrevolution);
                break;
            case "번지드롭":
                rideImageView.setImageResource(R.drawable.bungedrop);
                break;
            case "아틀란티스":
                rideImageView.setImageResource(R.drawable.atlantis);
                break;
            case "자이로스윙":
                rideImageView.setImageResource(R.drawable.ziroswing);
                break;
            case "자이로드롭":
                rideImageView.setImageResource(R.drawable.zirodrop);
                break;
            default:
                break;
        }

        return itemView;
    }



    private double calculateRouteDistance(List<LatLng> routePoints) {
        double totalDistance = 0.0;
        for (int i = 0; i < routePoints.size() - 1; i++) {
            totalDistance += SphericalUtil.computeDistanceBetween(routePoints.get(i), routePoints.get(i + 1));
        }
        return totalDistance;
    }
    // 경로를 지도에 그리는 메서드
    private void drawRoute(List<LatLng> routePoints) {
        PolylineOptions lineOptions = new PolylineOptions()
                .addAll(routePoints)
                .width(8)
                .color(Color.parseColor("#5274E6"));

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
}
