package com.main.rekordsnew.Edit_Update;

import com.main.rekordsnew.Others.OtherModel;

public class DeleteOtherClicked {
    private boolean success;
    private OtherModel otherModel;
    private String ref;

    public DeleteOtherClicked(boolean success, OtherModel otherModel, String ref) {
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
