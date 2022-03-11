package com.main.rekordsnew.EventBus;

public class CallClientClicked {
    private boolean clicked;
    private String number;

    public CallClientClicked(boolean clicked, String number) {
        this.clicked = clicked;
        this.number = number;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
