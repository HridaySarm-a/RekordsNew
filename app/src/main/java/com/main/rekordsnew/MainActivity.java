package com.main.rekordsnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.main.rekordsnew.Accountant.AccountantHomeActivity;
import com.main.rekordsnew.Admin.AdminHomeActivity;
import com.main.rekordsnew.Client.ClientHomeActivity;
import com.main.rekordsnew.Client.POJO.ClientRoot;
import com.main.rekordsnew.LeafCollectors.LeafCollectorHomeActivity;
import com.main.rekordsnew.Login.LoginActivity;
import com.main.rekordsnew.MedicalStaff.MedicalHomeActivity;
import com.main.rekordsnew.Objects.Common;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.OnBoarding.OnboardingActivity;
import com.main.rekordsnew.Others.OtherModel;
import com.main.rekordsnew.Prefrences.Prefs;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();
    Runnable runnable = () -> {
        if (Prefs.getLoggedUser(this) != null) {
            Gson gson = new Gson();
            switch (Prefs.getLoggedUser(this).get(0)) {
                case "ADMIN":
                    Global.CURRENT_ADMIN_KEY = Prefs.getLoggedUser(this).get(1);
                    startActivity(new Intent(MainActivity.this, AdminHomeActivity.class));
                    finish();
                    break;
                case "CLIENT":
                    Global.CURRENT_USER_CLIENT = gson.fromJson(Prefs.getLoggedUser(this).get(1), ClientRoot.class);
                    startActivity(new Intent(MainActivity.this, ClientHomeActivity.class));
                    finish();
                    break;
                case "LEAF_COLLECTOR":
                    Global.CURRENT_OTHER_USER = gson.fromJson(Prefs.getLoggedUser(this).get(1), OtherModel.class);
                    startActivity(new Intent(MainActivity.this, LeafCollectorHomeActivity.class));
                    finish();
                    break;
                case "ACCOUNTANT":
                    Global.CURRENT_OTHER_USER = gson.fromJson(Prefs.getLoggedUser(this).get(1), OtherModel.class);
                    startActivity(new Intent(MainActivity.this, AccountantHomeActivity.class));
                    finish();
                    break;
                case "MEDICAL_STAFF":
                    Global.CURRENT_OTHER_USER = gson.fromJson(Prefs.getLoggedUser(this).get(1), OtherModel.class);
                    startActivity(new Intent(MainActivity.this, MedicalHomeActivity.class));
                    finish();
                    break;
                default:
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();

            }
        } else {
            startActivity(new Intent(MainActivity.this, OnboardingActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        handler.postDelayed(runnable, 2000);
    }
}