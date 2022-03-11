package com.main.rekordsnew.ProfileSwithing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.main.rekordsnew.Objects.Global;
import com.main.rekordsnew.R;
import com.main.rekordsnew.databinding.FragmentChangePasswordBottomSheetDialogBinding;


public class ChangePasswordBottomSheetDialogFragment extends BottomSheetDialogFragment {

    FragmentChangePasswordBottomSheetDialogBinding binding;

    String type;

    public ChangePasswordBottomSheetDialogFragment(String type) {
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangePasswordBottomSheetDialogBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        initButtons();
    }

    private void initButtons() {
        binding.updatePasswordBtn.setOnClickListener(view -> {
            if (binding.confirmPassEdt.getText().toString().equals(binding.enterPassEdt.getText().toString())){
                if (type.equals("Admin")){
                    changeAdminPassword();
                }else if (type.equals("Client")){
                    changeClientPassword();
                }else if (type.equals("Accountant")){
                    changeAccountantPassword();
                }else if (type.equals("Leaf")){
                    changeLeafPassword();
                }else {
                    changeMedicalPassword();
                }
            }else {
                Snackbar.make(view,"Passwords do not match",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void changeMedicalPassword() {
        FirebaseDatabase.getInstance().getReference(Global.medicalRef)
                .child(Global.CURRENT_OTHER_USER.getKey())
                .child("password")
                .setValue(binding.confirmPassEdt.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
        });
    }

    private void changeLeafPassword() {
        FirebaseDatabase.getInstance().getReference(Global.leafRef)
                .child(Global.CURRENT_OTHER_USER.getKey())
                .child("password")
                .setValue(binding.confirmPassEdt.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
        });
    }

    private void changeAccountantPassword() {
        FirebaseDatabase.getInstance().getReference(Global.accountantRef)
                .child(Global.CURRENT_OTHER_USER.getKey())
                .child("password")
                .setValue(binding.confirmPassEdt.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
        });
    }

    private void changeClientPassword() {
        FirebaseDatabase.getInstance().getReference(Global.clientRef)
                .child(Global.CURRENT_OTHER_USER.getKey())
                .child("personalDetails")
                .child("password")
                .setValue(binding.confirmPassEdt.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
        });
    }


    public void changeAdminPassword(){
        FirebaseDatabase.getInstance().getReference("AdminCreds")
                .child("password")
                .setValue(binding.confirmPassEdt.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
        });
    }

}