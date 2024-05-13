package com.example.msa;

import static net.daum.mf.map.api.MapPoint.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map, container, false);

//        //MapView 추가
//        MapView mapView = new MapView(requireContext());
//        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(36.62845944143315, 127.45744134094953), true);
//        ViewGroup mapViewContainer = getView().findViewById(R.id.map_view);
//        mapViewContainer.addView(mapView);
//
//        //여기서부터 마커
//        addMarkers(mapView);

//        mapView.setZoomLevel(1, true);
//        mapView.zoomIn(true);
//        mapView.zoomOut(true);
//
//        //경영학관 36.63010160647784, 경도는 127.45689202384503
//        MapPoint MARKER_POINT1 = MapPoint.mapPointWithGeoCoord(36.63010160647784, 127.45689202384503);
//        //자연대2호관 36.62713695432668, 경도는 127.45687452412538
//        MapPoint MARKER_POINT2 = MapPoint.mapPointWithGeoCoord(36.62713695432668, 127.45687452412538);
//        //인문대학본관 36.63014875672698, 경도는 127.45869235613091
//        MapPoint MARKER_POINT3 = MapPoint.mapPointWithGeoCoord(36.63014875672698, 127.45869235613091);
//
//        // 마커 아이콘 추가하는 함수
//        MapPOIItem marker1 = new MapPOIItem();


//        // ListView 추가
//        ListView listView = getView().findViewById(R.id.list_view);
//        String[] data = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5","Item 6", "Item 7", "Item 8", "Item 9", "Item 10"}; // 임시 데이터
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, data);
//        listView.setAdapter(adapter);

        return view;
    }

    private void addMarkers(MapView mapView) {
        // 경영학관
        MapPOIItem marker1 = new MapPOIItem();
        marker1.setItemName("경영학관");
        marker1.setTag(0);
        marker1.setMapPoint(mapPointWithGeoCoord(36.63010160647784, 127.45689202384503));
        mapView.addPOIItem(marker1);

        // 자연대2호관
        MapPOIItem marker2 = new MapPOIItem();
        marker2.setItemName("자연대2호관");
        marker2.setTag(1);
        marker2.setMapPoint(mapPointWithGeoCoord(36.62713695432668, 127.45687452412538));
        mapView.addPOIItem(marker2);

        // 인문대학본관
        MapPOIItem marker3 = new MapPOIItem();
        marker3.setItemName("인문대학본관");
        marker3.setTag(2);
        marker3.setMapPoint(mapPointWithGeoCoord(36.63014875672698, 127.45869235613091));
        mapView.addPOIItem(marker3);
    }

}