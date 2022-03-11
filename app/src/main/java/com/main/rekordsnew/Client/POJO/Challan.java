package com.main.rekordsnew.Client.POJO;


import com.main.rekordsnew.Others.OtherModel;

import java.io.Serializable;
import java.util.List;

public class Challan implements Serializable {
    private List<Entry> entries;
    private int challanNo;
    private float rate,percent;
    private String date;
    private List<OtherModel> collectors;
    private String pdfUrl,csvUrl;
    private float totalQty,totalAmt;

    public Challan() {
    }

    public Challan(List<Entry> entries, int challanNo, float rate, float percent, String date, List<OtherModel> collectors, String pdfUrl, String csvUrl, float totalQty, float totalAmt) {
        this.entries = entries;
        this.challanNo = challanNo;
        this.rate = rate;
        this.percent = percent;
        this.date = date;
        this.collectors = collectors;
        this.pdfUrl = pdfUrl;
        this.csvUrl = csvUrl;
        this.totalQty = totalQty;
        this.totalAmt = totalAmt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public int getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(int challanNo) {
        this.challanNo = challanNo;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public List<OtherModel> getCollectors() {
        return collectors;
    }

    public void setCollectors(List<OtherModel> collectors) {
        this.collectors = collectors;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getCsvUrl() {
        return csvUrl;
    }

    public void setCsvUrl(String csvUrl) {
        this.csvUrl = csvUrl;
    }

    public float getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(float totalQty) {
        this.totalQty = totalQty;
    }

    public float getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(float totalAmt) {
        this.totalAmt = totalAmt;
    }

    @Override
    public String toString() {
        return "Challan{" +
                "entries=" + entries +
                ", challanNo=" + challanNo +
                ", rate=" + rate +
                ", percent=" + percent +
                ", date='" + date + '\'' +
                ", collectors=" + collectors +
                ", pdfUrl='" + pdfUrl + '\'' +
                ", csvUrl='" + csvUrl + '\'' +
                ", totalQty=" + totalQty +
                ", totalAmt=" + totalAmt +
                '}';
    }
}
