package com.main.rekordsnew.Admin.AddChallan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.main.rekordsnew.Admin.AdminHomeActivity;
import com.main.rekordsnew.Client.POJO.Challan;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.ActivityPdfviewerBinding;

import java.io.File;
import java.io.IOException;

public class PDFViewerActivity extends AppCompatActivity {

    private ActivityPdfviewerBinding binding;
    private Challan challan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfviewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        challan = (Challan) getIntent().getSerializableExtra("Challan");
        try {
            loadFileFromFirebase();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(PDFViewerActivity.this, "Failed to load pdf", Toast.LENGTH_SHORT).show();
        }
        initButtons();
    }

    private void initButtons() {
        binding.downloadPdfBtn.setOnClickListener(view -> {
            DownloadPDF(challan);
        });

        binding.donePdfViewBtn.setOnClickListener(view -> {
            startActivity(new Intent(PDFViewerActivity.this, AdminHomeActivity.class));
            finish();
        });
    }

    private void loadFileFromFirebase() throws IOException {

        File localFile = File.createTempFile("invoice", "pdf");
        StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(challan.getPdfUrl());
        httpsReference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
            if (taskSnapshot.getBytesTransferred() == taskSnapshot.getTotalByteCount()){
                binding.pdfView.fromFile(localFile).load();
                localFile.deleteOnExit();
            }else {
                Toast.makeText(PDFViewerActivity.this, "Invoice could not be loaded", Toast.LENGTH_SHORT).show();
            }

        }).addOnFailureListener(e -> {
            Log.e("LOAD ERROR",e.toString());
        });
    }


    private void DownloadPDF(Challan ch) {

        String drs = Environment.getExternalStorageDirectory().getAbsolutePath();
        ProgressDialog progressDialog = new ProgressDialog(PDFViewerActivity.this);
        File directory = new File(drs + File.separator + "Invoices");
        if (!directory.exists())
            directory.mkdir();
        File file = new File(directory,ch.getChallanNo()+".pdf");

        StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(ch.getPdfUrl());
        httpsReference.getFile(file).addOnSuccessListener(taskSnapshot -> {
            progressDialog.dismiss();
            showDownloadedDialog(file);
        }).addOnFailureListener(e -> {
            showErrorDialog();
        }).addOnProgressListener(snapshot -> {
            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
            progressDialog.setProgress((int)progress);
            progressDialog.show();

        });
    }

    private void showErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PDFViewerActivity.this);
        builder.setTitle("Download Failed");
        builder.setMessage("Failed to download");
        builder.setNegativeButton("OK", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDownloadedDialog(File file) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PDFViewerActivity.this);
        builder.setTitle("Bill Downloaded successfully");
        builder.setMessage("Do you want to see the downloaded file");
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        }).setPositiveButton("View Folder", (dialogInterface, i) -> {
            // location = "/sdcard/my_folder";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri mydir = FileProvider.getUriForFile(PDFViewerActivity.this, getApplicationContext().getPackageName() + ".provider", file);
            intent.setDataAndType(mydir,"application/*");
            // or use */*
            startActivity(intent);
            dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}