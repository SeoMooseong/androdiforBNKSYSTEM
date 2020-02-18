package com.bnk.comapny.bnksys.model;

public class Lir {
    String lDay;
    float lNation;
    float lLocal;

    public Lir() {
    }

    public Lir(String lDay, float lNation, float lLocal) {

        this.lDay = lDay;
        this.lNation = lNation;
        this.lLocal = lLocal;
    }

    @Override
    public String toString() {
        return "Lir{" +
                "lDay='" + lDay + '\'' +
                ", lNation=" + lNation +
                ", lLocal=" + lLocal +
                '}';
    }

    public String getlDay() {
        return lDay;
    }

    public void setlDay(String lDay) {
        this.lDay = lDay;
    }

    public float getlNation() {
        return lNation;
    }

    public void setlNation(float lNation) {
        this.lNation = lNation;
    }

    public float getlLocal() {
        return lLocal;
    }

    public void setlLocal(float lLocal) {
        this.lLocal = lLocal;
    }
}