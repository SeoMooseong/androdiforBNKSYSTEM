package com.bnk.comapny.bnksys.model;

public class ApartRecommand {
    String address;
    String roadress;
    String name;
    int pay;
    String day;
    float sizeM;
    int sizep;


    public ApartRecommand() {
    }

    public ApartRecommand(String address, String roadress, String name, int pay, String day, float sizeM, int sizep) {
        this.address = address;
        this.roadress = roadress;
        this.name = name;
        this.pay = pay;
        this.day = day;
        this.sizeM = sizeM;
        this.sizep = sizep;
    }

    public String getRoadress() {
        return roadress;
    }

    public void setRoadress(String roadress) {
        this.roadress = roadress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public float getSizeM() {
        return sizeM;
    }

    public void setSizeM(float sizeM) {
        this.sizeM = sizeM;
    }

    public int getSizep() {
        return sizep;
    }

    public void setSizep(int sizep) {
        this.sizep = sizep;
    }

    @Override
    public String toString() {
        return "ApartRecommand{" +
                "address='" + address + '\'' +
                ", roadress='" + roadress + '\'' +
                ", name='" + name + '\'' +
                ", pay=" + pay +
                ", day='" + day + '\'' +
                ", sizeM=" + sizeM +
                ", sizep=" + sizep +
                '}';
    }
}
