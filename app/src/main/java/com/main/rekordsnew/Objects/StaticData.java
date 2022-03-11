package com.main.rekordsnew.Objects;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.main.rekordsnew.Client.POJO.ClientRoot;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.ArrayList;
import java.util.List;

public class StaticData {
    private static List<PowerMenuItem> dropDownList = new ArrayList<>();
    private static List<PowerMenuItem> rolesList = new ArrayList<>();
    private static final String TAG = "StaticData";

    public static List<PowerMenuItem> getDropDownList() {
        dropDownList.add(new PowerMenuItem("Admin", "Admin"));
        dropDownList.add(new PowerMenuItem("Client", "Client"));
        dropDownList.add(new PowerMenuItem("Medical Staff", "Medical Staff"));
        dropDownList.add(new PowerMenuItem("Accountant", "Accountant"));
        dropDownList.add(new PowerMenuItem("Leaf Collector", "Leaf Collector"));
        return dropDownList;
    }

    public static List<PowerMenuItem> getRolesList() {
        rolesList.add(new PowerMenuItem("Leaf Collector", "Leaf Collector"));
        rolesList.add(new PowerMenuItem("Accountant", "Accountant"));
        rolesList.add(new PowerMenuItem("Medical Staff", "Medical Staff"));
        return rolesList;
    }


}
