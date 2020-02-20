package com.bnk.comapny.bnksys;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bnk.comapny.bnksys.parser.Data;
import com.bnk.comapny.bnksys.parser.Loan;

import org.w3c.dom.Text;

import java.util.List;

public class LoanAdapter2 extends RecyclerView.Adapter<LoanAdapter2.ResultViewHolder> {
    private Context context;
    private List<Loan> list;
    static class ResultViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        ResultViewHolder(LinearLayout v)
        {
            super(v);
            linearLayout=v;
        }
    }
    public LoanAdapter2(Context context, List<Loan> list)
    {
        this.context=context;
        this.list=list;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout v = (LinearLayout)inflater.inflate(R.layout.laon_list_item,viewGroup,false);
        return new ResultViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        TextView lname = (TextView)holder.linearLayout.findViewById(R.id.loannameuser);
        lname.setText(Data.loans.get(position).getFin_prdt_nm());
        TextView bname = (TextView)holder.linearLayout.findViewById(R.id.banknameuser);
        bname.setText(Data.loans.get(position).getKor_co_nm());
        TextView lowper = (TextView)holder.linearLayout.findViewById(R.id.lowper);
        lowper.setText(Data.loans.get(position).getLend_rate_min()+"%");
        TextView highper = (TextView)holder.linearLayout.findViewById(R.id.highper);
        highper.setText(Data.loans.get(position).getLend_rate_max()+"%");
        TextView aveper = (TextView)holder.linearLayout.findViewById(R.id.aveper);
        aveper.setText(Data.loans.get(position).getLend_rate_avg()+"%");
        TextView monthpayout = (TextView)holder.linearLayout.findViewById(R.id.monthpayout);
        monthpayout.setText(Data.loans.get(position).getRpay_type_nm());
        TextView mmpay = (TextView)holder.linearLayout.findViewById(R.id.monthpaypay);
        int remainmoney= (ResultActivity.seek_max*10000)-StartActivity.user.getMoney();
        double rate = (Data.loans.get(position).getLend_rate_max()/12)*0.001;
        double bunmo = Math.pow((double)1+rate,240)-1;

        double bunja = remainmoney*rate*Math.pow(1+rate,240);
        int result = (int)Math.round(bunja/bunmo);
        if(remainmoney>=0) {
            if (Data.loans.get(position).getRpay_type_nm().equals("만기일시상환방식")) {
                mmpay.setText("");
            } else if (Data.loans.get(position).getRpay_type_nm().equals("분할상환방식")) {
                String temp = (result+"").substring(0,(result+"").length()-4)+"만"+(result+"").substring((result+"").length()-4,(result+"").length());
                mmpay.setText("20년 기준 월 : "+temp+"원");
            }
        }
        else{
            mmpay.setText("대출이 필요없습니다");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
