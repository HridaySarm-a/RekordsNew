package com.main.rekordsnew.Others;

import java.io.Serializable;

public class OtherModel implements Serializable {
    String name,pan,aadhar,phone,address,password,image,key;

    public OtherModel(String name, String pan, String aadhar, String phone, String address, String password, String image, String key) {
        this.name = name;
        this.pan = pan;
        this.aadhar = aadhar;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.image = image;
        this.key = key;
    }

    public OtherModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
