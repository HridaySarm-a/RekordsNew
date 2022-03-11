package com.main.rekordsnew.Admin.POJO;

public class AdminCredsModel {
    String password,phone;

    public AdminCredsModel() {
    }

    public AdminCredsModel(String password, String phone) {
        this.password = password;
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
