package com.main.rekordsnew.EventBus;


import com.main.rekordsnew.Others.OtherModel;

public class EditOtherClicked {
    boolean success;
    OtherModel otherModel;
    String ref;


    public EditOtherClicked(boolean success, OtherModel otherModel, String ref) {
        this.success = success;
        this.otherModel = otherModel;
        this.ref = ref;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public OtherModel getOtherModel() {
        return otherModel;
    }

    public void setOtherModel(OtherModel otherModel) {
        this.otherModel = otherModel;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
