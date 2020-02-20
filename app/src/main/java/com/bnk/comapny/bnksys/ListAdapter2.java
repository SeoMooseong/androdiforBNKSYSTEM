package com.bnk.comapny.bnksys;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bnk.comapny.bnksys.model.Apartment;

import java.util.List;

public class ListAdapter2 extends RecyclerView.Adapter<ListAdapter2.MyViewHolder> {

    private Context context;
    private List<Apartment> list;

    static class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;

        MyViewHolder(LinearLayout v)
        {
            super(v);
            linearLayout=v;
        }
    }

    public ListAdapter2(Context context, List<Apartment> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ListAdapter2.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout v = (LinearLayout)inflater.inflate(R.layout.recommand_list_item,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextView uname = holder.linearLayout.findViewById(R.id.apartnameuser);
        uname.setText(list.get(position).getName());
        TextView upayout = holder.linearLayout.findViewById(R.id.payoutuser);
        String temp = list.get(position).getPayout()+"";
        if(temp.length() > 4){
            String billion = temp.substring(0, (temp.length() - 4));
            String million = temp.substring((temp.length() - 4));
            while(million.length() != 0 && million.charAt(0) == '0'){
                million = million.replaceFirst("0", "");
            }
            temp = billion + "억 " + million;
        }
        upayout.setText("실거래가 : "+temp+"만원");
        TextView usizeP = holder.linearLayout.findViewById(R.id.sizePuser);

        usizeP.setText("평수 : "+list.get(position).getSizeP()+"평");
//        TextView ucheck = (TextView)holder.linearLayout.findViewById(R.id.checking);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
