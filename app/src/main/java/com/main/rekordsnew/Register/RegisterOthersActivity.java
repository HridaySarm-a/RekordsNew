package com.main.rekordsnew.Register;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.main.rekordsnew.Admin.AdminHomeActivity;
import com.main.rekordsnew.Login.LoginActivity;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.Objects.StaticData;
import com.main.rekordsnew.Others.OtherModel;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.ActivityRegisterOthersBinding;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import java.util.UUID;

public class RegisterOthersActivity extends AppCompatActivity {


    private ActivityRegisterOthersBinding binding;
    public static final int LEAF = 0;
    public static final int ACCOUNTANT = 1;
    public static final int MEDICAL = 2;

    private String ROLE = "";
    private ProgressDialog loadingDialog;
    private Uri imageUri;

    StorageReference storageReference;
    private PowerMenu powerMenu;
    TextView progressTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterOthersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initButtons();
        initOthers();
        initDropdown();
    }

    private void initOthers() {
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("Uploading data...");
        storageReference = FirebaseStorage.getInstance().getReference("Other Images");
    }

    ActivityResultLauncher<Intent> imageResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        binding.addOtherImg.setImageURI(data.getData());
                        imageUri = data.getData();
                    }
                }
            });

    private void initButtons() {

        binding.addImageBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/");
            imageResultLauncher.launch(intent);
        });

        binding.aoUploadDataBtn.setOnClickListener(view -> {
            if (ROLE.equals("")) {
                Snackbar.make(view, "Please select a role", Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show();
            } else if (imageUri != null) {
                loadingDialog.show();
                StorageReference filereference = storageReference.child(System.currentTimeMillis() +
                        "." + getFileExtension(imageUri));
                filereference.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            filereference.getDownloadUrl().addOnSuccessListener(uri -> {
                                String uniqueID = UUID.randomUUID().toString();
                                OtherModel roleData = new OtherModel();
                                roleData.setName(binding.aoName.getText().toString());
                                roleData.setPhone(binding.aoPhone.getText().toString());
                                roleData.setPassword(binding.aoPassword.getText().toString());
                                roleData.setPan(binding.aoPan.getText().toString());
                                roleData.setAddress(binding.aoHomeAddress.getText().toString());
                                roleData.setAadhar(binding.aoAadhar.getText().toString());
                                roleData.setImage(uri.toString());
                                roleData.setKey(uniqueID);
                                FirebaseDatabase.getInstance().getReference(ROLE)
                                        .child(uniqueID)
                                        .setValue(roleData)
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                loadingDialog.dismiss();
                                                Toast.makeText(RegisterOthersActivity.this, "" + ROLE + "with image added successfully", Toast.LENGTH_SHORT).show();
                                                showOptionDialog();
                                            }
                                        }).addOnFailureListener(e -> {
                                    loadingDialog.dismiss();
                                    Toast.makeText(RegisterOthersActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });

                            });
                        }).addOnFailureListener(e -> Log.d("Error", e.getMessage())).addOnProgressListener(snapshot -> {
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progressTv.setText("Uploading  " + (int) progress + "%");
                });
            } else {
                loadingDialog.show();
                OtherModel roleData = new OtherModel();
                roleData.setName(binding.aoName.getText().toString());
                roleData.setPhone(binding.aoPhone.getText().toString());
                roleData.setPassword(binding.aoPassword.getText().toString());
                roleData.setPan(binding.aoPan.getText().toString());
                roleData.setAddress(binding.aoHomeAddress.getText().toString());
                roleData.setAadhar(binding.aoAadhar.getText().toString());
                String uniqueID = UUID.randomUUID().toString();
                roleData.setKey(uniqueID);
                FirebaseDatabase.getInstance().getReference(ROLE)
                        .child(uniqueID)
                        .setValue(roleData)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterOthersActivity.this, "" + ROLE + " added successfully", Toast.LENGTH_SHORT).show();
                                loadingDialog.dismiss();
                                showOptionDialog();
                            }
                        }).addOnFailureListener(e -> {
                    Toast.makeText(RegisterOthersActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                });
            }
        });

        binding.selectRoleCv.setOnClickListener(view -> {
            powerMenu.showAsDropDown(binding.selectRoleCv);
        });

    }

    private void initDropdown() {
        powerMenu = new PowerMenu.Builder(this)
                .addItemList(StaticData.getRolesList())
                .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
                .setMenuRadius(10f) // sets the corner radius.
                .setMenuShadow(5f) // sets the shadow.
                .setTextColor(ContextCompat.getColor(RegisterOthersActivity.this, R.color.white))
                .setTextGravity(Gravity.CENTER)
                .setTextSize(16)
                .setTextTypeface(Typeface.createFromAsset(
                        getAssets(), "fonts/opensans_medium.ttf"))
                .setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
                .setSelectedTextColor(Color.WHITE)
                .setMenuColor(ContextCompat.getColor(RegisterOthersActivity.this, R.color.greenish_grey))
                .setSelectedMenuColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .build();

    }

    private final OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener = new OnMenuItemClickListener<PowerMenuItem>() {
        @Override
        public void onItemClick(int position, PowerMenuItem item) {
            switch (position) {
                case LEAF:
                    ROLE = Global.leafRef;
                    binding.selectRoleCvTv.setText(Global.leafRef);
                    break;
                case MEDICAL:
                    ROLE = Global.medicalRef;
                    binding.selectRoleCvTv.setText(Global.medicalRef);
                    break;
                case ACCOUNTANT:
                    ROLE = Global.accountantRef;
                    binding.selectRoleCvTv.setText(Global.accountantRef);
                    break;

            }
            powerMenu.setSelectedPosition(position);
            powerMenu.dismiss();
        }
    };

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));

    }

    private void showOptionDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterOthersActivity.this);
        builder.setMessage("Do you want to add another ?");
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            startActivity(new Intent(RegisterOthersActivity.this, AdminHomeActivity.class));
            finish();
        }).setPositiveButton("Yes", (dialogInterface, i) -> {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterOthersActivity.this, AdminHomeActivity.class));
        finish();
    }

}