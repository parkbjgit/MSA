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
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.CircleOverlay;
import com.naver.maps.map.overlay.OverlayImage;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment2 extends Fragment implements OnMapReadyCallback {

    private List<MapMarker> ridingMarkers = new ArrayList<>();
    private static NaverMap naverMap;
    private MapView map_facility_choice;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        NaverMapSdk.getInstance(requireContext()).setClient(new NaverMapSdk.NaverCloudPlatformClient("lubhho3zva"));

        View view = inflater.inflate(R.layout.facility_select, container, false);

        map_facility_choice = view.findViewById(R.id.map_facility_choice);
        map_facility_choice.onCreate(savedInstanceState);
        map_facility_choice.getMapAsync(this);

        return view;
    }

    public void setMapOptions(NaverMap naverMap) {
       naverMap.setIndoorEnabled(true);
       naverMap.setNightModeEnabled(true);
       naverMap.setMapType(NaverMap.MapType.Navi);
       //naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true);
    }

    public void setMoveLocation(double x, double y){
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(x, y));
        naverMap.moveCamera(cameraUpdate);
    }

    private void addMapMarker(LatLng position, String caption) {
        // Create and configure the marker
        Marker marker = new Marker();
        marker.setPosition(position);
        marker.setCaptionText(caption);
        marker.setWidth(80);
        marker.setHeight(80);
        marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker));
        marker.setMap(naverMap);

    }

    public class MapMarker {
        Marker marker;

        public MapMarker(Marker marker) {
            this.marker = marker;
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        setMapOptions(naverMap); // Configure map options

        // Set initial camera position
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(37.51103128734522, 127.09836284873701), // Initial position
                16 // Zoom level
        );
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setScaleBarEnabled(false);
        uiSettings.setCompassEnabled(false);
        uiSettings.setLocationButtonEnabled(false);
        uiSettings.setZoomControlEnabled(false);

        naverMap.setCameraPosition(cameraPosition); // Apply camera position

        addMapMarker(new LatLng(37.511034520520695, 127.09717806527742), "후렌치레볼루션");
        addMapMarker(new LatLng(37.51120620917864, 127.09922739837569), "후룸라이드");
        addMapMarker(new LatLng(37.50877477183853, 127.10051625967026), "자이로드롭");
        addMapMarker(new LatLng(37.50883221943, 127.09914028644562), "아틀란티스");
        addMapMarker(new LatLng(37.50827786219699, 127.09969707129508), "자이로스윙");
        addMapMarker(new LatLng(37.50927477717089, 127.10009783506393), "번지드롭");
        addMapMarker(new LatLng(37.511701300660036, 127.09928543185079), "스페인해적선");
        addMapMarker(new LatLng(37.51051008661316, 127.09790593088849), "회전목마");

    }

    @Override
    public void onStart() {
        super.onStart();
        map_facility_choice.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        map_facility_choice.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map_facility_choice.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        map_facility_choice.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        map_facility_choice.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map_facility_choice.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        map_facility_choice.onSaveInstanceState(outState);
    }
}
