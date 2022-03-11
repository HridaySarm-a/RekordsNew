package com.main.rekordsnew.EventBus;

import com.main.rekordsnew.Others.OtherModel;

public class SwitchOtherClicked {
    boolean isClicked;
    OtherModel other;
    String TYPE;

    public SwitchOtherClicked(boolean isClicked, OtherModel other, String TYPE) {
        this.isClicked = isClicked;
        this.other = other;
        this.TYPE = TYPE;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public OtherModel getOther() {
        return other;
    }

    public void setOther(OtherModel other) {
        this.other = other;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }
}
