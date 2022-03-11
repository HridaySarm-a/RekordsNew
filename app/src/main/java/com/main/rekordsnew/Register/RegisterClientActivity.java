package com.main.rekordsnew.Register;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.webkit.MimeTypeMap;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.main.rekordsnew.Admin.AdminHomeActivity;
import com.main.rekordsnew.Client.ClientHomeActivity;
import com.main.rekordsnew.Client.POJO.BankAccount;
import com.main.rekordsnew.Client.POJO.ClientRoot;
import com.main.rekordsnew.Client.POJO.GardenDetails;
import com.main.rekordsnew.Client.POJO.PermanentAddress;
import com.main.rekordsnew.Client.POJO.PersonalDetails;
import com.main.rekordsnew.Client.POJO.TeaBoardDetails;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.ActivityRegisterClientBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class RegisterClientActivity extends AppCompatActivity {

    ActivityRegisterClientBinding binding;
    int[] location = new int[2];
    private static final String TAG = "RegisterClientActivity";

    // Data Model //
    PersonalDetails personalDetails = new PersonalDetails();
    BankAccount bankAccount = new BankAccount();
    GardenDetails gardenDetails = new GardenDetails();
    PermanentAddress permanentAddress = new PermanentAddress();
    TeaBoardDetails teaBoardDetails = new TeaBoardDetails();
    StorageReference storageReference;
    DatabaseReference mClientRef;

    private Uri imageUri;
    private ProgressDialog progressDialog;
    private Calendar calendar;
    // Data Model //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterClientBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.registerClientToolbar);
    }


    @Override
    protected void onStart() {
        super.onStart();
        initFirebase();
        initButtons();
    }

    private void initFirebase() {

        storageReference = FirebaseStorage.getInstance().getReference("Client Images");
        mClientRef = FirebaseDatabase.getInstance().getReference("Clients");

        progressDialog = new ProgressDialog(RegisterClientActivity.this);
    }

    private void initButtons() {
        calendar = Calendar.getInstance();
        binding.addImageBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/");
            imageResultLauncher.launch(intent);
        });

        binding.uploadClientButton.setOnClickListener(view -> {
            if (imageUri != null) {
                progressDialog.show();
                progressDialog.show();
                StorageReference filereference = storageReference.child(System.currentTimeMillis() +
                        "." + getFileExtension(imageUri));
                filereference.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            filereference.getDownloadUrl().addOnSuccessListener(uri -> {
                                ClientRoot clientRoot = getDataModel(uri.toString());
                                String uniqueID = UUID.randomUUID().toString();
                                clientRoot.setKey(uniqueID);
                                mClientRef.child(uniqueID)
                                        .setValue(clientRoot)
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                progressDialog.dismiss();
                                                Toast.makeText(RegisterClientActivity.this, "Client added successfully", Toast.LENGTH_SHORT).show();
                                                showAnotherDialog();
                                            }
                                        }).addOnFailureListener(e -> {
                                    Toast.makeText(RegisterClientActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                            });
                        }).addOnFailureListener(e -> Log.d("Error", e.getMessage())).addOnProgressListener(snapshot -> {
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setMessage("Uploading  " + (int) progress + "%");
                });

            } else {
                progressDialog.setMessage("Uploading client");
                progressDialog.show();
                ClientRoot clientRoot = getDataModel("No Image");
                Log.d(TAG, clientRoot.toString());
                String uniqueID = UUID.randomUUID().toString();
                clientRoot.setKey(uniqueID);
                mClientRef.child(uniqueID)
                        .setValue(clientRoot)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterClientActivity.this, "Client added successfully", Toast.LENGTH_SHORT).show();
                                showAnotherDialog();
                            }
                        }).addOnFailureListener(e -> {
                    Toast.makeText(RegisterClientActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

        DatePickerDialog.OnDateSetListener date = (datePicker, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        binding.openCalenderBtn
                .setOnClickListener(view -> {
                    new DatePickerDialog(RegisterClientActivity.this, date, calendar
                            .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                });
    }

    private void showAnotherDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterClientActivity.this);
        builder.setMessage("Do you want to add another ?");
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            startActivity(new Intent(RegisterClientActivity.this, AdminHomeActivity.class));
            finish();
        }).setPositiveButton("Yes", (dialogInterface, i) -> {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        TextView dateTv = binding.dateOfBirthTv;
        dateTv.setText(sdf.format(calendar.getTime()));
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));

    }


    ActivityResultLauncher<Intent> imageResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        binding.addClientImg.setImageURI(data.getData());
                        imageUri = data.getData();
                    }
                }
            });

    private ClientRoot getDataModel(String imageUrl) {

        ClientRoot clientRoot = new ClientRoot();

        /**
         * PERSONAL DETAILS
         */

        TextInputEditText name, foh_name, sex, cast, eduq, phone, password, email;
        name = binding.cpdName;
        foh_name = binding.cpdFohName;
        sex = binding.cpdSex;
        cast = binding.cpdCast;
        eduq = binding.cpdEduQual;
        phone = binding.cpdPhone;
        password = binding.cpdPassword;
        email = binding.cpdEmail;

        personalDetails.setName(name.getText().toString());
        personalDetails.setFahName(foh_name.getText().toString());
        personalDetails.setSex(sex.getText().toString());
        personalDetails.setCast(cast.getText().toString());
        personalDetails.setEducationalQualification(eduq.getText().toString());
        personalDetails.setPhone(phone.getText().toString());
        personalDetails.setPassword(password.getText().toString());
        personalDetails.setEmail(email.getText().toString());
        // Permanent Address //

        TextInputEditText village, po, subdiv, ps, dist, pincode, mouza;

        village = binding.cpdVillage;
        po = binding.cpdPO;
        subdiv = binding.cpdSubDivision;
        ps = binding.cpdPoliceStation;
        dist = binding.cpdDistrict;
        pincode = binding.cpdPincode;
        mouza = binding.cpdMouza;

        permanentAddress.setVillage(village.getText().toString());
        permanentAddress.setPostOffice(po.getText().toString());
        permanentAddress.setSubDivision(subdiv.getText().toString());
        permanentAddress.setPoliceStation(ps.getText().toString());
        permanentAddress.setDistrict(dist.getText().toString());
        permanentAddress.setPincode(pincode.getText().toString());
        permanentAddress.setMouza(mouza.getText().toString());

        // Permanent Address //


        /**
         * PERSONAL DETAILS
         */

        /**
         * Tea Board ID Details
         */

        TextInputEditText gardenName, qrCardNo, totalArea, pattaNo, dagNo, villageCti, poCti, subDivCti, mouzaCti, districtCti, pincodeCti;

        gardenName = binding.ctbidGardenName;
        qrCardNo = binding.ctbidQrCardNo;
        totalArea = binding.ctbidArea;
        pattaNo = binding.ctbidPattaNo;
        dagNo = binding.ctbidDagNo;
        villageCti = binding.ctbidVillage;
        poCti = binding.ctbidPO;
        subDivCti = binding.ctbidSubDivision;
        mouzaCti = binding.ctbidMouza;
        districtCti = binding.ctbidDistrict;
        pincodeCti = binding.ctbidPincode;

        teaBoardDetails.setGardenName(gardenName.getText().toString());
        teaBoardDetails.setQrCardNo(qrCardNo.getText().toString());
        teaBoardDetails.setTotalArea(totalArea.getText().toString());
        teaBoardDetails.setPattaNo(pattaNo.getText().toString());
        teaBoardDetails.setDagNo(dagNo.getText().toString());
        PermanentAddress teaBoardAddress = new PermanentAddress();
        teaBoardAddress.setVillage(villageCti.getText().toString());
        teaBoardAddress.setPostOffice(poCti.getText().toString());
        teaBoardAddress.setSubDivision(subDivCti.getText().toString());
        teaBoardAddress.setMouza(mouzaCti.getText().toString());
        teaBoardAddress.setDistrict(districtCti.getText().toString());
        teaBoardAddress.setPincode(pincodeCti.getText().toString());

        teaBoardDetails.setPermanentAddress(teaBoardAddress);

        /**
         * Tea Board ID Details
         */


        /**
         * Garden Details
         */

        TextInputEditText nameOfGarden, yearOfEstablishment, ifcopoDetails;
        CheckBox proprietorial, partnership, co_operative;

        nameOfGarden = binding.cgdGardenName;
        yearOfEstablishment = binding.cgdYearOfEst;
        ifcopoDetails = binding.cgdPaCoDetails;

        String constitute = "";

        proprietorial = binding.cgdProprietorial;
        partnership = binding.cgdPartnership;
        co_operative = binding.cgdCoOperative;

        if (proprietorial.isChecked()) {
            constitute = "Proprietorial";
            partnership.setChecked(false);
            co_operative.setChecked(false);
        } else if (partnership.isChecked()) {
            constitute = "Partnership";
            proprietorial.setChecked(false);
            co_operative.setChecked(false);
        } else if (partnership.isChecked()) {
            constitute = "Co-Operative";
            partnership.setChecked(false);
            proprietorial.setChecked(false);
        } else {
            constitute = "Not Registered";
        }

        gardenDetails.setGardenName(nameOfGarden.getText().toString());
        gardenDetails.setYearOfEst(yearOfEstablishment.getText().toString());
        gardenDetails.setPncDetails(ifcopoDetails.getText().toString());
        gardenDetails.setConstitution(constitute);


        /**
         * Garden Details
         */

        /**
         * Bank Account
         */

        TextInputEditText acNo, branchName, nameOfBank, ifsc;

        acNo = binding.cbdAcNo;
        branchName = binding.cbdBranch;
        nameOfBank = binding.cbdNameOfBank;
        ifsc = binding.cbdIfscCode;

        bankAccount.setAccountNo(acNo.getText().toString());
        bankAccount.setBranch(branchName.getText().toString());
        bankAccount.setBankName(nameOfBank.getText().toString());
        bankAccount.setIfscCode(ifsc.getText().toString());

        /**
         * Bank Account
         */


        /********************************************************************************
         *  Check Data
         ********************************************************************************/

        personalDetails.setPermanentAddress(permanentAddress);
        personalDetails.setBankAccount(bankAccount);


        clientRoot.setGardenDetails(gardenDetails);
        clientRoot.setPersonalDetails(personalDetails);
        clientRoot.setTeaBoardDetails(teaBoardDetails);

        /********************************************************************************
         *  Check Data
         ********************************************************************************/

        return clientRoot;
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterClientActivity.this, AdminHomeActivity.class));
        finish();
    }
}

