package com.bnk.comapny.bnksys;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.bnk.comapny.bnksys.SQL.DataAdapter;
import com.bnk.comapny.bnksys.SQL.MyDBHelper;
import com.bnk.comapny.bnksys.model.Apartment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import androidx.annotation.Nullable;

import net.daum.mf.map.api.MapPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private MyDBHelper myDBHelper;
    private FragmentManager manager = getSupportFragmentManager();
    private FragmentHome home = new FragmentHome();
    private FragmentAnalysis analysis = new FragmentAnalysis();
    private FragmentProfile profile = new FragmentProfile();

    public List<Apartment> apartmentList;

    private String keyword; //검색키워드

    private  void initLoadDB(){
        DataAdapter mDbHelper = new DataAdapter(getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();

        apartmentList = mDbHelper.getTableData();
        mDbHelper.close();
    }
    private SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //DB관련

        //툴바관련
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(R.string.bar_name);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setElevation(0);

        //바텀네비게이션 프래그먼트관련
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, home).commitAllowingStateLoss();

        BottomNavigationView bnaviview = findViewById(R.id.navigationView);

        bnaviview.setOnNavigationItemSelectedListener(new ItemSelectedListener());

        initLoadDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("아파트 명을 입력해주세요");
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        //자동완성 관련
        String data[]={"엘시티", "협화맨션", "센텀파크 1차", "해운대 힐스테이트", "해운대 래미안", "동부 올림픽타운", "해운대 자이1단지", "해운대 마린시티", "해운대 아이파크", "광안더샵"};

        KeywordArrayAdapter<String> dataAdapter = new KeywordArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, data);
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, data);

        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setDropDownAnchor(R.id.my_toolbar);
        searchAutoComplete.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
        searchAutoComplete.setAdapter(dataAdapter);

        //검색 목록 클릭 리스너
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                keyword = ((TextView)view).getText().toString();

                mProgress = ProgressDialog.show(MainActivity.this, "Wait", "Search...");
                GeocodeThread thread = new GeocodeThread();
                thread.start();
            }
        });

        return true;
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = manager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.home:
                    transaction.replace(R.id.frameLayout, home).commitAllowingStateLoss();
                    break;
                case R.id.analysis:
                    transaction.replace(R.id.frameLayout, analysis).commitAllowingStateLoss();
                    break;
                case R.id.profile:
                    transaction.replace(R.id.frameLayout, profile).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

    class KeywordArrayAdapter<String> extends ArrayAdapter<String> implements Filterable {
        private String data[];
        private ArrayList<String> filteredItemList = null;

        Filter listFilter;

        public KeywordArrayAdapter(@androidx.annotation.NonNull Context context, int resource, @androidx.annotation.NonNull String[] objects) {
            super(context, resource, objects);
            data = Arrays.copyOf(objects, objects.length);
        }


        @androidx.annotation.NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @androidx.annotation.NonNull ViewGroup parent) {

            View view = super.getView(position,convertView,parent);
            view.setBackgroundColor(Color.WHITE);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = findViewById(R.id.my_toolbar).getLayoutParams().width;
            view.setLayoutParams(layoutParams);

            return view;
        }

        @Override
        public int getCount() {
            return filteredItemList.size();
        }

        @Nullable
        @Override
        public String getItem(int position) {
            return filteredItemList.get(position);
        }

        @Override
        public int getPosition(@Nullable String item) {
            for(int i = 0; i < filteredItemList.size(); i++){
                if(filteredItemList.get(i).equals(item)){
                    return i;
                }
            }
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @androidx.annotation.NonNull
        @Override
        public Filter getFilter() {
            if(listFilter == null){
                listFilter = new ListFilter();
            }
            return listFilter;
        }

        private class ListFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults() ;
                ArrayList<String> itemList = new ArrayList<>() ;
                if(data != null && constraint != null){
                    for(int i = 0; i < data.length; i++){
                        if(data[i].toString().contains(constraint)){
                            itemList.add(data[i]);
                        }
                    }
                    results.values = itemList;
                    results.count = itemList.size();
                }
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                // update listview by filtered data list.
                filteredItemList = (ArrayList<String>) results.values ;
                if(filteredItemList == null) return;


                // notify
                if (results.count > 0) {
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                    for(int i = 0; i < filteredItemList.size(); i++){
                        System.out.println(filteredItemList.get(i));
                    }
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                    notifyDataSetChanged() ;
                } else {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    for(int i = 0; i < filteredItemList.size(); i++){
                        System.out.println(filteredItemList.get(i));
                    }
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    notifyDataSetInvalidated() ;
                }
            }
        }
    }


    //주소->좌표 전환
    ProgressDialog mProgress;
    Handler mAfterDown = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            mProgress.dismiss();
            //위치초기화이루어져야함
            List<Double> result = (List<Double>) msg.obj;
            System.out.println(result.get(0));
            System.out.println(result.get(1));
            Toast.makeText(getBaseContext(), result.get(0) + ", "+result.get(1), Toast.LENGTH_SHORT).show();
//            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(result.get(0), result.get(1)), true);
        }
    };

    class GeocodeThread extends Thread {
        @Override
        public void run() {
            try {
                URL url = new URL("https://dapi.kakao.com/v2/local/search/keyword.json?query=" + keyword);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            List<Double> result = getGeocode();
            Message message = mAfterDown.obtainMessage();
            message.obj = result;
            mAfterDown.sendMessage(message);
        }

        List<Double> getGeocode(){
            List<Double> result = new ArrayList<>();

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
}
