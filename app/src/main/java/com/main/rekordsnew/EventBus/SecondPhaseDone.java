package com.main.rekordsnew.EventBus;

import com.main.rekordsnew.Client.POJO.Challan;

public class SecondPhaseDone {
    private boolean successfull;
    private Challan challan;

    public SecondPhaseDone(boolean successfull, Challan challan) {
        this.successfull = successfull;
        this.challan = challan;
    }

    public boolean isSuccessfull() {
        return successfull;
    }

    public void setSuccessfull(boolean successfull) {
        this.successfull = successfull;
    }

    public Challan getChallan() {
        return challan;
    }

    public void setChallan(Challan challan) {
        this.challan = challan;
    }
}


