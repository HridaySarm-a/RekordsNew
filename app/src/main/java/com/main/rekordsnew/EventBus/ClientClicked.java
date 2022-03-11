package com.main.rekordsnew.EventBus;


import com.main.rekordsnew.Client.POJO.ClientRoot;

public class ClientClicked {
     boolean clicked;
     ClientRoot client;

    public ClientClicked(boolean clicked, ClientRoot client) {
        this.clicked = clicked;
        this.client = client;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public ClientRoot getClient() {
        return client;
    }

    public void setClient(ClientRoot client) {
        this.client = client;
    }
}
