package com.main.rekordsnew.LeafCollectors;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.main.rekordsnew.Client.POJO.ClientRoot;
import com.main.rekordsnew.LeafCollectors.Model.LeafEntryModel;
import com.main.rekordsnew.Login.LoginActivity;
import com.main.rekordsnew.Objects.Common;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.Objects.StaticData;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.ActivityLeafEntryBinding;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LeafEntryActivity extends AppCompatActivity {

    private ActivityLeafEntryBinding binding;
    private String dateStr, grower = "";
    private float quantity;
    private int listPos;
    private static final String TAG = "LeafEntryActivity";
    final Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    private static List<PowerMenuItem> clientList = new ArrayList<>();
    private PowerMenu dropdown;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeafEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDatePicker();
        progressDialog = new ProgressDialog(LeafEntryActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        getList();
        initButtons();
    }


    private final OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            binding.leafSelectGrowerCvBtn.setText(clientList.get(position).getTitle());
            listPos = position;
            grower = String.valueOf(clientList.get(position).getTitle());
            dropdown.setSelectedPosition(position);
            dropdown.dismiss();
        }
    };

    private void initDatePicker() {
        date = (datePicker, i, i1, i2) -> {
            calendar.set(Calendar.YEAR, i);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.DAY_OF_MONTH, i2);
            updateLabel();
        };
    }

    private void initButtons() {
        binding.leafSubmitBtn.setOnClickListener(view -> {
            try {
                quantity = Float.parseFloat(binding.leafEnterQtyEdt.getText().toString());
                if (dateStr.equals("") || grower.equals("")) {
                    Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
                } else if (quantity == 0) {
                    Toast.makeText(this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
                } else {

                    uploadData();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        });

        binding.leafSelectGrowerCvBtn.setOnClickListener(view -> {
            dropdown.showAsDropDown(binding.leafSelectGrowerCvBtn);
        });

        binding.leafSelectDateCvBtn.setOnClickListener(view -> {
            new DatePickerDialog(LeafEntryActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

    }

    private void getList() {
        FirebaseDatabase.getInstance().getReference(Global.clientRef)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot itemSnapShot : snapshot.getChildren()) {
                                ClientRoot clientRoot = itemSnapShot.getValue(ClientRoot.class);
                                clientList.add(new PowerMenuItem(clientRoot.getPersonalDetails().getName(), clientRoot.getPersonalDetails().getName()));
                            }
                            dropdown = new PowerMenu.Builder(LeafEntryActivity.this)
                                    .addItemList(clientList)
                                    .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
                                    .setMenuRadius(10f) // sets the corner radius.
                                    .setMenuShadow(5f) // sets the shadow.
                                    .setTextColor(ContextCompat.getColor(LeafEntryActivity.this, R.color.white))
                                    .setTextGravity(Gravity.CENTER)
                                    .setTextSize(16)
                                    .setTextTypeface(Typeface.createFromAsset(
                                            getAssets(), "fonts/opensans_medium.ttf"))
                                    .setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
                                    .setSelectedTextColor(Color.WHITE)
                                    .setMenuColor(ContextCompat.getColor(LeafEntryActivity.this, R.color.greenish_grey))
                                    .setSelectedMenuColor(ContextCompat.getColor(LeafEntryActivity.this, R.color.colorPrimary))
                                    .setOnMenuItemClickListener(onMenuItemClickListener)
                                    .build();
                            progressDialog.dismiss();
                        } else {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, error.toString());
                        progressDialog.dismiss();
                    }
                });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dateStr = dateFormat.format(calendar.getTime());
        binding.aleDateTv.setText(dateStr);
        String[] splitStr = dateStr.split("/");
        dateStr = "";
        for (String s : splitStr) {
            dateStr = new StringBuilder().append(dateStr).append(s).toString();
        }
    }

    private void uploadData() {
        LeafEntryModel leafEntryModel = new LeafEntryModel(dateStr, grower, quantity, Global.CURRENT_OTHER_USER.getKey());
        FirebaseDatabase.getInstance().getReference(Global.collectionRef)
                .child(dateStr)
                .child(Global.CURRENT_OTHER_USER.getKey())
                .setValue(leafEntryModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseDatabase.getInstance().getReference(Global.leafRef)
                                .child(Global.CURRENT_OTHER_USER.getKey())
                                .child("Collections")
                                .child(dateStr)
                                .setValue(leafEntryModel)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LeafEntryActivity.this, LeafCollectorHomeActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(e -> {
                            Log.e(TAG, e.getLocalizedMessage());
                        });
                    }
                }).addOnFailureListener(e -> {
            Log.e(TAG, e.getLocalizedMessage());
        });

    }
}