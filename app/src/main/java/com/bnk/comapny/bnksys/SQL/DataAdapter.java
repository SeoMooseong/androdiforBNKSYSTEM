package com.bnk.comapny.bnksys.SQL;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bnk.comapny.bnksys.model.Apartment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataAdapter {
    protected static final String TAG = "DataAdapter";
    protected static final String TABLE_NAME="apartment";

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
}
