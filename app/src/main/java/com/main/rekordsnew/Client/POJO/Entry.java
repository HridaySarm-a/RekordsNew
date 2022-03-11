package com.main.rekordsnew.Client.POJO;

import java.io.Serializable;

public class Entry implements Serializable {

    private float local,percent,net,amount,admin,comm,carrying,cess,misc,total,netAmount,minus;


    public Entry(float local, float percent, float net, float amount, float admin, float comm, float carrying, float cess, float misc, float total, float netAmount, float minus) {
        this.local = local;
        this.percent = percent;
        this.net = net;
        this.amount = amount;
        this.admin = admin;
        this.comm = comm;
        this.carrying = carrying;
        this.cess = cess;
        this.misc = misc;
        this.total = total;
        this.netAmount = netAmount;
        this.minus = minus;
    }

    public Entry() {
    }

    public float getLocal() {
        return local;
    }

    public void setLocal(float local) {
        this.local = local;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public float getNet() {
        return net;
    }

    public void setNet(float net) {
        this.net = net;
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

    public float getMinus() {
        return minus;
    }

    public void setMinus(float minus) {
        this.minus = minus;
    }
}
