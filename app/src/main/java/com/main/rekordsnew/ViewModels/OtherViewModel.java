package com.main.rekordsnew.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.main.rekordsnew.Interface.IOthersCallbackListener;
import com.main.rekordsnew.Others.OtherModel;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class OtherViewModel extends ViewModel implements IOthersCallbackListener, ViewModelProvider.Factory  {
    private MutableLiveData<List<OtherModel>> othersMutableLiveData;
    private IOthersCallbackListener iOthersCallbackListener;
    private String ref;
    private Application application;

    public OtherViewModel(Application application,String ref) {
        iOthersCallbackListener = this;
        this.ref = ref;
    }

    public MutableLiveData<List<OtherModel>> getOthersMutableLiveData() {
        if (othersMutableLiveData == null) {
            othersMutableLiveData = new MutableLiveData<>();
            LoadOthers(ref);
        }
        return othersMutableLiveData;
    }

    public void LoadOthers(String ref) {
        List<OtherModel> tempList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference(ref).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapShot : snapshot.getChildren()) {
                        OtherModel otherModel = itemSnapShot.getValue(OtherModel.class);
                        tempList.add(otherModel);
                    }
                }
                iOthersCallbackListener.onOthersLoadSuccess(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("OVM", error.toString());
            }
        });
    }

    @Override
    public void onOthersLoadSuccess(List<OtherModel> otherModelList) {
        othersMutableLiveData.setValue(otherModelList);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new OtherViewModel(application,ref);
    }
}
