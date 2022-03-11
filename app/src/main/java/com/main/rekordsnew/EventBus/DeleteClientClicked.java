package com.main.rekordsnew.EventBus;


import com.main.rekordsnew.Client.POJO.ClientRoot;

public class DeleteClientClicked {
    private boolean isClicked;
    private ClientRoot clientRoot;

    public DeleteClientClicked(boolean isClicked, ClientRoot clientRoot) {
        this.isClicked = isClicked;
        this.clientRoot = clientRoot;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public ClientRoot getClientRoot() {
        return clientRoot;
    }

    public void setClientRoot(ClientRoot clientRoot) {
        this.clientRoot = clientRoot;
    }
}
