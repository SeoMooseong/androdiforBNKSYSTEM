package com.bnk.comapny.bnksys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bnk.comapny.bnksys.model.Apartment;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<Apartment> list;

    public ListAdapter(Context context, List<Apartment> list) {
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
            view = inflater.inflate(R.layout.recommand_list_item,parent,false);
        }

        TextView uname = (TextView)view.findViewById(R.id.apartnameuser);
        uname.setText(list.get(position).getName());
        TextView upayout = (TextView)view.findViewById(R.id.payoutuser);
        String temp = list.get(position).getPayout()+"";
        String temp2 = list.get(position).getPayoutmax()+"";
        TextView uadress = (TextView)view.findViewById(R.id.adressuser);
        uadress.setText(list.get(position).getAddress());
        if(temp.length() > 4){
            String billion = temp.substring(0, (temp.length() - 4));
            String million = temp.substring((temp.length() - 4));
            while(million.length() != 0 && million.charAt(0) == '0'){
                million = million.replaceFirst("0", "");
            }
            temp = billion + "억 " + million;
        }
        if(temp2.length() > 4){
            String billion = temp2.substring(0, (temp2.length() - 4));
            String million = temp2.substring((temp2.length() - 4));
            while(million.length() != 0 && million.charAt(0) == '0'){
                million = million.replaceFirst("0", "");
            }
            temp2 = billion + "억 " + million;
        }

        upayout.setText("실거래가 : 최저"+temp+"만원 ~ 최고"+temp2+"만원");
        TextView usizeP = (TextView)view.findViewById(R.id.sizePuser);
        usizeP.setText("평수 : "+list.get(position).getSizeP()+"평");
//        TextView ucheck = (TextView)view.findViewById(R.id.checking);

        return view;
    }
}
