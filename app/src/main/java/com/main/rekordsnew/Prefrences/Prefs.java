package com.main.rekordsnew.Prefrences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.main.rekordsnew.Objects.Common;

import java.util.ArrayList;
import java.util.List;

public class Prefs {
    public static boolean saveLoggedUser(Context context, String type, String userData) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Common.USER_TYPE, type);
        editor.putString(Common.USER_DATA, userData);
        return editor.commit();
    }

    public static List<String> getLoggedUser(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences.getString(Common.USER_TYPE, null) != null){
            List<String> tempData = new ArrayList<>();
            tempData.add(sharedPreferences.getString(Common.USER_TYPE, null));
            tempData.add(sharedPreferences.getString(Common.USER_DATA, null));
            return tempData;
        }else {
            return null;
        }
    }
}
