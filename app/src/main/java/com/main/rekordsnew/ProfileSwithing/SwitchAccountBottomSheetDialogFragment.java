package com.main.rekordsnew.ProfileSwithing;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.main.rekordsnew.Adapters.SwitchAccountClientAdapter;
import com.main.rekordsnew.Adapters.SwitchAccountOtherAdapter;
import com.main.rekordsnew.Client.POJO.ClientRoot;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.Others.OtherModel;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.FragmentSwitchAccountBottomSheetDialogBinding;

import java.util.ArrayList;
import java.util.List;

public class SwitchAccountBottomSheetDialogFragment extends BottomSheetDialogFragment {

    FragmentSwitchAccountBottomSheetDialogBinding binding;
    public String TYPE;

    public SwitchAccountBottomSheetDialogFragment(String TYPE) {
        this.TYPE = TYPE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSwitchAccountBottomSheetDialogBinding.inflate(getLayoutInflater());
        initRv(TYPE);
        return binding.getRoot();
    }
    private void initRv(String type) {
        switch (type){
            case "Client":
                showClients();
                break;
            case "Accountant":
                showAccountants(type);
                break;
            case "Medical":
                showMedical(type);
                break;
            case "Leaf":
                showLeaf(type);
                break;
        }
    }

    private void showLeaf(String type) {
        List<OtherModel> tempList = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference(Global.leafRef)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot itemSnap : snapshot.getChildren()){
                            OtherModel otherModel = itemSnap.getValue(OtherModel.class);
                            tempList.add(otherModel);
                        }
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                        binding.bssaRv.setLayoutManager(gridLayoutManager);
                        binding.bssaRv.setAdapter(new SwitchAccountOtherAdapter(getContext(),tempList,type));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void showMedical(String type) {
        List<OtherModel> tempList = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference(Global.medicalRef)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot itemSnap : snapshot.getChildren()){
                            OtherModel otherModel = itemSnap.getValue(OtherModel.class);
                            tempList.add(otherModel);
                        }
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                        binding.bssaRv.setLayoutManager(gridLayoutManager);
                        binding.bssaRv.setAdapter(new SwitchAccountOtherAdapter(getContext(),tempList,type));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void showAccountants(String type) {
        List<OtherModel> tempList = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference(Global.accountantRef)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot itemSnap : snapshot.getChildren()){
                            OtherModel otherModel = itemSnap.getValue(OtherModel.class);
                            otherModel.setKey(itemSnap.getKey());
                            tempList.add(otherModel);
                        }
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                        binding.bssaRv.setLayoutManager(gridLayoutManager);
                        binding.bssaRv.setAdapter(new SwitchAccountOtherAdapter(getContext(),tempList,type));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void showClients() {
        List<ClientRoot> tempList = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference(Global.clientRef)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot itemSnap : snapshot.getChildren()){
                            ClientRoot clientRoot = itemSnap.getValue(ClientRoot.class);
                            clientRoot.setKey(itemSnap.getKey());
                            tempList.add(clientRoot);
                        }
                        binding.bssaRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.bssaRv.setAdapter(new SwitchAccountClientAdapter(getContext(),tempList));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}