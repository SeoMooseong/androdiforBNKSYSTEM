package com.bnk.comapny.bnksys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bnk.comapny.bnksys.SQL.DataAdapter;
import com.bnk.comapny.bnksys.model.Apartment;
import com.bnk.comapny.bnksys.model.Lir;
import com.bnk.comapny.bnksys.model.Pir;

import java.util.List;

public class LoadingActivity extends AppCompatActivity {
    public static List<Apartment> apartmentList;
    public static List<Pir> pirList;
    public static List<Lir> lirList;

    private  void initLoadDB(){
        DataAdapter mDbHelper = new DataAdapter(getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();

        apartmentList = mDbHelper.getTableData();
        pirList = mDbHelper.getTableDataP();
        lirList=mDbHelper.getTableDataL();
        mDbHelper.close();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        initLoadDB();
        Intent intent = new Intent(getApplicationContext(),StartActivity.class);
        startActivity(intent);
    }
}
