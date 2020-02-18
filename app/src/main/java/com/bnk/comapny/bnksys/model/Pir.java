package com.bnk.comapny.bnksys.model;

public class Pir {
    String pDay;
    float pNation;
    float pLocal;

    public Pir() {
    }

    public Pir(String pDay, float pNation, float pLocal) {
        this.pDay = pDay;
        this.pNation = pNation;
        this.pLocal = pLocal;
    }

    @Override
    public String toString() {
        return "Lir{" +
                "pDay='" + pDay + '\'' +
                ", pNation=" + pNation +
                ", pLocal=" + pLocal +
                '}';
    }

    public String getpDay() {
        return pDay;
    }

    public void setpDay(String pDay) {
        this.pDay = pDay;
    }

    public float getpNation() {
        return pNation;
    }

    public void setpNation(float pNation) {
        this.pNation = pNation;
    }

    public float getpLocal() {
        return pLocal;
    }

    public void setpLocal(float pLocal) {
        this.pLocal = pLocal;
    }
}
