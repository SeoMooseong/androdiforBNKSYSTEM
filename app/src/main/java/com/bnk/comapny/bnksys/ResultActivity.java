package com.bnk.comapny.bnksys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class ResultActivity extends AppCompatActivity {
    MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();

        Double mapX = Double.parseDouble(intent.getStringExtra("mapX"));
        Double mapY = Double.parseDouble(intent.getStringExtra("mapY"));

        String address = intent.getStringExtra("address");
        int idx = address.indexOf("@");
        String aptName = address.substring(idx + 1);

        Toast.makeText(this, mapX + ", " + mapY, Toast.LENGTH_SHORT).show();

        mapView = findViewById(R.id.result_map_view);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(mapX, mapY), true);

        MapPOIItem marker = new MapPOIItem();
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(mapX, mapY));
        marker.setItemName(aptName);
        marker.setTag(0);
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "Destroy called!");
    }
}
