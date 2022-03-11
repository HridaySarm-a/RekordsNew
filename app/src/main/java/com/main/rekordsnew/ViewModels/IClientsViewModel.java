package com.main.rekordsnew.ViewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.main.rekordsnew.Client.POJO.ClientRoot;
import com.main.rekordsnew.Interface.IClientsCallbackListener;
import com.main.rekordsnew.Objects.Global;

import java.util.ArrayList;
import java.util.List;

public class IClientsViewModel extends ViewModel implements IClientsCallbackListener {

    private MutableLiveData<List<ClientRoot>> mutableLiveDataClients;
    private MutableLiveData<String> mutableLiveDataMessage;

    private IClientsCallbackListener clientsCallbackListener;

    public IClientsViewModel() {
        clientsCallbackListener = this;
    }

    public MutableLiveData<List<ClientRoot>> getMutableLiveDataClients() {
        if (mutableLiveDataClients == null) {
            mutableLiveDataClients = new MutableLiveData<>();
            LoadClients();
        }
        return mutableLiveDataClients;
    }

    public MutableLiveData<String> getMutableLiveDataMessage() {
        return mutableLiveDataMessage;
    }

    public void LoadClients() {
        List<ClientRoot> clientsList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference(Global.clientRef)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                            ClientRoot clientRoot = itemSnapshot.getValue(ClientRoot.class);
                            clientRoot.setKey(itemSnapshot.getKey());
                            clientsList.add(clientRoot);
                        }
                        clientsCallbackListener.onClientsLoadSuccess(clientsList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        clientsCallbackListener.onClientsLoadFailed(error.getMessage());
                    }
                });
    }

    @Override
    public void onClientsLoadSuccess(List<ClientRoot> clients) {
        mutableLiveDataClients.setValue(clients);
    }

    @Override
    public void onClientsLoadFailed(String message) {
        mutableLiveDataMessage = new MutableLiveData<>();
        mutableLiveDataMessage.setValue(message);
    }
}
