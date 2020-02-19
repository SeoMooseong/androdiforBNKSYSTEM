package com.bnk.comapny.bnksys.model;

import java.util.List;
//주소, 집이름, 평수, 가격
public class ApartmentList {
    String address;
    List<Apartment> list;

    public ApartmentList() { }

    public ApartmentList(String address, List<Apartment> list) {
        this.address = address;
        this.list = list;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Apartment> getList() {
        return list;
    }

    public void setList(List<Apartment> list) {
        this.list = list;
    }
}
