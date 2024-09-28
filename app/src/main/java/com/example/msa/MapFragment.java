package com.example.msa;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    public enum Category {
        RIDING,
        RESTAURANT,
        CAFE,
        CONVENIENCE
    }

    // 마커와 서클 오버레이를 포함한 커스텀 클래스 정의
    public class MapMarker {
        Marker marker; // 지도 위의 마커
        CircleOverlay circle; // 마커 주위의 서클 오버레이
        Category category; // 마커의 카테고리

        // 생성자
        public MapMarker(Marker marker, CircleOverlay circle, Category category) {
            this.marker = marker;
            this.circle = circle;
            this.category = category;
        }
    }

    // 각 카테고리별 마커 리스트 초기화
    private List<MapMarker> ridingMarkers = new ArrayList<>();
    private List<MapMarker> restaurantMarkers = new ArrayList<>();
    private List<MapMarker> cafeMarkers = new ArrayList<>();
    private List<MapMarker> convenienceMarkers = new ArrayList<>();

    // NaverMap 객체 및 뷰 요소 초기화
    private NaverMap naverMap;
    private MapView map_fragment;
    private Button selectedButton;
    private TextView markerInformation;
    private EditText searchEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // NaverMap SDK 초기화
        NaverMapSdk.getInstance(requireContext()).setClient(new NaverMapSdk.NaverCloudPlatformClient("lubhho3zva"));
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // 지도 뷰 초기화 및 콜백 설정
        map_fragment = view.findViewById(R.id.map_fragment);
        map_fragment.onCreate(savedInstanceState);
        map_fragment.getMapAsync(this);

        // 필터 버튼 및 리스너 초기화
        Button ridingFilter = view.findViewById(R.id.riding_filter);
        Button restaurantFilter = view.findViewById(R.id.restaurant_filter);
        Button cafeFilter = view.findViewById(R.id.cafe_filter);
        Button convenienceFilter = view.findViewById(R.id.convenience_filter);

        // 검색 입력 필드 초기화 및 검색 리스너 설정
        searchEditText = view.findViewById(R.id.editFindSearch);
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                String searchText = searchEditText.getText().toString().trim();
                searchMarker(searchText); // 검색어로 마커 찾기
                return true; // 이벤트 소비
            }
            return false;
        });

        // 초기 선택된 필터 버튼 설정
        selectedButton = ridingFilter;
        ridingFilter.setSelected(true);

        // 필터 버튼 클릭 리스너 설정
        ridingFilter.setOnClickListener(this::onFilterClicked);
        restaurantFilter.setOnClickListener(this::onFilterClicked);
        cafeFilter.setOnClickListener(this::onFilterClicked);
        convenienceFilter.setOnClickListener(this::onFilterClicked);

        return view;
    }

    /**
     * 검색어를 입력받아 해당하는 마커를 찾아 지도 중심으로 이동시키는 메소드
     */
    private void searchMarker(String searchText) {
        if (searchText.isEmpty()) {
            Toast.makeText(getContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean found = false;

        // 모든 카테고리 마커를 순회하며 검색
        List<MapMarker> allMarkers = new ArrayList<>();
        allMarkers.addAll(ridingMarkers);
        allMarkers.addAll(restaurantMarkers);
        allMarkers.addAll(cafeMarkers);
        allMarkers.addAll(convenienceMarkers);

        for (MapMarker mapMarker : allMarkers) {
            if (mapMarker.marker.getCaptionText().equalsIgnoreCase(searchText)) {
                // 카메라 이동
                moveCameraToMarker(mapMarker.marker);
                found = true;
                break;
            }
        }

        if (!found) {
            Toast.makeText(getContext(), "해당하는 장소가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 마커 위치로 카메라를 이동시키는 메소드
     */
    private void moveCameraToMarker(Marker marker) {
        LatLng position = marker.getPosition();
        CameraPosition cameraPosition = new CameraPosition(position, 17); // 더 높은 줌 레벨
        naverMap.moveCamera(CameraUpdate.toCameraPosition(cameraPosition));

        // 마커 클릭 시 동작
        onMarkerClicked(marker);
    }

    /**
     * 필터 버튼 클릭 시 동작하는 메소드
     */
    private void onFilterClicked(View view) {
        Button clickedButton = (Button) view;

        // 이전에 선택된 버튼 해제
        if (selectedButton != null) {
            selectedButton.setSelected(false);
        }

        // 현재 클릭된 버튼 선택
        clickedButton.setSelected(true);
        selectedButton = clickedButton;

        // 클릭된 버튼에 따라 카테고리 선택
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
            selectedCategory = Category.RIDING; // 기본 카테고리
        }

        // 선택된 카테고리의 마커들만 표시
        showCategory(selectedCategory);
    }


    public void setMapOptions(NaverMap naverMap) {
        naverMap.setIndoorEnabled(true); // 실내 지도 활성화
        naverMap.setNightModeEnabled(true); // 야간 모드 활성화
        naverMap.setMapType(NaverMap.MapType.Navi); // 지도 타입을 네비게이션 모드로 설정

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setCompassEnabled(false); // 나침반 비활성화
        uiSettings.setScaleBarEnabled(false); // 축척 바 비활성화
        uiSettings.setLocationButtonEnabled(false); // 위치 버튼 비활성화
        uiSettings.setZoomControlEnabled(false); // 줌 컨트롤 비활성화
    }

    /**
     * 지정된 위치로 카메라를 이동시키는 메소드
     */
    public void setMoveLocation(double x, double y) {
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(x, y));
        naverMap.moveCamera(cameraUpdate);
    }

    /**
     * 지도에 마커와 원 오버레이를 추가하는 메소드
     */
    private void addMapMarker(LatLng position, String caption, Integer color, Category category) {
        // 마커 생성 및 설정
        Marker marker = new Marker();
        marker.setPosition(position);
        marker.setCaptionText(caption);
        marker.setWidth(80);
        marker.setHeight(80);
        marker.setIcon(OverlayImage.fromResource(R.drawable.ic_marker)); // 아이콘 설정
        marker.setMap(naverMap);

        // 색상이 지정된 경우 서클 오버레이 생성 및 설정
        CircleOverlay circle = null;
        if (color != null) {
            circle = new CircleOverlay();
            circle.setCenter(position);
            circle.setRadius(45); // 반경 설정 (미터 단위)
            circle.setColor(Color.argb(80, Color.red(color), Color.green(color), Color.blue(color))); // 80% 투명도 적용
            circle.setMap(naverMap);
        }

        // 마커를 MapMarker 객체로 감싸서 해당 카테고리의 리스트에 추가
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

        // 마커 클릭 시 동작할 리스너 설정
        marker.setOnClickListener(overlay -> {
            onMarkerClicked(marker); // 마커 클릭 시 호출할 메소드
            return true;
        });
    }

    /**
     * 마커를 클릭했을 때 해당 마커의 정보를 보여주는 bottom sheet 호출
     */
    public void onMarkerClicked(Marker clickedMarker) {
        String caption = null;
        Category clickedCategory = null;
        markerInformation = getView().findViewById(R.id.bottom_sheet_text);

        // 클릭된 마커가 속한 카테고리와 캡션을 찾기
        for (MapMarker mapMarker : ridingMarkers) {
            if (mapMarker.marker.equals(clickedMarker)) {
                caption = mapMarker.marker.getCaptionText();
                clickedCategory = mapMarker.category;
                break;
            }
        }

        if (caption == null) { // 다른 카테고리에서도 마커 찾기
            for (MapMarker mapMarker : restaurantMarkers) {
                if (mapMarker.marker.equals(clickedMarker)) {
                    caption = mapMarker.marker.getCaptionText();
                    clickedCategory = mapMarker.category;
                    break;
                }
            }
        }

        if (caption == null) {
            for (MapMarker mapMarker : cafeMarkers) {
                if (mapMarker.marker.equals(clickedMarker)) {
                    caption = mapMarker.marker.getCaptionText();
                    clickedCategory = mapMarker.category;
                    break;
                }
            }
        }

        if (caption == null) {
            for (MapMarker mapMarker : convenienceMarkers) {
                if (mapMarker.marker.equals(clickedMarker)) {
                    caption = mapMarker.marker.getCaptionText();
                    clickedCategory = mapMarker.category;
                    break;
                }
            }
        }

        // BottomSheet 인스턴스 생성
        BottomSheet bottomSheet = new BottomSheet();
        Bundle args = new Bundle();
        args.putString("markerName", caption); // BottomSheet에 전달할 정보 설정
        bottomSheet.setArguments(args);

        // BottomSheet를 화면에 표시
        bottomSheet.show(getParentFragmentManager(), bottomSheet.getTag());
    }

    /**
     * 선택된 카테고리의 마커만 표시하는 메소드
     */
    private void showCategory(Category category) {
        hideAllMarkers(); // 모든 마커를 숨김

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

        // 선택된 카테고리의 마커와 서클 오버레이를 지도에 표시
        for (MapMarker mapMarker : markersToShow) {
            mapMarker.marker.setMap(naverMap);
            if (mapMarker.circle != null) {
                mapMarker.circle.setMap(naverMap);
            }
        }
    }

    /**
     * 모든 마커를 지도에서 숨기는 메소드
     */
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
        setMapOptions(naverMap);

        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(37.51103128734522, 127.09836284873701),
                16  // 지도 줌 레벨
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

        addMapMarker(new LatLng(37.51095837793791, 127.0975742336192), "롯데리아", Color.RED, Category.RESTAURANT);
        addMapMarker(new LatLng(37.510776900704585, 127.09908127784729), "구복만두", Color.RED, Category.RESTAURANT);
        addMapMarker(new LatLng(37.510452000958864, 127.0965120788493), "명동할머니국수", Color.RED, Category.RESTAURANT);
        addMapMarker(new LatLng(37.51050881855063, 127.09903836250305), "라라코스트", Color.RED, Category.RESTAURANT);
        addMapMarker(new LatLng(37.51202643912463, 127.09936594924115), "여섯시오븐", Color.RED, Category.RESTAURANT);
        addMapMarker(new LatLng(37.51134986030663, 127.09950542410992), "롯데호텔월드 도림", Color.RED, Category.RESTAURANT);
        addMapMarker(new LatLng(37.5112875307151, 127.09793865680695), "아우어베이커리", Color.RED, Category.RESTAURANT);
        addMapMarker(new LatLng(37.50885112136215, 127.09971779419847), "걸작떡볶이치킨 롯데월드", Color.RED, Category.RESTAURANT);
        addMapMarker(new LatLng(37.50861282001368, 127.09986263348527), "BHC치킨 롯데월드빅토리아점", Color.RED, Category.RESTAURANT);
        addMapMarker(new LatLng(37.50891069658038, 127.10090601279207), "스쿨스토어", Color.RED, Category.RESTAURANT);
        addMapMarker(new LatLng(37.508925590377515,127.1006056053824 ), "샬레카페", Color.RED, Category.RESTAURANT);


        addMapMarker(new LatLng(37.51074774255304, 127.09643697699688), "투썸플레이스 잠실롯데점", Color.RED, Category.CAFE);
        addMapMarker(new LatLng(37.51035625702626, 127.0967105623164), "메가MGC커피 잠실롯데점", Color.RED, Category.CAFE);
        addMapMarker(new LatLng(37.51145411341729, 127.09652817210339), "부라타랩 롯데마트", Color.RED, Category.CAFE);
        addMapMarker(new LatLng(37.51089029383816, 127.09797656497143), "투썸플레이스 롯데월드점", Color.RED, Category.CAFE);
        addMapMarker(new LatLng(37.51158815291703, 127.09855592211865), "공차 롯데월드점", Color.RED, Category.CAFE);
        addMapMarker(new LatLng(37.51209664978282, 127.09877586325787), "풀바셋", Color.RED, Category.CAFE);
        addMapMarker(new LatLng(37.511098801197626, 127.09975755175732), "엔제리너스", Color.RED, Category.CAFE);
        addMapMarker(new LatLng(37.50898091016943, 127.09947907759614), "투썸플레이스 롯데월드매직아일랜드점", Color.RED, Category.CAFE);
        addMapMarker(new LatLng(37.50853941453432, 127.10001551939912), "공차 롯데월드매직아일랜드점", Color.RED, Category.CAFE);
        addMapMarker(new LatLng(37.508990484744636, 127.10042321516939), "캔디캐슬", Color.RED, Category.CAFE);


        addMapMarker(new LatLng(37.51153676167506, 127.09828475166064), "세븐일레븐", Color.RED, Category.CONVENIENCE);
        addMapMarker(new LatLng(37.511130386932436, 127.09632673907977), "세븐일레븐", Color.RED, Category.CONVENIENCE);

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
