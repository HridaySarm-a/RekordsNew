package com.main.rekordsnew.ViewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.main.rekordsnew.Interface.IOthersCallbackListener;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.Others.OtherModel;

import java.util.ArrayList;
import java.util.List;

public class LeafViewModel extends ViewModel implements IOthersCallbackListener {

    private MutableLiveData<List<OtherModel>> mutableLiveData;
    private IOthersCallbackListener othersCallbackListener;

    public LeafViewModel() {
        othersCallbackListener = this;
    }

    public MutableLiveData<List<OtherModel>> getMutableLiveData() {
        if (mutableLiveData == null){
            mutableLiveData = new MutableLiveData<>();
            LoadOtherData();
        }
        return mutableLiveData;
    }

    public void LoadOtherData() {
        List<OtherModel> tempList = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference(Global.leafRef)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot itemSnap : snapshot.getChildren()){
                            OtherModel otherModel = itemSnap.getValue(OtherModel.class);
                            otherModel.setKey(itemSnap.getKey());
                            tempList.add(otherModel);
                        }
                        othersCallbackListener.onOthersLoadSuccess(tempList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public void onOthersLoadSuccess(List<OtherModel> otherModelList) {
        mutableLiveData.setValue(otherModelList);
    }
}