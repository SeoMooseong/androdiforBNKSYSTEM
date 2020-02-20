package com.bnk.comapny.bnksys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bnk.comapny.bnksys.parser.Data;
import com.bnk.comapny.bnksys.parser.Loan;

import org.w3c.dom.Text;

import java.util.List;

public class LoanAdapter extends BaseAdapter {
    private Context context;
    private List<Loan> list;

    public LoanAdapter(Context context, List<Loan> list)
    {
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
        if(view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.laon_list_item,parent,false);
        }
        TextView lname = (TextView)view.findViewById(R.id.loannameuser);
        lname.setText(Data.loans.get(position).getFin_prdt_nm());
        TextView bname = (TextView)view.findViewById(R.id.banknameuser);
        bname.setText(Data.loans.get(position).getKor_co_nm());
        TextView lowper = (TextView)view.findViewById(R.id.lowper);
        lowper.setText(Data.loans.get(position).getLend_rate_min()+"%");
        TextView highper = (TextView)view.findViewById(R.id.highper);
        highper.setText(Data.loans.get(position).getLend_rate_max()+"%");
        TextView aveper = (TextView)view.findViewById(R.id.aveper);
        aveper.setText(Data.loans.get(position).getLend_rate_avg()+"%");
        TextView monthpayout = (TextView)view.findViewById(R.id.monthpayout);
        monthpayout.setText(Data.loans.get(position).getRpay_type_nm());

        TextView mmpay = (TextView)view.findViewById(R.id.monthpaypay);
        int remainmoney= (ResultActivity.seek_max*10000)-StartActivity.user.getMoney();
        double rate = (Data.loans.get(position).getLend_rate_avg()/12)*0.001;
        double bunmo = Math.pow((double)1+rate,240)-1;

        double bunja = remainmoney*rate*Math.pow(1+rate,240);
        int result = (int)Math.round(bunja/bunmo);
        if(remainmoney>=0) {
            if (Data.loans.get(position).getRpay_type_nm().equals("만기일시상환방식")) {
                mmpay.setText("유저 :"+StartActivity.user.getMoney()+"리얼머니"+ResultActivity.seek_max);

            } else if (Data.loans.get(position).getRpay_type_nm().equals("분할상환방식")) {
                mmpay.setText("20년 기준 월 : "+result+"원");
            }
        }
        else{
            mmpay.setText("24개월 기준 월 : "+ 11);
        }

        return view;
    }
}
