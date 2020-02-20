package com.bnk.comapny.bnksys;

import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bnk.comapny.bnksys.model.Apartment;
import com.bnk.comapny.bnksys.model.ApartmentList;
import com.bnk.comapny.bnksys.parser.Data;
import com.bnk.comapny.bnksys.util.NumberFormat;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultActivity extends AppCompatActivity {
    MapView mapView;
    ListView listView;
    LineChart lineChart;

    String address;

    ApartmentList tmpAptList;
    Apartment tmpApt;
    List<Apartment> aptList;
    List<List<Deal>> dealList;
    SeekBar sb;
    TextView tv;
    final int numData = 5;
    ArrayList<Entry> values1;
    List<Deal> dataList;
    private int seek_min;
    private int seek_max;
    final private int step =1;
    int nodeCnt;
    String[] quarters;


    private RecyclerView listRecommand;
    private RecyclerView.Adapter listAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView loanlist;
    private RecyclerView.Adapter loanAdapter;
    private RecyclerView.LayoutManager loanManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        showMap();
        showList();
        showChart();
        sb = (SeekBar)findViewById(R.id.seek);
        tv = (TextView)findViewById(R.id.loan_default);
        seek_min = aptList.get(0).getPayout();
        seek_max = aptList.get(0).getPayout();
//        sb.setMax((seek_max-seek_min)/step);
        for(Apartment aptt : aptList)
        {
            if(seek_min>=aptt.getPayout())
            {
                seek_min=aptt.getPayout();
            }
            if(seek_max<=aptt.getPayout())
            {
                seek_max=aptt.getPayout();
            }
        }
        System.out.println("최소 : "+seek_min+"최대 : "+seek_max);
        sb.getProgressDrawable().setColorFilter(Color.rgb(255,118,113), PorterDuff.Mode.SRC_IN);
        sb.getThumb().setColorFilter(Color.rgb(255,118,113), PorterDuff.Mode.SRC_IN);
        sb.setMax(seek_max);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println(progress);
                String temp = progress+"";

                if(temp.length()>4)
                {
                    temp = temp.substring(0, temp.length() - 4) + "억" + temp.substring((temp.length() - 4), temp.length()) + "만원";
                    tv.setText(temp);
                }
                else
                {
                    temp = progress+"만원";
                    tv.setText(temp);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                System.out.println("일음");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println("일음");
            }
        });
    }

    private void showMap(){
        //지도 관련..
        Intent intent = getIntent();

        Double mapX = Double.parseDouble(intent.getStringExtra("mapX"));
        Double mapY = Double.parseDouble(intent.getStringExtra("mapY"));

        address = intent.getStringExtra("address");
        int idx = address.indexOf("@");
        String aptName = address.substring(idx + 1);

        Toast.makeText(this, mapX + ", " + mapY, Toast.LENGTH_SHORT).show();

        mapView = findViewById(R.id.result_map_view);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(mapX, mapY), true);

        MapPOIItem marker = new MapPOIItem();
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(mapX, mapY));
        marker.setItemName(aptName);
        marker.setTag(0);
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);
    }

    private void showList(){
        //리스트 관련..
        address = address.replace("$", "");
        address = address.replace("@", "");


        for(int i = 0; i < LoadingActivity.aptAdressList.size(); i++){
            tmpAptList = LoadingActivity.aptAdressList.get(i);
            if(tmpAptList.getAddress().equals(address)){
                aptList = tmpAptList.getList();
                break;
            }
        }
        listRecommand = (RecyclerView) findViewById(R.id.result_list);
        layoutManager = new LinearLayoutManager(this);
        listRecommand.setLayoutManager(layoutManager);
        listAdapter = new ResultAdapter2(this,aptList);
        listRecommand.setAdapter(listAdapter);
//        ListView listView = findViewById(R.id.result_list);
//        AptAdapter aptAdapter = new AptAdapter(this, R.id.result_list, aptList);
//        listView.setAdapter(aptAdapter);
        loanlist = (RecyclerView) findViewById(R.id.loan_list);
        loanManager = new LinearLayoutManager(this);
        loanlist.setLayoutManager(loanManager);
        loanAdapter = new LoanAdapter2(this, Data.loans);
        loanlist.setAdapter(loanAdapter);
    }

    private void showChart(){
        //그래프 관련..
        HashSet<Integer> sizeSet = new HashSet(); //평수List
        for(int i = 0; i < aptList.size(); i++){
            sizeSet.add(aptList.get(i).getSizeP());
        }

        List<Integer> sizeList = new ArrayList<>(sizeSet);
        Collections.sort(sizeList); //평수List 오름차순으로 변경

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.result_graph_filter, sizeList);
        Spinner filter = findViewById(R.id.result_graph_filter);
        filter.setAdapter(adapter); //평수List를 Spinner 목록에 등록

        dealList = new ArrayList<>(); //평수별 Apartment 정보 list
        for(int i = 0; i < sizeList.size(); i++){
            dealList.add(new ArrayList<Deal>());
        }

        for(int i = 0; i < aptList.size(); i++){ //평수List와 평수별<Apartment>List의 인덱스를 동일하게 구성
            for(int j = 0; j < sizeList.size(); j++){
                tmpApt = aptList.get(i);
                if(tmpApt.getSizeP() == sizeList.get(j)){
                    dealList.get(j).add(new Deal(tmpApt.getContractYM() + tmpApt.getContractD(), tmpApt.getPayout()));
                    break;
                }
            }
        }

        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                drawChart(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                drawChart(0);
            }
        });
    }

    private void drawChart(int index){
        lineChart = (LineChart) findViewById(R.id.result_graph);
        values1 = new ArrayList<>();

        dataList = dealList.get(index);
        Collections.sort(dataList);
        Entry entry = null;

        nodeCnt = numData > dataList.size() ? dataList.size() : numData; //numData or dataList.size 중 작은 것을 기준으로 설정
        for(int i = nodeCnt; i > 0; i--){
            entry = new Entry(nodeCnt - i, dataList.get(dataList.size() - i).payout);
            values1.add(entry);
        }

        LineDataSet dataSet = new LineDataSet(values1, "매매"); //LineDataSet 선언
        dataSet.setColor(ContextCompat.getColor(this, R.color.PointColor)); //LineChart에서 Line Color 설정
        dataSet.setCircleColor(ContextCompat.getColor(this,R.color.PointColor)); // LineChart에서 Line Circle Color 설정
        dataSet.setCircleHoleColor(ContextCompat.getColor(this,R.color.PointColor)); // LineChart에서 Line Hole Circle Color 설정
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        quarters = new String[nodeCnt];

        int size = 0;
        for(int i = nodeCnt; i > 0; i--){
            quarters[size++] = dataList.get(dataList.size() - i).getDate();
        }

        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                int val = (int)value;
                if(nodeCnt == 1 && (val == -1 || val == 1)){
                    return "";
                }else{
                    return quarters[val];
                }
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
        lineChart.setDescription(null);
        lineChart.setData(data);
        lineChart.invalidate();
    }

    public class Deal implements Comparable<Deal> {
        String date;
        int payout;

        public Deal() { }

        public Deal(String date, int payout) {
            if(date.length() == 7){ //2020111과 같은 월에 0이 생략된 경우 예외처리
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                String tmp = date.substring(0, 6);
                System.out.println(tmp);
                tmp += "0";
                System.out.println(tmp);
                tmp += date.substring(6);
                System.out.println(tmp);
                date = tmp;
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            }
            this.date = date;
            this.payout = payout;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getPayout() {
            return payout;
        }

        public void setPayout(int payout) {
            this.payout = payout;
        }

        @Override
        public int compareTo(Deal o) {
            return date.compareTo(o.date);
        }
    }

    public class AptAdapter extends ArrayAdapter<Apartment> {
        private Context context;
        private List<Apartment> list;
        private LayoutInflater inflater = null;



        public AptAdapter(@NonNull Context context, int resource, @NonNull List<Apartment> list) {
            super(context, resource, list);
            this.context = context;
            this.list = list;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Nullable
        @Override
        public Apartment getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class ViewHolder {
            //이름, 층수, 크기(sizeM)m2, 크기(sizeP)평수, 계약년월일, 매매대금
            public TextView name;
            public TextView size;
            public TextView floor;
            public TextView date;
            public TextView payout;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            ViewHolder holder;

            if(row == null){
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                row = layoutInflater.inflate(R.layout.result_list_item, parent, false);

                holder = new ViewHolder();
                holder.name = (TextView)row.findViewById(R.id.result_list_item_name);
                holder.size = (TextView)row.findViewById(R.id.result_list_item_size);
                holder.floor = (TextView)row.findViewById(R.id.result_list_item_floor);
                holder.date = (TextView)row.findViewById(R.id.result_list_item_date);
                holder.payout = (TextView)row.findViewById(R.id.result_list_item_payout);

                row.setTag(holder);

            }else{
                holder = (ViewHolder) row.getTag();
            }

            String Tag = row.getTag().toString();
            int idx = Tag.indexOf("@");
            String tag = Tag.substring(idx + 1);

            Apartment apt = (Apartment) list.get(position);


            holder.name.setText(apt.getName());
            holder.size.setText("평수 : "+apt.getSizeP() + "평(" + apt.getSizeM() + ")");
            holder.floor.setText("층수 : " + apt.getFloor() + "층");
            String tmp = apt.getContractD();
            if(tmp.length() <= 1){
                tmp = "0" + tmp;
            }
            holder.date.setText("계약일자 : "+apt.getContractYM() + tmp);
            tmp = apt.getPayout() + "";
            if(tmp.length() > 4){
                String billion = tmp.substring(0, (tmp.length() - 4));
                String million = tmp.substring((tmp.length() - 4));
                while(million.length() != 0 && million.charAt(0) == '0'){
                    million = million.replaceFirst("0", "");
                }
                tmp = billion + "억 " + million;
            }
            holder.payout.setText("매매금액 : " +tmp);

            return row;
        }
    }
}
