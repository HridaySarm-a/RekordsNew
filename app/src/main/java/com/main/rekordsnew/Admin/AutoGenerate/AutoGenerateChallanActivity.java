package com.main.rekordsnew.Admin.AutoGenerate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.main.rekordsnew.Adapters.AutoChallanAdapters.AutoChallanAdapter;
import com.main.rekordsnew.Admin.AutoGenerate.Models.AutoChallanModel;
import com.main.rekordsnew.EventBus.ACAClicked;
import com.main.rekordsnew.LeafCollectors.Model.LeafEntryModel;
import com.main.rekordsnew.Objects.Global;
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
    private ProgressDialog progressDialog;
    private static final String TAG = "AutoGenerateChallanActi";
    PopupWindow popupWindow;
    AutoChallanAdapter autoChallanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAutoGenerateChallanBinding.inflate(getLayoutInflater());
        date = getIntent().getStringExtra("Date");
        Log.d(TAG, date);
        progressDialog = new ProgressDialog(AutoGenerateChallanActivity.this);
        progressDialog.setMessage("Generating challan");
        progressDialog.show();
        generateChallan();
        setContentView(binding.getRoot());
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
        binding.aagcRv.setAdapter(new AutoChallanAdapter(AutoGenerateChallanActivity.this, autoChallanModelList));
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
        popupWindow = new PopupWindow(AutoGenerateChallanActivity.this);
        View popUpView = LayoutInflater.from(AutoGenerateChallanActivity.this).inflate(R.layout.challan_input_layout, null);
        popupWindow.setContentView(popUpView);

        switch (type) {
            case "PERCENTAGE":
                showPopupImpl("Edit Percentage", popUpView, position, autoChallanModel);
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
        popupWindow.showAsDropDown(popUpView, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.update();
        TextInputEditText inputEdt = popUpView.findViewById(R.id.cil_input_edt);
        ImageView cancelBtn = popUpView.findViewById(R.id.cil_cancel_btn);
        TextView title = popUpView.findViewById(R.id.cil_title);
        title.setText(info);
        MaterialButton updateBtn = popUpView.findViewById(R.id.cil_update_btn);
        cancelBtn.setOnClickListener(view -> {
            popupWindow.dismiss();
        });

        updateBtn.setOnClickListener(view -> {
            switch (info) {
                case "PERCENTAGE":
                    autoChallanModel.setPercentage(Float.parseFloat(inputEdt.getText().toString()));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "MINUS":
                    autoChallanModel.setMinus(Float.parseFloat(inputEdt.getText().toString()));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "NET":
                    autoChallanModel.setNet(Float.parseFloat(inputEdt.getText().toString()));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "RATE":
                    autoChallanModel.setRate(Float.parseFloat(inputEdt.getText().toString()));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "AMOUNT":
                    autoChallanModel.setAmount(Float.parseFloat(inputEdt.getText().toString()));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "ADMIN":
                    autoChallanModel.setAdmin(Float.parseFloat(inputEdt.getText().toString()));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "COMM":
                    autoChallanModel.setComm(Float.parseFloat(inputEdt.getText().toString()));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "CARRYING":
                    autoChallanModel.setCarrying(Float.parseFloat(inputEdt.getText().toString()));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "CESS":
                    autoChallanModel.setCess(Float.parseFloat(inputEdt.getText().toString()));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "MISC":
                    autoChallanModel.setMisc(Float.parseFloat(inputEdt.getText().toString()));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "TOTAL":
                    autoChallanModel.setTotal(Float.parseFloat(inputEdt.getText().toString()));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;
                case "NET_AMOUNT":
                    autoChallanModel.setNetAmount(Float.parseFloat(inputEdt.getText().toString()));
                    autoChallanAdapter.updateData(position, autoChallanModel);
                    break;


            }
        });

    }


}