package com.main.rekordsnew.EventBus;


import com.main.rekordsnew.Client.POJO.ClientRoot;

public class EditClientClicked {

    boolean success;
    ClientRoot client;


    public EditClientClicked(boolean success, ClientRoot client) {
        this.success = success;
        this.client = client;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ClientRoot getClient() {
        return client;
    }

    public void setClient(ClientRoot client) {
        this.client = client;
    }
}
