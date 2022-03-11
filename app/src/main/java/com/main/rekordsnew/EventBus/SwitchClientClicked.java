package com.main.rekordsnew.EventBus;


import com.main.rekordsnew.Client.POJO.ClientRoot;

public class SwitchClientClicked {
    boolean isClicked;
    ClientRoot client;

    public SwitchClientClicked(boolean isClicked, ClientRoot client) {
        this.isClicked = isClicked;
        this.client = client;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public ClientRoot getClient() {
        return client;
    }

    public void setClient(ClientRoot client) {
        this.client = client;
    }
}
