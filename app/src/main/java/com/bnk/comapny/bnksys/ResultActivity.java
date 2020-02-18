package com.bnk.comapny.bnksys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bnk.comapny.bnksys.model.Apartment;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.List;

public class ResultActivity extends AppCompatActivity {
    MapView mapView;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();

        Double mapX = Double.parseDouble(intent.getStringExtra("mapX"));
        Double mapY = Double.parseDouble(intent.getStringExtra("mapY"));

        String address = intent.getStringExtra("address");
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

        address = address.replace("$", "");
        address = address.replace("@", "");

        List<Apartment> aptList = null;

        for(int i = 0; i < MainActivity.aptAdressList.size(); i++){
            if(MainActivity.aptAdressList.get(i).getAddress().equals(address)){
                aptList = MainActivity.aptAdressList.get(i).getList();
                break;
            }
        }

        ListView listView = findViewById(R.id.result_list);
        AptAdapter aptAdapter = new AptAdapter(this, R.id.result_list, aptList);
        listView.setAdapter(aptAdapter);

//        if(aptList != null){
//            TextView[] views = new TextView[aptList.size()];
//            for(int i = 0;i < aptList.size(); i++){
//                System.out.println(aptList.get(i).getPayout());
//                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                views[i].setLayoutParams(params);
//                resultList.addView(views[i], params);
//            }
//        }

//        ArrayAdapter<Apartment> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, aptList);
//        listView = findViewById(R.id.result_list);
//        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "Destroy called!");
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
            holder.size.setText(apt.getSizeP() + "평(" + apt.getSizeM() + ")");
            holder.floor.setText(apt.getFloor() + "층");
            String tmp = apt.getContractD();
            if(tmp.length() <= 1){
                tmp = "0" + tmp;
            }
            holder.date.setText(apt.getContractYM() + tmp);
            tmp = apt.getPayout() + "";
            if(tmp.length() > 4){
                String billion = tmp.substring(0, (tmp.length() - 4));
                String million = tmp.substring((tmp.length() - 4));
                while(million.length() != 0 && million.charAt(0) == '0'){
                    million = million.replaceFirst("0", "");
                }
                tmp = billion + "억 " + million;
            }
            holder.payout.setText(tmp);

            return row;
        }
    }
}
