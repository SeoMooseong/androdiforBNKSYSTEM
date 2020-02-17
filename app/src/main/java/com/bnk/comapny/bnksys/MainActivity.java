package com.bnk.comapny.bnksys;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bnk.comapny.bnksys.SQL.DataAdapter;
import com.bnk.comapny.bnksys.SQL.MyDBHelper;
import com.bnk.comapny.bnksys.model.Apartment;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    private MyDBHelper myDBHelper;
    private FragmentManager manager = getSupportFragmentManager();
    private FragmentHome home = new FragmentHome();
    private FragmentAnalysis analysis = new FragmentAnalysis();
    private FragmentProfile profile = new FragmentProfile();

    public List<Apartment> apartmentList;

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
        String data[]={"Emmanuel", "Olayemi", "Henrry", "Mark", "Steve", "Ayomide", "David", "Anthony", "Adekola", "Adenuga"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, data){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                view.setBackgroundColor(Color.WHITE);
//                view.setLayoutParams();
                return view;
            }
        };
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setAdapter(dataAdapter);

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

    class keywordArrayAdapter extends ArrayAdapter<Object> {
        public keywordArrayAdapter(@androidx.annotation.NonNull Context context, int resource) {
            super(context, resource);
        }

        @androidx.annotation.NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @androidx.annotation.NonNull ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            view.setBackgroundColor(Color.WHITE);
            return view;
        }
    }
}
