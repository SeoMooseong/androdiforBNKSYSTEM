package com.bnk.comapny.bnksys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bnk.comapny.bnksys.model.Apartment;

import java.util.List;

public class ResultAdapter extends BaseAdapter {
    private Context context;
    private List<Apartment> list;

    public ResultAdapter(Context context, List<Apartment> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view==null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            view =inflater.inflate(R.layout.result_list_item,parent,false);
        }
        TextView name = (TextView)view.findViewById(R.id.result_list_item_name);
        TextView size = (TextView)view.findViewById(R.id.result_list_item_size);
        TextView floor = (TextView)view.findViewById(R.id.result_list_item_floor);
        TextView date = (TextView)view.findViewById(R.id.result_list_item_date);
        TextView payout = (TextView)view.findViewById(R.id.result_list_item_payout);
        name.setText(list.get(position).getName());
        size.setText("평수 : "+list.get(position).getSizeP()+"평("+list.get(position).getSizeM()+")");
        floor.setText("층수 : "+list.get(position).getFloor()+"층");
        String temp = list.get(position).getContractD();
        if(temp.length()<=1)
        {
            temp="0"+temp;
        }
        date.setText("계약일자 : "+list.get(position).getContractYM()+temp);
        temp = list.get(position).getPayout()+"";
        if(temp.length() > 4){
            String billion = temp.substring(0, (temp.length() - 4));
            String million = temp.substring((temp.length() - 4));
            while(million.length() != 0 && million.charAt(0) == '0'){
                million = million.replaceFirst("0", "");
            }
            temp = billion + "억 " + million+"만원";
        }
        payout.setText("실거래가 : "+temp);
        return view;
    }
}
