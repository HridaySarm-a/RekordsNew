package com.main.rekordsnew.Edit_Update;

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
import android.webkit.MimeTypeMap;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.main.rekordsnew.Admin.AdminHomeActivity;
import com.main.rekordsnew.Client.POJO.BankAccount;
import com.main.rekordsnew.Client.POJO.ClientRoot;
import com.main.rekordsnew.Client.POJO.GardenDetails;
import com.main.rekordsnew.Client.POJO.PermanentAddress;
import com.main.rekordsnew.Client.POJO.PersonalDetails;
import com.main.rekordsnew.Client.POJO.TeaBoardDetails;
import com.main.rekordsnew.R;
import com.main.rekordsnew.Register.RegisterClientActivity;
import com.main.rekordsnew.databinding.ActivityEditClientBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class EditClientActivity extends AppCompatActivity {

    ActivityEditClientBinding binding;
    ClientRoot data;
    int[] location = new int[2];
    private static final String TAG = "EditClientActivity";

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
        binding = ActivityEditClientBinding.inflate(getLayoutInflater());
        data = (ClientRoot) getIntent().getSerializableExtra("ClientDetails");
        initView(data);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.editClientToolbar);
    }

    private void initView(ClientRoot data) {
        if (!data.getPersonalDetails().getImage().equals("")) {
            Glide.with(this).load(data.getPersonalDetails().getImage()).into(binding.editClientImg);
        }
        binding.cpdEditName.setText(data.getPersonalDetails().getName());
        binding.cpdEditFohName.setText(data.getPersonalDetails().getFahName());
        binding.cpdEditSex.setText(data.getPersonalDetails().getSex());
        binding.dateOfBirthTv.setText(data.getPersonalDetails().getDob());
        binding.cpdEditCast.setText(data.getPersonalDetails().getCast());
        binding.cpdEditEduQual.setText(data.getPersonalDetails().getEducationalQualification());
        binding.cpdEditPhone.setText(data.getPersonalDetails().getPhone());
        binding.cpdEditPassword.setText(data.getPersonalDetails().getPassword());
        binding.cpdEditEmail.setText(data.getPersonalDetails().getEmail());

        binding.cpdEditVillage.setText(data.getPersonalDetails().getPermanentAddress().getVillage());
        binding.cpdEditPO.setText(data.getPersonalDetails().getPermanentAddress().getPostOffice());
        binding.cpdEditSubDivision.setText(data.getPersonalDetails().getPermanentAddress().getSubDivision());
        binding.cpdEditMouza.setText(data.getPersonalDetails().getPermanentAddress().getMouza());
        binding.cpdEditDistrict.setText(data.getPersonalDetails().getPermanentAddress().getDistrict());
        binding.cpdEditPoliceStation.setText(data.getPersonalDetails().getPermanentAddress().getPoliceStation());
        binding.cpdEditPincode.setText(data.getPersonalDetails().getPermanentAddress().getPincode());


        binding.ctbidEditGardenName.setText(data.getTeaBoardDetails().getGardenName());
        binding.ctbidEditQrCardNo.setText(data.getTeaBoardDetails().getQrCardNo());
        binding.ctbidEditArea.setText(data.getTeaBoardDetails().getTotalArea());
        binding.ctbidEditPattaNo.setText(data.getTeaBoardDetails().getPattaNo());
        binding.ctbidEditDagNo.setText(data.getTeaBoardDetails().getDagNo());
        binding.ctbidEditVillage.setText(data.getTeaBoardDetails().getPermanentAddress().getVillage());
        binding.ctbidEditPO.setText(data.getTeaBoardDetails().getPermanentAddress().getPostOffice());
        binding.ctbidEditSubDivision.setText(data.getTeaBoardDetails().getPermanentAddress().getSubDivision());
        binding.ctbidEditMouza.setText(data.getTeaBoardDetails().getPermanentAddress().getMouza());
        binding.ctbidEditDistrict.setText(data.getTeaBoardDetails().getPermanentAddress().getDistrict());
        binding.ctbidEditPincode.setText(data.getTeaBoardDetails().getPermanentAddress().getPincode());

        binding.cgdEditGardenName.setText(data.getGardenDetails().getGardenName());
        binding.cgdEditYearOfEst.setText(data.getGardenDetails().getYearOfEst());
        binding.cgdEditPaCoDetails.setText(data.getGardenDetails().getPncDetails());

        binding.cbdEditNameOfBank.setText(data.getPersonalDetails().getBankAccount().getBankName());
        binding.cbdEditBranch.setText(data.getPersonalDetails().getBankAccount().getBranch());
        binding.cbdEditIfscCode.setText(data.getPersonalDetails().getBankAccount().getIfscCode());
        binding.cbdEditAcNo.setText(data.getPersonalDetails().getBankAccount().getAccountNo());

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

        progressDialog = new ProgressDialog(EditClientActivity.this);
    }

    private void initButtons() {
        calendar = Calendar.getInstance();
        binding.editImageBtn.setOnClickListener(view -> {
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
                                                Toast.makeText(EditClientActivity.this, "Client added successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(EditClientActivity.this, AdminHomeActivity.class));
                                                finish();
                                            }
                                        }).addOnFailureListener(e -> {
                                    Toast.makeText(EditClientActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(EditClientActivity.this, "Client Updated Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditClientActivity.this, AdminHomeActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(e -> {
                    Toast.makeText(EditClientActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    new DatePickerDialog(EditClientActivity.this, date, calendar
                            .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                });
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
                        binding.editImageBtn.setImageURI(data.getData());
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
        name = binding.cpdEditName;
        foh_name = binding.cpdEditFohName;
        sex = binding.cpdEditSex;
        cast = binding.cpdEditCast;
        eduq = binding.cpdEditEduQual;
        phone = binding.cpdEditPhone;
        password = binding.cpdEditPassword;
        email = binding.cpdEditEmail;

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

        village = binding.cpdEditVillage;
        po = binding.cpdEditPO;
        subdiv = binding.cpdEditSubDivision;
        ps = binding.cpdEditPoliceStation;
        dist = binding.cpdEditDistrict;
        pincode = binding.cpdEditPincode;
        mouza = binding.cpdEditMouza;

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

        gardenName = binding.ctbidEditGardenName;
        qrCardNo = binding.ctbidEditQrCardNo;
        totalArea = binding.ctbidEditArea;
        pattaNo = binding.ctbidEditPattaNo;
        dagNo = binding.ctbidEditDagNo;
        villageCti = binding.ctbidEditVillage;
        poCti = binding.ctbidEditPO;
        subDivCti = binding.ctbidEditSubDivision;
        mouzaCti = binding.ctbidEditMouza;
        districtCti = binding.ctbidEditDistrict;
        pincodeCti = binding.ctbidEditPincode;

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

        nameOfGarden = binding.cgdEditGardenName;
        yearOfEstablishment = binding.cgdEditYearOfEst;
        ifcopoDetails = binding.cgdEditPaCoDetails;

        String constitute = "";

        proprietorial = binding.cgdEditProprietorial;
        partnership = binding.cgdEditPartnership;
        co_operative = binding.cgdEditCoOperative;

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

        acNo = binding.cbdEditAcNo;
        branchName = binding.cbdEditBranch;
        nameOfBank = binding.cbdEditNameOfBank;
        ifsc = binding.cbdEditIfscCode;

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
        startActivity(new Intent(EditClientActivity.this, AdminHomeActivity.class));
        finish();

    }
}