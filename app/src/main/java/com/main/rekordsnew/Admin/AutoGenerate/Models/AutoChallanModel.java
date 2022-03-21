package com.main.rekordsnew.Admin.AutoGenerate.Models;

import java.io.Serializable;

public class AutoChallanModel implements Serializable {
    private int slNo, local;
    private float percentage, minus, net, rate, amount, admin, comm, carrying, cess, misc, total, netAmount;

    public AutoChallanModel(int slNo, int local, float rate) {
        this.slNo = slNo;
        this.local = local;
        this.percentage = 0;
        this.minus = 0;
        this.net = 0;
        this.rate = rate;
        this.amount = 0;
        this.admin = 0;
        this.comm = 0;
        this.carrying = 0;
        this.cess = 0;
        this.misc = 0;
        this.total = 0;
        this.netAmount = local * rate;
    }

    public AutoChallanModel() {
    }

    public int getSlNo() {
        return slNo;
    }

    public void setSlNo(int slNo) {
        this.slNo = slNo;
    }

    public int getLocal() {
        return local;
    }

    public void setLocal(int local) {
        this.local = local;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public float getMinus() {
        return minus;
    }

    public void setMinus(float minus) {
        this.minus = minus;
    }

    public float getNet() {
        return net;
    }

    public void setNet(float net) {
        this.net = net;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getAdmin() {
        return admin;
    }

    public void setAdmin(float admin) {
        this.admin = admin;
    }

    public float getComm() {
        return comm;
    }

    public void setComm(float comm) {
        this.comm = comm;
    }

    public float getCarrying() {
        return carrying;
    }

    public void setCarrying(float carrying) {
        this.carrying = carrying;
    }

    public float getCess() {
        return cess;
    }

    public void setCess(float cess) {
        this.cess = cess;
    }

    public float getMisc() {
        return misc;
    }

    public void setMisc(float misc) {
        this.misc = misc;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(float netAmount) {
        this.netAmount = netAmount;
    }

}
