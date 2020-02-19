package com.bnk.comapny.bnksys.SQL;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bnk.comapny.bnksys.model.Apartment;
import com.bnk.comapny.bnksys.model.Lir;
import com.bnk.comapny.bnksys.model.Pir;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataAdapter {
    protected static final String TAG = "DataAdapter";
    protected static final String TABLE_NAME="apartment";
    protected static final String TABLE_NAMEP="pir";
    protected static final String TABLE_NAMEL="lir";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private MyDBHelper myDBHelper;

    public DataAdapter(Context context)
    {
        this.mContext=context;
        myDBHelper = new MyDBHelper(mContext);
    }

    public DataAdapter createDatabase() throws SQLException{
        try{
            myDBHelper.createDataBase();
        }catch(IOException mIOException)
        {
            Log.e(TAG,mIOException.toString()+" unableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DataAdapter open() throws SQLException{
        try{
            myDBHelper.openDataBase();
            myDBHelper.close();
            mDb=myDBHelper.getReadableDatabase();
        }catch(SQLException se)
        {
            Log.e(TAG,"open >> "+se.toString());
            throw se;
        }
        return this;
    }
    public void close()
    {
        myDBHelper.close();
    }

    public List getTableData(){
        try{
            String sql ="SELECT * FROM " + TABLE_NAME;

            // 모델 넣을 리스트 생성
            List apartlist = new ArrayList();

            // TODO : 모델 선언
            Apartment apartment = null;

            Cursor mCur = mDb.rawQuery(sql, null);

            if(mCur !=null)
            {
                while(mCur.moveToNext())
                {
                    apartment = new Apartment();
                    apartment.setAddress(mCur.getString(0));
                    apartment.setBunji(mCur.getString(1));
                    apartment.setName(mCur.getString(2));
                    apartment.setSizeM(Float.parseFloat(mCur.getString(3)));
                    apartment.setContractYM(mCur.getString(4));
                    apartment.setContractD(mCur.getString(5));
                    apartment.setPayout(Integer.parseInt(mCur.getString(6).replace(",","")));
                    apartment.setFloor(Integer.parseInt(mCur.getString(7)));
                    apartment.setBuildYear(mCur.getString(8));
                    apartment.setRoadress(mCur.getString(9));
                    apartment.setSizeP(Integer.parseInt(mCur.getString(10)));

                    apartlist.add(apartment);
                }
            }
            return apartlist;

        }catch (SQLException mSQLException)
        {
            Log.e(TAG,"getTestData >>"+mSQLException.toString());
            throw mSQLException;
        }
    }
    public List getTableDataL(){
        try{
            String sql ="SELECT * FROM " + TABLE_NAMEL;

            // 모델 넣을 리스트 생성
            List lirlist = new ArrayList();

            // TODO : 모델 선언
            Lir lir = null;

            Cursor mCur = mDb.rawQuery(sql, null);

            if(mCur !=null)
            {
                while(mCur.moveToNext())
                {
                    lir = new Lir();
                    lir.setlDay(mCur.getString(0));
                    lir.setlNation(Float.parseFloat(mCur.getString(1)));
                    lir.setlLocal(Float.parseFloat(mCur.getString(2)));

                    lirlist.add(lir);
                }
            }
            return lirlist;

        }catch (SQLException mSQLException)
        {
            Log.e(TAG,"getTestData >>"+mSQLException.toString());
            throw mSQLException;
        }
    }
    public List getTableDateW(String LOCATION){
        try{

            String sql ="select * " +
                        "from (select address, Bunji, name, sizeM, contractYM, ContractD, min(tPayout) as payout, floor, BuildYear, roadress, sizeP " +
                              "from (select address, Bunji, name, sizeM, contractYM, ContractD, Cast(Replace(payout,\",\",\"\") as int) tPayout, floor, BuildYear, roadress, sizeP, address || roadress || sizeP as filter " +
                                    "from apartment " +
                                    "order by filter, payout) " +
                              "group by filter) " +
                         "where address like '%"+LOCATION+"%' order by payout asc";
//            String sql = "select * from apartment";
            System.out.println("sql 문 : "+sql);
            // 모델 넣을 리스트 생성
            List apartlist = new ArrayList();

            // TODO : 모델 선언
            Apartment apartment = null;

            Cursor mCur = mDb.rawQuery(sql, null);

            if(mCur !=null)
            {
                while(mCur.moveToNext())
                {
                    apartment = new Apartment();
                    apartment.setAddress(mCur.getString(0));
                    apartment.setBunji(mCur.getString(1));
                    apartment.setName(mCur.getString(2));
                    apartment.setSizeM(Float.parseFloat(mCur.getString(3)));
                    apartment.setContractYM(mCur.getString(4));
                    apartment.setContractD(mCur.getString(5));
                    apartment.setPayout(Integer.parseInt(mCur.getString(6).replace(",","")));
                    apartment.setFloor(Integer.parseInt(mCur.getString(7)));
                    apartment.setBuildYear(mCur.getString(8));
                    apartment.setRoadress(mCur.getString(9));
                    apartment.setSizeP(Integer.parseInt(mCur.getString(10)));
                    System.out.println(apartment.toString()+"");
                    apartlist.add(apartment);
                }
            }
            return apartlist;

        }catch (SQLException mSQLException)
        {
            Log.e(TAG,"getTestData >>"+mSQLException.toString());
            throw mSQLException;
        }
    }
    public List getTableDataP(){
        try{
            String sql ="SELECT * FROM " + TABLE_NAMEP;

            // 모델 넣을 리스트 생성
            List pirlist = new ArrayList();

            // TODO : 모델 선언
            Pir pir = null;

            Cursor mCur = mDb.rawQuery(sql, null);

            if(mCur !=null)
            {
                while(mCur.moveToNext())
                {
                    pir = new Pir();
                    pir.setpDay(mCur.getString(0));
                    pir.setpNation(Float.parseFloat(mCur.getString(1)));
                    pir.setpLocal(Float.parseFloat(mCur.getString(2)));

                    pirlist.add(pir);
                }
            }
            return pirlist;

        }catch (SQLException mSQLException)
        {
            Log.e(TAG,"getTestData >>"+mSQLException.toString());
            throw mSQLException;
        }
    }

}
