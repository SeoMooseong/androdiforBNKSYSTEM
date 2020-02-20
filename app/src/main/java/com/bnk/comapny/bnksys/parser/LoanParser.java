package com.bnk.comapny.bnksys.parser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LoanParser implements Runnable {
    String clientKey = "70cb7688bc60456cc07744a6c6e9acbd";

    private String urlStr = "http://finlife.fss.or.kr/finlifeapi/mortgageLoanProductsSearch.json?";


    public String savingParse(String bankCode, int page){
        String str, receiveMsg = null;
        URL url = null;
        try {//"auth="+clientKey +
            url = new URL(urlStr+ "auth="+clientKey +"&topFinGrpNo=020000&pageNo="+page+"&financeCd="+bankCode);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            //conn.setRequestProperty("x-waple-authorization", clientKey);

            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                System.out.println("receiveMsg : "+ receiveMsg);

                reader.close();
            } else {
                System.out.println("통신 결과: "+ conn.getResponseCode() + "에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receiveMsg;

    }

    public ArrayList<Loan> jsonToList(String json){

        ArrayList<Loan> lones = new ArrayList<>();
        ArrayList<Loan> temps = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jarr = jsonObject.getJSONObject("result").getJSONArray("baseList");

            for(int i = 0 ; i < jarr.length() ; i++){
                JSONObject jo = jarr.getJSONObject(i);

                String dcls_month = jo.isNull("dcls_month") ? "" :  jo.getString("dcls_month");
                String fin_co_no = jo.isNull("fin_co_no") ? "" :  jo.getString("fin_co_no");
                String kor_co_nm = jo.isNull("kor_co_nm") ? "" :  jo.getString("kor_co_nm");
                String fin_prdt_cd = jo.isNull("fin_prdt_cd") ? "" :  jo.getString("fin_prdt_cd");
                String fin_prdt_nm = jo.isNull("fin_prdt_nm") ? "" :  jo.getString("fin_prdt_nm");
                String join_way = jo.isNull("join_way") ? "" :  jo.getString("join_way");
                String loan_inci_expn = jo.isNull("loan_inci_expn") ? "" :  jo.getString("loan_inci_expn");
                String erly_rpay_fee = jo.isNull("erly_rpay_fee") ? "" :  jo.getString("erly_rpay_fee");
                String dly_rate = jo.isNull("dly_rate") ? "" :  jo.getString("dly_rate");
                String loan_lmt = jo.isNull("loan_lmt") ? "" :  jo.getString("loan_lmt");
                String dcls_strt_day = jo.isNull("dcls_strt_day") ? "" :  jo.getString("dcls_strt_day");
                String dcls_end_day = jo.isNull("dcls_end_day") ? "" :  jo.getString("dcls_end_day");
                String fin_co_subm_day = jo.isNull("fin_co_subm_day") ? "" :  jo.getString("fin_co_subm_day");

                Loan s = new Loan(dcls_month, fin_co_no, kor_co_nm, fin_prdt_cd, fin_prdt_nm, join_way, loan_inci_expn, erly_rpay_fee, dly_rate, loan_lmt, dcls_strt_day, dcls_end_day, fin_co_subm_day);
                temps.add(s);
            }

            JSONArray jbrr = jsonObject.getJSONObject("result").getJSONArray("optionList");

            for(int i = 0 ; i < jbrr.length() ; i++) {
                JSONObject jo = jbrr.getJSONObject(i);

                String dcls_month = jo.isNull("dcls_month") ? "" :  jo.getString("dcls_month");
                String fin_co_no = jo.isNull("fin_co_no") ? "" :  jo.getString("fin_co_no");
                String fin_prdt_cd = jo.isNull("fin_prdt_cd") ? "" :  jo.getString("fin_prdt_cd");
                String mrtg_type = jo.isNull("mrtg_type") ? "" :  jo.getString("mrtg_type");
                String mrtg_type_nm = jo.isNull("mrtg_type_nm") ? "" :  jo.getString("mrtg_type_nm");
                String rpay_type = jo.isNull("rpay_type") ? "" :  jo.getString("rpay_type");
                String rpay_type_nm = jo.isNull("rpay_type_nm") ? "" :  jo.getString("rpay_type_nm");
                String lend_rate_type = jo.isNull("lend_rate_type") ? "" :  jo.getString("lend_rate_type");
                String lend_rate_type_nm = jo.isNull("lend_rate_type_nm") ? "" :  jo.getString("lend_rate_type_nm");
                double lend_rate_min = jo.isNull("lend_rate_min") ? 0 :  jo.getDouble("lend_rate_min");
                double lend_rate_max = jo.isNull("lend_rate_max") ? 0 :  jo.getDouble("lend_rate_max");
                double lend_rate_avg = jo.isNull("lend_rate_avg") ? 0 :  jo.getDouble("lend_rate_avg");


                Loan s = new Loan(dcls_month, fin_co_no, fin_prdt_cd, mrtg_type, mrtg_type_nm, rpay_type, rpay_type_nm, lend_rate_type, lend_rate_type_nm, lend_rate_min, lend_rate_max, lend_rate_avg);

                for(Loan loan : temps){
                    if (loan.getDcls_month().equals(s.getDcls_month()) && loan.getFin_co_no().equals(s.getFin_co_no()) && loan.getFin_prdt_cd().equals(s.getFin_prdt_cd())){
                        Loan newSaving = new Loan(loan, s);
                        lones.add(newSaving);
                        break;
                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lones;
    }
    public int getMaxPage(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            int maxPage = jsonObject.getJSONObject("result").getInt("max_page_no");
            return maxPage;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public void addLoanList(String bankCode){
        String json = savingParse(bankCode, 1);
        System.out.println("Loan test: "+ json );
        int maxPage = getMaxPage(json);
        ArrayList<Loan> list = jsonToList(json);
        Data.loans.addAll(list);
        System.out.println("Loan list size : " + list.size());
        for(int i = 2 ; i <= maxPage ; i++){
            String aPageJson = savingParse(bankCode, 1);
            ArrayList<Loan> aPageList = jsonToList(aPageJson);
            Data.loans.addAll(aPageList);
        }
        System.out.println("Loan Data size : " + Data.loans.size());
    }
    @Override
    public void run() {
        addLoanList("0010017");
        addLoanList("0010024");
    }
}
