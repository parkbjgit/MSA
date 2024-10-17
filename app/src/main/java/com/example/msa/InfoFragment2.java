package com.example.msa;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment2 extends Fragment {

//    private List<MapMarker> markers = new ArrayList<>();
//
//    private NaverMap naverMap;
//    private MapView map_facility_choice;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //NaverMapSdk.getInstance(requireContext()).setClient(new NaverMapSdk.NaverCloudPlatformClient("lubhho3zva"));

        View view = inflater.inflate(R.layout.fragment_info2, container, false);
//
//        map_facility_choice = view.findViewById(R.id.map_facility_choice);
//        map_facility_choice.onCreate(savedInstanceState);
//        map_facility_choice.getMapAsync(this);

        return view;
    }

//    public void setMapOptions(NaverMap naverMap) {
//       naverMap.setIndoorEnabled(true);
//       naverMap.setNightModeEnabled(true);
//       naverMap.setMapType(NaverMap.MapType.Navi);
//       //naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true);
//
//        UiSettings uiSettings = naverMap.getUiSettings();
//        uiSettings.setCompassEnabled(false);
//        uiSettings.setScaleBarEnabled(false);
//        uiSettings.setLocationButtonEnabled(false);
//        uiSettings.setZoomControlEnabled(false);
//    }

//    @Override
//    public boolean onClick(@NonNull Overlay overlay) {
//        if (overlay instanceof Marker) {
//            Marker clickedMarker = (Marker) overlay;
//            String caption = clickedMarker.getCaptionText();
//
//            InfoFragment3 infoFragment3 = new InfoFragment3();
//            Bundle args = new Bundle();
//            args.putString("markerName", caption);
//            infoFragment3.setArguments(args);
//
//            getParentFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, infoFragment3)
//                    .addToBackStack(null)
//                    .commit();
//
//
//
//            return true;
//        }
//        return false;
//    }

//    public class MapMarker {
//        Marker marker;
//
//        public MapMarker(Marker marker) {
//            this.marker = marker;
//        }
//    }

//    private void addMapMarker(LatLng position, String caption) {
//        try {
//            // 마커 생성 및 설정
//            Marker marker = new Marker();
//            marker.setPosition(position);
//            marker.setCaptionText(caption);
//            marker.setWidth(80);
//            marker.setHeight(80);
//            marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker));
//            marker.setMap(naverMap);
//
//            //마커 클릭 리스너 설정
//            marker.setOnClickListener(this); // 프래그먼트가 클릭 리스너를 처리하도록 설정
//
//            markers.add(new MapMarker(marker));
//            // 마커 클릭 시 이벤트 처리
//        } catch (Exception e) {
//            e.printStackTrace();
//            // 필요 시 사용자에게 오류 메시지 표시
//        }
//    }


//    @Override
//    public void onMapReady(@NonNull NaverMap naverMap) {
//        this.naverMap = naverMap;
//        setMapOptions(naverMap); // Configure map options
//
//        // Set initial camera position
//        CameraPosition cameraPosition = new CameraPosition(
//                new LatLng(37.51103128734522, 127.09836284873701), // Initial position
//                16 // Zoom level
//        );
//
//        naverMap.setCameraPosition(cameraPosition); // Apply camera position
//
//        addMapMarker(new LatLng(37.511034520520695, 127.09717806527742), "후렌치레볼루션");
//        addMapMarker(new LatLng(37.51120620917864, 127.09922739837569), "후룸라이드");
//        addMapMarker(new LatLng(37.50877477183853, 127.10051625967026), "자이로드롭");
//        addMapMarker(new LatLng(37.50883221943, 127.09914028644562), "아틀란티스");
//        addMapMarker(new LatLng(37.50827786219699, 127.09969707129508), "자이로스윙");
//        addMapMarker(new LatLng(37.50927477717089, 127.10009783506393), "번지드롭");
//        addMapMarker(new LatLng(37.511701300660036, 127.09928543185079), "스페인해적선");
//        addMapMarker(new LatLng(37.51051008661316, 127.09790593088849), "회전목마");
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        map_facility_choice.onStart();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        map_facility_choice.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        map_facility_choice.onPause();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        map_facility_choice.onStop();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        map_facility_choice.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        map_facility_choice.onLowMemory();
//    }
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        map_facility_choice.onSaveInstanceState(outState);
//    }
}
