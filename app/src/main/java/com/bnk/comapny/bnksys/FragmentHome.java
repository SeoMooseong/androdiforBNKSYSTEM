package com.bnk.comapny.bnksys;

import android.content.Entity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bnk.comapny.bnksys.model.Pir;
import com.bnk.comapny.bnksys.model.User;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {
    PieChart pieChart;
    LineChart lineChart;
    View homeview;
    TextView salarytext;
    TextView pirtext;
    TextView lirtext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          homeview = inflater.inflate(R.layout.home,container,false);

          userPanel(StartActivity.user);
          chartMoney(StartActivity.user);
          chartPIRLIR();
          return homeview;
    }
    public void userPanel(User user){
        salarytext = homeview.findViewById(R.id.salarym);
        int salaryY = (user.getSalaryM()*12)/10000;
        salarytext.setText(salaryY+"만원");

        pirtext = homeview.findViewById(R.id.pirm);

        float pirRecently = LoadingActivity.pirList.get(LoadingActivity.pirList.size()-1).getpLocal();
        double pirM = salaryY*pirRecently;
        pirtext.setText(Math.round(pirM)+"만원");
        lirtext = homeview.findViewById(R.id.lirm);

        float lirRecently = LoadingActivity.lirList.get(LoadingActivity.lirList.size()-1).getlLocal();
        double lirM = salaryY*lirRecently;
        lirtext.setText(Math.round(lirM)+"만원");
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

        LineDataSet setComp1 = new LineDataSet(yPir,"PIR");
        LineDataSet setComp2 = new LineDataSet(yLir,"LIR");
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
        Description description = new Description();
        description.setText("부산 지역 : PIR=집값/연봉, LIR=주택담보대출/연봉");
        lineChart.setDescription(description);
        lineChart.setData(data);
        lineChart.invalidate();



    }
    public void chartMoney(User user){
        pieChart = (PieChart)homeview.findViewById(R.id.money);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(60f);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(user.getSalaryM(),"월급"));
        yValues.add(new PieEntry(user.getDeposit()/12,"대출"));

        pieChart.setCenterText("월급 대출 비");
        pieChart.setCenterTextSize(14f);
        pieChart.setCenterTextColor(Color.rgb(122, 122, 122));

        pieChart.animateY(1000, Easing.EaseInOutCubic); //애니메이션
        int[] color_pie = {Color.rgb(255, 118, 113),Color.rgb(255, 179, 113)};
        PieDataSet dataSet = new PieDataSet(yValues,"");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(color_pie);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);

    }
}
