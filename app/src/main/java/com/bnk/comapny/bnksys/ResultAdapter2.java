package com.bnk.comapny.bnksys;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bnk.comapny.bnksys.model.Apartment;

import java.util.List;

public class ResultAdapter2 extends RecyclerView.Adapter<ResultAdapter2.ResultViewHolder> {
    private Context context;
    private List<Apartment> list;
    static class ResultViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        ResultViewHolder(LinearLayout v)
        {
            super(v);
            linearLayout=v;
        }
    }
    public ResultAdapter2(Context context, List<Apartment> list)
    {
        this.context=context;
        this.list = list;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout v = (LinearLayout)inflater.inflate(R.layout.result_list_item,viewGroup,false);
        return new ResultViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        TextView name = (TextView)holder.linearLayout.findViewById(R.id.result_list_item_name);
        TextView size = (TextView)holder.linearLayout.findViewById(R.id.result_list_item_size);
        TextView floor = (TextView)holder.linearLayout.findViewById(R.id.result_list_item_floor);
        TextView date = (TextView)holder.linearLayout.findViewById(R.id.result_list_item_date);
        TextView payout = (TextView)holder.linearLayout.findViewById(R.id.result_list_item_payout);
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
