package com.bnk.comapny.bnksys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bnk.comapny.bnksys.SQL.DataAdapter;
import com.bnk.comapny.bnksys.model.Apartment;
import com.bnk.comapny.bnksys.model.ApartmentList;
import com.bnk.comapny.bnksys.model.Lir;
import com.bnk.comapny.bnksys.model.Pir;
import com.bnk.comapny.bnksys.parser.Data;
import com.bnk.comapny.bnksys.parser.LoanParser;

import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends AppCompatActivity {
    public static List<Apartment> apartmentList;
    public static List<Pir> pirList;
    public static List<Lir> lirList;
    public static List<ApartmentList> aptAdressList;

    private static final int PERMISSIONS_REQUEST_CODE = 100;

    private static final String [] LOCATION_PERMISSIONS = new String [] {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private  void initLoadDB(){
        DataAdapter mDbHelper = new DataAdapter(getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();
        LoanParser loanParser = new LoanParser();
        Thread loanThread = new Thread(loanParser);
        loanThread.start();

        apartmentList = mDbHelper.getTableData();
        pirList = mDbHelper.getTableDataP();
        lirList=mDbHelper.getTableDataL();
        mDbHelper.close();


        aptAdressList = new ArrayList<>();
        String tmp;
        List<Apartment> tmpArr;
        boolean check = false;
        for(int i = 0; i < apartmentList.size(); i++){
            tmp = apartmentList.get(i).getAddress() + " " + apartmentList.get(i).getRoadress() + " " + apartmentList.get(i).getName();
            for(int j = 0; j < aptAdressList.size(); j++){
                if(tmp.equals(aptAdressList.get(j).getAddress())){

                    tmpArr = aptAdressList.get(j).getList();
                    tmpArr.add(apartmentList.get(i));
                    aptAdressList.get(j).setList(tmpArr);
                    check = true;
                    if(apartmentList.get(i).getName().contains("엘시티")){
                        System.out.println(aptAdressList.get(i).getList().size());
                    }
                    break;
                }
            }
            if(!check){
                ApartmentList apt = new ApartmentList(tmp, new ArrayList<Apartment>());
                apt.getList().add(apartmentList.get(i));
                aptAdressList.add(apt);
            }else{
                check = false;
            }
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);


        Intent intent = new Intent(getApplicationContext(),StartActivity.class);
        startActivity(intent);
        initLoadDB();

        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);

        if(permission1 != PackageManager.PERMISSION_GRANTED || permission2 != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(LoadingActivity.this, LOCATION_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE);
        }
        finish();
    }
}
