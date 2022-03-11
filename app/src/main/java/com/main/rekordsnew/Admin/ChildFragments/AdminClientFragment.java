package com.main.rekordsnew.Admin.ChildFragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.main.rekordsnew.Adapters.ClientsAdapter;
import com.main.rekordsnew.Client.POJO.ClientRoot;
import com.main.rekordsnew.EventBus.CallClientClicked;
import com.main.rekordsnew.EventBus.DeleteClientClicked;
import com.main.rekordsnew.Interface.ICurrentFragment;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.Register.RegisterClientActivity;
import com.main.rekordsnew.ViewModels.IClientsViewModel;
import com.main.rekordsnew.databinding.FragmentAdminClientBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AdminClientFragment extends Fragment {

    FragmentAdminClientBinding binding;
    IClientsViewModel clientsViewModel;
    Intent callIntent;
    ICurrentFragment currentFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminClientBinding.inflate(getLayoutInflater());
        initButtons();
        clientsViewModel = ViewModelProviders.of(this).get(IClientsViewModel.class);
        initRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    private void initRecyclerView() {
        clientsViewModel.getMutableLiveDataClients().observe(getViewLifecycleOwner(), clientRoots -> {
            binding.adminClientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.adminClientsRecyclerView.setAdapter(new ClientsAdapter(getContext(),clientRoots));
        });
    }


    private void initButtons() {
        binding.addClientBtn.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), RegisterClientActivity.class));
            requireActivity().finish();
        });
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onDeleteClicked(DeleteClientClicked event){
        if (event.isClicked()){
            deleteClient(event.getClientRoot());
            EventBus.getDefault().removeAllStickyEvents();
        }
    }

    private void deleteClient(ClientRoot root) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Deleting please wait...");
        progressDialog.show();
        FirebaseDatabase.getInstance().getReference(Global.clientRef)
                .child(root.getKey())
                .removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        clientsViewModel.LoadClients();
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Client deleted successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
        });
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onCallClicked(CallClientClicked event){
        if (event.isClicked()){
            callClient(event.getNumber());
            EventBus.getDefault().removeAllStickyEvents();
        }
    }

    private void callClient(String number) {

        callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        callIntent.setData(Uri.parse("tel:91" + number));    //this is the phone number calling

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE);
        }else {
            getActivity().startActivity(callIntent);
        }

    }

    ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result){
                        getActivity().startActivity(callIntent);
                    }else {
                        Toast.makeText(getContext(), "Accept permissions to continue", Toast.LENGTH_SHORT).show();
                    }
                }
            });


}