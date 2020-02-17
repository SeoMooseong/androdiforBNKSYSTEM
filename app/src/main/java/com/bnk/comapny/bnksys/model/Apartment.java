package com.bnk.comapny.bnksys.model;

public class Apartment {
    String name; //이름
    String address; //시군구주소
    String Bunji; //시군구번지
    String roadress; //도로명주소
    int payout; //매매대금
    int floor; //층수
    float sizeM; //크기(m2)
    int sizeP; //크기(평수)
    String ContractYM; //계약년월
    String ContractD; //계약일
    String BuildYear;

    public Apartment() {
        this.name = "";
        this.address = "";
        this.Bunji = "";
        this.roadress = "";
        this.payout = 0;
        this.floor = 0;
        this.sizeM = 0;
        this.ContractYM = "";
        this.ContractD = "";
        this.sizeP = 0;
        this.BuildYear = "";

    }

    public Apartment(String name, String address, String bunji, String roadress, int payout, int floor, float sizeM, String contractYM, String contractD, int sizeP, String BuildYear) {
        this.name = name;
        this.address = address;
        this.Bunji = bunji;
        this.roadress = roadress;
        this.payout = payout;
        this.floor = floor;
        this.sizeM = sizeM;
        this.ContractYM = contractYM;
        this.ContractD = contractD;
        this.sizeP = sizeP;
        this.BuildYear = BuildYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBunji() {
        return Bunji;
    }

    public void setBunji(String bunji) {
        Bunji = bunji;
    }

    public String getRoadress() {
        return roadress;
    }

    public void setRoadress(String roadress) {
        this.roadress = roadress;
    }

    public int getPayout() {
        return payout;
    }

    public void setPayout(int payout) {
        this.payout = payout;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public float getSizeM() {
        return sizeM;
    }

    public void setSizeM(float sizeM) {
        this.sizeM = sizeM;
    }

    public int getSizeP() {
        return sizeP;
    }

    public void setSizeP(int sizeP) {
        this.sizeP = sizeP;
    }

    public String getContractYM() {
        return ContractYM;
    }

    public void setContractYM(String contractYM) {
        ContractYM = contractYM;
    }

    public String getContractD() {
        return ContractD;
    }

    public void setContractD(String contractD) {
        ContractD = contractD;
    }

    public String getBuildYear() {
        return BuildYear;
    }

    public void setBuildYear(String buildYear) {
        BuildYear = buildYear;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", Bunji='" + Bunji + '\'' +
                ", roadress='" + roadress + '\'' +
                ", payout=" + payout +
                ", floor=" + floor +
                ", sizeM=" + sizeM +
                ", sizeP=" + sizeP +
                ", ContractYM='" + ContractYM + '\'' +
                ", ContractD='" + ContractD + '\'' +
                ", BuildYear='" + BuildYear + '\'' +
                '}';
    }
}
