package com.bnk.comapny.bnksys;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class FragmentProfile extends Fragment implements MapView.CurrentLocationEventListener, ActivityCompat.OnRequestPermissionsResultCallback {
    View profile;

    private static final String LOG_TAG = "LocationDemoActivity";
    private MapView mapView;
    private Button currentLocationBtn;
    private Button searchBtn;
    private EditText searchKeyword;

    private static final int PERMISSIONS_REQUEST_CODE = 100;

    private static final String [] LOCATION_PERMISSIONS = new String [] {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        profile = inflater.inflate(R.layout.profile,container,false);
        showMap();
        return profile;
    }

    public void showMap(){
        mapView = profile.findViewById(R.id.map_view);
        currentLocationBtn = profile.findViewById(R.id.currentLocationBtn);
        searchBtn = profile.findViewById(R.id.search);
        searchKeyword = profile.findViewById(R.id.keyword);

        currentLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                Log.d("현재위치", "called()!");
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress = ProgressDialog.show(getActivity(), "Wait", "Search...");
                GeocodeThread thread = new GeocodeThread();
                thread.start();



            }
        });

        int permission1 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        int permission2 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);

        if(permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED){
//            mapView.setCurrentLocationEventListener(this);
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        }else{
            ActivityCompat.requestPermissions(getActivity(), LOCATION_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE);
        }

//        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(33.450701, 126.570667), true);
//
//        MapPOIItem mapPOIItem = null;
//
//        mapPOIItem = new MapPOIItem();
//
//        mapPOIItem.setMapPoint(MapPoint.mapPointWithGeoCoord(33.450701, 126.570667));
//        mapPOIItem.setItemName("spot01");
//        mapPOIItem.setTag(0);
//        mapPOIItem.setMarkerType(MapPOIItem.MarkerType.YellowPin);
//        mapView.addPOIItem(mapPOIItem);
//
//        mapPOIItem.setMapPoint(MapPoint.mapPointWithGeoCoord(33.450879, 126.569940));
//        mapPOIItem.setItemName("spot02");
//        mapPOIItem.setTag(1);
//        mapPOIItem.setMarkerType(MapPOIItem.MarkerType.BluePin);
//        mapView.addPOIItem(mapPOIItem);
//
//        mapPOIItem.setMapPoint(MapPoint.mapPointWithGeoCoord(33.451393, 126.570738));
//        mapPOIItem.setItemName("spot03");
//        mapPOIItem.setTag(2);
//        mapPOIItem.setMarkerType(MapPOIItem.MarkerType.RedPin);
//        mapView.addPOIItem(mapPOIItem);
    }

    ProgressDialog mProgress;
    Handler mAfterDown = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            mProgress.dismiss();
            //위치초기화이루어져야함
            List<Double> result = (List<Double>) msg.obj;
            System.out.println(result.get(0));
            System.out.println(result.get(1));
            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(result.get(0), result.get(1)), true);
        }
    };

    class GeocodeThread extends Thread {
        @Override
        public void run() {
//            URL url = new URL("https://dapi.kakao.com/v2/local/search/keyword.json?query=" + keyword);
            List<Double> result = getGeocode();
            Message message = mAfterDown.obtainMessage();
            message.obj = result;
            mAfterDown.sendMessage(message);
        }

        List<Double> getGeocode(){
            List<Double> result = new ArrayList<>();
            String keyword = searchKeyword.getText().toString();
            try {
                StringBuilder data = new StringBuilder();
                URL url = new URL("https://dapi.kakao.com/v2/local/search/keyword.json?query="
                        + URLEncoder.encode(keyword, "UTF-8"));

                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestProperty("Authorization","KakaoAK 674b7eb941c4940588f16389ba69e6fe");

                if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while(true){
                        String line = br.readLine();
                        if(line == null)
                            break;
                        data.append(line + '\n');
                    }
                    br.close();
                }else {
                    //실패
                }
                conn.disconnect();

                result = getCode(data.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return result;
        }

        private List<Double> getCode(String jsonData){
            List<Double> result = new ArrayList<>();
            JSONObject reader = null;
            try {
                reader = new JSONObject(jsonData);
                JSONArray objects = reader.getJSONArray("documents");
                for(int i = 0; i < objects.length(); i++){
                    JSONObject object = objects.getJSONObject(i);
                    if(object.getString("category_name").equals("부동산 > 주거시설 > 아파트")){
                        result.add(Double.parseDouble(object.getString("y")));
                        result.add(Double.parseDouble(object.getString("x")));
                        break;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }


    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }
}