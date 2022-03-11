package com.main.rekordsnew.Client.POJO;

import java.util.HashMap;

public class ClientRoot {
    private PersonalDetails personalDetails;
    private TeaBoardDetails teaBoardDetails;
    private GardenDetails gardenDetails;
    private String key;
    private HashMap<String,Challan> Challans;

    public ClientRoot(PersonalDetails personalDetails, TeaBoardDetails teaBoardDetails, GardenDetails gardenDetails, String key, HashMap<String, Challan> challans) {
        this.personalDetails = personalDetails;
        this.teaBoardDetails = teaBoardDetails;
        this.gardenDetails = gardenDetails;
        this.key = key;
        Challans = challans;
    }

    public ClientRoot() {
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public TeaBoardDetails getTeaBoardDetails() {
        return teaBoardDetails;
    }

    public void setTeaBoardDetails(TeaBoardDetails teaBoardDetails) {
        this.teaBoardDetails = teaBoardDetails;
    }

    public GardenDetails getGardenDetails() {
        return gardenDetails;
    }

    public void setGardenDetails(GardenDetails gardenDetails) {
        this.gardenDetails = gardenDetails;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public HashMap<String, Challan> getChallans() {
        return Challans;
    }

    public void setChallans(HashMap<String, Challan> challans) {
        Challans = challans;
    }

    @Override
    public String toString() {
        return "ClientRoot{" +
                "personalDetails=" + personalDetails +
                ", teaBoardDetails=" + teaBoardDetails +
                ", gardenDetails=" + gardenDetails +
                ", key='" + key + '\'' +
                ", Challans=" + Challans +
                '}';
    }
}
