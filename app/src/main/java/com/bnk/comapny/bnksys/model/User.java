package com.bnk.comapny.bnksys.model;

import java.util.ArrayList;

public class User {
    String name;
    int SalaryM;
    int Money;
    int Deposit;
    ArrayList<String> apartname;

    public User() {
        this.name="호성";
        this.SalaryM=7000000;
        this.Money=10000000;
        this.Deposit=10000000;
        this.apartname= new ArrayList<>();
    }

    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        Money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalaryM() {
        return SalaryM;
    }

    public void setSalaryM(int salaryM) {
        SalaryM = salaryM;
    }

    public ArrayList<String> getApartname() {
        return apartname;
    }

    public void setApartname(ArrayList<String> apartname) {
        this.apartname = apartname;
    }

    public int getDeposit() {
        return Deposit;
    }

    public void setDeposit(int deposit) {
        Deposit = deposit;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", SalaryM=" + SalaryM +
                ", apartname=" + apartname +
                '}';
    }
}
