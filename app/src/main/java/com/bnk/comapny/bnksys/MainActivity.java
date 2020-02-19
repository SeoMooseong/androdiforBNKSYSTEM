package com.bnk.comapny.bnksys;

import android.Manifest;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.bnk.comapny.bnksys.SQL.DataAdapter;
import com.bnk.comapny.bnksys.model.Apartment;
import com.bnk.comapny.bnksys.model.ApartmentList;
import com.bnk.comapny.bnksys.model.Lir;
import com.bnk.comapny.bnksys.model.Pir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private FragmentManager manager = getSupportFragmentManager();
    private FragmentHome home = new FragmentHome();
    private FragmentAnalysis analysis = new FragmentAnalysis();
    private FragmentProfile profile = new FragmentProfile();

    private String keyword; //검색키워드

    private List<String> addressList;
    private List<String> addressFakeList;

    private SearchView search;

    private List<String> convertList(List<Apartment> ori){
        addressFakeList = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        Apartment apt = null;

        for(int i = 0; i < ori.size(); i++){
            apt = ori.get(i);
            addressFakeList.add(apt.getAddress() + " $" + apt.getRoadress() + " @" + apt.getName());
            list.add(apt.getAddress() + " " +apt.getRoadress() + " " + apt.getName());
        }

        TreeSet<String> tmpList = new TreeSet<>(list);
        addressList = new ArrayList<>(tmpList);
        tmpList = new TreeSet<>(addressFakeList);
        addressFakeList = new ArrayList<>(tmpList);

        return addressList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int i=0;
        for(Apartment ap : StartActivity.recommandList)
        {
            System.out.println(ap.getName()+" "+(i++)+"번쨰");
        }

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
        KeywordArrayAdapter<String> dataAdapter = new KeywordArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, convertList(LoadingActivity.apartmentList));

        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setDropDownAnchor(R.id.my_toolbar);
        searchAutoComplete.setDropDownWidth(getResources().getDisplayMetrics().widthPixels);
        searchAutoComplete.setAdapter(dataAdapter);

        //검색 목록 클릭 리스너
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                keyword = ((TextView)view).getText().toString();

                for(int i = 0; i < addressList.size(); i++){
                    if(keyword.equals(addressList.get(i))){
                        keyword = addressFakeList.get(i);
                        break;
                    }
                }
//                Toast.makeText(MainActivity.this, keyword, Toast.LENGTH_SHORT).show();
                mProgress = ProgressDialog.show(MainActivity.this, "Wait", "Search...");

                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

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
        private List<String> list = null;
        private List<String> filteredItemList = null;

        Filter listFilter;

        public KeywordArrayAdapter(@androidx.annotation.NonNull Context context, int resource, @androidx.annotation.NonNull List<String> objects) {
            super(context, resource, objects);
            list = objects;
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
            if(filteredItemList == null){
                return 0;
            }
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
                List<String> itemList = new ArrayList<>() ;
                if(list != null && constraint != null){
                    for(int i = 0; i < list.size(); i++){
                        if(list.get(i).toString().contains(constraint)){
                            itemList.add(list.get(i));
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

                filteredItemList = (ArrayList<String>) results.values ;
                if(filteredItemList == null) return;

                if (results.count > 0) {
                    notifyDataSetChanged() ;
                } else {
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

            List<String> result = (List<String>) msg.obj;
            Toast.makeText(MainActivity.this, result.get(0) + ", " + result.get(1), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);

            intent.putExtra("mapX", result.get(0));
            intent.putExtra("mapY", result.get(1));
            intent.putExtra("address", result.get(2));
            startActivity(intent);
        }
    };

    class GeocodeThread extends Thread {
        @Override
        public void run() {
            List<Double> geoCode = getGeocode();

            List<String> result = new ArrayList<>();

            result.add(geoCode.get(0)+"");
            result.add(geoCode.get(1)+"");
            result.add(keyword);

            Message message = mAfterDown.obtainMessage();
            message.obj = result;
            mAfterDown.sendMessage(message);
        }

        List<Double> getGeocode(){
            List<Double> result = new ArrayList<>();

            try {
                System.out.println(keyword);
                String tmpKeyword = keyword;
                int idx = keyword.indexOf(" @");
                keyword = keyword.substring(0, idx);
                keyword = keyword.replace("$", "");
                System.out.println(keyword);
                StringBuilder data = new StringBuilder();
                URL url = new URL("https://dapi.kakao.com/v2/local/search/address.json?query="
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
                keyword = tmpKeyword;
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
                    result.add(Double.parseDouble(object.getString("y")));
                    result.add(Double.parseDouble(object.getString("x")));
                    break;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }
    }
}
