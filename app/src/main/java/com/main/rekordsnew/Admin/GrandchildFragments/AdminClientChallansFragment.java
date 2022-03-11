package com.main.rekordsnew.Admin.GrandchildFragments;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.main.rekordsnew.Adapters.ChallanAdapter;
import com.main.rekordsnew.Admin.AddChallan.AddChallanActivity;
import com.main.rekordsnew.Client.POJO.Challan;
import com.main.rekordsnew.Client.POJO.ClientRoot;
import com.main.rekordsnew.EventBus.DownloadCSVClicked;
import com.main.rekordsnew.EventBus.DownloadPDFClicked;
import com.main.rekordsnew.Interface.ICurrentFragment;
import com.main.rekordsnew.databinding.FragmentAdminClientChallansBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdminClientChallansFragment extends Fragment {

    FragmentAdminClientChallansBinding binding;
    ClientRoot client;
    private FirebaseStorage storage;
    private ChallanAdapter challanAdapter;

    public AdminClientChallansFragment(ClientRoot client) {
        this.client = client;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminClientChallansBinding.inflate(getLayoutInflater());
        storage = FirebaseStorage.getInstance();
        ICurrentFragment currentFragment = (ICurrentFragment) getContext();
        if (currentFragment != null) {
            currentFragment.currentFragment("ADMIN_CLIENT_CHALLAN");
        }
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        initButtons();
        initChallans();
    }

    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    private void initChallans() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        List<Challan> challanList = new ArrayList<>();
        if (client.getChallans() != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                client.getChallans().forEach((key,value)-> challanList.add(value));
            }
        }

        challanAdapter = new ChallanAdapter(getContext(),challanList);
        binding.faccChallansRv.setLayoutManager(linearLayoutManager);
        binding.faccChallansRv.setAdapter(challanAdapter);
    }

    private void initButtons() {
        binding.faccAddNewChallanBtn.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), AddChallanActivity.class));
            requireActivity().finish();
        });
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onDownloadCSVClicked(DownloadCSVClicked event){
        if (event.isSuccess()){
            DownloadCSV(event.getChallan());
            EventBus.getDefault().removeStickyEvent(DownloadCSVClicked.class);
        }
    }

    private void DownloadCSV(Challan ch) {
        DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(ch.getCsvUrl());
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        long reference = downloadManager.enqueue(request);
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onDownloadPDFClicked(DownloadPDFClicked event){
        if (event.isSuccess()){
            DownloadPDF(event.getChallan());
//            EventBus.getDefault().removeStickyEvent(DownloadCSVClicked.class);
        }
    }

    private void DownloadPDF(Challan ch) {

        String drs = Environment.getExternalStorageDirectory().getAbsolutePath();
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        File directory = new File(drs + File.separator + "Invoices");
        if (!directory.exists())
            directory.mkdir();
        File file = new File(directory,ch.getChallanNo()+".pdf");

        StorageReference httpsReference = storage.getReferenceFromUrl(ch.getPdfUrl());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Download Failed");
        builder.setMessage("Failed to download");
        builder.setNegativeButton("OK", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDownloadedDialog(File file) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Bill Downloaded successfully");
        builder.setMessage("Do you want to see the downloaded file");
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        }).setPositiveButton("View Folder", (dialogInterface, i) -> {
            // location = "/sdcard/my_folder";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri mydir = FileProvider.getUriForFile(getContext(), getActivity().getApplicationContext().getPackageName() + ".provider", file);
            intent.setDataAndType(mydir,"application/*");
            // or use */*
            startActivity(intent);
            dialogInterface.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}