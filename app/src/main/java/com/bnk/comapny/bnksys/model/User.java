package com.bnk.comapny.bnksys.model;

import java.util.ArrayList;

public class User {
    String name;
    int SalaryM;
    int Money;
    int Deposit;
    String Field;
    ArrayList<String> apartname;

    public User() {
    }

    public User(String name, int salaryM, int money, String field) {
        this.name = name;
        this.SalaryM = salaryM;
        this.Money = money;
        this.Deposit = 0;
        this.Field = field;
        this.apartname = null;
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

    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        Money = money;
    }

    public int getDeposit() {
        return Deposit;
    }

    public void setDeposit(int deposit) {
        Deposit = deposit;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public ArrayList<String> getApartname() {
        return apartname;
    }

    public void setApartname(ArrayList<String> apartname) {
        this.apartname = apartname;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", SalaryM=" + SalaryM +
                ", Money=" + Money +
                ", Deposit=" + Deposit +
                ", Field='" + Field + '\'' +
                ", apartname=" + apartname +
                '}';
    }
}
