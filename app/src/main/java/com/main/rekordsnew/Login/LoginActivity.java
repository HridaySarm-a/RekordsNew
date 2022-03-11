package com.main.rekordsnew.Login;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.main.rekordsnew.Admin.AdminHomeActivity;
import com.main.rekordsnew.Admin.POJO.AdminCredsModel;
import com.main.rekordsnew.Client.ClientHomeActivity;
import com.main.rekordsnew.Client.POJO.ClientRoot;
import com.main.rekordsnew.LeafCollectors.LeafCollectorHomeActivity;
import com.main.rekordsnew.Objects.Common;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.Objects.MrToast;
import com.main.rekordsnew.Objects.StaticData;
import com.main.rekordsnew.Others.OtherModel;
import com.main.rekordsnew.Prefrences.Prefs;
import com.main.rekordsnew.R;
import com.main.rekordsnew.Register.RegisterClientActivity;
import com.main.rekordsnew.databinding.ActivityLoginBinding;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private PowerMenu powerMenu;
    private ProgressDialog progressDialog;
    private static final String TAG = "LoginActivity";
    private int listPos = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_PAN);
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating");
        progressDialog.setMessage("Please Wait...");
        initDropdown();
        initButtons();
    }

    private void initButtons() {
        binding.selectLoginCv.setOnClickListener(view -> {
            powerMenu.showAsDropDown(binding.selectLoginCv);
        });

        binding.loginBtn.setOnClickListener(view -> {
            switch (listPos) {
                case 99:
                    MrToast.showWarning(LoginActivity.this, "OOPS!", "Please select a login type to continue");
                    break;
                case 0:
                    checkAdminInfo();
                    break;
                case 1:
                    checkClientInfo();
                    break;
                case 2:
                    checkMedicalStaff();
                    break;
                case 3:
                    checkAccountant();
                    break;
                case 4:
                    checkLeafCollector();
                    break;
                default:
                    MrToast.showError(LoginActivity.this, "Error", "Please select a login type to continue");
                    break;

            }
        });

        binding.registerClientTvBtn.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterClientActivity.class));

        });
    }

    private void checkLeafCollector() {
        final boolean[] found = {false};
        FirebaseDatabase.getInstance().getReference(Global.leafRef)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                                OtherModel otherModel = itemSnapshot.getValue(OtherModel.class);
                                if (otherModel.getPhone().equals(binding.loginPhoneEdt.getText().toString())) {
                                    if (otherModel.getPassword().equals(binding.loginPasswordEdt.getText().toString())) {
                                        found[0] = true;
                                        otherModel.setKey(itemSnapshot.getKey());
                                        Global.CURRENT_OTHER_USER = otherModel;
                                        Gson gson = new Gson();
                                        String json = gson.toJson(otherModel);
                                        progressDialog.dismiss();
                                        Prefs.saveLoggedUser(LoginActivity.this, "LEAF_COLLECTOR", json);
                                        startActivity(new Intent(LoginActivity.this, LeafCollectorHomeActivity.class));
                                        finish();
                                    }
                                }
                            }
                            if (!found[0]) {
                                Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG,error.getMessage());
                    }
                });
    }

    private void checkAccountant() {
        MrToast.showInfo(LoginActivity.this, "Hello", "This feature will be available soon");
    }

    private void checkMedicalStaff() {
        MrToast.showInfo(LoginActivity.this, "Hello", "This feature will be available soon");
    }

    private void checkClientInfo() {
        if (binding.loginPhoneEdt.getText().toString().length() == 10) {
            progressDialog.show();
            final boolean[] found = {false};
            FirebaseDatabase.getInstance().getReference(Global.clientRef)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                                ClientRoot clientRoot = itemSnapshot.getValue(ClientRoot.class);
                                if (clientRoot.getPersonalDetails().getPhone().equals(binding.loginPhoneEdt.getText().toString())) {
                                    if (clientRoot.getPersonalDetails().getPassword().equals(binding.loginPasswordEdt.getText().toString())) {
                                        found[0] = true;
                                        clientRoot.setKey(itemSnapshot.getKey());
                                        Global.CURRENT_USER_CLIENT = clientRoot;
                                        Gson gson = new Gson();
                                        String json = gson.toJson(clientRoot);
                                        progressDialog.dismiss();
                                        Prefs.saveLoggedUser(LoginActivity.this, "CLIENT", json);
                                        startActivity(new Intent(LoginActivity.this, ClientHomeActivity.class));
                                        finish();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            if (found[0]) {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "No account associated with this phone number found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.dismiss();
                        }
                    });
        } else {
            Toast.makeText(LoginActivity.this, "Incorrect credentials", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAdminInfo() {
        if (binding.loginPhoneEdt.getText().toString().length() != 10) {
            MrToast.showError(LoginActivity.this, "Invalid Phone Number", "Please enter a valid phone number");
        } else if (binding.loginPasswordEdt.getText().toString().length() < 4) {
            MrToast.showError(LoginActivity.this, "Invalid Password", "Please enter correct password");
        } else {
            checkPhoneAndPassword();
        }
    }

    private void checkPhoneAndPassword() {
        progressDialog.show();
        FirebaseDatabase.getInstance()
                .getReference("AdminCreds")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            AdminCredsModel adminCredsModel = snapshot.getValue(AdminCredsModel.class);
                            if (binding.loginPhoneEdt.getText().toString().equals(adminCredsModel.getPhone())) {
                                if (binding.loginPasswordEdt.getText().toString().equals(adminCredsModel.getPassword())) {
                                    progressDialog.dismiss();
                                    Prefs.saveLoggedUser(LoginActivity.this, "ADMIN", adminCredsModel.getPhone());
                                    startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
                                    finish();
                                } else {
                                    progressDialog.dismiss();
                                    MrToast.showError(LoginActivity.this, "Failed!", "Invalid Password");
                                }
                            } else {
                                progressDialog.dismiss();
                                MrToast.showError(LoginActivity.this, "Failed!", "No account associated with the phone number found");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                        MrToast.showError(LoginActivity.this, "Failed!", error.getMessage());
                    }
                });
    }

    private void initDropdown() {
        powerMenu = new PowerMenu.Builder(this)
                .addItemList(StaticData.getDropDownList())
                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
                .setMenuRadius(10f) // sets the corner radius.
                .setMenuShadow(5f) // sets the shadow.
                .setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.white))
                .setTextGravity(Gravity.CENTER)
                .setTextSize(16)
                .setTextTypeface(Typeface.createFromAsset(
                        getAssets(), "fonts/opensans_medium.ttf"))
                .setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
                .setSelectedTextColor(Color.WHITE)
                .setMenuColor(ContextCompat.getColor(LoginActivity.this, R.color.greenish_grey))
                .setSelectedMenuColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build();

    }

    private final OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            binding.loginTypeTv.setText(StaticData.getDropDownList().get(position).getTitle());
            listPos = position;
            powerMenu.setSelectedPosition(position);
            powerMenu.dismiss();
        }
    };

}