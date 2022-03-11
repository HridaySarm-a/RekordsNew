package com.main.rekordsnew.Interface;


import com.main.rekordsnew.Client.POJO.ClientRoot;

import java.util.List;

public interface IClientsCallbackListener {
    void onClientsLoadSuccess(List<ClientRoot> clients);
    void onClientsLoadFailed(String message);
}
