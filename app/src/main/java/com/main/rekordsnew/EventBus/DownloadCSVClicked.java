package com.main.rekordsnew.EventBus;

import com.main.rekordsnew.Client.POJO.Challan;

public class DownloadCSVClicked {
    private boolean success;
    private Challan challan;

    public DownloadCSVClicked(boolean success, Challan challan) {
        this.success = success;
        this.challan = challan;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Challan getChallan() {
        return challan;
    }

    public void setChallan(Challan challan) {
        this.challan = challan;
    }
}
