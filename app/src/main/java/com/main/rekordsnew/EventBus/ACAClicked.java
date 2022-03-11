package com.main.rekordsnew.EventBus;

import com.main.rekordsnew.Admin.AutoGenerate.Models.AutoChallanModel;

public class ACAClicked {

    private boolean success;
    private int position;
    AutoChallanModel autoChallanModel;
    private String Type;

    public ACAClicked(boolean success, int position, AutoChallanModel autoChallanModel, String type) {
        this.success = success;
        this.position = position;
        this.autoChallanModel = autoChallanModel;
        Type = type;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public AutoChallanModel getAutoChallanModel() {
        return autoChallanModel;
    }

    public void setAutoChallanModel(AutoChallanModel autoChallanModel) {
        this.autoChallanModel = autoChallanModel;
    }
}
