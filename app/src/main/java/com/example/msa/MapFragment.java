package com.example.msa;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Circle;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap gMap;
    public enum Category {
        ALL,
        RIDING,
        RESTAURANT,
        CAFE,
        CONVENIENCE
    }
    // 마커와 서클 오버레이를 포함한 커스텀 클래스 정의
    public class MapMarker {
        Marker marker; // 지도 위의 마커
        Circle circle; // 마커 주위의 서클 오버레이
        Category category; // 마커의 카테고리

        // 생성자
        public MapMarker(Marker marker, Circle circle, Category category) {
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

    private Button selectedButton;
    private TextView markerInformation;
    private EditText searchEditText;
    private Category currentCategory = Category.ALL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_container);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // 필터 버튼 및 리스너 초기화
        Button allFilter = view.findViewById(R.id.all_filter);
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
        selectedButton = allFilter;
        allFilter.setSelected(true);

        // 필터 버튼 클릭 리스너 설정
        allFilter.setOnClickListener(this::onFilterClicked);
        ridingFilter.setOnClickListener(this::onFilterClicked);
        restaurantFilter.setOnClickListener(this::onFilterClicked);
        cafeFilter.setOnClickListener(this::onFilterClicked);
        convenienceFilter.setOnClickListener(this::onFilterClicked);

        return view;
    }

    /**
     * 검색어를 입력받아 해당하는 마커를 찾아 지도 중심으로 이동시키는 메소드
     */
    /**
     * 검색어를 입력받아 해당하는 마커를 찾아 지도 중심으로 이동시키는 메서드
     */
    private void searchMarker(String searchText) {
        if (searchText.isEmpty()) {
            Toast.makeText(getContext(), "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean found = false;

        // 모든 카테고리 마커를 순회하며 검색
        List<MapMarker> searchMarkers = new ArrayList<>();
        switch (currentCategory) {
            case ALL:
                searchMarkers.addAll(ridingMarkers);
                searchMarkers.addAll(restaurantMarkers);
                searchMarkers.addAll(cafeMarkers);
                searchMarkers.addAll(convenienceMarkers);
                break;
            case RIDING:
                searchMarkers.addAll(ridingMarkers);
                break;
            case CAFE:
                searchMarkers.addAll(cafeMarkers);
                break;
            case RESTAURANT:
                searchMarkers.addAll(restaurantMarkers);
                break;
            case CONVENIENCE:
                searchMarkers.addAll(convenienceMarkers);
                break;
        }

        // 검색어와 일치하는 마커 찾기
        for (MapMarker mapMarker : searchMarkers) {
            if (mapMarker.marker.getTitle() != null &&
                    mapMarker.marker.getTitle().equalsIgnoreCase(searchText)) {
                // 검색된 마커로 카메라 이동
                moveCameraToMarker(mapMarker.marker);
                found = true;
                break;
            }
        }

        // 검색 결과가 없을 경우 메시지 표시
        if (!found) {
            Toast.makeText(getContext(), "해당하는 장소가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }


    /*
     * 마커 위치로 카메라를 이동시키는 메서드
     */
    private void moveCameraToMarker(Marker marker) {
        LatLng position = marker.getPosition();

        // 카메라를 해당 위치로 이동하고 줌 레벨을 17로 설정
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 17);
        gMap.moveCamera(cameraUpdate);

        // 마커 클릭 시 동작 수행
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

        if (view.getId() == R.id.all_filter) {
            selectedCategory = Category.ALL;
        } else if (view.getId() == R.id.riding_filter) {
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

        currentCategory = selectedCategory;

        // 선택된 카테고리의 마커들만 표시
        showCategory(selectedCategory);
    }

    /**
     * 지정된 위치로 카메라를 이동시키는 메서드
     */
    public void setMoveLocation(double latitude, double longitude) {
        LatLng targetLocation = new LatLng(latitude, longitude);

        // 카메라 업데이트 생성 (줌 레벨 포함)
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(targetLocation, 16);

        // 카메라 이동
        gMap.moveCamera(cameraUpdate);
    }

    /**
     * 주어진 위치에 마커와 서클을 추가하는 메서드
     */
    private void addMapMarker(LatLng position, String caption, Integer color, Category category) {
        // 마커 아이콘 크기를 70x70으로 조정
        Bitmap customMarker = createCustomMarker(caption);

        // 마커 옵션 생성 및 설정
        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .title(caption) // 마커의 제목에 캡션 설정
                .icon(BitmapDescriptorFactory.fromBitmap(customMarker)); // 아이콘 설정

        // 마커를 지도에 추가
        Marker marker = gMap.addMarker(markerOptions);


        // 색상이 지정된 경우 서클 오버레이 생성 및 설정
        Circle circle = null;
        if (color != null) {
            circle = gMap.addCircle(new CircleOptions()
                    .center(position) // 서클 중심 설정
                    .radius(30) // 반경 (미터 단위)
                    .fillColor(Color.argb(80, Color.red(color), Color.green(color), Color.blue(color))) // 투명도 적용
                    .strokeColor(color) // 경계 색상 설정
                    .strokeWidth(2)); // 경계 두께 설정
        }

        // MapMarker 객체로 마커와 서클을 감싸서 카테고리별 리스트에 추가
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

        // 마커에 MapMarker 객체를 태그로 설정
        if (marker != null) {
            marker.setTag(mapMarker);
        }

        // 마커 클릭 리스너 설정 (GoogleMap의 리스너 사용)
        gMap.setOnMarkerClickListener(clickedMarker -> {
            MapMarker taggedMarker = (MapMarker) clickedMarker.getTag(); // 태그에서 MapMarker 가져오기
            if (taggedMarker != null) {
                onMarkerClicked(clickedMarker); // 클릭된 마커 처리
            }
            return true; // 이벤트 소비
        });
    }


    /**
     * 마커 아이콘의 크기를 조정하는 메서드
     */
    private Bitmap resizeMarkerIcon(int resId, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), resId);
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
    }



    /**
     * 마커를 클릭했을 때 해당 마커의 정보를 보여주는 BottomSheet 호출
     */
    public boolean onMarkerClicked(Marker clickedMarker) {
        MapMarker mapMarker = (MapMarker) clickedMarker.getTag(); // 태그에서 MapMarker 객체 가져오기

        if (mapMarker != null) {
            String caption = clickedMarker.getTitle(); // 마커의 제목 가져오기
            Category clickedCategory = mapMarker.category;

            // BottomSheet 인스턴스 생성
            BottomSheetDialog bottomSheet = new BottomSheetDialog(requireContext());
            bottomSheet.setContentView(R.layout.fragment_bottom_sheet);

            // BottomSheet에 표시할 정보 설정
            TextView markerInfo = bottomSheet.findViewById(R.id.facility_name);
            markerInfo.setText(caption);

            // BottomSheet를 화면에 표시
            bottomSheet.show();
            return true; // 클릭 이벤트 소비
        }
        return false; // 클릭 이벤트가 처리되지 않음
    }

    /**
     * 선택된 카테고리의 마커만 표시하는 메서드
     */
    private void showCategory(Category category) {
        hideAllMarkers(); // 모든 마커를 숨김

        List<MapMarker> markersToShow;

        // 모든 카테고리의 마커를 표시
        if (category == Category.ALL) {
            markersToShow = new ArrayList<>();
            markersToShow.addAll(ridingMarkers);
            markersToShow.addAll(restaurantMarkers);
            markersToShow.addAll(cafeMarkers);
            markersToShow.addAll(convenienceMarkers);
        } else {
            // 선택된 카테고리의 마커만 표시
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
        }

        // 선택된 카테고리의 마커와 서클을 표시
        for (MapMarker mapMarker : markersToShow) {
            mapMarker.marker.setVisible(true);
            if (mapMarker.circle != null) {
                mapMarker.circle.setVisible(true);
            }
        }
    }

    /**
     * 모든 마커와 서클을 숨기는 메서드
     */
    private void hideAllMarkers() {
        // 모든 카테고리의 마커와 서클을 숨김
        List<MapMarker> allMarkers = new ArrayList<>();
        allMarkers.addAll(ridingMarkers);
        allMarkers.addAll(restaurantMarkers);
        allMarkers.addAll(cafeMarkers);
        allMarkers.addAll(convenienceMarkers);

        for (MapMarker mapMarker : allMarkers) {
            mapMarker.marker.setVisible(false);
            if (mapMarker.circle != null) {
                mapMarker.circle.setVisible(false);
            }
        }
    }

    @Override
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


        // 서울에 마커 추가
        LatLng LotteWorld = new LatLng(37.51103128734522, 127.09836284873701);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(LotteWorld)
                .title("롯데월드");


        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LotteWorld, 16));

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
