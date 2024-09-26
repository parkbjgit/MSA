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

public class MapFragment extends Fragment implements OnMapReadyCallback {

    // Define category enum
    public enum Category {
        RIDING,
        RESTAURANT,
        CAFE,
        CONVENIENCE
    }

    public class MapMarker {
        Marker marker;
        CircleOverlay circle;
        Category category;

        public MapMarker(Marker marker, CircleOverlay circle, Category category) {
            this.marker = marker;
            this.circle = circle;
            this.category = category;
        }
    }

    private List<MapMarker> ridingMarkers = new ArrayList<>();
    private List<MapMarker> restaurantMarkers = new ArrayList<>();
    private List<MapMarker> cafeMarkers = new ArrayList<>();
    private List<MapMarker> convenienceMarkers = new ArrayList<>();

    private NaverMap naverMap; // NaverMap instance
    private MapView map_fragment; // MapView instance
    private Button selectedButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NaverMapSdk.getInstance(requireContext()).setClient(new NaverMapSdk.NaverCloudPlatformClient("lubhho3zva"));
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        map_fragment = view.findViewById(R.id.map_fragment);
        map_fragment.onCreate(savedInstanceState);
        map_fragment.getMapAsync(this);

        // Initialize filter buttons and set listeners
        Button ridingFilter = view.findViewById(R.id.riding_filter);
        Button restaurantFilter = view.findViewById(R.id.restaurant_filter);
        Button cafeFilter = view.findViewById(R.id.cafe_filter);
        Button convenienceFilter = view.findViewById(R.id.convenience_filter);

        selectedButton = ridingFilter;
        ridingFilter.setSelected(true);

        ridingFilter.setOnClickListener(this::onFilterClicked);
        restaurantFilter.setOnClickListener(this::onFilterClicked);
        cafeFilter.setOnClickListener(this::onFilterClicked);
        convenienceFilter.setOnClickListener(this::onFilterClicked);

        return view;
    }

    private void onFilterClicked(View view) {
        Button clickedButton = (Button) view;

        if (selectedButton != null) {
            selectedButton.setSelected(false);
        }

        clickedButton.setSelected(true);
        selectedButton = clickedButton;

        Category selectedCategory;

        if (view.getId() == R.id.riding_filter) {
            selectedCategory = Category.RIDING;
        } else if (view.getId() == R.id.restaurant_filter) {
            selectedCategory = Category.RESTAURANT;
        } else if (view.getId() == R.id.cafe_filter) {
            selectedCategory = Category.CAFE;
        } else if (view.getId() == R.id.convenience_filter) {
            selectedCategory = Category.CONVENIENCE;
        } else {
            selectedCategory = Category.RIDING; // Default category
        }

        showCategory(selectedCategory);
    }

    public void setMapOptions(NaverMap naverMap) {
        naverMap.setIndoorEnabled(true); // Enable indoor maps
        naverMap.setNightModeEnabled(true); // Enable night mode
        naverMap.setMapType(NaverMap.MapType.Navi); // Set map type to Navi

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setScaleBarEnabled(false);
        uiSettings.setLocationButtonEnabled(false);
        uiSettings.setZoomControlEnabled(false);
        // POI 레이어 숨기기
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, false);
    }

    /**
     * Moves the camera to a specified location.
     */
    public void setMoveLocation(double x, double y) {
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(x, y));
        naverMap.moveCamera(cameraUpdate);
    }

    private void addMapMarker(LatLng position, String caption, Integer color, Category category) {
        // Create and configure the marker
        Marker marker = new Marker();
        marker.setPosition(position);
        marker.setCaptionText(caption);
        marker.setWidth(80);
        marker.setHeight(80);
        marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker));
        marker.setMap(naverMap);

        // Create and configure the circle overlay if color is provided
        CircleOverlay circle = null;
        if (color != null) {
            circle = new CircleOverlay();
            circle.setCenter(position);
            circle.setRadius(45); // Radius in meters
            circle.setColor(Color.argb(80, Color.red(color), Color.green(color), Color.blue(color))); // 50% transparency
            circle.setMap(naverMap);
        }

        // Create a MapMarker instance and add it to the appropriate list
        MapMarker mapMarker = new MapMarker(marker, circle, category);
        switch (category) {
            case RIDING:
                ridingMarkers.add(mapMarker);
                break;
            case RESTAURANT:
                restaurantMarkers.add(mapMarker);
                break;
            case CAFE:
                cafeMarkers.add(mapMarker);
                break;
            case CONVENIENCE:
                convenienceMarkers.add(mapMarker);
                break;
        }
    }

    private void showCategory(Category category) {
        hideAllMarkers();

        List<MapMarker> markersToShow;
        switch (category) {
            case RIDING:
                markersToShow = ridingMarkers;
                break;
            case RESTAURANT:
                markersToShow = restaurantMarkers;
                break;
            case CAFE:
                markersToShow = cafeMarkers;
                break;
            case CONVENIENCE:
                markersToShow = convenienceMarkers;
                break;
            default:
                markersToShow = new ArrayList<>();
        }

        for (MapMarker mapMarker : markersToShow) {
            mapMarker.marker.setMap(naverMap);
            if (mapMarker.circle != null) {
                mapMarker.circle.setMap(naverMap);
            }
        }
    }

    private void hideAllMarkers() {
        for (MapMarker mapMarker : ridingMarkers) {
            mapMarker.marker.setMap(null);
            if (mapMarker.circle != null) {
                mapMarker.circle.setMap(null);
            }
        }

        for (MapMarker mapMarker : restaurantMarkers) {
            mapMarker.marker.setMap(null);
            if (mapMarker.circle != null) {
                mapMarker.circle.setMap(null);
            }
        }

        for (MapMarker mapMarker : cafeMarkers) {
            mapMarker.marker.setMap(null);
            if (mapMarker.circle != null) {
                mapMarker.circle.setMap(null);
            }
        }

        for (MapMarker mapMarker : convenienceMarkers) {
            mapMarker.marker.setMap(null);
            if (mapMarker.circle != null) {
                mapMarker.circle.setMap(null);
            }
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        setMapOptions(naverMap); // Configure map options

        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(37.51103128734522, 127.09836284873701),
                16 // Zoom level
        );

        naverMap.setCameraPosition(cameraPosition);

        addMapMarker(new LatLng(37.511034520520695, 127.09717806527742), "후렌치레볼루션", Color.RED, Category.RIDING);
        addMapMarker(new LatLng(37.51120620917864, 127.09922739837569), "후룸라이드", Color.GREEN, Category.RIDING);
        addMapMarker(new LatLng(37.50877477183853, 127.10051625967026), "자이로드롭", Color.YELLOW, Category.RIDING);
        addMapMarker(new LatLng(37.50883221943, 127.09914028644562), "아틀란티스", Color.GREEN, Category.RIDING);
        addMapMarker(new LatLng(37.50827786219699, 127.09969707129508), "자이로스윙", Color.RED, Category.RIDING);
        addMapMarker(new LatLng(37.50927477717089, 127.10009783506393), "번지드롭", Color.RED, Category.RIDING);
        addMapMarker(new LatLng(37.511701300660036, 127.09928543185079), "스페인해적선", Color.RED, Category.RIDING);
        addMapMarker(new LatLng(37.51051008661316, 127.09790593088849), "회전목마", Color.YELLOW, Category.RIDING);

        addMapMarker(new LatLng(37.510000, 127.098000), "맛집 A", Color.BLUE, Category.RESTAURANT);
        addMapMarker(new LatLng(37.509000, 127.097500), "맛집 B", Color.BLUE, Category.RESTAURANT);


        addMapMarker(new LatLng(37.507000, 127.100000), "카페 A", Color.MAGENTA, Category.CAFE);
        addMapMarker(new LatLng(37.506500, 127.101000), "카페 B", Color.MAGENTA, Category.CAFE);

        addMapMarker(new LatLng(37.508500, 127.099500), "편의점 A", Color.GRAY, Category.CONVENIENCE);
        addMapMarker(new LatLng(37.509500, 127.098500), "편의점 B", Color.GRAY, Category.CONVENIENCE);

        setMoveLocation(37.51103128734522, 127.09836284873701);

        showCategory(Category.RIDING);
    }

    @Override
    public void onStart() {
        super.onStart();
        map_fragment.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        map_fragment.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map_fragment.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        map_fragment.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        map_fragment.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map_fragment.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        map_fragment.onSaveInstanceState(outState);
    }
}
