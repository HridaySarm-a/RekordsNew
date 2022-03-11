package com.main.rekordsnew.Client.POJO;

public class PermanentAddress {
    private String village,policeStation,postOffice,subDivision,mouza,pincode,district;

    public PermanentAddress(String village, String policeStation, String postOffice, String subDivision, String mouza, String pincode, String district) {
        this.village = village;
        this.policeStation = policeStation;
        this.postOffice = postOffice;
        this.subDivision = subDivision;
        this.mouza = mouza;
        this.pincode = pincode;
        this.district = district;
    }

    public PermanentAddress() {
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getSubDivision() {
        return subDivision;
    }

    public void setSubDivision(String subDivision) {
        this.subDivision = subDivision;
    }

    public String getMouza() {
        return mouza;
    }

    public void setMouza(String mouza) {
        this.mouza = mouza;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
