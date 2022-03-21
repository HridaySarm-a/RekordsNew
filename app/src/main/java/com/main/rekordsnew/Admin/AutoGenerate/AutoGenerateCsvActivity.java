package com.main.rekordsnew.Admin.AutoGenerate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.main.rekordsnew.Adapters.ChallanTableAdapter;
import com.main.rekordsnew.Admin.AddChallan.GenerateChallanActivity;
import com.main.rekordsnew.Admin.AddChallan.PDFViewerActivity;
import com.main.rekordsnew.Client.POJO.Challan;
import com.main.rekordsnew.Client.POJO.Entry;
import com.main.rekordsnew.Objects.CsvFileDataSourceImpl;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.Others.OtherModel;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.ActivityAutoGenerateCsvBinding;
import com.main.rekordsnew.databinding.ActivityGenerateChallanBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import au.com.bytecode.opencsv.CSVWriter;
import me.kariot.invoicegenerator.data.ModelInvoiceFooter;
import me.kariot.invoicegenerator.data.ModelInvoiceHeader;
import me.kariot.invoicegenerator.data.ModelInvoiceInfo;
import me.kariot.invoicegenerator.data.ModelInvoiceItem;
import me.kariot.invoicegenerator.data.ModelInvoicePriceInfo;
import me.kariot.invoicegenerator.data.ModelTableHeader;
import me.kariot.invoicegenerator.utils.InvoiceGenerator;

public class AutoGenerateCsvActivity extends AppCompatActivity {

    ActivityAutoGenerateCsvBinding binding;
    Challan challan;
    CsvFileDataSourceImpl csvFileDataSource;
    FirebaseStorage storage;
    FirebaseStorage storage2;
    StorageReference storageReference;
    StorageReference storageReference2;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAutoGenerateCsvBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initButtons();
        initFirebase();
    }
    private void initFirebase() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        storage2 = FirebaseStorage.getInstance();
        storageReference2 = storage2.getReference();
        progressDialog = new ProgressDialog(AutoGenerateCsvActivity.this);
        progressDialog.setMessage("Uploading...");

    }

    private void initButtons() {
        binding.submitAndSendBtn.setOnClickListener(view -> {
            progressDialog.show();
            createPDF(challan);
        });
    }

    private void createPDF(Challan challan) {
        ModelInvoiceHeader.ModelAddress modelAddress = new ModelInvoiceHeader.ModelAddress(
                "Mazbat","Dist-Udalguri","P.O-Mazbat"
        );
        ModelInvoiceHeader modelInvoiceHeader = new ModelInvoiceHeader(
                "1234567890","johndoe@gmail.com","www.testweb.com",modelAddress
        );

        ModelInvoiceInfo.ModelCustomerInfo modelCustomerInfo = new ModelInvoiceInfo.ModelCustomerInfo(
                Global.SELECTED_CLIENT.getPersonalDetails().getName(),Global.SELECTED_CLIENT.getTeaBoardDetails().getPermanentAddress().getVillage(),
                Global.SELECTED_CLIENT.getTeaBoardDetails().getPermanentAddress().getDistrict(),Global.SELECTED_CLIENT.getTeaBoardDetails().getPermanentAddress().getPincode()
        );


        ModelInvoiceInfo modelInvoiceInfo = new ModelInvoiceInfo(
                modelCustomerInfo,
                "65464",
                "21/05/2021",
                "9000"
        );


        ModelTableHeader modelTableHeader = new ModelTableHeader(
                "Description","Quantity","DIS. Amount","VAT%","New Amount"
        );



        List<ModelInvoiceItem> items = new ArrayList<>();
        ModelInvoiceItem  modelInvoiceItem = new ModelInvoiceItem(
                "1","Challan","Challan No. 3222","21/05/2021","556","60000"
        );
        items.add(modelInvoiceItem);

        ModelInvoicePriceInfo modelInvoicePriceInfo = new ModelInvoicePriceInfo(
                "60000","0","60000"
        );


        ModelInvoiceFooter modelInvoiceFooter = new ModelInvoiceFooter("Thank you for your business");

        InvoiceGenerator pdfGenerator = new InvoiceGenerator(AutoGenerateCsvActivity.this);
        pdfGenerator.setInvoiceLogo(R.drawable.logo_black);
        pdfGenerator.setCurrency("Rs");
        pdfGenerator.setInvoiceColor("#0000FF");

        pdfGenerator.setInvoiceHeaderData(modelInvoiceHeader);
        pdfGenerator.setInvoiceInfo(modelInvoiceInfo);
        pdfGenerator.setInvoiceTableHeaderDataSource(modelTableHeader);
        pdfGenerator.setInvoiceTableData(items);
        pdfGenerator.setPriceInfoData(modelInvoicePriceInfo);
        pdfGenerator.setInvoiceFooterData(modelInvoiceFooter);


        Uri pdfUri = pdfGenerator.generatePDF("bill.pdf");
        uploadPDF(challan,pdfUri);
    }

    private void uploadPDF(Challan ch, Uri pdfUri) {
        final StorageReference riversRef = storageReference.child("Invoices/"+Global.SELECTED_CLIENT.getPersonalDetails().getName()+"/" + UUID.randomUUID().toString());
        riversRef.putFile(pdfUri);
        UploadTask uploadTask = riversRef.putFile(pdfUri);
        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                progressDialog.dismiss();
                throw task.getException();

            }
            // Continue with the task to get the download URL
            return riversRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                ch.setPdfUrl(downloadUri.toString());
                UploadChallanToDatabase(ch);
            } else {
                progressDialog.dismiss();
                Toast.makeText(AutoGenerateCsvActivity.this, "Failed to upload pdf", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void UploadChallanToDatabase(Challan ch) {
        FirebaseDatabase.getInstance()
                .getReference(Global.clientRef)
                .child(Global.SELECTED_CLIENT.getKey())
                .child(Global.challansRef)
                .child(String.valueOf(ch.getChallanNo()))
                .setValue(ch)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        uploadChallantoClients(ch);
                    }
                }).addOnFailureListener(e -> {
            Log.e("ERROR",e.getMessage());
        });
    }

    private void uploadChallantoClients(Challan ch1) {
        for (int i = 0;i<ch1.getEntries().size();i++){
            updateLeafCollectors(ch1.getEntries().get(i),ch1.getCollectors().get(i));
        }
        progressDialog.dismiss();
        Toast.makeText(AutoGenerateCsvActivity.this, "Challan uploaded successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AutoGenerateCsvActivity.this, PDFViewerActivity.class);
        intent.putExtra("Challan",ch1);
        startActivity(intent);
    }

    private void updateLeafCollectors(Entry entry, OtherModel otherModel) {
        FirebaseDatabase.getInstance().getReference(Global.leafRef)
                .child(otherModel.getKey())
                .child("Entries")
                .child(String.valueOf(UUID.randomUUID()))
                .setValue(entry)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Log.d("UPLOAD","Uploaded "+otherModel.getName());
                    }
                }).addOnFailureListener(e -> {
            Log.e("ERR",e.getMessage());
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        String filePath = initCSV().getAbsolutePath();
        Uri fileUri = Uri.parse(filePath);
        csvFileDataSource  = new CsvFileDataSourceImpl(AutoGenerateCsvActivity.this,fileUri);
        binding.tableLayoutGenerateChallan.setAdapter(new ChallanTableAdapter(AutoGenerateCsvActivity.this,csvFileDataSource));

        try {
            uploadCSV(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void uploadCSV(String filePath) throws FileNotFoundException {
        progressDialog.setMessage("Uploading CSV...");
        progressDialog.show();
        final StorageReference riversRef = storageReference2.child("CSV Files/"+Global.SELECTED_CLIENT.getPersonalDetails().getName()+"/" + UUID.randomUUID().toString());
        InputStream stream = new FileInputStream(filePath);
        UploadTask uploadTask = riversRef.putStream(stream);
        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                progressDialog.dismiss();
                Log.e("CSV ERROR",task.getException().toString());

            }
            // Continue with the task to get the download URL
            return riversRef.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                challan.setCsvUrl(downloadUri.toString());
                progressDialog.dismiss();
            } else {
                progressDialog.dismiss();
                Toast.makeText(AutoGenerateCsvActivity.this, "Failed to upload csv", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private File initCSV() {

        String drs = Environment.getExternalStorageDirectory().getAbsolutePath();

        File directory = new File(drs + File.separator + "Challans");
        if (!directory.exists())
            directory.mkdir();

        File file = new File(directory,UUID.randomUUID()+".csv");

        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);
            List<String[]> data = new ArrayList<>();
            data.add(new String[] { "Sl. No.","Name of the Grower", "Local", "Percent(%)","Minus","Net","Rate","Amount","Comm.","Carrying","Cess","Misc.","Total","Net Amount" });
            for (int i=0;i<challan.getEntries().size();i++){
                data.add(new String[] { String.valueOf(i+1),challan.getCollectors().get(i).getName(), ""+tempList.get(i).getLocal(),""+challan.getPercent(),
                        ""+tempList.get(i).getMinus(),""+tempList.get(i).getNet(),"Rs "+challan.getRate(),"Rs "+tempList.get(i).getAmount(),
                        "Rs "+tempList.get(i).getComm(),"Rs "+tempList.get(i).getCarrying(),"Rs "+tempList.get(i).getCess(),"Rs "+tempList.get(i).getMisc(),
                        "Rs "+tempList.get(i).getTotal(),"Rs "+tempList.get(i).getNetAmount()
                });
            }

            writer.writeAll(data);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file;
    }
}