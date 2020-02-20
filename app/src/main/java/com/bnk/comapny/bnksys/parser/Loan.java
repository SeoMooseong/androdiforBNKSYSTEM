package com.bnk.comapny.bnksys.parser;

public class Loan {
    private String dcls_month;
    private String fin_co_no ;
    private String kor_co_nm;
    private String fin_prdt_cd ;
    private String fin_prdt_nm;
    private String join_way;
    private String loan_inci_expn;
    private String erly_rpay_fee;
    private String dly_rate;
    private String loan_lmt;
    private String dcls_strt_day;
    private String dcls_end_day;
    private String fin_co_subm_day;


    private String mrtg_type;
    private String mrtg_type_nm;
    private String rpay_type;
    private String rpay_type_nm;
    private String lend_rate_type;
    private String lend_rate_type_nm;
    private double lend_rate_min;
    private double lend_rate_max;
    private double lend_rate_avg;

    public Loan(String dcls_month, String fin_co_no, String kor_co_nm, String fin_prdt_cd, String fin_prdt_nm, String join_way, String loan_inci_expn, String erly_rpay_fee, String dly_rate, String loan_lmt, String dcls_strt_day, String dcls_end_day, String fin_co_subm_day, String mrtg_type, String mrtg_type_nm, String rpay_type, String rpay_type_nm, String lend_rate_type, String lend_rate_type_nm, double lend_rate_min, double lend_rate_max, double lend_rate_avg) {
        this.dcls_month = dcls_month;
        this.fin_co_no = fin_co_no;
        this.kor_co_nm = kor_co_nm;
        this.fin_prdt_cd = fin_prdt_cd;
        this.fin_prdt_nm = fin_prdt_nm;
        this.join_way = join_way;
        this.loan_inci_expn = loan_inci_expn;
        this.erly_rpay_fee = erly_rpay_fee;
        this.dly_rate = dly_rate;
        this.loan_lmt = loan_lmt;
        this.dcls_strt_day = dcls_strt_day;
        this.dcls_end_day = dcls_end_day;
        this.fin_co_subm_day = fin_co_subm_day;
        this.mrtg_type = mrtg_type;
        this.mrtg_type_nm = mrtg_type_nm;
        this.rpay_type = rpay_type;
        this.rpay_type_nm = rpay_type_nm;
        this.lend_rate_type = lend_rate_type;
        this.lend_rate_type_nm = lend_rate_type_nm;
        this.lend_rate_min = lend_rate_min;
        this.lend_rate_max = lend_rate_max;
        this.lend_rate_avg = lend_rate_avg;
    }

    public Loan(String dcls_month, String fin_co_no, String kor_co_nm, String fin_prdt_cd, String fin_prdt_nm, String join_way, String loan_inci_expn, String erly_rpay_fee, String dly_rate, String loan_lmt, String dcls_strt_day, String dcls_end_day, String fin_co_subm_day) {
        this.dcls_month = dcls_month;
        this.fin_co_no = fin_co_no;
        this.kor_co_nm = kor_co_nm;
        this.fin_prdt_cd = fin_prdt_cd;
        this.fin_prdt_nm = fin_prdt_nm;
        this.join_way = join_way;
        this.loan_inci_expn = loan_inci_expn;
        this.erly_rpay_fee = erly_rpay_fee;
        this.dly_rate = dly_rate;
        this.loan_lmt = loan_lmt;
        this.dcls_strt_day = dcls_strt_day;
        this.dcls_end_day = dcls_end_day;
        this.fin_co_subm_day = fin_co_subm_day;
    }

    public Loan(String dcls_month, String fin_co_no, String fin_prdt_cd , String mrtg_type, String mrtg_type_nm, String rpay_type, String rpay_type_nm, String lend_rate_type, String lend_rate_type_nm, double lend_rate_min, double lend_rate_max, double lend_rate_avg) {
        this.dcls_month = dcls_month;
        this.fin_co_no = fin_co_no;
        this.fin_prdt_cd = fin_prdt_cd ;
        this.mrtg_type = mrtg_type;
        this.mrtg_type_nm = mrtg_type_nm;
        this.rpay_type = rpay_type;
        this.rpay_type_nm = rpay_type_nm;
        this.lend_rate_type = lend_rate_type;
        this.lend_rate_type_nm = lend_rate_type_nm;
        this.lend_rate_min = lend_rate_min;
        this.lend_rate_max = lend_rate_max;
        this.lend_rate_avg = lend_rate_avg;
    }

    public Loan(Loan l1, Loan l2) {
        this(l1.dcls_month,l1.fin_co_no,l1.kor_co_nm,l1.fin_prdt_cd,l1.fin_prdt_nm,l1.join_way,l1.loan_inci_expn,l1.erly_rpay_fee,l1.dly_rate,l1.loan_lmt,l1.dcls_strt_day,l1.dcls_end_day,l1.fin_co_subm_day,l2.mrtg_type,l2.mrtg_type_nm,l2.rpay_type,l2.rpay_type_nm,l2.lend_rate_type,l2.lend_rate_type_nm,l2.lend_rate_min,l2.lend_rate_max,l2.lend_rate_avg);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "dcls_month='" + dcls_month + '\'' +
                ", fin_co_no='" + fin_co_no + '\'' +
                ", kor_co_nm='" + kor_co_nm + '\'' +
                ", fin_prdt_cd='" + fin_prdt_cd + '\'' +
                ", fin_prdt_nm='" + fin_prdt_nm + '\'' +
                ", join_way='" + join_way + '\'' +
                ", loan_inci_expn='" + loan_inci_expn + '\'' +
                ", erly_rpay_fee='" + erly_rpay_fee + '\'' +
                ", dly_rate='" + dly_rate + '\'' +
                ", loan_lmt='" + loan_lmt + '\'' +
                ", dcls_strt_day='" + dcls_strt_day + '\'' +
                ", dcls_end_day='" + dcls_end_day + '\'' +
                ", fin_co_subm_day='" + fin_co_subm_day + '\'' +
                ", mrtg_type='" + mrtg_type + '\'' +
                ", mrtg_type_nm='" + mrtg_type_nm + '\'' +
                ", rpay_type='" + rpay_type + '\'' +
                ", rpay_type_nm='" + rpay_type_nm + '\'' +
                ", lend_rate_type='" + lend_rate_type + '\'' +
                ", lend_rate_type_nm='" + lend_rate_type_nm + '\'' +
                ", lend_rate_min=" + lend_rate_min +
                ", lend_rate_max=" + lend_rate_max +
                ", lend_rate_avg=" + lend_rate_avg +
                '}';
    }

    public String getDcls_month() {
        return dcls_month;
    }

    public void setDcls_month(String dcls_month) {
        this.dcls_month = dcls_month;
    }

    public String getFin_co_no() {
        return fin_co_no;
    }

    public void setFin_co_no(String fin_co_no) {
        this.fin_co_no = fin_co_no;
    }

    public String getKor_co_nm() {
        return kor_co_nm;
    }

    public void setKor_co_nm(String kor_co_nm) {
        this.kor_co_nm = kor_co_nm;
    }

    public String getFin_prdt_cd() {
        return fin_prdt_cd;
    }

    public void setFin_prdt_cd(String fin_prdt_cd) {
        this.fin_prdt_cd = fin_prdt_cd;
    }

    public String getFin_prdt_nm() {
        return fin_prdt_nm;
    }

    public void setFin_prdt_nm(String fin_prdt_nm) {
        this.fin_prdt_nm = fin_prdt_nm;
    }

    public String getJoin_way() {
        return join_way;
    }

    public void setJoin_way(String join_way) {
        this.join_way = join_way;
    }

    public String getLoan_inci_expn() {
        return loan_inci_expn;
    }

    public void setLoan_inci_expn(String loan_inci_expn) {
        this.loan_inci_expn = loan_inci_expn;
    }

    public String getErly_rpay_fee() {
        return erly_rpay_fee;
    }

    public void setErly_rpay_fee(String erly_rpay_fee) {
        this.erly_rpay_fee = erly_rpay_fee;
    }

    public String getDly_rate() {
        return dly_rate;
    }

    public void setDly_rate(String dly_rate) {
        this.dly_rate = dly_rate;
    }

    public String getLoan_lmt() {
        return loan_lmt;
    }

    public void setLoan_lmt(String loan_lmt) {
        this.loan_lmt = loan_lmt;
    }

    public String getDcls_strt_day() {
        return dcls_strt_day;
    }

    public void setDcls_strt_day(String dcls_strt_day) {
        this.dcls_strt_day = dcls_strt_day;
    }

    public String getDcls_end_day() {
        return dcls_end_day;
    }

    public void setDcls_end_day(String dcls_end_day) {
        this.dcls_end_day = dcls_end_day;
    }

    public String getFin_co_subm_day() {
        return fin_co_subm_day;
    }

    public void setFin_co_subm_day(String fin_co_subm_day) {
        this.fin_co_subm_day = fin_co_subm_day;
    }

    public String getMrtg_type() {
        return mrtg_type;
    }

    public void setMrtg_type(String mrtg_type) {
        this.mrtg_type = mrtg_type;
    }

    public String getMrtg_type_nm() {
        return mrtg_type_nm;
    }

    public void setMrtg_type_nm(String mrtg_type_nm) {
        this.mrtg_type_nm = mrtg_type_nm;
    }

    public String getRpay_type() {
        return rpay_type;
    }

    public void setRpay_type(String rpay_type) {
        this.rpay_type = rpay_type;
    }

    public String getRpay_type_nm() {
        return rpay_type_nm;
    }

    public void setRpay_type_nm(String rpay_type_nm) {
        this.rpay_type_nm = rpay_type_nm;
    }

    public String getLend_rate_type() {
        return lend_rate_type;
    }

    public void setLend_rate_type(String lend_rate_type) {
        this.lend_rate_type = lend_rate_type;
    }

    public String getLend_rate_type_nm() {
        return lend_rate_type_nm;
    }

    public void setLend_rate_type_nm(String lend_rate_type_nm) {
        this.lend_rate_type_nm = lend_rate_type_nm;
    }

    public double getLend_rate_min() {
        return lend_rate_min;
    }

    public void setLend_rate_min(double lend_rate_min) {
        this.lend_rate_min = lend_rate_min;
    }

    public double getLend_rate_max() {
        return lend_rate_max;
    }

    public void setLend_rate_max(double lend_rate_max) {
        this.lend_rate_max = lend_rate_max;
    }

    public double getLend_rate_avg() {
        return lend_rate_avg;
    }

    public void setLend_rate_avg(double lend_rate_avg) {
        this.lend_rate_avg = lend_rate_avg;
    }
}
