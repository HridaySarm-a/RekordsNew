package com.main.rekordsnew.Client.POJO;

public class GardenDetails {
    private String gardenName,yearOfEst,constitution,pncDetails;

    public GardenDetails(String gardenName, String yearOfEst, String constitution, String pncDetails) {
        this.gardenName = gardenName;
        this.yearOfEst = yearOfEst;
        this.constitution = constitution;
        this.pncDetails = pncDetails;
    }

    public GardenDetails() {
    }

    public String getGardenName() {
        return gardenName;
    }

    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }

    public String getYearOfEst() {
        return yearOfEst;
    }

    public void setYearOfEst(String yearOfEst) {
        this.yearOfEst = yearOfEst;
    }

    public String getConstitution() {
        return constitution;
    }

    public void setConstitution(String constitution) {
        this.constitution = constitution;
    }

    public String getPncDetails() {
        return pncDetails;
    }

    public void setPncDetails(String pncDetails) {
        this.pncDetails = pncDetails;
    }

    @Override
    public String toString() {
        return "GardenDetails{" +
                "gardenName='" + gardenName + '\'' +
                ", yearOfEst='" + yearOfEst + '\'' +
                ", constitution='" + constitution + '\'' +
                ", pncDetails='" + pncDetails + '\'' +
                '}';
    }
}
