package com.bnk.comapny.bnksys;

import android.app.ProgressDialog;
import android.content.Context;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Entity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bnk.comapny.bnksys.SQL.DataAdapter;
import com.bnk.comapny.bnksys.model.Apartment;
import com.bnk.comapny.bnksys.model.Pir;
import com.bnk.comapny.bnksys.model.User;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentHome extends Fragment {
//    PieChart pieChart;
    LineChart lineChart;
    View homeview;
    TextView salarytext;
    TextView pirtext;
    TextView lirtext;
    TextView fieldtext;
    public static double pirM;
    public static double lirM;
    private int salaryY;
    CardView fieldCard;
    String[] fields;
    ProgressDialog mProgress;

    private void initLoadDB(String field){
        DataAdapter mDbHelper = new DataAdapter(getActivity());
        mDbHelper.open();
        System.out.println("지역구 들어옴 : "+field);
        StartActivity.recommandList = mDbHelper.getTableDateW(field,0,pirM);

        mDbHelper.close();
        listRecommand = homeview.findViewById(R.id.list_page);
        layoutManager = new LinearLayoutManager(getActivity());
        listRecommand.setLayoutManager(layoutManager);
        listAdapter = new ListAdapter2(getActivity(),StartActivity.recommandList);
        listRecommand.setAdapter(listAdapter);
//        ListView listView = homeview.findViewById(R.id.list_page);
//        recAdapter recadapter = new recAdapter(getActivity(),R.id.list_page,StartActivity.recommandList);
//        listView.setAdapter(recadapter);
        mProgress.dismiss();
    }
    private RecyclerView listRecommand;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          homeview = inflater.inflate(R.layout.home,container,false);
            listRecommand = (RecyclerView)homeview.findViewById(R.id.list_page);
            layoutManager = new LinearLayoutManager(getActivity());
            listRecommand.setLayoutManager(layoutManager);
            listAdapter = new ListAdapter2(getActivity(),StartActivity.recommandList);
            listRecommand.setAdapter(listAdapter);
          userPanel(StartActivity.user);
//          chartMoney(StartActivity.user);
          chartPIRLIR();
          return homeview;
    }
    public void userPanel(User user){
        salarytext = homeview.findViewById(R.id.salarym);
        salaryY = (user.getSalaryM()*12)/10000;
        String temps = salaryY+"";
        if(temps.length()>4) {
            salarytext.setText(temps.substring(0, temps.length() - 4) + "억" + temps.substring((temps.length() - 4), temps.length()) + "만원");
        }else{
            salarytext.setText(temps+"만원");
        }
        pirtext = homeview.findViewById(R.id.pirm);
        float pirRecently = LoadingActivity.pirList.get(LoadingActivity.pirList.size()-1).getpLocal();
        pirM = salaryY*pirRecently;
        String tempp = Math.round(pirM)+"";
        if(tempp.length()>4) {
            pirtext.setText(tempp.substring(0, tempp.length() - 4) + "억" + tempp.substring((tempp.length() - 4), tempp.length()) + "만원");
        }else
        {
            pirtext.setText(tempp+"만원");
        }
        lirtext = homeview.findViewById(R.id.lirm);
        float lirRecently = LoadingActivity.lirList.get(LoadingActivity.lirList.size()-1).getlLocal();
        lirM = salaryY*lirRecently;
        String templ= Math.round(lirM)+"";
        if(templ.length()>4) {
            lirtext.setText(templ.substring(0, templ.length() - 4) + "억" + templ.substring((templ.length() - 4), templ.length()) + "만원");
        }else
        {
            lirtext.setText(templ+"만원");
        }
        fieldtext = homeview.findViewById(R.id.fieldm);
        fieldtext.setText(user.getField());

        fieldCard = homeview.findViewById(R.id.user_card_feild);
        fields = getResources().getStringArray(R.array.my_array);
        fieldCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getResources().getString(R.string.main_field_dialog_title));

                builder.setItems(fields, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        fieldtext.setText(fields[which]);
                        mProgress = ProgressDialog.show(getActivity(), "Wait", "Loading...");
                        initLoadDB(fields[which]);
                    }
                });
                builder.show();
            }
        });
    }
    public void chartPIRLIR(){
        lineChart = (LineChart)homeview.findViewById(R.id.pirlir);
        ArrayList<Entity> values = new ArrayList<>();

        ArrayList<Entity> xDay = new ArrayList<>();
        ArrayList<Entry> yPir = new ArrayList<>();
        ArrayList<Entry> yLir = new ArrayList<>();
        int numData = 5;
        for(int i =0;i<LoadingActivity.pirList.size();i++)
        {
            Entry c1e1 = new Entry(i,LoadingActivity.pirList.get(i).getpLocal());
            Entry c2e2 = new Entry(i,LoadingActivity.lirList.get(i).getlLocal());
            yPir.add(c1e1);
            yLir.add(c2e2);
        }

        LineDataSet setComp1 = new LineDataSet(yPir,"PIR : 집값/연봉");
        LineDataSet setComp2 = new LineDataSet(yLir,"LIR : 주택담보대출/연봉");
        setComp1.setColor(ContextCompat.getColor(getContext(),R.color.PointColor));
        setComp1.setCircleColor(ContextCompat.getColor(getContext(),R.color.PointColor));
        setComp1.setCircleHoleColor(ContextCompat.getColor(getContext(),R.color.PointColor));
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp2.setColor(ContextCompat.getColor(getContext(),R.color.PointColor2));
        setComp2.setCircleColor(ContextCompat.getColor(getContext(),R.color.PointColor2));
        setComp2.setCircleHoleColor(ContextCompat.getColor(getContext(),R.color.PointColor2));
        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);


        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);
        final String[] quarters = new String[LoadingActivity.pirList.size()];

        int size=0;
        for(Pir pir : LoadingActivity.pirList)
        {
            quarters[size++]=pir.getpDay();
        }

        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                return quarters[(int) value];
            }

        };

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();
    }
}
