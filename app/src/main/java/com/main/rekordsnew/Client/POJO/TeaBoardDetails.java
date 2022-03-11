package com.main.rekordsnew.Client.POJO;

public class TeaBoardDetails {
    private String gardenName,qrCardNo,totalArea,pattaNo,dagNo;
    private PermanentAddress permanentAddress;

    public TeaBoardDetails(String gardenName, String qrCardNo, String totalArea, String pattaNo, String dagNo, PermanentAddress permanentAddress) {
        this.gardenName = gardenName;
        this.qrCardNo = qrCardNo;
        this.totalArea = totalArea;
        this.pattaNo = pattaNo;
        this.dagNo = dagNo;
        this.permanentAddress = permanentAddress;
    }

    public TeaBoardDetails() {
    }

    public String getGardenName() {
        return gardenName;
    }

    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }

    public String getQrCardNo() {
        return qrCardNo;
    }

    public void setQrCardNo(String qrCardNo) {
        this.qrCardNo = qrCardNo;
    }

    public String getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(String totalArea) {
        this.totalArea = totalArea;
    }

    public String getPattaNo() {
        return pattaNo;
    }

    public void setPattaNo(String pattaNo) {
        this.pattaNo = pattaNo;
    }

    public String getDagNo() {
        return dagNo;
    }

    public void setDagNo(String dagNo) {
        this.dagNo = dagNo;
    }

    public PermanentAddress getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(PermanentAddress permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    @Override
    public String toString() {
        return "TeaBoardDetails{" +
                "gardenName='" + gardenName + '\'' +
                ", qrCardNo='" + qrCardNo + '\'' +
                ", totalArea='" + totalArea + '\'' +
                ", pattaNo='" + pattaNo + '\'' +
                ", dagNo='" + dagNo + '\'' +
                ", permanentAddress=" + permanentAddress +
                '}';
    }
}
