package com.main.rekordsnew.Admin.AutoGenerate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.main.rekordsnew.Adapters.AutoChallanAdapters.AutoChallanAdapter;
import com.main.rekordsnew.Admin.AddChallan.GenerateChallanActivity;
import com.main.rekordsnew.Admin.AutoGenerate.Models.AutoChallanModel;
import com.main.rekordsnew.Client.POJO.Challan;
import com.main.rekordsnew.Client.POJO.Entry;
import com.main.rekordsnew.EventBus.ACAClicked;
import com.main.rekordsnew.LeafCollectors.Model.LeafEntryModel;
import com.main.rekordsnew.Objects.Common;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.Others.OtherModel;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.ActivityAutoGenerateChallanBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class AutoGenerateChallanActivity extends AppCompatActivity {

    private ActivityAutoGenerateChallanBinding binding;
    String date;
    float currentTeaPrice;
    int currentChallanNo;
    private ProgressDialog progressDialog;
    private static final String TAG = "AutoGenerateChallanActi";
    AlertDialog popupWindow, acbliAlertDialog;
    AutoChallanAdapter autoChallanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAutoGenerateChallanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        date = getIntent().getStringExtra("Date");

        Log.d(TAG, date);
        progressDialog = new ProgressDialog(AutoGenerateChallanActivity.this);
        progressDialog.setMessage("Generating challan");
        progressDialog.show();
        getCurrentChallanNo();
    }

    private void getCurrentChallanNo() {
        FirebaseDatabase.getInstance().getReference(Common.CURRENT_CHALLAN_NO_REF)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String challanNo = snapshot.getValue(String.class);
                            if (challanNo != null) {
                                currentChallanNo = Integer.parseInt(challanNo);
                            }
                            getCurrentTeaPrice();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG,error.getMessage());
                    }
                });
    }

    private void getCurrentTeaPrice() {
        FirebaseDatabase.getInstance().getReference(Common.CURRENT_TEA_PRICE_REF)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String teaPrice = snapshot.getValue(String.class);
                            if (teaPrice != null) {
                                currentTeaPrice = Float.parseFloat(teaPrice);
                            }
                            generateChallan();
                            initButtons();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG,error.getMessage());
                    }
                });
    }

    private void initButtons() {

        binding.aagcFilterBtn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AutoGenerateChallanActivity.this);
            View view2 = LayoutInflater.from(this).inflate(R.layout.auto_challan_batch_list_item, null);
            CardView prcntge, minus, net, rate, admin, comm, carrying, cess, misc;
            prcntge = view2.findViewById(R.id.acbli_percent);
            prcntge.setOnClickListener(viewZ -> {
                acbliAlertDialog.dismiss();
                changeOverallData("PERCENTAGE");
            });
            minus = view2.findViewById(R.id.acbli_minus);
            minus.setOnClickListener(viewZ -> {
                acbliAlertDialog.dismiss();
                changeOverallData("MINUS");
            });
            net = view2.findViewById(R.id.acbli_net);
            net.setOnClickListener(viewZ -> {
                acbliAlertDialog.dismiss();
                changeOverallData("NET");
            });
            rate = view2.findViewById(R.id.acbli_rate);
            rate.setOnClickListener(viewZ -> {
                acbliAlertDialog.dismiss();
                changeOverallData("RATE");
            });
            admin = view2.findViewById(R.id.acbli_admin);
            admin.setOnClickListener(viewZ -> {
                acbliAlertDialog.dismiss();
                changeOverallData("ADMIN");
            });
            comm = view2.findViewById(R.id.acbli_comm);
            comm.setOnClickListener(viewZ -> {
                acbliAlertDialog.dismiss();
                changeOverallData("COMM");
            });
            carrying = view2.findViewById(R.id.acbli_carrying);
            carrying.setOnClickListener(viewZ -> {
                acbliAlertDialog.dismiss();
                changeOverallData("CARRYING");
            });
            cess = view2.findViewById(R.id.acbli_cess);
            cess.setOnClickListener(viewZ -> {
                acbliAlertDialog.dismiss();
                changeOverallData("CESS");
            });
            misc = view2.findViewById(R.id.acbli_misc);
            misc.setOnClickListener(viewZ -> {
                acbliAlertDialog.dismiss();
                changeOverallData("MISC");
            });

            builder.setView(view2);
            acbliAlertDialog = builder.create();
            acbliAlertDialog.show();

        });

        binding.autoGenerateSubmitBtn.setOnClickListener(view -> {
            List<AutoChallanModel> tempList =autoChallanAdapter.getList();
            Challan challan = new Challan();
            List<Entry> entries = new ArrayList<>();
            float percent;

            List<OtherModel> collectors;
            String pdfUrl,csvUrl;
            float totalQty,totalAmt;
            for (int i=0;i<tempList.size();i++){
                Entry entry = new Entry();

            }
            Intent intent = new Intent(AutoGenerateChallanActivity.this, GenerateChallanActivity.class);

        });
    }

    private void changeOverallData(String info) {
        List<AutoChallanModel> tempList = autoChallanAdapter.getList();
        AlertDialog.Builder builder = new AlertDialog.Builder(AutoGenerateChallanActivity.this);
        View popUpView = LayoutInflater.from(AutoGenerateChallanActivity.this).inflate(R.layout.challan_input_layout, null);
        TextInputEditText inputEdt = popUpView.findViewById(R.id.cil_input_edt);
        ImageView cancelBtn = popUpView.findViewById(R.id.cil_cancel_btn);
        TextView title = popUpView.findViewById(R.id.cil_title);
        title.setText(info);
        cancelBtn.setOnClickListener(view -> {
            popupWindow.dismiss();
        });

        builder.setView(popUpView);
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.setPositiveButton("UPDATE", (dialogInterface, i) -> {
            float num = Float.parseFloat(inputEdt.getText().toString());

            switch (info) {
                case "PERCENTAGE":
                    for (int k = 0; k < tempList.size(); k++) {
                        tempList.get(k).setPercentage(num);
                    }
                    updateList("Edit PERCENTAGE", tempList);
                    Toast.makeText(this, "Editing percentage", Toast.LENGTH_SHORT).show();
                    break;
                case "MINUS":
                    for (int k = 0; k < tempList.size(); k++) {
                        tempList.get(k).setMinus(num);
                    }
                    updateList("Edit MINUS", tempList);
                    break;
                case "NET":
                    for (int k = 0; k < tempList.size(); k++) {
                        tempList.get(k).setNet(num);
                    }
                    updateList("Edit NET", tempList);
                    break;
                case "RATE":
                    for (int k = 0; k < tempList.size(); k++) {
                        tempList.get(k).setRate(num);
                    }
                    updateList("Edit RATE", tempList);
                    break;
                case "ADMIN":
                    for (int k = 0; k < tempList.size(); k++) {
                        tempList.get(k).setAdmin(num);
                    }
                    updateList("Edit ADMIN", tempList);
                    break;
                case "COMM":
                    for (int k = 0; k < tempList.size(); k++) {
                        tempList.get(k).setComm(num);
                    }
                    updateList("Edit COMM", tempList);
                    break;
                case "CARRYING":
                    for (int k = 0; k < tempList.size(); k++) {
                        tempList.get(k).setCarrying(num);
                    }
                    updateList("Edit CARRYING", tempList);
                    break;
                case "CESS":
                    for (int k = 0; k < tempList.size(); k++) {
                        tempList.get(k).setCess(num);
                    }
                    updateList("Edit CESS", tempList);
                    break;
                case "MISC":
                    for (int k = 0; k < tempList.size(); k++) {
                        tempList.get(k).setPercentage(num);
                    }
                    updateList("Edit MISC", tempList);
                    break;
            }

        });

        popupWindow = builder.create();
        popupWindow.show();

    }

    private void updateList(String edit_net_amount, List<AutoChallanModel> tempList) {
        autoChallanAdapter.updateList(tempList);
    }

    private void generateChallan() {
        List<LeafEntryModel> tempList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference(Global.collectionRef)
                .child(date)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot itemSnapShot : snapshot.getChildren()) {
                            LeafEntryModel leafEntryModel = itemSnapShot.getValue(LeafEntryModel.class);
                            leafEntryModel.setKey(itemSnapShot.getKey());
                            tempList.add(leafEntryModel);
                        }
                        initRecyclerView(tempList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, error.getMessage());
                    }
                });
    }

    private void initRecyclerView(List<LeafEntryModel> tempList) {
        progressDialog.dismiss();
        List<AutoChallanModel> autoChallanModelList = new ArrayList<>();
        for (int i = 0; i < tempList.size(); i++) {
            AutoChallanModel autoChallanModel = new AutoChallanModel();
            autoChallanModel.setLocal((int) tempList.get(i).getQuantity());
            autoChallanModelList.add(autoChallanModel);
        }
        binding.aagcRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        autoChallanAdapter = new AutoChallanAdapter(AutoGenerateChallanActivity.this, autoChallanModelList);
        binding.aagcRv.setAdapter(autoChallanAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onChangePercentageClicked(ACAClicked event) {
        if (event.isSuccess()) {
            showPopup(event.getPosition(), event.getAutoChallanModel(), event.getType());
            EventBus.getDefault().removeStickyEvent(ACAClicked.class);
        }
    }

    private void showPopup(int position, AutoChallanModel autoChallanModel, String type) {
        View popUpView = LayoutInflater.from(AutoGenerateChallanActivity.this).inflate(R.layout.challan_input_layout, null);
        switch (type) {
            case "PERCENTAGE":
                showPopupImpl("Edit PERCENTAGE", popUpView, position, autoChallanModel);
                Toast.makeText(this, "Editing percentage", Toast.LENGTH_SHORT).show();
                break;
            case "MINUS":
                showPopupImpl("Edit MINUS", popUpView, position, autoChallanModel);
                break;
            case "NET":
                showPopupImpl("Edit NET", popUpView, position, autoChallanModel);
                break;
            case "RATE":
                showPopupImpl("Edit RATE", popUpView, position, autoChallanModel);
                break;
            case "AMOUNT":
                showPopupImpl("Edit AMOUNT", popUpView, position, autoChallanModel);
                break;
            case "ADMIN":
                showPopupImpl("Edit ADMIN", popUpView, position, autoChallanModel);
                break;
            case "COMM":
                showPopupImpl("Edit COMM", popUpView, position, autoChallanModel);
                break;
            case "CARRYING":
                showPopupImpl("Edit CARRYING", popUpView, position, autoChallanModel);
                break;
            case "CESS":
                showPopupImpl("Edit CESS", popUpView, position, autoChallanModel);
                break;
            case "MISC":
                showPopupImpl("Edit MISC", popUpView, position, autoChallanModel);
                break;
            case "TOTAL":
                showPopupImpl("Edit TOTAL", popUpView, position, autoChallanModel);
                break;
            case "NET_AMOUNT":
                showPopupImpl("Edit NET_AMOUNT", popUpView, position, autoChallanModel);
                break;

        }
    }

    private void showPopupImpl(String info, View popUpView, int position, AutoChallanModel autoChallanModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AutoGenerateChallanActivity.this);
        TextInputEditText inputEdt = popUpView.findViewById(R.id.cil_input_edt);
        ImageView cancelBtn = popUpView.findViewById(R.id.cil_cancel_btn);
        TextView title = popUpView.findViewById(R.id.cil_title);
        title.setText(info);
        cancelBtn.setOnClickListener(view -> {
            popupWindow.dismiss();
        });

        builder.setView(popUpView);
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        builder.setPositiveButton("Update", (dialogInterface, i) -> {
            switch (info) {
                case "Edit PERCENTAGE":
                    Toast.makeText(this, "Its percentage", Toast.LENGTH_SHORT).show();
                    autoChallanModel.setPercentage(Float.parseFloat(inputEdt.getText().toString()));
                    Log.d(TAG, String.valueOf(Float.parseFloat(inputEdt.getText().toString())));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "Edit MINUS":
                    autoChallanModel.setMinus(Float.parseFloat(inputEdt.getText().toString()));
                    Log.d(TAG, String.valueOf(Float.parseFloat(inputEdt.getText().toString())));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "Edit NET":
                    autoChallanModel.setNet(Float.parseFloat(inputEdt.getText().toString()));
                    Log.d(TAG, String.valueOf(Float.parseFloat(inputEdt.getText().toString())));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "Edit RATE":
                    autoChallanModel.setRate(Float.parseFloat(inputEdt.getText().toString()));
                    Log.d(TAG, String.valueOf(Float.parseFloat(inputEdt.getText().toString())));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "Edit AMOUNT":
                    autoChallanModel.setAmount(Float.parseFloat(inputEdt.getText().toString()));
                    Log.d(TAG, String.valueOf(Float.parseFloat(inputEdt.getText().toString())));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "Edit ADMIN":
                    autoChallanModel.setAdmin(Float.parseFloat(inputEdt.getText().toString()));
                    Log.d(TAG, String.valueOf(Float.parseFloat(inputEdt.getText().toString())));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "Edit COMM":
                    autoChallanModel.setComm(Float.parseFloat(inputEdt.getText().toString()));
                    Log.d(TAG, String.valueOf(Float.parseFloat(inputEdt.getText().toString())));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "Edit CARRYING":
                    autoChallanModel.setCarrying(Float.parseFloat(inputEdt.getText().toString()));
                    Log.d(TAG, String.valueOf(Float.parseFloat(inputEdt.getText().toString())));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "Edit CESS":
                    autoChallanModel.setCess(Float.parseFloat(inputEdt.getText().toString()));
                    Log.d(TAG, String.valueOf(Float.parseFloat(inputEdt.getText().toString())));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "Edit MISC":
                    autoChallanModel.setMisc(Float.parseFloat(inputEdt.getText().toString()));
                    Log.d(TAG, String.valueOf(Float.parseFloat(inputEdt.getText().toString())));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "Edit TOTAL":
                    autoChallanModel.setTotal(Float.parseFloat(inputEdt.getText().toString()));
                    Log.d(TAG, String.valueOf(Float.parseFloat(inputEdt.getText().toString())));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "Edit NET_AMOUNT":
                    autoChallanModel.setNetAmount(Float.parseFloat(inputEdt.getText().toString()));
                    Log.d(TAG, String.valueOf(Float.parseFloat(inputEdt.getText().toString())));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
            }
        });
        popupWindow = builder.create();
        popupWindow.show();

    }


}